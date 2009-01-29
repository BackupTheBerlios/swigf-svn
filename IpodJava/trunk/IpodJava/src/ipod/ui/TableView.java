/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package ipod.ui;

import static joc.Static.NO;
import ipod.ui.events.ListSelectionEvent;
import ipod.ui.events.ListSelectionListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import joc.Message;
import joc.Static;
import obc.CGRect;
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
	public TableView(List<T> data) {
		initWithFrame$(new CGRect(0, 0, 320, 400));
		this.data = data;
		cells = new ArrayList<UITableCell>(data.size());
		for (Object element : data) {
			UISimpleTableCell cell = new UISimpleTableCell();
			cell.init();
			cell.setTitle$(element.toString());
			cells.add(cell);
		}
		setSeparatorStyle$(1);
		setReusesTableCells$(Static.NO);
		setDataSource$(this);
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
		SimpleApplication.log("numberOfRowsInTable => "+data.size());
		return data.size();
	}

	@Message
	public UITableCell table$cellForRow$column$(UITable table, int row, UITableColumn col) {
		SimpleApplication.log("cellForRow");
		//return cells.get(row);
		UISimpleTableCell cell = new UISimpleTableCell();
		cell.init();
		cell.setTitle$("hello");
		return cell;
	}

	@Message
	public byte table$canSelectRow$(UITable table, int row) {
		return NO;
	}

	@Message
	public void tableRowSelected$(NSNotification notification) {
		for (ListSelectionListener listener : listeners) {
			listener.selectItem(new ListSelectionEvent(cells.get(selectedRow()), data
					.get(selectedRow()), selectedRow()));
		}
	}

}
