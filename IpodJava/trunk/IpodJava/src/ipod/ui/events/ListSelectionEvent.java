/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package ipod.ui.events;

public class ListSelectionEvent extends Event {
	private int listIndex;
	private Object item;
	
	public ListSelectionEvent(Object source, Object item, int listIndex) {
		super(source);
		this.listIndex = listIndex;
		this.item = item;
	}

	public int getListIndex() {
		return listIndex;
	}
	
	public Object getItem() {
		return item;
	}
}
