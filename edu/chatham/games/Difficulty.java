package edu.chatham.games;

public enum Difficulty {
	BEGINNER (10, 9, 9), 
	INTERMEDIATE (40, 16, 16), 
	ADVANCED (99, 16, 30);
	
	public int mines;
	public int rows;
	public int cols;

	Difficulty(int mines, int rows, int cols) {
		this.mines = mines;
		this.rows = rows;
		this.cols = cols;
	}
	
	public static Difficulty parseDifficulty(String s) {
		switch(s) {
		case "Intermediate": return INTERMEDIATE;
		case "Advanced": return ADVANCED;
		default: return BEGINNER;
		}
	}
	
	public String toString() {
		return name().charAt(0) + name().substring(1).toLowerCase();
	}
	
	
}
