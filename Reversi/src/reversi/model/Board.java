/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package reversi.model;

import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Board {
	public interface ModelChangeListener {
		public void modelUpdate();
	}

	public static final int WHITE = -1;
	public static final int BLACK = 1;
	public static final int EMPTY = 0;

	public static final int SIZE = 8;
	private static final int LINESIZE = 2 * SIZE;

	private static final int[] directions = new int[] { -1, 1, -LINESIZE, LINESIZE, -LINESIZE - 1,
			-LINESIZE + 1, LINESIZE - 1, LINESIZE + 1 };

	private static final int[] fieldHashs;
	static {
		Random rand = new Random(0);
		fieldHashs = new int[SIZE * LINESIZE];
		for (int i = 0; i < SIZE * LINESIZE; i++) {
			fieldHashs[i] = rand.nextInt(Integer.MAX_VALUE);
		}
	}

	private int fields[];
	private List<ModelChangeListener> listeners = new LinkedList<ModelChangeListener>();
	private int hashValue;
	private int movingColor;

	public Board() {
		fields = new int[SIZE * LINESIZE];
		init();
		movingColor = BLACK;
	}

	public Board(Board brd) {
		movingColor = brd.movingColor;
		fields = new int[brd.fields.length];
		hashValue = brd.hashValue;
		System.arraycopy(brd.fields, 0, fields, 0, brd.fields.length);
	}

	public void init() {
		setField(3, 3, WHITE);
		setField(4, 4, WHITE);
		setField(3, 4, BLACK);
		setField(4, 3, BLACK);
	}

	public void addModelChangeListener(ModelChangeListener listener) {
		listeners.add(listener);
	}

	private void fireModelChange() {
		for (ModelChangeListener listener : listeners) {
			listener.modelUpdate();
		}
	}

	public void setField(int index, int col) {
		fields[index] = col;
		hashValue ^= fieldHashs[index];
	}

	public void setField(int x, int y, int col) {
		setField(indexFromPt(x, y), col);
	}

	int indexFromPt(int x, int y) {
		return x | (y << 4);
	}

	int indexFromPt(Point pt) {
		return indexFromPt(pt.x, pt.y);
	}

	private int getField(int index) {
		return fields[index];
	}

	public int getField(int x, int y) {
		return getField(indexFromPt(x, y));
	}

	public int getField(Point pt) {
		return getField(pt.x, pt.y);
	}

	private boolean isOutOfBounds(int index) {
		return (index & 0xff88) != 0;
	}

	public boolean isOutOfBounds(Point pt) {
		return isOutOfBounds(indexFromPt(pt));
	}

	/**
	 * Checks wether a field is free to be set by the given color. That is:
	 * <li> the field at <code>pt</code> is empty</li>
	 * <li> one of the adjacent fields has the color of the oponent and further away on its line is
	 * a field occupied by <col>.</li>
	 * 
	 * @param pt
	 * @param col
	 * @return
	 */
	public boolean isFreeToSet(Point pt, int col) {
		// the field must free
		if (!isOutOfBounds(pt) && getField(pt) == EMPTY) {
			int fieldIndex = indexFromPt(pt.x, pt.y);
			for (int dir : directions) {
				// at least one direction has to be set by the opposite color ...
				int index = fieldIndex + dir;
				boolean foundline = false;
				while (!isOutOfBounds(index) && getField(index) == -col) {
					foundline = true;
					index += dir;
				}
				// and must be followed by one piece of our own color
				if (!isOutOfBounds(index) && getField(index) == col && foundline) {
					return true;
				}
			}
		}
		return false;
	}
	
	public int turnableFields(Point pt, int col) {
		int count = 0;
		// the field must free
		if (!isOutOfBounds(pt) && getField(pt) == EMPTY) {
			int fieldIndex = indexFromPt(pt.x, pt.y);
			for (int dir : directions) {
				// at least one direction has to be set by the opposite color ...
				int index = fieldIndex + dir;
				boolean foundline = false;
				int linecount = 0;
				while (!isOutOfBounds(index) && getField(index) == -col) {
					foundline = true;
					linecount++;
					index += dir;
				}
				// and must be followed by one piece of our own color
				if (!isOutOfBounds(index) && getField(index) == col && foundline) {
					count += linecount;
				}
			}
		}		
		return count;
	}

	public boolean colorCanPlay(int col) {
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				if (isFreeToSet(new Point(x, y), col)) {
					return true;
				}
			}
		}
		return false;
	}

	public void setPiece(int x, int y, int col) {
		setField(x, y, col);
		int fieldIndex = indexFromPt(x, y);
		for (int dir : directions) {
			// at least one direction has to be set by the opposite color ...
			int index = fieldIndex + dir;
			int piecesToTurn = 0;
			boolean foundline = false;
			while (!isOutOfBounds(index) && getField(index) == -col) {
				foundline = true;
				piecesToTurn++;
				index += dir;
			}
			// and must be followed by one piece of our own color
			if (!isOutOfBounds(index) && getField(index) == col && foundline) {
				for (int i = 0; i < piecesToTurn; i++) {
					index -= dir;
					setField(index, col);
				}
			}
		}
		fireModelChange();
	}

	public void setPiece(Point pt, int col) {
		setPiece(pt.x, pt.y, col);
	}

	public int count(int col) {
		int count = 0;
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				if (getField(x, y) == col) {
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public String toString() {
		String str = "";
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				if (getField(x, y) == WHITE) {
					str += "O";
				}
				else if (getField(x, y) == BLACK) {
					str += "X";
				}
				else {
					str += "+";
				}
			}
			str += "\n";
		}
		return str;
	}

	@Override
	public int hashCode() {
		return hashValue;
	}

	public void save(String name) {
		try {
			BufferedOutputStream fo = new BufferedOutputStream(new FileOutputStream(name));
			for (int i = 0; i < fields.length; i++) {
				fo.write(fields[i]+2);
			}
			fo.close();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void load(String name) {
		try {
			BufferedInputStream fi = new BufferedInputStream(new FileInputStream(name));
			for (int i = 0; i < fields.length; i++) {
				fields[i] = fi.read()-2;
			}
			fi.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Copy the given Board into this instance.
	 * @param brd
	 */
	public void copy(Board brd) {
		fields = brd.fields;
		movingColor = brd.movingColor;
		fireModelChange();
	}

	public int getMovingColor() {
		return movingColor;
	}

	public void setMovingColor(int movingColor) {
		this.movingColor = movingColor;
		fireModelChange();
	}
}
