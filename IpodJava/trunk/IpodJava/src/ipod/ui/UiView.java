/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package ipod.ui;

import obc.CGRect;
import obc.UIView;

public class UiView extends UIView {
	UiNavigationBar navbar;

	public UiView(CGRect rect) {
		initWithFrame$(rect);
	}

	/**
	 * Adds a navigation bar with default height.
	 * 
	 * @param navbar
	 * @param width
	 */
	public void addNavigationBar(UiNavigationBar navbar) {
		this.navbar = navbar;
		addSubview$(navbar);
	}
	
}
