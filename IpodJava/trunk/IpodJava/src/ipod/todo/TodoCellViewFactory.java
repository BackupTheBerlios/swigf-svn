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
import java.util.GregorianCalendar;

import obc.CGRect;
import obc.UIColor;
import obc.UIFont;
import obc.UILabel;
import obc.UITableViewCell;

public class TodoCellViewFactory implements CellFactory {

	private static final SimpleDateFormat GERMAN_DATE = new SimpleDateFormat("dd.MM.yyyy");
	private ListModel<Todo> model;
	private float rowHeight;

	private static class TodoCell extends UITableViewCell {
		private ToggleButton toggle;
		private UILabel dateLabel;
		private UILabel titleLabel;
		private int row;
		private int section;
		private ListModel<Todo> model;

		public TodoCell(ListModel<Todo> listmodel, float height) {
			this.model = listmodel;
			init();
			// a toggle button on the left
			toggle = new ToggleButton();
			toggle.addActionListener(new ActionListener() {
				public void actionPerformed(Event event) {
					Todo todo = model.get(section, row);
					todo.completed = toggle.getValue() ? new Date() : null;
					model.updateItemAt(section, row, model.get(section, row));
				}
			});
			float offset = (height - toggle.size().height) / 2 + 1;
			toggle.setFrame$(new CGRect(offset, offset, toggle.size().width, toggle.size().height));
			addSubview$(toggle);

			// two labels on the right
			// date and ...
			dateLabel = new UILabel();
			dateLabel.initWithFrame$(new CGRect(height, 2, 320 - height, 16));
			dateLabel.setFont$(UIFont.$boldSystemFontOfSize$(10));
			dateLabel.setTextColor$(UIColor.$lightGrayColor());
			addSubview$(dateLabel);
			// ... the title
			titleLabel = new UILabel();
			titleLabel.initWithFrame$(new CGRect(height, 17, 320 - 2*height, height - 17));
			titleLabel.setFont$(UIFont.$boldSystemFontOfSize$(height - 22));
			addSubview$(titleLabel);
		}

		public void initWith(int section, int row) {
			Todo todo = model.get(section, row);
			toggle.setValue(todo.isFinished());
			// mark date red if reached and not completed
			if (todo.completed == null
					&& todo.duedate.compareTo(GregorianCalendar.getInstance().getTime()) < 0) {
				dateLabel.setTextColor$(UIColor.$redColor());
			}
			else {
				dateLabel.setTextColor$(UIColor.$lightGrayColor());
			}
			String dateLabelText = GERMAN_DATE.format(todo.duedate);
			if (todo.completed != null) {
				dateLabelText += ", erledigt: " + GERMAN_DATE.format(todo.completed);
			}
			dateLabel.setText$(dateLabelText);
			titleLabel.setText$(todo.title);
			// setEditing$(Static.YES);
			// setShowingDeleteConfirmation$(Static.YES);
			this.row = row;
			this.section = section;
		}
	}

	public TodoCellViewFactory(ListModel<Todo> model, float rowHeight) {
		this.model = model;
		this.rowHeight = rowHeight;
	}

	public UITableViewCell createCell(String identifier) {
		return new TodoCell(model, rowHeight);
	}

	public void fillCellWithData(UITableViewCell cell, int section, int row) {
		((TodoCell) cell).initWith(section, row);
	}

}
