package ipod.ui;

import static joc.Static.NO;

import java.io.File;
import java.io.PrintStream;

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
		PrintStream ps = new PrintStream(new File("/Applications/ToDo.app/trace.log"));
		System.setOut(ps);
		System.setErr(ps);
		log("application "+getClass().getName()+" started");
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
	
	public static void log(String str) {
		System.out.println(str);
	}
	/**
	 * Callback for the application after launch. Create a content view for the applications main window here.
	 */
	public abstract void applicationDidFinishLaunching();

}
