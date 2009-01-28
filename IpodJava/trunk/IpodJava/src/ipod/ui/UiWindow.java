package ipod.ui;

import obc.UIWindow;

public class UiWindow extends UiView {
	
	public UiWindow() {
		this.delegate = new UIWindow();
		getDelegate().initWithContentRect
	}
	
	public UIWindow getDelegate() {
		return (UIWindow) delegate;
	}

}
