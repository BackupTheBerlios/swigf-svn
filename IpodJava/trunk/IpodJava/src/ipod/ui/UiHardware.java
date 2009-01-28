package ipod.ui;

import obc.CGRect;
import obc.UIHardware;

public class UiHardware {

	public static CGRect getFullScreenApplicationContentRect() {
		return UIHardware.$fullScreenApplicationContentRect();
	}
	
	public static int getDeviceOrientation(byte arg0) {
		return UIHardware.$deviceOrientation$(arg0);
	}
}
