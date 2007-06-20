package de.swigf.scene;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import de.swigf.elements.AbstractGraphElement;

public class Scene extends JComponent {
	private List<AbstractGraphElement> elements = new ArrayList<AbstractGraphElement>();
	private SceneController controller;
	private int gridSpace = 10;
	private static final Color WHITE_GRAY = new Color(230, 230, 230);

	public Scene() {
		setBackground(Color.WHITE);
		controller = new SceneController(this);
		addMouseListener(controller);
		addMouseMotionListener(controller);
	}

	/**
	 * Adds an AbstractGraphElement at the given position to the screen. Elements are paintet in the
	 * order, they were added to the scene. That means the last object will be in the foreground.
	 * 
	 * @param element
	 * @param x
	 * @param y
	 */
	public void addElement(AbstractGraphElement element, int x, int y) {
		element.setPosition(x, y);
		elements.add(element);
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(WHITE_GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.LIGHT_GRAY);
		for (int x= 0; x<getWidth(); x+=getGridSpace()) {
			g.drawLine(x, 0, x, getHeight());
		}
		for (int y=0; y<getHeight(); y+=getGridSpace()) {
			g.drawLine(0, y, getWidth(), y);
		}
		for (AbstractGraphElement el : elements) {
			el.paint(g);
		}
	}

	/**
	 * Find the last element of the scene, which contains the given coordinates.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	AbstractGraphElement findElement(int x, int y) {
		// REMARK: searching for elements could be optimized by organizing the element into a quad
		// tree
		for (int i = elements.size() - 1; i >= 0; i--) {
			AbstractGraphElement el = elements.get(i);
			if (el.contains(x, y)) {
				return el;
			}
		}
		return null;
	}

	public int getGridSpace() {
		return gridSpace;
	}

	public void setGridSpace(int gridSpace) {
		this.gridSpace = gridSpace;
	}
	
	public Color getSelectionColor() {
		return Color.MAGENTA;
	}
}
