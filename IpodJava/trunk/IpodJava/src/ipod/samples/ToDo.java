package ipod.samples;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import ipod.base.Logger;
import ipod.ui.AlertSheet;
import ipod.ui.NavigationBar;
import ipod.ui.SimpleApplication;
import ipod.ui.SimpleStackView;
import ipod.ui.events.ActionListener;
import ipod.ui.events.Event;
import ipod.ui.events.ListSelectionEvent;
import ipod.ui.events.ListSelectionListener;
import ipod.ui.table.TableModel;
import ipod.ui.table.TableView;

public class ToDo extends SimpleApplication {

	private static class Todo {
		String title;
		Date duedate;
		Boolean finished;

		public Todo(String title, Date duedate) {
			this.title = title;
			this.duedate = duedate;
			this.finished = new Boolean(false);
		}
	}

	private static class TodoTable implements TableModel {
		public List<Todo> data = new LinkedList<Todo>();

		public int getColumnCount() {
			return 2;
		}

		public int getColumnWidth(int col) {
			if (col == 0) {
				return 40;
			}
			return 288;
		}

		public Object getData(int row, int col) {
			Todo todo = data.get(row);
			if (col == 0) {
				return todo.finished;
			}
			return todo.title;
		}

		public int getRowCount() {
			return data.size();
		}

		public void updateData(int row, int col, Object value) {
			data.get(row).finished = (Boolean) value;
		}

	};

	@Override
	public void applicationDidFinishLaunching() {
		// a table for contents
		final TodoTable tableModel = new TodoTable();
		final SimpleStackView view = new SimpleStackView(getWindow().bounds());
		// navigation bar
		final NavigationBar navbar = new NavigationBar("Todo", NavigationBar.BW_STYLE);
		view.addNavigationBar(navbar);
		navbar.setRightButton("+", NavigationBar.DEFAULT_BUTTON_STYLE);
		final TableView table = new TableView(tableModel, 40);
		navbar.addRightButtonActionListener(new ActionListener() {
			public void actionPerformed(Event event) {
				AlertSheet.confirmUserRequest(view, "New todo", null, "enter todo here",
						new ActionListener() {
							public void actionPerformed(Event event) {
								Logger.debug("User entered: " + event.getSource());
								tableModel.data
										.add(new Todo(event.getSource().toString(), new Date()));
								table.reloadData();
							}
						});
			}
		});
		table.addListSelectionListener(new ListSelectionListener() {
			public void selectItem(ListSelectionEvent event) {
				navbar.pushNavItem(event.getItem());
			}
		});
		view.addCenterView(table);
		// add view to window
		view.layout();
		// view.addViewWithFrame(new CheckBoxTableCell().init(), new CGRect(100, 100, 32, 32));
		// view.addViewWithFrame(new TextField(18, "Label: ", "enter text here"), new CGRect(10,
		// 100,
		// 300, 24));
		addView(view);
	}

}
