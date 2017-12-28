package edu.chatham.games;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class MineCell extends Cell {

	private final Image mine = new ImageIcon("mine.png").getImage();
	
	public MineCell(Board board, int r, int c) {
		super(board, r, c);
	}

	public MineCell(Board board, int row, int col, int mineCount, boolean isRevealed, boolean isFlagged) {
		super(board, row, col, mineCount, isRevealed, isFlagged);
	}
	
	@Override
	public void reveal() {
		isRevealed = true;
		setBackground(Color.WHITE);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (isRevealed()) {
			g.drawImage(mine, 0, 0, getWidth(), getHeight(), null);
		}
	}
}
