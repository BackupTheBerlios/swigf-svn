package ipod.samples;

import java.util.ArrayList;
import java.util.List;

import ipod.ui.AlertSheet;
import ipod.ui.SimpleApplication;
import ipod.ui.NavigationBar;
import ipod.ui.SimpleStackView;
import ipod.ui.TableView;
import ipod.ui.events.ActionListener;
import ipod.ui.events.Event;
import ipod.ui.events.ListSelectionEvent;
import ipod.ui.events.ListSelectionListener;

public class ToDo extends SimpleApplication {

	@Override
	public void applicationDidFinishLaunching() {
		SimpleStackView view = new SimpleStackView(getWindow().bounds());
		// navigation bar
		final NavigationBar navbar = new NavigationBar("ToDo Sample");
		navbar.pushNavItem("My Table");
		navbar.setRightButton("+", NavigationBar.DEFAULT_BUTTON_STYLE);
		navbar.addRightButtonActionListener(new ActionListener() {
			public void actionPerformed(Event event) {
				AlertSheet.showMessage("My Message", "You pressed the '+' button. Didn't you, little bastard?");
			}
		});
		view.addNavigationBar(navbar);
		// a table for contents
		List<String> data = new ArrayList<String>();
		for (int i=0; i<50; i++) {
			data.add("Entry "+i);
		}
		TableView<String> table = new TableView<String>(data);
		table.setDisclosureForAllElements(TableView.BLUE_CIRCLE_DISCLOSURE_STYLE, true);
		table.addListSelectionListener(new ListSelectionListener(){
			public void selectItem(ListSelectionEvent event) {
				navbar.pushNavItem(event.getItem());
			}
		});
		view.addCenterView(table);
		// add view to window
		addView(view);
	}

}
