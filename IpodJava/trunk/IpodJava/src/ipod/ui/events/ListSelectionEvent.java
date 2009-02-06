/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package ipod.ui.events;

public class ListSelectionEvent extends Event {
	private int listIndex;
	private int sectionIndex;

	public ListSelectionEvent(Object source, int listIndex, int sectionIndex) {
		super(source);
		this.listIndex = listIndex;
		this.sectionIndex = sectionIndex;
	}

	public int getListIndex() {
		return listIndex;
	}

	public int getSectionIndex() {
		return sectionIndex;
	}
}
