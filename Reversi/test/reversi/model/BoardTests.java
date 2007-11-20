/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package reversi.model;

import java.awt.Point;

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
	
}
