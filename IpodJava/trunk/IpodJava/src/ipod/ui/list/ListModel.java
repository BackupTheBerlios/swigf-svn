/**
 * (c) 2009 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package ipod.ui.list;

import ipod.ui.events.ActionListener;
import ipod.ui.events.Event;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class ListModel<T extends Sectionable> {
	private class Section {
		String name;
		List<T> sectiondata = new ArrayList<T>();

		public Section(String name) {
			this.name = name;
		}
	}

	List<Section> sections = new ArrayList<Section>();
	List<ActionListener> listeners = new LinkedList<ActionListener>();

	public abstract void addItem(T item);

	public abstract T removeItemAt(int section, int index);

	public abstract void updateItemAt(int section, int index, T newItem);

	public void addUpdateListener(ActionListener listener) {
		listeners.add(listener);
	}

	public void fireUpdate() {
		for (ActionListener listener : listeners) {
			listener.actionPerformed(new Event(ListModel.this));
		}
	}

	public int getSectionCount() {
		return sections.size();
	}

	public int getCountForSection(int section) {
		return sections.get(section).sectiondata.size();
	}
	
	public String getSectionHeader(int section) {
		return sections.get(section).name;
	}

	public void clear() {
		sections.clear();
	}

	public T get(int section, int row) {
		return sections.get(section).sectiondata.get(row);
	}

	private Section indexOfSection(String sectionName) {
		for (Section section : sections) {
			if (section.name.equals(sectionName)) {
				return section;
			}
		}
		return null;
	}

	public void addSilently(T item) {
		Section section = indexOfSection(item.getSectionName());
		if (section == null) {
			section = new Section(item.getSectionName());
			sections.add(section);
		}
		section.sectiondata.add(item);
	}

	public T removeSilently(int section, int row) {
		return sections.get(section).sectiondata.remove(row);
	}
}
