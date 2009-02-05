/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package ipod.ui.list;

import ipod.base.Logger;
import ipod.ui.Layoutable;
import ipod.ui.events.ListSelectionEvent;
import ipod.ui.events.ListSelectionListener;

import java.util.LinkedList;
import java.util.List;

import joc.Message;
import obc.CGRect;
import obc.NSIndexPath;
import obc.UITableView;
import obc.UITableViewCell;

public class ListView<T extends ListModel<?>> extends UITableView implements Layoutable {
	private static final CGRect ZERO_RECT = new CGRect(0, 0, 0, 0);
	/**
	 * A simple black arrow.
	 */
	public static final int DEFAULT_DISCLOSURE_STYLE = 0;
	/**
	 * A blue circle with a white arrow.
	 */
	public static final int BLUE_CIRCLE_DISCLOSURE_STYLE = 1;

	private T model;
	private List<ListSelectionListener> listeners = new LinkedList<ListSelectionListener>();
	private CellFactory cellFactory = new CellFactory() {
		public UITableViewCell createCell(String identifier) {
			return new UITableViewCell().initWithFrame$reuseIdentifier$(ZERO_RECT, identifier);
		}

		public void fillCellWithData(UITableViewCell cell, int row) {
			cell.setText$(model.get(row));
		}
	};

	public ListView(T model, int rowHeight) {
		this.model = model;
		initWithFrame$(new CGRect(0, 0, 320, 479));
		Logger.info("New ListView with model of size created");
		setSeparatorStyle$(1);
		setDataSource$(this);
		setDelegate$(this);
		setRowHeight$(rowHeight);
		reloadData();
	}

	/**
	 * Provide a cell factory to implement your own look for a cell.
	 * 
	 * @param factory
	 */
	public void setCellViewFactory(CellFactory factory) {
		cellFactory = factory;
	}
	
	public void addListSelectionListener(ListSelectionListener listener) {
		listeners.add(listener);
	}

	@Message
	public int tableView$numberOfRowsInSection$(ListView<T> view, int section) {
		Logger.debug("Message: tableView$numberOfRowsInSection$ " + section + " = "
				+ model.getCountForSection(section));
		return model.getCountForSection(section);
	}

	@Message
	public UITableViewCell tableView$cellForRowAtIndexPath$(ListView<T> view, NSIndexPath indexPath) {
		Logger.debug("Message: tableView$cellForRowAtIndexPath " + indexPath.row());
		String identifier = this.toString();
		UITableViewCell cell = (UITableViewCell) dequeueReusableCellWithIdentifier$(identifier);
		if (cell == null) {
			cell = cellFactory.createCell(identifier);
		}
		cellFactory.fillCellWithData(cell, indexPath.row());
		return cell;
	}

	@Message
	public int swipe$withEvent$(int arg0, Object arg1) {
		Logger.debug("Message: swipe " + arg1.getClass().getName());
		return 0;
	}

	@Message
	public void tableView$didSelectRowAtIndexPath$(ListView<T> view, NSIndexPath indexPath) {
		Logger.info("ListView selected " + indexPath.row());
		for (ListSelectionListener listener : listeners) {
			listener.selectItem(new ListSelectionEvent(cellForRowAtIndexPath$(indexPath), indexPath.row()));
		}
	}
}
