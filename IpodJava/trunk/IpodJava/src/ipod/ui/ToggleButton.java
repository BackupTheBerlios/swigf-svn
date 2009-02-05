package ipod.ui;

import java.util.LinkedList;
import java.util.List;

import joc.Message;
import joc.Selector;
import joc.Static;
import ipod.base.Logger;
import ipod.ui.events.ActionListener;
import ipod.ui.events.Event;
import obc.UIButton;
import obc.UIImage;

public class ToggleButton extends UIButton {
	private static UIImage uncheckedImage;
	private static UIImage checkedImage;
	private Boolean toggle;
	private List<ActionListener> listeners = new LinkedList<ActionListener>();
	
	public static final int kUIControlEventMouseUpInside = 1 << 6;
	public static final int kUIControlEventMouseDown = 1 << 0;
	public static final int kUIControlEventMouseMovedInside = 1 << 2;
	public static final int kUIControlEbentMouseMovedOuside = 1 << 3;

	static {
		uncheckedImage = (UIImage) UIImage.$applicationImageNamed$("Checkbox_empty.png");
		checkedImage = (UIImage) UIImage.$applicationImageNamed$("Checkbox_checked.png");
	}
	
	public ToggleButton(boolean init) {
		init();
		this.toggle = Boolean.valueOf(init);
		Logger.info("New ToggleButton created with image "+uncheckedImage.size().width);
		setImage$forState$(toggle?checkedImage:uncheckedImage, 0);
		addTarget$action$forEvents$(this, new Selector("buttonClicked"), kUIControlEventMouseDown);
		setShowPressFeedback$(Static.YES);
		setEnabled$(Static.YES);
		setSize$(checkedImage.size());
	}

	public ToggleButton() {
		this(false);
	}

	public void setValue(Boolean value) {
		toggle = value;
		setImage$forState$(toggle?checkedImage:uncheckedImage, 0);	
	}
	
	public Boolean getValue() {
		return toggle;
	}
	
	public void addActionListener(ActionListener listener) {
		listeners.add(listener);
	}
	
	@Message
	public void buttonClicked(Object sender) {
		Logger.debug("ToggleButton clicked !!!");
		toggle = Boolean.valueOf(!toggle);
		setImage$forState$(toggle?checkedImage:uncheckedImage, 0);
		for (ActionListener listener : listeners) {
			listener.actionPerformed(new Event(this));
		}
	}
	
	
}
