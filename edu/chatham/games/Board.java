package edu.chatham.games;

import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

@SuppressWarnings("serial")
public class Board extends JPanel {

	private Difficulty difficulty;
	private MessageDisplayable message;
	private Cell[][] cells;
	private List<MineCell> mines;
	private boolean isActive = true;
	private int revealed = 0;
	
	public Board(Difficulty difficulty, MessageDisplayable message) {
		setLayout(new GridLayout(difficulty.rows, difficulty.cols));
		this.message = message;
		this.difficulty = difficulty;
		cells = new Cell[difficulty.rows][difficulty.cols];
		mines = new ArrayList<MineCell>();
		populateBoard();
	}
	
	public Board(Difficulty difficulty, MessageDisplayable message, Cell[][] cells, List<MineCell> mines, boolean isActive, int revealed) {
		setLayout(new GridLayout(difficulty.rows, difficulty.cols));
		this.difficulty = difficulty;
		this.message = message;
		this.cells = cells;
		this.mines = mines;
		this.isActive = isActive;
		this.revealed = revealed;
		addGraphics();
	}
	
	private void populateBoard() {
		addMineCells();
		addEmptyCells();
		addGraphics();
	}
	
	public Difficulty getDifficulty() {
		return difficulty;
	}
	
	public void setActive(boolean active) {
		this.isActive = active;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	private void addMineCells() {
		for (int i = 0; i < difficulty.mines; i++) {
			while(true) {
				int r = (int)(Math.random() * difficulty.rows);
				int c = (int)(Math.random() * difficulty.cols);
				if (cells[r][c] == null) {
					MineCell cell = new MineCell(this, r, c);
					cells[r][c] = cell;
					mines.add(cell);
					break;
				}
			}
		}
	}
	
	private void addEmptyCells() {
		for (int r = 0; r < difficulty.rows; r++) {
			for (int c = 0; c < difficulty.cols; c++) {
				if (cells[r][c] == null) {
					Cell cell = new EmptyCell(this, r, c);
					cells[r][c] = cell;
					for (Cell adjacent: getAdjacentCells(cells[r][c])) {
						if (adjacent instanceof MineCell) {
							cell.addToMineCount(); 
						}
					}
				}
			}
		}
	}
	
	public List<Cell> getAdjacentCells(Cell cell) {
		List<Cell> adjacent = new ArrayList<Cell>();
		
		for (int r = -1; r <= 1; r++) {
			for (int c = -1; c <= 1; c++) {
				if (r == 0 && c == 0) continue;

				int row = cell.row + r;
				int col = cell.col + c;
				
				if (row < 0 || col < 0) continue;		
				if (row == difficulty.rows || col == difficulty.cols) continue;			
				
				adjacent.add(cells[row][col]);
			}
		}	
		
		return adjacent;
	}
	
	private void playSound(String file) {
		try {
			File mineSound = new File(file);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(mineSound);
			Clip clip = AudioSystem.getClip();
	        clip.open(audioIn);
	        clip.start();
		} catch (UnsupportedAudioFileException | IOException e) {
			System.err.println("Error: unable to read audio file");
		} catch (LineUnavailableException e) {
			System.err.println("Error: unable to play audio");
		}              
	}
	
	public void reveal(Cell cell) {
		cell.reveal();

		if (cell instanceof EmptyCell) {
			revealed++;
			if (revealed == difficulty.cols * difficulty.rows - difficulty.mines) {
				message.displayMessage("You Win");
				isActive = false;
			}

			if (cell.getMineCount() == 0) {
				List<Cell> adjacent = getAdjacentCells(cell);
				for (Cell c: adjacent) {
					if (!c.isRevealed) reveal(c);
				}	
			}
			
		}
		
		if (cell instanceof MineCell) {
			playSound("bomb.wav");
			
			for (MineCell c: mines) c.reveal();
			
			message.displayMessage("You Lose");
			isActive = false;
		}
		
		paintImmediately(getBounds());
	}
	
	public void addGraphics() {
		for (int r = 0; r < difficulty.rows; r++) {
			for (int c = 0; c < difficulty.cols; c++) {
				add(cells[r][c]);
			}
		}
	}
}
