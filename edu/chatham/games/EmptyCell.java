package edu.chatham.games;

import java.awt.Color;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class EmptyCell extends Cell {

	public EmptyCell(Board board, int r, int c) {
		super(board, r, c);
	}

	public EmptyCell(Board board, int row, int col, int mineCount, boolean isRevealed, boolean isFlagged) {
		super(board, row, col, mineCount, isRevealed, isFlagged);
	}

	@Override
	public void reveal() {
		isRevealed = true;
		setFlagged(false);
		setBackground(Color.WHITE);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (isRevealed() && getMineCount() > 0) {
			g.setColor(Color.BLACK);
			g.drawString("" + getMineCount(), 20, 30);
		}
	}
}
