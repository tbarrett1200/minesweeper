package edu.chatham.games;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Minesweeper extends JFrame implements MessageDisplayable {
	
	private static final int TIME_LIMIT = 90;
	
	public static Minesweeper currentGame;
	
	private JLabel statusMessage;
	private JButton newGameButton;
	private JComboBox<Difficulty> difficultyComboBox;
	private JLabel timeLabel;
	private JLabel mineLabel;
	
	private JPanel north;
	private JPanel south;
	private Board board;

	private Timer timer;
	
	private int timeRemaining = TIME_LIMIT;
	
	public Minesweeper() {
		setTitle("MineSweeper");

		setupGame();
		setupNorth();
		setupSouth();

		add(board, BorderLayout.CENTER);
		add(north, BorderLayout.NORTH);
		add(south, BorderLayout.SOUTH);

		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);

		timer = new Timer(1000, e -> decrementTimer());
		timer.start();
		
		newGameButton.addActionListener(event -> newGame());
	}


	private void decrementTimer() {
		
		if (!board.isActive()) return;
		
		if (timeRemaining > 0) {
			timeRemaining--;
			timeLabel.setText("Time: " + timeRemaining);
		} else {
			displayMessage("You ran out of time");
			board.setActive(false);
		}
	}
	
	private void resetTimer() {
		timeRemaining = TIME_LIMIT;
	}
	
	/* Sets up the panel to the north of the JFrame */
	private void setupNorth() {
		north = new JPanel();
		statusMessage = new JLabel("Minesweeper");
		north.add(statusMessage);
	}

	/* Sets up the panel to the south of the JFrame */
	private void setupSouth() {
		south = new JPanel();
		
		newGameButton = new JButton("New Game");
		difficultyComboBox = new JComboBox<Difficulty>(Difficulty.values());
		difficultyComboBox.setSelectedItem(Difficulty.BEGINNER);
		timeLabel = new JLabel("Time: " + TIME_LIMIT);
		mineLabel = new JLabel("Mines: " + board.getDifficulty().mines);

		south.add(timeLabel);
		south.add(newGameButton);
		south.add(difficultyComboBox);
		south.add(mineLabel);
	}

	/* Sets up the board */
	private void setupGame() {
		board = new Board(Difficulty.BEGINNER, this);
	}

	/* Replaces the game with a new game*/
	private void newGame() {
		displayMessage("Minesweeper");
		remove(board);
		board = new Board((Difficulty)difficultyComboBox.getSelectedItem(), this);
		add(board);
		pack();
		setLocationRelativeTo(null);
		resetTimer();
		timeLabel.setText("Time: " + TIME_LIMIT);
		mineLabel.setText("Mines: " + board.getDifficulty().mines);
	}

	@Override
	public void displayMessage(String s) {
		statusMessage.setText(s);
	}
	
	

	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	new Minesweeper();
        });
	}
}
