package ipod.samples;

import obc.CGRect;
import ipod.ui.AlertSheet;
import ipod.ui.NavigationBar;
import ipod.ui.SimpleApplication;
import ipod.ui.SimpleStackView;
import ipod.ui.ToggleButton;
import ipod.ui.events.ActionListener;
import ipod.ui.events.Event;
import ipod.ui.events.ListSelectionEvent;
import ipod.ui.events.ListSelectionListener;
import ipod.ui.table.TableModel;
import ipod.ui.table.TableView;

public class ToDo extends SimpleApplication {

	@Override
	public void applicationDidFinishLaunching() {
		final SimpleStackView view = new SimpleStackView(getWindow().bounds());
		// navigation bar
		final NavigationBar navbar = new NavigationBar("ToDo Sample",
				NavigationBar.TRANSPARENT_STYLE);
		view.addNavigationBar(navbar);
		navbar.pushNavItem("My Table");
		navbar.setRightButton("+", NavigationBar.DEFAULT_BUTTON_STYLE);
		navbar.addRightButtonActionListener(new ActionListener() {
			public void actionPerformed(Event event) {
				AlertSheet.showMessage(view, "My Message",
						"You pressed the '+' button. Didn't you, little bastard?");
			}
		});
		// a table for contents
		TableModel tableModel = new TableModel() {

			@Override
			public int getColumnCount() {
				return 3;
			}

			@Override
			public int getColumnWidth(int col) {
				return 100;
			}

			@Override
			public Object getData(int row, int col) {
				if (col == 0) {
					return new Boolean(col % 2 == 0);
				}
				return "cell(" + row + "," + col + ")";
			}

			@Override
			public int getRowCount() {
				return 50;
			}

		};
		TableView table = new TableView(tableModel, 32);
		table.addListSelectionListener(new ListSelectionListener() {
			public void selectItem(ListSelectionEvent event) {
				navbar.pushNavItem(event.getItem());
			}
		});
		view.addCenterView(table);
		// add view to window
		view.layout();
		// view.addViewWithFrame(new TextField(18, "Label: ", "enter text here"), new CGRect(10,
		// 100,
		// 300, 24));
		view.addViewWithFrame(new ToggleButton(), new CGRect(100, 100, 24, 24));
		addView(view);
	}

}
