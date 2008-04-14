/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package reversi.model;

import java.awt.Point;
import java.util.Arrays;

import junit.framework.TestCase;

public class BoardTests extends TestCase {

	public void testIndex() {
		Board brd = new Board();
		assertEquals(18, brd.indexFromPt(2, 1));
	}

	public void testOutOfBounds() {
		Board brd = new Board();
		assertTrue(brd.isOutOfBounds(new Point(-1, 0)));
		assertTrue(brd.isOutOfBounds(new Point(0, -1)));
		assertTrue(brd.isOutOfBounds(new Point(8, 0)));
		assertTrue(brd.isOutOfBounds(new Point(0, 8)));
		assertTrue(brd.isOutOfBounds(new Point(8, 8)));
		assertTrue(brd.isOutOfBounds(new Point(-5, -5)));
		assertFalse(brd.isOutOfBounds(new Point(0, 0)));
		assertFalse(brd.isOutOfBounds(new Point(7, 7)));
	}

	public void testIsFreeToSet() {
		Board brd = new Board();
		brd.setField(3, 3, Board.BLACK);
		brd.setField(4, 4, Board.BLACK);
		brd.setField(5, 5, Board.WHITE);
		brd.setField(2, 3, Board.BLACK);
		assertTrue(brd.isFreeToSet(new Point(2, 2), Board.WHITE));
		assertFalse(brd.isFreeToSet(new Point(6, 6), Board.WHITE));
	}

	public void testCreateTurnInfo() {
		Board brd = new Board();
		brd.setField(3, 3, Board.BLACK);
		brd.setField(4, 4, Board.BLACK);
		brd.setField(5, 5, Board.WHITE);
		brd.setField(2, 3, Board.BLACK);
		brd.setField(5, 4, Board.BLACK);
		brd.setField(5, 3, Board.BLACK);
		brd.setField(5, 2, Board.WHITE);
		brd.setField(4, 2, Board.BLACK);
		brd.setField(3, 2, Board.BLACK);
		System.out.println(brd);
		assertTrue(brd.createTurn(new Point(1, 3), Board.WHITE) == null);
		Turn turnsPerLine = brd.createTurn(new Point(2, 2), Board.WHITE);
		int[] expectedLines = new int[] { 0, 2, 0, 0, 0, 0, 0, 2 };
		assertTrue(Arrays.equals(expectedLines, turnsPerLine.turns));
	}

	public void testTurnAndUndoTurn() {
		Board brd = new Board();
		brd.setField(3, 3, Board.BLACK);
		brd.setField(4, 4, Board.BLACK);
		brd.setField(5, 5, Board.WHITE);
		brd.setField(2, 3, Board.BLACK);
		brd.setField(5, 4, Board.BLACK);
		brd.setField(5, 3, Board.BLACK);
		brd.setField(5, 2, Board.WHITE);
		brd.setField(4, 2, Board.BLACK);
		brd.setField(3, 2, Board.BLACK);
		System.out.println(brd);
		Turn turn = brd.createTurn(new Point(2, 2), Board.WHITE);
		brd.playTurn(turn);
		System.out.println(brd);
		brd.undoTurn(turn);
		System.out.println(brd);
		// compare before and after ...
	}
}
