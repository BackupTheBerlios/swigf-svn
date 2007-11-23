/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package reversi.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;

import reversi.controller.Controller;
import reversi.model.Board;

public class ReversiBoardView extends JComponent implements Board.ModelChangeListener {
	private static final Color DARKGREEN = new Color(0, 200, 30);
	private Board brd;
	private Controller controller;
	private boolean showPossibleMoves = true;

	public ReversiBoardView(Controller ctrl, Board board) {
		this.controller = ctrl;
		this.brd = board;
		brd.addModelChangeListener(this);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int cellWidth = getWidth() / Board.SIZE;
				int cellHeight = getHeight() / Board.SIZE;
				int brdx = e.getX() / cellWidth;
				int brdy = e.getY() / cellHeight;
				if (controller.playPiece(brdx, brdy, controller.getMovingColor())) {
				}
				// setCursor(new Cursor(Cursor.WAIT_CURSOR));
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int cellWidth = getWidth() / Board.SIZE;
				int cellHeight = getHeight() / Board.SIZE;
				int brdx = e.getX() / cellWidth;
				int brdy = e.getY() / cellHeight;
				if (brd.isFreeToSet(new Point(brdx, brdy), controller.getMovingColor())) {
					setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
				else {
					setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
				}
			}
		});
	}

	@Override
	public void paint(Graphics g) {
		int cellWidth = getWidth() / Board.SIZE;
		int cellHeight = getHeight() / Board.SIZE;
		g.setColor(DARKGREEN);
		g.fillRect(0, 0, Board.SIZE * cellWidth, Board.SIZE * cellHeight);
		g.setColor(Color.BLACK);
		for (int i = 0; i <= Board.SIZE; i++) {
			g.drawLine(0, cellHeight * i, Board.SIZE * cellWidth, cellHeight * i);
			g.drawLine(i * cellWidth, 0, i * cellWidth, Board.SIZE * cellHeight);
		}
		if (brd != null) {
			for (int y = 0; y < Board.SIZE; y++) {
				for (int x = 0; x < Board.SIZE; x++) {
					int field = brd.getField(x, y);
					if (field != Board.EMPTY) {
						Color col;
						if (field == Board.WHITE) {
							col = Color.WHITE;
						}
						else {
							col = Color.BLACK;
						}
						g.setColor(Color.GRAY);
						g.fillOval(x * cellWidth + 5, y * cellHeight + 5, cellWidth - 10,
								cellHeight - 10);
						g.setColor(col);
						g.fillOval(x * cellWidth + 6, y * cellHeight + 6, cellWidth - 12,
								cellHeight - 12);
					}
					else if (showPossibleMoves
							&& brd.isFreeToSet(new Point(x, y), brd.getMovingColor())) {
						g.setColor(Color.RED);
						g.fillOval(x * cellWidth + cellWidth / 2 - 3, y * cellHeight + cellHeight
								/ 2 - 3, 6, 6);
					}
				}
			}
		}
	}

	public void setBoard(Board brd) {
		this.brd = brd;
	}

	public void modelUpdate() {
		repaint();
	}
}
