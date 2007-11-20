/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package reversi.controller;

import junit.framework.TestCase;
import reversi.model.Board;

public class ReversiAITests extends TestCase {

	public void testNodeNumber() throws InterruptedException {
		Board brd = new Board();
		brd.setPiece(5, 3, Board.WHITE);
		brd.setPiece(3, 2, Board.BLACK);
		brd.setPiece(2, 3, Board.WHITE);
		ReversiAI ai = new ReversiAI(new Controller(brd, null));
		Thread tr = ai.play(brd, Board.BLACK);
		tr.join();
		System.out.println(brd);
		System.out.println("done");
	}

	int[] cornerProb = new int[] { 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, -1,
			1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			1, 1, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, -1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	public void testKeepCorner() throws InterruptedException {
		Board brd = createBoard(cornerProb);
		System.out.println(brd);
		ReversiAI ai = new ReversiAI(new Controller(brd, null));
		Thread tr = ai.play(brd, Board.BLACK);
		tr.join();
		System.out.println(brd);
	}

	private Board createBoard(int[] fields) {
		Board brd = new Board();
		for (int i = 0; i < fields.length; i++) {
			brd.setField(i, fields[i]);
		}
		return brd;
	}

	private void printBoard() {
		Board brd = new Board();
		brd.load("test.rev");
		System.out.println(brd);
		System.out.print("{");
		for (int i = 0; i < 16 * 8; i++) {
			if (i != 0)
				System.out.print(", ");
			if (i % 16 == 0)
				System.out.println();
			System.out.print(brd.getField(i % 16, i / 16));
		}
		System.out.println("}");

	}

}
