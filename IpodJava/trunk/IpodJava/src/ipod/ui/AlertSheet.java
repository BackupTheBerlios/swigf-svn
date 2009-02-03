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
import obc.UIAlertSheetTextField;
import obc.UIAlertView;
import obc.UITextField;
import obc.UIView;

public class AlertSheet extends UIAlertView {

	private static final int BORDER_STLE = 4;

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
	public AlertSheet(String title, int style) {
		init();
		setAlertSheetStyle$(style);
		setTitle$(title);
		setDelegate$(this);
	}

	public void addButton(String label) {
		int bn = addButtonWithTitle$(label);
		Logger.debug("Add button with " + bn);
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
	public void alertSheet$buttonClicked$(UIAlertSheetTextField view, int buttonNumber) {
		for (ActionListener listener : listeners) {
			listener.actionPerformed(new Event(new Integer(buttonNumber)));
		}
	}

	/**
	 * Presents a message to the user. The sheet contains an ok-button to acknowledge the message.
	 * 
	 * @param view
	 * @param title
	 * @param message
	 */
	public static void showMessage(UIView view, String title, String message) {
		final AlertSheet alertSheet = new AlertSheet(title, BW_STYLE);
		alertSheet.setBodyText$(message);
		alertSheet.addButton("Ok");
		alertSheet.addActionListener(new ActionListener() {
			public void actionPerformed(Event event) {
				alertSheet.dismiss();
			}
		});
		Logger.info("showMessage(): " + title + " : " + message);
		alertSheet.presentSheetInView$(view);
	}

	/**
	 * Creates a message with a text input field.
	 * 
	 * @param view
	 * @param title Title of the dialog
	 * @param body Body text for the dialog
	 * @param inputlabel A label for the empty text field to explain the input
	 * @param successAction This ActionListener will be called in case ok is pressed. The given
	 *            event contains the entered string.
	 */
	public static void confirmUserRequest(UIView view, String title, String body,
			String inputlabel, final ActionListener successAction) {
		final AlertSheet alertSheet = new AlertSheet(title, BORDER_STLE);
		alertSheet.addButton("Ok");
		alertSheet.addButton("Cancel");
		alertSheet.setBodyText$("Body ???");
		alertSheet.addTextFieldWithValue$label$("", inputlabel);
		((UITextField) alertSheet.textField()).setClearButtonMode$(3);
		alertSheet.addActionListener(new ActionListener() {
			public void actionPerformed(Event event) {
				Logger.debug("Action caught on AlertSheet:" + event.getSource());
				if (((Integer) event.getSource()).intValue() == 1) {
					successAction.actionPerformed(new Event(((UITextField) alertSheet.textField())
							.text().toString()));
				}
				alertSheet.dismiss();
			}
		});
		Logger.info("confirmUserRequest(): " + title);
		// alertSheet.presentSheetFromAboveView$(view);
		alertSheet.show();
	}

}
