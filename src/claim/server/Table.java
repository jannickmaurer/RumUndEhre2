package claim.server;

import java.util.ArrayList;

import claim.commons.Card;

public class Table extends Playroom {
	// Cards on the table - player's cards are stored in Account object
	private ArrayList<String> tableCards = new ArrayList<>();

	public Table() {
		super();
	}

	private DeckOfCards deck;
	private ArrayList<Card> cardsP1 = new ArrayList<>();
	private ArrayList<Card> cardsP2 = new ArrayList<>();
	private ArrayList<Card> cardsTable = new ArrayList<>();
	private ArrayList<Card> undeadsP1 = new ArrayList<>();
	private ArrayList<Card> undeadsP2 = new ArrayList<>();
	
	
	//Entweder das so belassen oder in unsere Kontrollerklasse einfügen, respektive generieren
	/*
	 * TODO: TXT
	 */
	public void deal() {
		deck = new DeckOfCards();
		Card card;
		for (int i = 0; i < 3; i++) {
			if (deck.getCardsRemaining() > 26) {
				for (int j = 0; j < 13; j++) {
					card = deck.dealCard();
					if (i == 0) cardsP1.add(card);
					else cardsP2.add(card);
				}
			} else {
				card = deck.dealCard();
				cardsTable.add(card);
			}
		}
	}
	
	public String getPlayerCards() {
		
		return "hallo";
	}
	
	public void roundWinnerIs(Card cardP1, Card cardP2) {
		evaluateWinnerCard(cardP1, cardP2);
		
	}
	
	
	//Dave: Falls eine der beiden Karten ein Untoter ist oder beide, muss diese dem Spieler
	//auf den Punktestapel zugesandt werden der gewonnen hat.
	public String evaluateWinnerCard(Card cardP1, Card cardP2) {
		//gibt den Sieger aus. Rückgabewert noch unbekannt, evtl. boolean, zu definieren. Eingabe auch
		//evtl. zuerst aus String noch Karte machen
		if(suitToString(cardP1) == "goblin" && suitToString(cardP2) == "knight" ||
				suitToString(cardP1) == "knight" && suitToString(cardP2) == "goblin") {
			if(suitToString(cardP1) == "knight") return "P1";				
			else return "P2";
		}
		if(suitToString(cardP1) == suitToString(cardP2) || 
			(suitToString(cardP1) != "double" && suitToString(cardP2) == "double")) {
			switch (cardP1.compareTo(cardP2)) {
			case  1: return "P1";
			case  0: return "P1";
			case -1: return "P2";
			}
		}
		return "P1";
	}
	
	public void addUndead(Card cardP1, Card cardP2, String winner) {
		if(suitToString(cardP1) == "undead" || suitToString(cardP2) == "undead") {
			switch (winner) {
			case "P1":	if(suitToString(cardP1) == "undead") undeadsP1.add(cardP1);
						if(suitToString(cardP2) == "undead") undeadsP1.add(cardP2); break;
			case "P2":  if(suitToString(cardP1) == "undead") undeadsP2.add(cardP1);
						if(suitToString(cardP2) == "undead") undeadsP2.add(cardP2); break;
			}
		}
	}
	
	//Dave: Wandelt die Karte in einen String und gibt nur den suit der Karte als String zurück
	public String suitToString(Card card) {
		String cardString = card.toString();
	    String[] tmp = cardString.split("\\_");
    	return tmp[0];
	}
	
	
}
