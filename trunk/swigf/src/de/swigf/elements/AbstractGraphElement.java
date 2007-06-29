package de.swigf.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import de.swigf.scene.Scene;

public abstract class AbstractGraphElement {
	protected Scene scene;
	protected String name;
	private Rectangle bounds;
	private Point selectionOffset;

	public AbstractGraphElement(Scene scene) {
		this.scene = scene;
	}

	/**
	 * Paints the element.
	 * 
	 * @param gr
	 */
	public abstract void paint(Graphics gr);

	/**
	 * Checks wether the current object contains the given coordinates.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public abstract boolean contains(int x, int y);

	/**
	 * Selects the element and stores the offset between the given coordinates and the position of
	 * the element.
	 * 
	 * @param x
	 * @param y
	 */
	public void select(int x, int y) {
		selectionOffset = new Point(x - bounds.x, y - bounds.y);
		drawSelection();
	}

	protected void drawSelection() {
		Graphics2D g = (Graphics2D) scene.getGraphics();
		g.setStroke(new BasicStroke(0, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 1,
				new float[] { 3, 3 }, 0.5f));
		g.setColor(Color.MAGENTA);
		g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
	}
	
	/**
	 * Repaint the selected area and do any cleanup necessary at deselection.
	 *
	 */
	public abstract void deselect();

	/**
	 * Drags the element from its current position to the given coordinates. The responsibility to
	 * repaint the element during dragging lies with the AGE.
	 * 
	 * @param x
	 * @param y
	 */
	public abstract void drag(int x, int y);

	/**
	 * Retrieves all points of the element, which can be used to dock this element with another
	 * elment or the grid of the scene. Coordinates of the docking points are relative to the origin
	 * of the AGE.
	 * 
	 * @return Returns an array of dockable points.
	 */
	public abstract Point[] getDockingPoints();

	/**
	 * Snap the object to the grid with the docking point, which has the least distance to the grid.
	 * 
	 * @param originalPoint
	 * @return
	 */
	protected Point snapToGrid(Point originalPoint) {
		Point snappedPoint = new Point(originalPoint.x + 2 * scene.getGridSpace(), originalPoint.y
				+ 2 * scene.getGridSpace());
		for (Point dockPt : getDockingPoints()) {
			Point currentSnap = new Point();
			currentSnap.x = ((originalPoint.x + dockPt.x + scene.getGridSpace() / 2) / scene
					.getGridSpace())
					* scene.getGridSpace() - dockPt.x;
			currentSnap.y = ((originalPoint.y + dockPt.y + scene.getGridSpace() / 2) / scene
					.getGridSpace())
					* scene.getGridSpace() - dockPt.y;
			if (distance(originalPoint, currentSnap) < distance(originalPoint, snappedPoint)) {
				snappedPoint = new Point(currentSnap.x, currentSnap.y);
			}
		}
		return snappedPoint;
	}

	private int distance(Point pt, Point pt2) {
		return (pt.x - pt2.x) * (pt.x - pt2.x) + (pt.y - pt2.y) * (pt.y - pt2.y);
	}

	public Point getPosition() {
		return new Point(bounds.x, bounds.y);
	}

	public abstract void setPosition(int x, int y);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public Point getSelectionOffset() {
		return selectionOffset;
	}

}
