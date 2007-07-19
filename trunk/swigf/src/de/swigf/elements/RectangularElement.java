/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package de.swigf.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import de.swigf.scene.Scene;

public class RectangularElement extends AbstractGraphElement {
	private Color foreground = new Color(50, 0, 255);
	private Color background = new Color(255, 255, 150);
	private Insets insets = new Insets(5, 10, 10, 10);

	public RectangularElement(Scene scene, String name) {
		super(scene);
		this.name = name;
	}

	@Override
	public void drag(int x, int y) {
		// translate with the offset of the mouse inside the component and then snap to grid
		Point snappedPt = snapToGrid(new Point(x - getSelectionOffset().x, y
				- getSelectionOffset().y));
		// calculate a rectangle from the elements bounds (old position of the elment) plus the
		// translation (new position of the element) for a background buffer
		int oldX = getBounds().x;
		int oldY = getBounds().y;
		int deltaX = Math.abs(getBounds().x - snappedPt.x) + 1;
		int deltaY = Math.abs(getBounds().y - snappedPt.y) + 1;
		setPosition(snappedPt.x, snappedPt.y);
		BufferedImage bufferedImage = new BufferedImage(getBounds().width + deltaX,
				getBounds().height + deltaY, BufferedImage.TYPE_INT_RGB);
		Graphics2D gr = bufferedImage.createGraphics();
		// as the buffer only contains a small clipping area of the scene, we have to translate with
		// the offset and then repaint the scene in the buffer
		int rectx = Math.min(getBounds().x, oldX);
		int recty = Math.min(getBounds().y, oldY);
		gr.translate(-rectx, -recty);
		scene.paint(gr);
		// copy the buffer back to the foreground
		Graphics sceneGraphics = scene.getGraphics();
		sceneGraphics.drawImage(bufferedImage, rectx, recty, null);
		paintSelection(sceneGraphics);
	}

	@Override
	public void deselect() {
		super.deselect();
		BufferedImage bufferedImage = new BufferedImage(getBounds().width, getBounds().height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D gr = bufferedImage.createGraphics();
		// as the buffer only contains a small clipping area of the scene, we have to translate with
		// the offset and then repaint the scene in the buffer
		gr.translate(-getBounds().x, -getBounds().y);
		scene.paint(gr);
		// copy the buffer back to the foreground
		scene.getGraphics().drawImage(bufferedImage, getBounds().x, getBounds().y, null);
	}

	@Override
	public boolean contains(int x, int y) {
		if (x >= getBounds().x && x <= getBounds().x + getBounds().width && y >= getBounds().y
				&& y <= getBounds().y + getBounds().height) {
			return true;
		}
		return false;
	}

	@Override
	public void paintElement(Graphics gr) {
		gr.setColor(background);
		gr.fillRect(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
		gr.setColor(foreground);
		gr.drawRect(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
		gr.drawString(name, getBounds().x + insets.left, getBounds().y + insets.top
				+ gr.getFontMetrics().getHeight());
	}

	@Override
	public String toString() {
		return "AGE '" + getName() + "' at (" + getBounds().x + ", " + getBounds().y + ")";
	}

	@Override
	public void setPosition(int x, int y) {
		Graphics gr = scene.getGraphics();
		Rectangle textDim = gr.getFontMetrics().getStringBounds(name, 0, name.length() - 1, gr)
				.getBounds();
		setBounds(new Rectangle(x, y, textDim.width + insets.left + insets.right, textDim.height
				+ insets.top + insets.bottom));
	}

	@Override
	public Point[] getDockingPoints() {
		return new Point[] { new Point(0, 0), new Point(getBounds().width / 2, 0),
				new Point(getBounds().width, 0),
				new Point(getBounds().width / 2, getBounds().height),
				new Point(getBounds().width, getBounds().height / 2),
				new Point(0, getBounds().height / 2) };
	}
}
