package ipod.ui;

import joc.Message;
import obc.CGRect;
import obc.UINavigationBar;
import obc.UINavigationItem;

public class UiNavigationBar extends UINavigationBar {

	/**
	 * style: gray gradient background with text.
	 */
	public static final int DEFAULT_STYLE = 0;

	/**
	 * style: solid black background with white text.
	 */
	public static final int BW_STYLE = 1;

	/**
	 * style: transparent black background with white text.
	 */
	public static final int TRANSPARENT_STYLE = 2;
	
	/**
	 * Just a plain gray button.
	 */
	public static final int DEFAULT_BUTTON_STYLE = 0;

	public UiNavigationBar() {
		this(null, DEFAULT_STYLE);
	}
	
	public UiNavigationBar(String title) {
		this(title, DEFAULT_STYLE);
	}

	public UiNavigationBar(String title, int style) {
		// TODO window bounds should be transferred
		initWithFrame$(new CGRect(0, 0, 320,
				UINavigationBar.$defaultSize().height));

		setBarStyle$(style);
		if (title != null) {
			UINavigationItem navitem = new UINavigationItem().initWithTitle$(title);
			pushNavigationItem$(navitem);
		}
		setDelegate$(this);
	}

	public void pushNavItem(String itemTitle) {
		UINavigationItem navitem = new UINavigationItem().initWithTitle$(itemTitle);
		pushNavigationItem$(navitem);
	}

	public void popNavItem() {
		popNavigationItem();
	}
	
	public void setRightButton(String label, int buttonStyle) {
		showLeftButton$withStyle$rightButton$withStyle$(null, 0, label, buttonStyle);
	}

	@Message
	public void navigationBar$buttonClicked$(Object unused, int buttonNumber) {
		switch (buttonNumber) {
		// right button
		case 0:
			pushNavItem("right");
			break;
		// left
		case 1:
			pushNavItem("left");
			break;
		}
	}

}
