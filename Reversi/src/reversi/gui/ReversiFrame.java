/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package reversi.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import reversi.controller.Controller;
import reversi.gui.resource.ResourceManager;
import reversi.model.Board;

public class ReversiFrame extends JFrame {
	private ReversiBoardView boardView;
	private Board brd;
	private Controller controller;
	private JToolBar toolbar;

	public ReversiFrame() {
		super("Reversi");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setSize(300, 330);
		setLayout(new BorderLayout());
		brd = new Board();
		controller = new Controller(brd, this);
		boardView = new ReversiBoardView(controller, brd);
		boardView.setBoard(brd);
		getContentPane().add(boardView, BorderLayout.CENTER);
		createToolbar();
	}

	public void endOfGame() {
		int whiteCount = brd.count(Board.WHITE);
		int blackCount = brd.count(Board.BLACK);
		String message = null;
		if (whiteCount > blackCount) {
			message = "White wins!";
		}
		if (whiteCount < blackCount) {
			message = "Black wins!";
		}
		if (whiteCount == blackCount) {
			message = "Draw";
		}
		message += " (" + whiteCount + ":" + blackCount + ")";
		JOptionPane.showMessageDialog(this, message);

	}

	public void createToolbar() {
		toolbar = new JToolBar(JToolBar.HORIZONTAL);
		toolbar.setFloatable(false);
		getContentPane().add(toolbar, BorderLayout.NORTH);

		createAction("New game", "document-new.png", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.newGame();
			};
		});
		createAction("Save game", "document-save.png", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				brd.save("test.rev");
			}
		});
		createAction("Undo last move", "edit-undo.png", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.undo();
			}
		});
		createAction("Preferences", "document-properties.png", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
	}

	private void createAction(String tooltip, String iconName, ActionListener al) {
		JButton undo = new JButton(ResourceManager.getIcon(iconName));
		undo.setToolTipText(tooltip);
		undo.addActionListener(al);
		toolbar.add(undo);
	}

	public static void main(String[] args) {
		(new ReversiFrame()).setVisible(true);
	}

}
