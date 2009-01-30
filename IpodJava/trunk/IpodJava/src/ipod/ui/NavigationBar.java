package ipod.ui;

import ipod.base.Logger;
import ipod.ui.events.ActionListener;
import ipod.ui.events.Event;

import java.util.LinkedList;
import java.util.List;

import joc.Message;
import obc.CGRect;
import obc.UINavigationBar;
import obc.UINavigationItem;

/**
 * A UiNavigationBar is stack-like organized navigation interface. You can push elements to it as
 * you navigate through a hierarchy. The UiNavigationBar will show three elements:
 * 
 * <li>The previous item as a button on the left side. Pushing the button will remove the actual
 * stack element and switch to the pressed element.</li>
 * 
 * <li>The current item will be shown as the title of the bar.</li>
 * 
 * <li>Optionally a button on the right side can be shown. Pressing it might start any kind of
 * action.</li>
 * 
 * @author L123073
 * 
 */
public class NavigationBar extends UINavigationBar {

	private static final String CONTENTS = "contents";

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

	private List<ActionListener> leftButtonListeners = new LinkedList<ActionListener>();
	private List<ActionListener> rightButtonListeners = new LinkedList<ActionListener>();

	public NavigationBar() {
		this(null, DEFAULT_STYLE);
	}

	public NavigationBar(String title) {
		this(title, DEFAULT_STYLE);
	}

	public NavigationBar(String title, int style) {
		initWithFrame$(new CGRect(0, 0, 10, 10));
		Logger.debug("New NavigationBar with title " + title + " and style " + style + " created");
		setBarStyle$(style);
		if (title != null) {
			UINavigationItem navitem = new UINavigationItem().initWithTitle$(title);
			pushNavigationItem$(navitem);
		}
		setDelegate$(this);
	}

	/**
	 * Pushes an item to the navigation bar. The item's toString() method will be used to display
	 * the item.
	 * 
	 * @param item
	 */
	public void pushNavItem(Object item) {
		UINavigationItem navitem = new UINavigationItem().initWithTitle$(item.toString());
		// navitem.addObject$toPropertyWithKey$(item, CONTENTS);
		pushNavigationItem$(navitem);
	}

	/**
	 * Pops the last item from the navigation bar.
	 */
	public void popNavItem() {
		popNavigationItem();
	}

	/**
	 * Sets a button on the right side of the navigation bar.
	 * 
	 * @param label
	 * @param buttonStyle
	 */
	public void setRightButton(String label, int buttonStyle) {
		showLeftButton$withStyle$rightButton$withStyle$(null, 0, label, buttonStyle);
	}

	public void addLeftButtonActionListener(ActionListener actionListener) {
		leftButtonListeners.add(actionListener);
	}

	public void addRightButtonActionListener(ActionListener actionListener) {
		rightButtonListeners.add(actionListener);
	}

	@Message
	public void navigationBar$buttonClicked$(Object unused, int buttonNumber) {
		switch (buttonNumber) {
		// right button
		case 0:
			for (ActionListener listener : rightButtonListeners) {
				listener.actionPerformed(new Event(this));
			}
			break;
		// left
		case 1:
			Object source = null;// ((UINavigationItem) backItem()).valueForKey$(CONTENTS);
			for (ActionListener listener : leftButtonListeners) {
				listener.actionPerformed(new Event(source));
			}
			break;
		}
	}

}
