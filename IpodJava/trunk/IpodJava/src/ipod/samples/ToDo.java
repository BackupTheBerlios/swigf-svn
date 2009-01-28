package ipod.samples;

import ipod.ui.SimpleUiApplication;
import ipod.ui.UiNavigationBar;
import ipod.ui.UiView;

public class ToDo extends SimpleUiApplication {

	@Override
	public void applicationDidFinishLaunching() {
		UiView view = new UiView(getWindow().bounds());
		UiNavigationBar navbar = new UiNavigationBar("ToDo Sample");

		navbar.pushNavItem("item 1");
		navbar.pushNavItem("item 2");
		navbar.setRightButton("+", UiNavigationBar.DEFAULT_BUTTON_STYLE);
		view.addNavigationBar(navbar);
		addView(view);
	}

}
