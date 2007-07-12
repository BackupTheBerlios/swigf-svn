/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package de.swigf.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.swigf.scene.Scene;

public class ConnectorElement extends AbstractGraphElement {
	private List<Point> points;
	private int selectedIndex;
	private static final int selectionWidth = 10;
	private Color foreground = Color.BLACK;
	private static final int accuracy = selectionWidth;

	public ConnectorElement(Scene scene, Point pt1, Point pt2) {
		super(scene);
		points = new ArrayList<Point>();
		points.add(pt1);
		points.add(pt2);
		setBounds(new Rectangle(pt1.x, pt1.y, 5, 5));
	}

	@Override
	public boolean contains(int x, int y) {
		boolean selected = false;
		Iterator<Point> it = points.iterator();
		Point pt1 = it.next();
		while (it.hasNext()) {
			Point pt2 = it.next();
			// check hit for horizontal line
			if (pt1.y == pt2.y && x >= Math.min(pt1.x, pt2.x) && x <= Math.max(pt1.x, pt2.x)
					&& y >= pt2.y - accuracy && y <= pt2.y + accuracy) {
				selected = true;
			}
			// check hit for vertical line
			if (pt1.x == pt2.x && y >= Math.min(pt1.y, pt2.y) && y <= Math.max(pt1.y, pt2.y)
					&& x >= pt2.x - accuracy && x <= pt2.x + accuracy) {
				selected = true;
			}
			pt1 = pt2;
		}
		return selected;
	}

	@Override
	protected void paintSelection(Graphics gr) {
		gr.setColor(scene.getSelectionColor());
		for (Point pt : points) {
			gr.drawRect(pt.x - selectionWidth / 2, pt.y - selectionWidth / 2, selectionWidth,
					selectionWidth);
		}
	}

	@Override
	public void prepareSelect(int x, int y) {
		selectedIndex = -1;
		for (int i = 0; i < points.size(); i++) {
			Point pt = points.get(i);
			if (x >= pt.x - selectionWidth / 2 && x <= pt.x + selectionWidth / 2
					&& y >= pt.y - selectionWidth / 2 && y <= pt.y + selectionWidth / 2) {
				selectedIndex = i;
			}
		}
	}

	@Override
	public void drag(int x, int y) {
		if (selectedIndex<0) {
			return;
		}
		// Graphics gr = scene.getGraphics();
		// gr.setXORMode(Color.YELLOW);
		// drawLines(gr);
		Point selectedPoint = points.get(selectedIndex);
		// some point neighbouring the edge
		if (selectedIndex == 1 && points.size() > 2) {
			Point pt = points.get(0);
			addPointBetween(1, pt, selectedPoint);
			selectedIndex = 2;
		}
		if (selectedIndex == getLastIndex() - 1 && points.size() > 2) {
			Point pt = points.get(getLastIndex());
			addPointBetween(getLastIndex(), pt, selectedPoint);
		}
		// for points in the middle move the two adjacent points on either the same horizontal
		// or same vertical line
		if (selectedIndex > 1 && selectedIndex < getLastIndex() - 1 && points.size() > 2) {
			points.get(selectedIndex - 1).setLocation(x, points.get(selectedIndex - 2).y);
			points.get(selectedIndex + 1).setLocation(points.get(selectedIndex + 2).x, y);
		}

		// selecting and moving the point
		if (selectedIndex >= 0) {
			points.get(selectedIndex).setLocation(x, y);
		}


		// moving the start point
		if (selectedIndex == 0 && selectedPoint.x != points.get(1).x
				&& selectedPoint.y != points.get(1).y) {
			if (points.size() == 2) {
				points.add(1, new Point(selectedPoint.x, points.get(1).y));
			}
			else {
				points.get(1).setLocation(selectedPoint.x, points.get(2).y);
			}
		}
		// moving the end point
		int lastIndex = points.size() - 1;
		if (selectedIndex == lastIndex && selectedPoint.x != points.get(lastIndex - 1).x
				&& selectedPoint.y != points.get(lastIndex - 1).y) {
			if (points.size() == 2) {
				points.add(lastIndex, new Point(selectedPoint.x, points.get(lastIndex - 1).y));
				selectedIndex += 1;
			}
			else {
				points.get(lastIndex - 1).setLocation(selectedPoint.x, points.get(lastIndex - 2).y);
			}
		}
		scene.repaint();
	}

	@Override
	public void release() {
		removePointsInLine();
		scene.repaint();
	}
	
	private int getLastIndex() {
		return points.size() - 1;
	}

	/**
	 * Adds two point halfway between the two given points at the specified index. The second point
	 * is given with
	 * 
	 * @param index
	 * @param pt1
	 * @param pt2
	 */
	private void addPointBetween(int index, Point pt1, Point pt2) {
		points.add(index, new Point((pt1.x - pt2.x) / 2 + pt2.x, (pt1.y - pt2.y) / 2 + pt2.y));
		points.add(index, new Point((pt1.x - pt2.x) / 2 + pt2.x, (pt1.y - pt2.y) / 2 + pt2.y));
	}

	/**
	 * Checks the point list for three points in a horizontal or vertical line. If such a triple is 
	 * found, the middle point is removed.
	 */
	private void removePointsInLine() {
		for (int i = 1; i < getLastIndex(); i++) {
			Point pt1 = points.get(i - 1);
			Point pt2 = points.get(i);
			Point pt3 = points.get(i + 1);
			if ((pt1.x == pt2.x && pt2.x == pt3.x) || (pt1.y == pt2.y && pt2.y == pt3.y)) {
				points.remove(i);
				if (selectedIndex > i) {
					selectedIndex--;
				}
				if (selectedIndex == i) {
					if (selectedIndex == getLastIndex()) {
						selectedIndex--;
					}
				}
				i--;
			}
		}
	}

	@Override
	public Point[] getDockingPoints() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void paintElement(Graphics gr) {
		gr.setColor(foreground);
		drawLines(gr);
	}

	private void drawLines(Graphics gr) {
		Iterator<Point> it = points.iterator();
		Point pt1 = it.next();
		while (it.hasNext()) {
			Point pt2 = it.next();
			gr.drawLine(pt1.x, pt1.y, pt2.x, pt2.y);
			pt1 = pt2;
		}
	}

	@Override
	public void setPosition(int x, int y) {
		// TODO Auto-generated method stub

	}

	public Point getPoint(int i) {
		return points.get(i);
	}


}
