/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package ipod.ui;

import ipod.base.Logger;
import ipod.ui.events.ActionListener;
import ipod.ui.events.Event;

import java.util.LinkedList;
import java.util.List;

import joc.Message;
import obc.CGRect;
import obc.UIAlertView;
import obc.UIView;

public class AlertSheet extends UIAlertView {

	/**
	 * Gray gradient background with white text.
	 */
	public static final int DEFAULT_STYLE = 0;

	/**
	 * Solid black background with white text.
	 */
	public static final int BW_STYLE = 1;

	/**
	 * Transparent black background with white text.
	 */
	public static final int TRANSPARENT_STYLE = 2;

	private List<ActionListener> listeners = new LinkedList<ActionListener>();

	/**
	 * 
	 * @param title
	 * @param bodyText
	 * @param style
	 */
	public AlertSheet(String title, String bodyText, int style) {
		initWithFrame$(new CGRect(0, 240, 240, 240));
		setAlertSheetStyle$(style);
		setTitle$(title);
		setBodyText$(bodyText);
		setDelegate$(this);
	}

	public void addButton(String label) {
		addButtonWithTitle$(label);
	}

	public void addDestructiveButton(String label) {
		setDestructiveButton$(addButtonWithTitle$(label));
	}

	/**
	 * Adds an ActionListener to this alert sheet. The fired event will contain an Integer denoting
	 * the chosen button counting from 1 for the topmost button to n for the n-th button.
	 * 
	 * @param listener
	 */
	public void addActionListener(ActionListener listener) {
		listeners.add(listener);
	}

	@Message
	public void alertSheet$buttonClicked$(int buttonNumber) {
		for (ActionListener listener : listeners) {
			listener.actionPerformed(new Event(new Integer(buttonNumber)));
		}
	}

	public static void showMessage(UIView view, String title, String message) {
		final AlertSheet alertSheet = new AlertSheet(title, message, DEFAULT_STYLE);
		alertSheet.addButton("Ok");
		alertSheet.addActionListener(new ActionListener() {
			public void actionPerformed(Event event) {
				alertSheet.dismiss();
			}
		});
		Logger.info("showMessage(): "+title+" : "+message);
		alertSheet.presentSheetInView$(view);
	}
}
