/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package ipod.ui;

import ipod.base.Logger;
import ipod.ui.events.ListSelectionEvent;
import ipod.ui.events.ListSelectionListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import joc.Message;
import joc.Static;
import obc.CGRect;
import obc.NSCFArray;
import obc.NSNotification;
import obc.UISimpleTableCell;
import obc.UITable;
import obc.UITableCell;
import obc.UITableColumn;

public class TableView<T> extends UITable {
	/**
	 * A simple black arrow.
	 */
	public static final int DEFAULT_DISCLOSURE_STYLE = 0;
	/**
	 * A blue circle with a white arrow.
	 */
	public static final int BLUE_CIRCLE_DISCLOSURE_STYLE = 1;

	private List<T> data;
	private List<UITableCell> cells;
	private List<ListSelectionListener> listeners = new LinkedList<ListSelectionListener>();

	/**
	 * Constructs a single columned table with the contents of the list. The elements of the data
	 * object may implement the Interface ... to influence their presentation and behavior in the
	 * table.
	 * 
	 * @param data
	 */
	public TableView(List<T> data, int rowHeight) {
		initWithFrame$(new CGRect(0, 0, 320, 479));
		Logger.debug("New TableView with " + data.size() + " entries created");
		this.data = data;
		cells = new ArrayList<UITableCell>(data.size());
		for (Object element : data) {
			UISimpleTableCell cell = new UISimpleTableCell();
			cell.init();
			cell.setTitle$(element.toString());
			cells.add(cell);
		}
		setSeparatorStyle$(1);
		// TODO setReusesTableCells$(Static.YES);
		setDataSource$(this);
		setDelegate$(this);
		setRowHeight$(rowHeight);
		reloadData();
	}

	public TableView(List<T> data) {
		this(data, 32);
	}
	
	public void setDisclosureForAllElements(int style, boolean b) {
		for (UITableCell cell : cells) {
			cell.setShowDisclosure$(b ? Static.YES : Static.NO);
			cell.setDisclosureStyle$(style);
		}
	}

	@Override
	public UITable initWithFrame$(CGRect rect) {
		super.initWithFrame$(rect);
		UITableColumn column = new UITableColumn();
		column.initWithTitle$identifier$width$("Column", "column", 320);
		addTableColumn$(column);
		return this;
	}
	
	@Override
	public void setFrame$(CGRect arg0) {
		super.setFrame$(arg0);
		Logger.debug(tableColumns());
		NSCFArray array = (NSCFArray) tableColumns();
	}
	
	/**
	 * Adds a {@link ListSelectionListener} to the table.
	 * 
	 * @param listener
	 */
	public void addListSelectionListener(ListSelectionListener listener) {
		listeners.add(listener);
	}

	// TODO can these methods be private ?
	@Message
	public int numberOfRowsInTable$(UITable table) {
		Logger.debug("numberOfRowsInTable => " + data.size());
		return data.size();
	}

	@Message
	public UITableCell table$cellForRow$column$(UITable table, int row, UITableColumn col) {
		Logger.debug("cellForRowColumn(" + row + "," + col.getIdentifier() + ")");
		return cells.get(row);
	}

	@Message
	public byte table$canSelectRow$(UITable table, int row) {
		return Static.YES;
	}

	@Message
	public void tableRowSelected$(NSNotification notification) {
		for (ListSelectionListener listener : listeners) {
			listener.selectItem(new ListSelectionEvent(cells.get(selectedRow()), data
					.get(selectedRow()), selectedRow()));
		}
	}

}
