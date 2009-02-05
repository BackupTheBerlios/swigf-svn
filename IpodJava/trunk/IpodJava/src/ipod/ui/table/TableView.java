/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package ipod.ui.table;

import ipod.base.Logger;
import ipod.ui.events.ListSelectionEvent;
import ipod.ui.events.ListSelectionListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import joc.Message;
import joc.Static;
import obc.CGRect;
import obc.CGSize;
import obc.NSNotification;
import obc.UISimpleTableCell;
import obc.UITable;
import obc.UITableCell;
import obc.UITableColumn;

public class TableView extends UITable {
	/**
	 * A simple black arrow.
	 */
	public static final int DEFAULT_DISCLOSURE_STYLE = 0;
	/**
	 * A blue circle with a white arrow.
	 */
	public static final int BLUE_CIRCLE_DISCLOSURE_STYLE = 1;

	private TableModel model;
	private Map<Long, Integer> columnToIndexMap = new HashMap<Long, Integer>();
	private List<ListSelectionListener> listeners = new LinkedList<ListSelectionListener>();

	/**
	 * Constructs a single columned table with the contents of the list. The elements of the data
	 * object may implement the Interface ... to influence their presentation and behavior in the
	 * table.
	 * 
	 * @param data
	 */
	public TableView(TableModel model, int rowHeight) {
		this.model = model;
		initWithFrame$(new CGRect(0, 0, 320, 479));
		Logger.info("New TableView [" + model.getRowCount() + "," + model.getColumnCount()
				+ "] created");
		setSeparatorStyle$(1);
		setReusesTableCells$(Static.YES);
		setDataSource$(this);
		setDelegate$(this);
		setRowHeight$(rowHeight);
		reloadData();
	}

	@Override
	public UITable initWithFrame$(CGRect rect) {
		super.initWithFrame$(rect);
		for (int col = 0; col < model.getColumnCount(); col++) {
			UITableColumn column = new UITableColumn();
			column.initWithTitle$identifier$width$("Column" + col, "column" + col, model
					.getColumnWidth(col));
			Logger.debug("Created column with id: " + column.getIdentifier());
			columnToIndexMap.put(column.getIdentifier(), col);
			addTableColumn$(column);
		}
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

	private UITableCell createDefaultCellRenderer(int row, int col, UITableCell reusing) {
		Object cellData = model.getData(row, col);
		UITableCell cell = reusing;
		// TODO reusing
		if (cellData instanceof Boolean) {
			if (cell == null || !(cell instanceof CheckBoxTableCell)) {
				cell = new CheckBoxTableCell(model);
				((CheckBoxTableCell) cell).init(new CGSize(model.getColumnWidth(col),
						rowHeight() - 1));
			}
			((CheckBoxTableCell) cell).updateWithCell(row, col);

		}
		else {
			if (cell == null || !(cell instanceof UISimpleTableCell)) {
				cell = new UISimpleTableCell();
				cell.init();
			}
			((UISimpleTableCell) cell).setTitle$(cellData.toString());
		}
		return cell;
	}
	
	// TODO can these methods be private ?
	@Message
	public int numberOfRowsInTable$(UITable table) {
		return model.getRowCount();
	}

	@Message
	public UITableCell table$cellForRow$column$reusing$(UITable table, int row,
			UITableColumn column, UITableCell reusing) {
		// Logger.debug("cellForRowColumn(" + row + "," + column.getIdentifier() + ")");
		int col = columnToIndexMap.get(column.getIdentifier());
		// Logger.debug("Translates to cellForRowColumn(" + row + "," + col + ")");
		return createDefaultCellRenderer(row, col, reusing);
	}

	@Message
	public byte table$canSelectRow$(UITable table, int row) {
		return Static.YES;
	}

	@Message
	public void tableRowSelected$(NSNotification notification) {
		for (ListSelectionListener listener : listeners) {
			// TODO calculate column from tap position (notification?)
			listener.selectItem(new ListSelectionEvent(this, selectedRow()));
		}
	}
	
	@Message
	public void _willDeleteRow$forTableCell$viaEdge$animateOthers$(int row, Object arg1, int arg2,
			byte arg3) {
		Logger.debug("TableView.willDeleteRow: "+row);
		super._willDeleteRow$forTableCell$viaEdge$animateOthers$(row, arg1, arg2, arg3);
		model.updateData(row, -1, null);
	}

	// @Message
	// public int swipe$withEvent$(int type, Pointer<__GSEvent> eventPtr) {
	// __GSEvent event = new __GSEvent();
	// event.unbox(eventPtr);
	// for (Object obj : event.getFields()) {
	// Logger.debug("swipe event" + obj);
	// }
	//
	// return super.swipe$withEvent$(type, eventPtr);
	// }

}
