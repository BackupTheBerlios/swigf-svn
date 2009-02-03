/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package ipod.ui;

import joc.Static;
import obc.CGSize;
import obc.UIFont;
import obc.UITextField;

public class TextField extends UITextField {

	public static final int BS_TRANSPARENT = 0;
	public static final int BS_FINE = 1;
	public static final int BS_RECT = 2;
	public static final int BS_ROUNDED = 3;

	public static final int TA_LEFT = 0;
	public static final int TA_CENTER = 1;
	public static final int TA_RIGHT = 2;

	public TextField(String label, String prefill, CGSize size) {
		init();
		setSize$(size);
		float fontSize = size.height-8;
		UIFont font = new UIFont().initWithName$size$("Arial", fontSize);
		setFont$(font);
		setLabel$(label);
		setText$(prefill);
		setTextAlignment$(TA_LEFT);
		setClearButtonMode$(3);
		setBorderStyle$(BS_ROUNDED);
		setClearsOnBeginEditing$(Static.YES);
	}
}
