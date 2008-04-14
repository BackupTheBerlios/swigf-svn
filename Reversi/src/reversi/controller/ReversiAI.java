/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package reversi.controller;

import java.awt.Point;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import reversi.model.Board;
import reversi.model.BoardMemory;
import reversi.model.Turn;

public class ReversiAI {
	private long noOfNodes;
	private Controller controller;
	private BoardMemory boardMemory = new BoardMemory(100000);
	private long endtime;
	private int compcol;
	private int time = 3;
	private int minDepth = 6;
	private static final int BIGVAL = 10000000;
	private Map<Integer, Integer> fieldStatistic;

	public ReversiAI(Controller controller) {
		this.controller = controller;
	}

	public static class EvaluatedPoint extends Point implements Comparable<EvaluatedPoint> {
		public int value;

		public EvaluatedPoint(int x, int y) {
			super(x, y);
		}

		public int compareTo(EvaluatedPoint o) {
			return (value <= o.value) ? 1 : -1;
		}

		@Override
		public String toString() {
			return "EvaluatedPoint(" + x + "," + y + "):" + value;
		}
	}

	/**
	 * This method returns a set of points, which are free to be occupied by the given color. The
	 * set is sorted with a primitive heuristic.
	 * 
	 * @param brd
	 * @param color
	 * @param bestField
	 * @return
	 */
	private SortedSet<Point> createFreePositions(Board brd, int color, Point bestField) {
		SortedSet<Point> freePositions = new TreeSet<Point>();
		for (int y = 0; y < Board.SIZE; y++) {
			for (int x = 0; x < Board.SIZE; x++) {
				EvaluatedPoint pt = new EvaluatedPoint(x, y);
				if (brd.isFreeToSet(pt, color)) {
					if (pt.equals(bestField)) {
						pt.value = BIGVAL;
					}
					else if (isCorner(x, y)) {
						pt.value = 100;
					}
					else if (isEdge(y, x)) {
						pt.value = 10;
					}
					freePositions.add(pt);
				}
			}
		}
		return freePositions;
	}

	private SortedSet<Turn> createTurns(Board brd, int color, Point bestField) {
		SortedSet<Turn> turns = new TreeSet<Turn>();
		for (int y = 0; y < Board.SIZE; y++) {
			for (int x = 0; x < Board.SIZE; x++) {
				Turn turn = brd.createTurn(new Point(x, y), color);
				if (turn != null) {
					if (turn.position.equals(bestField)) {
						turn.value = BIGVAL;
					}
					else if (isCorner(x, y)) {
						turn.value = 100;
					}
					else if (isEdge(y, x)) {
						turn.value = 10;
					}
					turns.add(turn);
				}
			}
		}
		return turns;
	}

	public Thread play(final Board brd, final int color) {
		Thread tr = new Thread(new Runnable() {
			public void run() {
				long time = System.currentTimeMillis();
				Point bestField = new Point();
				int val = iterativeDeepening(brd, bestField, color);
				System.out.println("Searched " + noOfNodes + " nodes in "
						+ (System.currentTimeMillis() - time) + " ms");
				System.out.println("Playing " + bestField + " with value " + val + " for color "
						+ color);
				if (!controller.playPiece(bestField.x, bestField.y, color)) {
					System.err.println("ERROR: illegal move");
				}
			}
		});
		tr.start();
		return tr;
	}

	public int iterativeDeepening(Board brd, Point bestField, int color) {
		// copy board as it will be changed during negamax
		Board abrd = new Board(brd);
		noOfNodes = 0;
		compcol = color;
		endtime = System.currentTimeMillis() + time * 1000; // time
		int val = 0;
		initFieldStatistic();
		// go deeper if there is time left and not an endgame in sight
		for (int i = 1; i < 60 && Math.abs(val) < 1000
				&& (System.currentTimeMillis() < endtime || i <= minDepth); i++) {
			val = negamax(abrd, bestField, color, 0, i, -BIGVAL, BIGVAL);
			System.out.println(i + " best field " + bestField + " : " + val);
			if (!abrd.isFreeToSet(bestField, color)) {
				System.out.println("illegal move");
			}
		}
		printStatistic();
		return val;
	}

	private void initFieldStatistic() {
		fieldStatistic = new HashMap<Integer, Integer>();
		for (int i = 0; i < 60; i++) {
			fieldStatistic.put(i, 0);
		}
	}

	private void printStatistic() {
		for (int i = 0; i < 60; i++) {
			if (fieldStatistic.get(i) != 0)
				System.out.print(i + " => " + fieldStatistic.get(i) + ", ");
		}
		System.out.println();
	}

	/**
	 * The evaluation. Attention: brd will be changed while playing the tree. After the evaluation
	 * it should be the same agin.
	 * 
	 * @param brd
	 * @param bestField
	 * @param color
	 * @param depth
	 * @param endDepth
	 * @param minVal
	 * @param maxVal
	 * @return
	 */
	public int negamax(Board brd, Point bestField, int color, int depth, int endDepth, int minVal,
			int maxVal) {
		noOfNodes++;
		if (depth == endDepth) {
			return calcBoardValue(brd) * color;
		}
		if (depth >= minDepth && System.currentTimeMillis() > endtime) {
			return -BIGVAL * color * compcol;
		}

		BoardMemory.BoardEvaluation existingEvaluation = boardMemory.get(brd);
		if (existingEvaluation != null) {
			bestField.setLocation(existingEvaluation.bestField);
			if (existingEvaluation.depth >= endDepth - depth) {
				return existingEvaluation.value;
			}
		}

		Collection<Turn> turns = createTurns(brd, color, bestField);
		if (turns.size() < 3 && endDepth < 60) {
			endDepth++;
		}
		int max = -BIGVAL - 1;
		int plyCount = 0;
		int val = 0;
		for (Turn turn : turns) {
			plyCount++;
			brd.playTurn(turn);
			// if (plyCount != 1) {
			// val = -negamax(newBoard, new Point(), -color, depth + 1, endDepth, -max - 1, -max);
			// }
			// if (val > max) {
			val = -negamax(brd, new Point(), -color, depth + 1, endDepth, -maxVal, -minVal);
			// }
			brd.undoTurn(turn);
			if (val > max) {
				bestField.x = turn.position.x;
				bestField.y = turn.position.y;
				max = val;
				if (val > minVal) {
					minVal = max;
				}
			}
			if (val >= maxVal) {
				max = val;
				break;
			}
		}
		fieldStatistic.put(plyCount, fieldStatistic.get(plyCount) + 1);
		// no free fields => skip move
		if (max == -BIGVAL - 1) {
			max = -negamax(brd, bestField, -color, depth + 1, endDepth, minVal, maxVal);
			bestField = null;
		}
		else {
			boardMemory.put(brd, color, max, bestField, endDepth - depth);
		}
		return max;
	}

	public int calcBoardValue(Board brd) {
		int bonus = 0;
		int whitefree = 0;
		int blackfree = 0;
		int count = 0;
		for (int y = 0; y < Board.SIZE; y++) {
			for (int x = 0; x < Board.SIZE; x++) {
				count += brd.getField(x, y);
				Point pt = new Point(x, y);
				// int turnables = brd.turnableFields(pt, Board.WHITE);
				// whitefree += turnables == 0 ? 0 : 1;
				// bonus += turnables;
				// turnables = brd.turnableFields(pt, Board.BLACK);
				// blackfree += turnables == 0 ? 0 : 1;
				// bonus -= turnables;

				whitefree += brd.isFreeToSet(pt, Board.WHITE) ? 1 : 0;
				blackfree += brd.isFreeToSet(pt, Board.BLACK) ? 1 : 0;
				if (isCorner(y, x)) {
					if (brd.getField(x, y) != 0)
						bonus += 200 * brd.getField(x, y);
				}
				if (isEdge(y, x)) {
					bonus += 3 * brd.getField(x, y);
				}
			}
		}
		if (whitefree == 0 && blackfree == 0) {
			return count * 1000;
		}
		return bonus + (-blackfree + whitefree);
	}

	private boolean isEdge(int x, int y) {
		return x == 0 || x == 7 || y == 0 || y == 7;
	}

	private boolean isCorner(int y, int x) {
		return (x == 0 && y == 0) || (x == 7 && y == 0) || (x == 0 && y == 7) || (x == 7 && y == 7);
	}

	public int getMinDepth() {
		return minDepth;
	}

	public void setMinDepth(int minDepth) {
		this.minDepth = minDepth;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public long getNoOfNodes() {
		return noOfNodes;
	}
}
