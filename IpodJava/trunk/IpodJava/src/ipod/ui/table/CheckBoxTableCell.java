/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package ipod.ui.table;

import ipod.base.Logger;
import ipod.ui.ToggleButton;
import ipod.ui.events.ActionListener;
import ipod.ui.events.Event;
import joc.Static;
import obc.CGRect;
import obc.CGSize;
import obc.UIImageAndTextTableCell;
import obc.UITableCell;

public class CheckBoxTableCell extends UIImageAndTextTableCell {

	private ToggleButton toggle;
	private TableModel tableModel;
	private int row, col;

	public CheckBoxTableCell(TableModel tableModel) {
		toggle = new ToggleButton(false);
		this.tableModel = tableModel;
	}

	public UITableCell init(CGSize cellSize) {
		super.init();
		Logger.debug("toggle size = " + toggle.size().width + "," + toggle.size().height
				+ " cell: " + cellSize.width + "," + cellSize.height);
		toggle.setFrame$(new CGRect((cellSize.width - toggle.size().width) / 2,
				(cellSize.height - toggle.size().height) / 2, toggle.size().width,
				toggle.size().height));
		setFrame$(toggle.frame());
		addSubview$(toggle);
		setEnabled$(Static.YES);
		toggle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(Event event) {
				tableModel.updateData(row, col, getContentValue());
			}
		});
		return this;
	}

	@Override
	public UITableCell init() {
		return init(toggle.size());
	}

	public void updateWithCell(int row, int col) {
		this.row = row;
		this.col = col;
		toggle.setValue((Boolean) tableModel.getData(row, col));
	}

	public Boolean getContentValue() {
		return toggle.getValue();
	}

}
