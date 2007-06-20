package de.swigf.scene;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import de.swigf.elements.AbstractGraphElement;

public class SceneController extends MouseInputAdapter {
	Scene scene;
	AbstractGraphElement selectedObject;
	Point selectionOffset;

	public SceneController(Scene scene) {
		this.scene = scene;
	}

	public void mouseClicked(MouseEvent e) {
		System.out.println(selectedObject);
	}

	public void mousePressed(MouseEvent e) {
		if (selectedObject != null) {
			selectedObject.deselect();
		}
		selectedObject = scene.findElement(e.getX(), e.getY());
		if (selectedObject != null) {
			selectedObject.select(e.getX(), e.getY());
		}
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (selectedObject != null) {
			selectedObject.drag(e.getX(), e.getY());
		}
	}

}
