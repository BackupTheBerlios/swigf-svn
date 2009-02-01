package ipod.ui;

import joc.Message;
import joc.Selector;
import ipod.base.Logger;
import obc.UIButton;
import obc.UIImage;

public class ToggleButton extends UIButton {
	private UIImage uncheckedImage;
	private UIImage checkedImage;
	Boolean toggle = new Boolean(true);

	public ToggleButton() {
		init();
		uncheckedImage = (UIImage) UIImage.$applicationImageNamed$("Checkbox_empty.png");
		checkedImage = (UIImage) UIImage.$applicationImageNamed$("Checkbox_checked.png");
		Logger.info("New ToggleButton created with image "+uncheckedImage.size());
		setImage$forState$(toggle?checkedImage:uncheckedImage, 0);
		addTarget$action$forEvents$(this, new Selector(255), 255);
	}
	
	@Message
	public void buttonClicked$sender() {
		Logger.debug("ToggleButton clicked !!!");
		toggle = new Boolean(!toggle);
		setImage$forState$(toggle?checkedImage:uncheckedImage, 0);
	}
	
	
}
