/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package de.swigf.util;

import java.awt.Point;

public class VectorMath {

	private VectorMath() {
		// VectorMath is a library and should not be instantiated
	}

	/**
	 * Returns the <b>square</b> of the distance between two points.
	 * @param pt
	 * @param pt2
	 * @return
	 */
	public static int squaredDistance(Point pt, Point pt2) {
		return (pt.x - pt2.x) * (pt.x - pt2.x) + (pt.y - pt2.y) * (pt.y - pt2.y);
	}

}
