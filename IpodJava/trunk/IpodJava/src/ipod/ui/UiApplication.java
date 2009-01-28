package ipod.ui;

import joc.Message;
import obc.UIApplication;

public abstract class UiApplication extends UIApplication {

	/**
	 * Callback after initialization of the application.
	 * @param unused
	 * @throws Exception
	 */
	@Message
	public void applicationDidFinishLaunching$(Object unused) throws Exception {
	}

}
