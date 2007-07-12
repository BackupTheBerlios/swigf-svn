/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package de.swigf.scene;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

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
		getInputMap().put(KeyStroke.getKeyStroke("ctrl C"), "copy");
		getActionMap().put("copy", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("copy");
				copyToClipboard();
			}
		});
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

	private static class TransferableImage implements ClipboardOwner, Transferable {
		private Image image;

		public TransferableImage(Image image) {
			this.image = image;
		}

		public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException,
				IOException {
			return image;
		}

		public DataFlavor[] getTransferDataFlavors() {
			return new DataFlavor[] { DataFlavor.imageFlavor };
		}

		public boolean isDataFlavorSupported(DataFlavor flavor) {
			return false;
		}

		public void lostOwnership(Clipboard clipboard, Transferable contents) {
		}
	}

	/** 
	 * Copys the screen into the clipboard.
	 */
	void copyToClipboard() {
		int offsetx = Integer.MAX_VALUE;
		int offsety = Integer.MAX_VALUE;
		int width = 0;
		int height = 0;
		for (AbstractGraphElement element : elements) {
			offsetx = Math.min(offsetx, element.getBounds().x);
			offsety = Math.min(offsety, element.getBounds().y);
			width = Math.max(width, element.getBounds().x + element.getBounds().width);
			height = Math.max(height, element.getBounds().y + element.getBounds().height);
		}
		if (width > 0 && height > 0) {
			width = width - offsetx + 10;
			height = height - offsety + 10;
			offsetx -= 5;
			offsety -= 5;
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D gr = image.createGraphics();
			gr.translate(-offsetx, -offsety);
			paint(gr);
			TransferableImage contents = new TransferableImage(image);
			getToolkit().getSystemClipboard().setContents(contents, contents);
		}
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(WHITE_GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.LIGHT_GRAY);
		for (int x = 0; x < getWidth(); x += getGridSpace()) {
			g.drawLine(x, 0, x, getHeight());
		}
		for (int y = 0; y < getHeight(); y += getGridSpace()) {
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
