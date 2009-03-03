/**
 * (c) 2009 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package ipod.todo;

import ipod.base.Logger;
import joc.Message;
import joc.Static;
import obc.CGRect;
import obc.UIPreferencesTable;
import obc.UIPreferencesTableCell;
import obc.UIPreferencesTextTableCell;

public class TodoPrefTable extends UIPreferencesTable {

	public TodoPrefTable() {
		initWithFrame$(new CGRect(0, 0, 320, 480));
		setDataSource$(this);
		reloadData();
	}

	@Message
	public int numberOfGroupsInPreferencsTable$(UIPreferencesTable table) {
		Logger.debug("PrefTable.numberOfGroups()");
		return 0;
	}

	@Message
	public UIPreferencesTableCell preferencesTable$cellForGroup$(UIPreferencesTable table, int group) {
		Logger.debug("PrefTable.cellForGroup()");
		UIPreferencesTextTableCell cell = new UIPreferencesTextTableCell();
		cell.init();
		cell.setTitle$("Todo");
		cell.setShowSelection$(Static.NO);
		cell.setValue$("what is this");
		return cell;
	}

	@Message
	public float preferencesTable$heightForRow$inGroup$withProposedHeight$(
			UIPreferencesTable table, int row, int group, float proposedHeight) {
		Logger.debug("PrefTable.heightForRowInGroup()");
		return 48;
	}

	@Message
	public byte preferencesTable$isLabelGroup$(UIPreferencesTable table, int group) {
		Logger.debug("PrefTable.isLabelGroup()");
		return Static.NO;
	}

	@Message
	public UIPreferencesTableCell preferencesTable$cellForRow$inGroup$(UIPreferencesTable table,
			int row, int group) {
		Logger.debug("PrefTable.cellForGroupAndRow()");
		UIPreferencesTextTableCell cell = new UIPreferencesTextTableCell();
		cell.init();
		cell.setTitle$("Todo cell");
		cell.setShowSelection$(Static.YES);
		cell.setValue$("what is this cell");
		return cell;

	}
}
