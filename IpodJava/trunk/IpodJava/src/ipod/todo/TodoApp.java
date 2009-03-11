package ipod.todo;

import ipod.base.Logger;
import ipod.ui.AlertSheet;
import ipod.ui.NavigationBar;
import ipod.ui.SimpleApplication;
import ipod.ui.SimpleStackView;
import ipod.ui.events.ActionListener;
import ipod.ui.events.Event;
import ipod.ui.events.ListSelectionEvent;
import ipod.ui.events.ListSelectionListener;
import ipod.ui.list.ListModel;
import ipod.ui.list.ListView;

import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;

import joc.Message;
import joc.Static;
import obc.CGRect;

public class TodoApp extends SimpleApplication {
	@Override
	public void applicationDidFinishLaunching() throws Exception {
		// The window
		final SimpleStackView view = new SimpleStackView(getWindow().bounds());

		// TODO These methods of the model should be implemented in the model. Binding to DAO could
		// be achieved by Events.
		// Model
		final ListModel<Todo> listModel = new ListModel<Todo>() {
			@Override
			public void addItem(Todo item) {
				addSilently(item);
				TodoDao.getInstance().createTodo(item);
				fireUpdate();
			}

			@Override
			public Todo removeItemAt(int section, int row) {
				Todo todo = get(section, row);
				removeSilently(section, row);
				TodoDao.getInstance().remove(todo);
				fireUpdate();
				return todo;
			}

			@Override
			public void updateItemAt(int section, int row, Todo newItem) {
				TodoDao.getInstance().update(newItem);
				removeSilently(section, row);
				addSilently(newItem);
				fireUpdate();
			}
		};
		listModel.setComparator(new Comparator<Todo>() {
			public int compare(Todo o1, Todo o2) {
				if (o1.completed!=null && o2.completed==null) {
					return 1;
				}
				if (o2.completed!=null && o1.completed==null) {
					return -1;
				}
				int priorityComparison = o1.priority - o2.priority;
				if (priorityComparison != 0) {
					return priorityComparison;
				}
				if (o1.completed != null && o2.completed != null) {
					int completeComparison = o2.completed.compareTo(o1.completed);
					if (completeComparison != 0) {
						return completeComparison;
					}
				}
				return o1.duedate.compareTo(o2.duedate);
			}
		});
		// load contents for table model
		TodoDao.getInstance().loadTodos(listModel);

		// Table
		final ListView<ListModel<Todo>> table = new ListView<ListModel<Todo>>(listModel, 40);
		table.initWithFrame$(new CGRect(0, 0, 320, 480));
		table.setCellViewFactory(new TodoCellViewFactory(listModel, 40));
		table.setUserInteractionEnabled$(Static.YES);
		table.setEnabledGestures$(Static.YES);
		table.addSelectionListener(new ListSelectionListener() {
			public void selectItem(ListSelectionEvent event) {
				Logger.debug("Edit row " + event.getListIndex());
				//TodoPrefTable prefs = new TodoPrefTable();
				//view.addSubview$(prefs);
			}
		});
		table.addDeletionListener(new ListSelectionListener() {
			public void selectItem(ListSelectionEvent event) {
				Logger.debug("Delete row " + event.getListIndex());
				listModel.removeItemAt(event.getSectionIndex(), event.getListIndex());
				table.reloadData();
			}
		});
		listModel.addUpdateListener(new ActionListener() {
			public void actionPerformed(Event event) {
				Logger.debug("Table updated");
				listModel.sort();
				table.reloadData();
			}
		});
		view.addCenterView(table);

		// Navigation bar
		final NavigationBar navbar = new NavigationBar("Todo", NavigationBar.BW_STYLE);
		navbar.setButtons("Löschen", NavigationBar.RED_BUTTON_STYLE, "+",
				NavigationBar.DEFAULT_BUTTON_STYLE);
		navbar.addRightButtonActionListener(new ActionListener() {
			public void actionPerformed(Event event) {
				AlertSheet.requestTextInput(view, "Neues Todo", null, "Neues Todo eingeben",
						new ActionListener() {
							public void actionPerformed(Event event) {
								try {
									Logger.debug("User entered: " + event.getSource());
									Calendar cal = GregorianCalendar.getInstance();
									cal.add(Calendar.DATE, 14);
									Todo todo = new Todo(event.getSource().toString(), 2, cal
											.getTime());
									listModel.addItem(todo);
									table.reloadData();
								}
								catch (Exception e) {
									Logger.error(e);
								}
							}
						});
			}
		});
		navbar.addLeftButtonActionListener(new ActionListener() {
			public void actionPerformed(Event event) {
				if (table.isEditing() == Static.NO) {
					table.setEditing$(Static.YES);
					navbar.setLeftButtonLabel("Fertig", NavigationBar.BLUE_BUTTON_STYLE);
				}
				else {
					table.setEditing$(Static.NO);
					navbar.setLeftButtonLabel("Löschen", NavigationBar.RED_BUTTON_STYLE);
				}
			}
		});
		view.addNavigationBar(navbar);
		// building the window
		view.layout();
		addView(view);
		Logger.debug("Show everything");
	}

	@Message
	public void applicationWillSuspend() {
		Logger.info("Message: Application suspended.");
		super.applicationWillSuspend();
	}

}
