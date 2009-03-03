/**
 * (c) 2009 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package ipod.upnpcontrol;

import obc.UIImage;
import obc.UILabel;
import obc.UITableViewCell;
import ipod.ui.list.CellFactory;

public class MusicTitleCellFactory implements CellFactory{

	private static class TitleCell extends UITableViewCell {
		UIImage image;
		UILabel artist;
		UILabel songTitle;
		UILabel albumTitle;
		// UIImage.$imageWithData$(data);
		
	}
	
	public UITableViewCell createCell(String identifier) {
		return new TitleCell();
	}

	public void fillCellWithData(UITableViewCell cell, int section, int row) {
	}

}
