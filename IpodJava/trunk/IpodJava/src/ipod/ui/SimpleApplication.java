package ipod.ui;

import static joc.Static.NO;
import ipod.base.Logger;
import joc.Message;
import obc.CGRect;
import obc.UIApplication;
import obc.UIHardware;
import obc.UIView;
import obc.UIWindow;

/**
 * Creates an application with a full screen window.
 * 
 * @author Axel
 */
public abstract class SimpleApplication extends UIApplication {

	private UIWindow window;

	@Message
	public void applicationDidFinishLaunching$(Object unused) throws Exception {
		Logger.init(getClass().getSimpleName().toString());
		Logger.info("application "+getClass().getName()+" started");
		// create full screen window
		CGRect outer = UIHardware.$fullScreenApplicationContentRect();
		window = new UIWindow().initWithContentRect$(outer);
		window.orderFront$(this);
		window.makeKey$(this);
		window._setHidden$(NO);
		try {
		applicationDidFinishLaunching();
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public void addView(UIView view) {
		window.setContentView$(view);
	}

	public UIWindow getWindow() {
		return window;
	}
	
	/**
	 * Callback for the application after launch. Create a content view for the applications main window here.
	 */
	public abstract void applicationDidFinishLaunching();

}
