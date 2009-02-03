/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package ipod.ui.table;

import ipod.base.Logger;
import ipod.ui.ToggleButton;
import joc.Static;
import obc.CGRect;
import obc.CGSize;
import obc.UIImageAndTextTableCell;
import obc.UITableCell;

public class CheckBoxTableCell extends UIImageAndTextTableCell {

	ToggleButton toggle;

	public CheckBoxTableCell(Boolean b) {
		toggle = new ToggleButton(b);
		
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
		return this;
	}

	@Override
	public UITableCell init() {
		return init(toggle.size());
	}

	public void setContentValue(Boolean value) {
		toggle.setValue(value);
	}

	public Boolean getContentValue() {
		return toggle.getValue();
	}

}
