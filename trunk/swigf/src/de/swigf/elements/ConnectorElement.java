package de.swigf.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import de.swigf.scene.Scene;

public class ConnectorElement extends AbstractGraphElement {
	private Point[] points;
	private int selectedIndex;
	private static final int selectionWidth = 10;
	private Color foreground = Color.BLACK;
	private static final int accuracy = selectionWidth;

	public ConnectorElement(Scene scene, Point pt1, Point pt2) {
		super(scene);
		points = new Point[2];
		points[0] = pt1;
		points[1] = pt2;
		setBounds(new Rectangle(pt1.x, pt1.y, 5, 5));
	}

	@Override
	public boolean contains(int x, int y) {
		boolean selected = false;
		for (int i = 1; i < points.length; i++) {
			// check hit for horizontal line
			if (points[i - 1].y == points[i].y && x >= Math.min(points[i - 1].x, points[i].x)
					&& x <= Math.max(points[i - 1].x, points[i].x) && y >= points[i].y - accuracy
					&& y <= points[i].y + accuracy) {
				selected = true;
			}
			// check hit for vertical line
			if (points[i - 1].x == points[i].x && y >= Math.min(points[i - 1].y, points[i].y)
					&& y <= Math.max(points[i - 1].y, points[i].y) && x >= points[i].x - accuracy
					&& x <= points[i].x + accuracy) {
				selected = true;
			}
		}
		// TODO Auto-generated method stub
		return selected;
	}

	@Override
	protected void drawSelection() {
		Graphics gr = scene.getGraphics();
		gr.setColor(scene.getSelectionColor());
		for (Point pt : points) {
			gr.drawRect(pt.x - selectionWidth / 2, pt.y - selectionWidth / 2, selectionWidth,
					selectionWidth);
		}
	}

	@Override
	public void select(int x, int y) {
		selectedIndex = -1;
		for (int i = 0; i<points.length; i++) {
			Point pt = points[i];
			if (x >= pt.x - selectionWidth / 2 && x <= pt.x + selectionWidth / 2
					&& y >= pt.y - selectionWidth / 2 && y <= pt.y + selectionWidth / 2) {
				selectedIndex = i;
			}
		}
		drawSelection();
	}

	@Override
	public void drag(int x, int y) {
		Graphics gr = scene.getGraphics();
		gr.setXORMode(Color.YELLOW);
		drawLines(gr);
		if (selectedIndex>=0) {
			points[selectedIndex] = new Point(x,y);
		}
		drawLines(gr);
	}

	@Override
	public Point[] getDockingPoints() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void paint(Graphics gr) {
		gr.setColor(foreground);
		drawLines(gr);
	}

	private void drawLines(Graphics gr) {
		for (int i = 1; i < points.length; i++) {
			gr.drawLine(points[i - 1].x, points[i - 1].y, points[i].x, points[i].y);
		}
	}

	@Override
	public void setPosition(int x, int y) {
		// TODO Auto-generated method stub

	}

	public Point getPoint(int i) {
		return points[i];
	}

}
