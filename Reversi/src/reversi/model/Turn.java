/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package reversi.model;

import java.awt.Point;

/**
 * A turn describes a position on the board and how many pieces can be turned if occupied by the
 * given color.
 * @author L123073
 *
 */
public class Turn implements Comparable<Turn>{
	public int[] turns;
	public Point position;
	public int value;
	public int color;
	
	public Turn(Point pt, int[] turns, int color) {
		this.turns = turns;
		this.position = pt;
		this.color = color;
	}
	
	public int compareTo(Turn o) {
		return (value <= o.value) ? 1 : -1;
	}

	public void play(Board brd) {
		
	}
}
