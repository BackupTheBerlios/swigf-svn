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
	
	public ReversiFrame() {
		super("Reversi");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setSize(500, 525);
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
		JToolBar jtb = new JToolBar(JToolBar.HORIZONTAL);
		jtb.setFloatable(false);
		getContentPane().add(jtb, BorderLayout.NORTH);
		// save
		JButton save = new JButton(ResourceManager.getIcon("document-save-as.png"));
		save.setToolTipText("Save");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				brd.save("test.rev");
			}
		});
		jtb.add(save);
		// undo
		JButton undo = new JButton(ResourceManager.getIcon("edit-undo.png"));
		undo.setToolTipText("Undo");
		undo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				controller.undo();
			}
		});
		jtb.add(undo);
		
	}

	public static void main(String[] args) {
		(new ReversiFrame()).setVisible(true);
	}

}
