/**
 * (c) 2009 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package ipod.todo;

import ipod.ui.ToggleButton;
import ipod.ui.events.ActionListener;
import ipod.ui.events.Event;
import ipod.ui.list.CellFactory;
import ipod.ui.list.ListModel;

import java.text.SimpleDateFormat;
import java.util.Date;

import obc.CGRect;
import obc.UIColor;
import obc.UIFont;
import obc.UILabel;
import obc.UITableViewCell;

public class TodoCellViewFactory implements CellFactory {

	private static final SimpleDateFormat GERMAN_DATE = new SimpleDateFormat("dd.MM.yyyy");
	private ListModel<Todo> model;

	private static class TodoCell extends UITableViewCell {
		private ToggleButton toggle;
		private UILabel dateLabel;
		private UILabel titleLabel;
		private int row;
		private ListModel<Todo> model;

		public TodoCell(ListModel<Todo> listmodel) {
			this.model = listmodel;
			float height = 40;
			init();
			// a toggle button on the left
			toggle = new ToggleButton();
			toggle.addActionListener(new ActionListener() {
				public void actionPerformed(Event event) {
					Todo todo = model.get(row);
					todo.completed = toggle.getValue() ? new Date() : null;
					model.updateItemAt(row, model.get(row));
				}
			});
			float offset = (height - toggle.size().height) / 2;
			toggle.setFrame$(new CGRect(offset, offset, toggle.size().width, toggle.size().height));
			addSubview$(toggle);

			// two labels on the right
			// the title...
			titleLabel = new UILabel();
			titleLabel.initWithFrame$(new CGRect(height, 17, 320 - height, height - 17));
			titleLabel.setFont$(UIFont.$boldSystemFontOfSize$(height - 22));
			addSubview$(titleLabel);
			// ... and the date
			dateLabel = new UILabel();
			dateLabel.initWithFrame$(new CGRect(height, 2, 320 - height, 10));
			dateLabel.setFont$(UIFont.$boldSystemFontOfSize$(10));
			dateLabel.setTextColor$(UIColor.$lightGrayColor());
			addSubview$(dateLabel);
		}

		public void initWithRow(int row) {
			Todo todo = model.get(row);
			toggle.setValue(todo.isFinished());
			dateLabel.setText$(GERMAN_DATE.format(todo.duedate));
			titleLabel.setText$(todo.title);
			this.row = row;
		}
	}

	public TodoCellViewFactory(ListModel<Todo> model) {
		this.model = model;
	}

	public UITableViewCell createCell(String identifier) {
		return new TodoCell(model);
	}

	public void fillCellWithData(UITableViewCell cell, int row) {
		((TodoCell) cell).initWithRow(row);
	}

}
