/**
 * (c) 2009 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package ipod.upnpcontrol;

import java.util.HashMap;

public class HttpConnection {

	private HashMap<String, String> headerMap = new HashMap<String, String>();
	private String method;
	
	public HttpConnection() {
		headerMap.put("Connection:", "close");
	}
}
