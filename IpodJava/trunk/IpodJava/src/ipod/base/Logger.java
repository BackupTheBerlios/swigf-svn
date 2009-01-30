/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package ipod.base;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	static {
		 try {
			PrintStream ps = new PrintStream(new File("/private/var/tmp/javatrace.log"));
			System.setOut(ps);
			System.setErr(ps);
		}
		catch (IOException exc) {
			exc.printStackTrace();
		}
	}

	private static String componentName;
	private static SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd HH:mm:ss.SSS");

	private Logger() {
	}

	public static void init(String component) {
		componentName = component;
	}

	private static void log(String level, Object msg) {
		StringBuffer prefix = new StringBuffer(formatter
				.format(new Date(System.currentTimeMillis()))).append(" ").append(level)
				.append(" ").append(componentName).append(" - ");
		System.out.println(prefix.append(msg));
		if (msg instanceof Throwable) {
			((Throwable) msg).printStackTrace();
		}
	}

	public static void info(Object msg) {
		log("INFO ", msg);
	}

	public static void debug(Object msg) {
		log("DEBUG", msg);
	}

	public static void error(Object msg) {
		log("ERROR", msg);
	}

}
