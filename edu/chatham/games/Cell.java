package edu.chatham.games;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * An abstract class that models a Minesweeper cell.
 * @author Mark Jones
 *
 */

@SuppressWarnings("serial")
public abstract class Cell extends JPanel {

	public static final int CELL_WIDTH = 50;
	public static final int CELL_HEIGHT = 50;
	private static final Color COLOR = new Color(81, 232, 204);
	
	// the instance variables are protected for convenient access by the subclasses
	protected Board board;
	protected int row, col, mineCount;
	protected boolean isRevealed;
	private boolean isFlagged;
	
	private static final Image flag = new ImageIcon("flag.png").getImage();

	public Cell(Board board, int row, int col, int mineCount, boolean isRevealed, boolean isFlagged) {
		setPreferredSize(new Dimension(CELL_WIDTH, CELL_HEIGHT));
		setBackground(COLOR);

		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (board.isActive()) {
					if (SwingUtilities.isRightMouseButton(e)) toggleFlag();
					else if (!isFlagged()) board.reveal(Cell.this);
					board.paintImmediately(board.getParent().getBounds());
				}
			}
		});
		
		this.board = board;
		this.row = row;
		this.col = col;
		this.mineCount = mineCount;
		this.isRevealed = isRevealed;
		this.isFlagged = isFlagged;
	}
	
	/**
	 * Creates a Minesweeper Cell given a row and a column.
	 * The default unrevealed cell is currently a simple blue rectangle. 
	 * @param r
	 * @param c
	 */
	public Cell(Board board, int r, int c) {
		this(board, r, c, 0, false, false);
	}
	
	/**
	 * Any subclass of Cell must implement this method to change the 
	 * appearance and state of the cell to reflect its being revealed.
	 */
	public abstract void reveal();
	
	/**
	 * Test if the cell has been revealed.
	 * @return   true if the cell has been revealed, false otherwise
	 */
	public boolean isRevealed() {
		return isRevealed;
	}
	
	public void toggleFlag() {
		isFlagged = !isFlagged;
	}

	public void setFlagged(boolean f) {
		isFlagged = f;
	}
	
	public boolean isFlagged() {
		return isFlagged;
	}
	
	/**
	 * A cell has a count of the surrounding mines.
	 */
	public void addToMineCount() {
		mineCount++;
	}
	
	/**
	 * Getter for the mine count.
	 * @return
	 */
	public int getMineCount() {
		return mineCount;
	}

	/**
	 * Getter for the cell's row.
	 * @return   the row
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Getter for the cell's column
	 * @return   the column
	 */
	public int getCol() {
		return col;
	}
	
	/**
	 * A printable representation of a Cell.
	 */
	public String toString() {
		return String.format("[%d,%d]", row, col);
	}
	
	protected void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, 50, 50);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, 50, 50);
		if (isFlagged()) {
			g.drawImage(flag, 0, 0, getWidth(), getHeight(), null);
		}
	}
}
	