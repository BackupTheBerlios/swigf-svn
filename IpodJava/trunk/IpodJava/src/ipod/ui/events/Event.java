/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package ipod.ui.events;

public class Event {
	private Object source;

	public Event(Object source) {
		this.source = source;
	}

	public Object getSource() {
		return source;
	}

}
