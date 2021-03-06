/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package simpleeditor;

import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import de.swigf.elements.ConnectorElement;
import de.swigf.elements.RectangularElement;
import de.swigf.scene.Scene;

public class SimpleEditor extends JFrame {

	private Scene scene;

	public SimpleEditor() {
		super("SimpleEditor");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		scene = new Scene();
		getContentPane().add(scene);
		setSize(900, 800);
		setVisible(true);
		for (int i = 0; i < 10; i++) {
			scene.addElement(new RectangularElement(scene, "Test" + i), 100 + i * 2, 100 + i * 2);
		}
		for (int i = 0; i < 5; i++) {
			scene.addElement(new ConnectorElement(scene, new Point(150 + i * 20, 100 + i * 20),
					new Point(400 + i * 20, 100 + i * 20)), 0, 0);
		}
	}

	public static void main(String[] args) {
		new SimpleEditor();
	}
}
