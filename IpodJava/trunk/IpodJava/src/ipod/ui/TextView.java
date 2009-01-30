/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package ipod.ui;

import joc.Static;
import obc.UIFont;
import obc.UITextView;

public class TextView extends UITextView {

	public TextView(int fontSize) {
		// TODO try init instead
		init();
		UIFont font = new UIFont().initWithName$size$("Arial", fontSize);
		setFont$(font);//"font-family: Helvetica; font-size: 18; font-weight: bold");
		setEditable$(Static.NO);
		setContentToHTMLString$("<html><body>Hello <i>world</i></body></html>");
		//setText$("<html><body>Hello <i>world</i></body></html>");
		
	}
	
}
