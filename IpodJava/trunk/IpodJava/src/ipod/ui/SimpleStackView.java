/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package ipod.ui;

import obc.CGRect;
import obc.UINavigationBar;
import obc.UIView;

/**
 * Simple view class for stacking. Add your center view, navigation bar... as needed and call
 * <code>initSubviews()</code> afterwards.
 */
public class SimpleStackView extends UIView {
	private NavigationBar navigationBar;
	private UIView centerView;
	private CGRect bounds;

	public SimpleStackView(CGRect rect) {
		bounds = rect;
		initWithFrame$(bounds);
	}

	/**
	 * Adds a navigation bar with default height.
	 * 
	 * @param navbar
	 * @param width
	 */
	public void addNavigationBar(NavigationBar navbar) {
		this.navigationBar = navbar;
		addSubview$(navigationBar);
	}

	public void addCenterView(UIView view) {
		centerView = view;
		addSubview$(centerView);
	}

	/**
	 * Layouts the subviews.
	 */
	public void layout() {
		float heightOfCenterView = bounds.size.height;
		float offset = 0;
		if (navigationBar != null) {
			navigationBar.setFrame$(new CGRect(0, 0, bounds.size.width, UINavigationBar
					.$defaultSize().height));
			if (navigationBar.barStyle() != NavigationBar.TRANSPARENT_STYLE) {
				heightOfCenterView -= UINavigationBar.$defaultSize().height;
				offset += UINavigationBar.$defaultSize().height;
			}
		}
		if (centerView != null) {
			centerView.setFrame$(new CGRect(0, offset, bounds.size.width, heightOfCenterView));
		}
	}

	// TODO check if normal bounds is not working
	public CGRect getBounds() {
		return bounds;
	}

}
