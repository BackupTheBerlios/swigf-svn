/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package reversi.model;

import java.awt.Point;

public class BoardMemory {

	public static class BoardEvaluation {
		public BoardEvaluation(int hashcode, int value, Point best, int depth) {
			this.value = value;
			this.bestField = best;
			this.hashcode = hashcode;
			this.depth = depth;
		}

		public int depth;
		public int hashcode;
		public int value;
		public Point bestField;
		public int type;
	}

	private BoardEvaluation[] transpositionTable;
	private int size;

	public BoardMemory(int size) {
		this.size = size;
		transpositionTable = new BoardEvaluation[size];
	}

	private int hashToIndex(int hash) {
		return hash % size;
	}

	public void put(Board brd, int color, int value, Point best, int depth) {
		transpositionTable[hashToIndex(brd.hashCode())] = new BoardEvaluation(brd.hashCode(),
				value, best, depth);
	}

	public BoardEvaluation get(Board brd) {
		int index = hashToIndex(brd.hashCode());
		if (transpositionTable[index] != null
				&& transpositionTable[index].hashcode == brd.hashCode()) {
			return transpositionTable[index];
		}
		return null;
	}

	public boolean contains(Board brd) {
		int index = hashToIndex(brd.hashCode());
		if (transpositionTable[index].hashcode == brd.hashCode()) {
			return true;
		}
		else {
			return false;
		}
	}
}
