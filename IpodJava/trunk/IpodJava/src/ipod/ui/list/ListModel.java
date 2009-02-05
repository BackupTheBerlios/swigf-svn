/**
 * (c) 2009 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package ipod.ui.list;

import java.util.ArrayList;
import java.util.List;

public abstract class ListModel<T extends Sectionable> {
	List<T> data = new ArrayList<T>();

	
	public abstract void addItem(T item);
	public abstract T removeItemAt(int index);
	public abstract void updateItemAt(int index, T newItem);
	
	
	public int getCountForSection(int rowsInSection) {
		return  data.size();
	}

	public void clear() {
		data.clear();
	}
	

	public T get(int row) {
		return data.get(row);
	}
	
	public void addSilently(T item) {
		data.add(item);
	}
	
	public T removeSilently(int row) {
		return data.remove(row);
	}
}
