package reversi.gui.resource;

import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ResourceManager {
	public static Icon getIcon(String name) {
		return new ImageIcon(Toolkit.getDefaultToolkit().createImage(
				ClassLoader.getSystemResource("reversi/gui/resource/"+name)));
	}
}
