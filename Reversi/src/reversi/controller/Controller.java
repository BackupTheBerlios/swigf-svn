/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package reversi.controller;

import java.awt.Point;
import java.util.LinkedList;

import reversi.gui.ReversiFrame;
import reversi.model.Board;

public class Controller {
	public static final int HUMAN = 1;
	public static final int COMPUTER = 2;
	private int[] player = new int[2];
	private Board brd;
	private ReversiFrame frame;
	private ReversiAI reversiAI;
	private LinkedList<Board> history = new LinkedList<Board>();

	public Controller(Board brd, ReversiFrame frame) {
		this.brd = brd;
		this.frame = frame;
		player[0] = COMPUTER;
		player[1] = HUMAN;
//		player[0] = HUMAN;
//		player[1] = COMPUTER;
		reversiAI = new ReversiAI(this);
	}

	public void setPlayerMode(int color, int mode) {
		player[(color + 1) / 2] = mode;
	}

	public int getPlayerMode(int color) {
		return player[(color + 1) / 2];
	}

	public int getMovingColor() {
		return brd.getMovingColor();
	}

	public boolean playPiece(int x, int y, int color) {
		if (brd.getMovingColor() == color) {
			Point fieldPos = new Point(x, y);
			if (brd.isFreeToSet(fieldPos, color)) {
				history.add(new Board(brd));
				brd.setPiece(x, y, color);
				// now check, who plays next
				if (brd.colorCanPlay(-color)) {
					brd.setMovingColor(-brd.getMovingColor());
				}
				else {
					if (!brd.colorCanPlay(color)) {
						frame.endOfGame();
						return true;
					}
				}
				if (getPlayerMode(brd.getMovingColor()) == COMPUTER) {
					reversiAI.play(brd, brd.getMovingColor());
				}
				return true;
			}
		}
		return false;
	}

	public void undo() {
		if (history.size() > 0) {
			System.out.println("undo");
			brd.copy(history.getLast());
			history.removeLast();
		}
	}
	
	public void newGame() {
		brd.copy(new Board());
	}
}
