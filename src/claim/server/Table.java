package claim.server;

import java.util.ArrayList;

import claim.commons.Card;

public class Table extends Playroom {
	// Cards on the table - player's cards are stored in Account object
	private ArrayList<String> tableCards = new ArrayList<>();

	public Table() {
		super();
	}
	
	/*
	 * TODO: ACHTUNG tableCards und cardsTable, einer muss weg --> tableCards behalten
	 */

	private DeckOfCards deck;
	private ArrayList<Card> cardsP1 = new ArrayList<>();
	private ArrayList<Card> cardsP2 = new ArrayList<>();
	private ArrayList<Card> cardsTable = new ArrayList<>();
	private ArrayList<Card> undeadsP1 = new ArrayList<>();
	private ArrayList<Card> undeadsP2 = new ArrayList<>();
	private ArrayList<Card> tmpUndeads = new ArrayList<>();
	private ArrayList<Card> wonCardsP1 = new ArrayList<>();
	private ArrayList<Card> wonCardsP2 = new ArrayList<>();

	
	
	//Entweder das so belassen oder in unsere Kontrollerklasse einfügen, respektive generieren
	/*
	 * TODO: TXT
	 */
	public void deal() {
		deck = new DeckOfCards();
		Card card;
		System.out.println("Methode deal: Wert von getCardsRemaining vor dem ausgeben: "+ deck.getCardsRemaining());
		for (int i = 0; i < 3; i++) {
			if (deck.getCardsRemaining() > 26) {
				System.out.println("Methode deal: Wert von getCardsRemaining nach jedem ausgeben an Spieler: "+ deck.getCardsRemaining());
				for (int j = 0; j < 13; j++) {
					card = deck.dealCard();
					if (i == 0) cardsP1.add(card);
					else cardsP2.add(card);
				}
			} else {
				System.out.println("Methode deal: Wert von getCardsRemaining nur für Tischkarten: "+ deck.getCardsRemaining());
				for (int j = 0; j < deck.getCardsRemaining(); j++) {
					card = deck.dealCard();
					cardsTable.add(card);
				}
			}
		}
	}
	
//	public void finishRound(Card cadrP1, Card cardP2) {
//		
//	}
	
	/*
	 * TODO: Karte bekommen mit Jannick anschauen, ob die allenfalls als string kommt und gewandelt
	 * werden muss.
	 * ---------------------
	 * Rückgabe mit Jannick anschauen:
	 * - Mit was für einem Datentyp gebe ich den Sieger zurück?? 
	 * - Wer oder wie greifen wir aud die tmpUndeads zu, respektive macht jannick das direkt oder
	 *   muss ich noch eine zugriffs methode schreiben. 
	 */
	public void finishRound(Card cardP1, Card cardP2, Card actualTableCard) {//evtl.auslesen aus Array
		String winner = evaluateWinnerCard(cardP1, cardP2);
		addUndead(cardP1, cardP2, winner);
		//return Statement an Jannick in Absprache mit Ihm einfügen
		switch (winner) {
		case "P1": wonCardsP1.add(actualTableCard);
				   wonCardsP2.add(getNextTableCard()); break;
		case "P2": wonCardsP2.add(actualTableCard);
				   wonCardsP1.add(getNextTableCard()); break;

		}

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
	
	/*
	 * David: Add "Undead" cards to the winners ArrayList
	 */
	public void addUndead(Card cardP1, Card cardP2, String winner) {
		tmpUndeads.clear();
		if(suitToString(cardP1) == "undead" || suitToString(cardP2) == "undead") {
			switch (winner) {
			case "P1":	if(suitToString(cardP1) == "undead") undeadsP1.add(cardP1); tmpUndeads.add(cardP1);
						if(suitToString(cardP2) == "undead") undeadsP1.add(cardP2); tmpUndeads.add(cardP2);
						break;
			case "P2":  if(suitToString(cardP1) == "undead") undeadsP2.add(cardP1); tmpUndeads.add(cardP1);
						if(suitToString(cardP2) == "undead") undeadsP2.add(cardP2); tmpUndeads.add(cardP2);
						break;
			}
		}
	}
	
	/*
	 * David: Gibt die letzte Karte der Tischkarten zurück und löscht diese
	 */
	public Card getNextTableCard() {
        Card card = (cardsTable.size() > 0) ? cardsTable.remove(cardsTable.size()-1) : null;
		return card;
	}
	
	//Dave: Wandelt die Karte in einen String und gibt nur den suit der Karte als String zurück
	public String suitToString(Card card) {
		String cardString = card.toString();
	    String[] tmp = cardString.split("\\_");
    	return tmp[0];
	}
	
	
}
