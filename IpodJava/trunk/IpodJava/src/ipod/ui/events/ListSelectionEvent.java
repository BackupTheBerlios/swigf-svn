/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package ipod.ui.events;

public class ListSelectionEvent extends Event {
	private int listIndex;

	public ListSelectionEvent(Object source, int listIndex) {
		super(source);
		this.listIndex = listIndex;
	}

	public int getListIndex() {
		return listIndex;
	}

}
