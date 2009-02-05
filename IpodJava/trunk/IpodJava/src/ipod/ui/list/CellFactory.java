/**
 * (c) 2009 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package ipod.ui.list;

import obc.UITableViewCell;

public interface CellFactory {
	public UITableViewCell createCell(String identifier);
	public void fillCellWithData(UITableViewCell cell, int row);
}
