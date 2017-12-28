package edu.chatham.games;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXParserBoard extends DefaultHandler {

	private Board board;
	
	private Difficulty difficulty;
	long seed;
	
	public Board getBoard() {
		return board;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
		switch (qName) {
		case "Board":
			difficulty = Difficulty.parseDifficulty(attributes.getValue("difficulty"));
			seed = Long.parseLong(attributes.getValue("seed"));
		}
		
		
	}
}
