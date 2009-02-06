/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package ipod.todo;

public class Sequence {
	private static int sequenceid = 0;

	private Sequence() {
	}

	public static int next() {
		sequenceid++;
		return sequenceid;
	}

	public static void initMax(int id) {
		sequenceid = Math.max(id, sequenceid);
	}
}
