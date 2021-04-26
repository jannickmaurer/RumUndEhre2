package claim.server;

import java.util.ArrayList;
import java.util.Collections;

import claim.commons.Card;

public class Table extends Playroom {
	// Cards on the table - player's cards are stored in Account object
	//private ArrayList<String> tableCards = new ArrayList<>();

	public Table() {
		super();
	}
	
	/*
	 * TODO: ACHTUNG tableCards und cardsTable, einer muss weg --> tableCards behalten
	 * - Wie versendet Jannick die Tischkarten?? sollte immer über getTableCards laufen, damit
	 *   die gespielte Karte gelöscht wird
	 * - Soll für die offene Tischkarte eine eigene Card auf dem Table eröffnet werden bevor diese 
	 *   versendet wir? Könnte ich bruachen, ansonsten muss ich sie bekommen
	 */

	private DeckOfCards deck;
	private ArrayList<Card> cardsP1 = new ArrayList<>();
	private ArrayList<Card> cardsP2 = new ArrayList<>();
	private ArrayList<Card> cardsTable = new ArrayList<>();
	private ArrayList<Card> undeadsP1 = new ArrayList<>();
	private ArrayList<Card> undeadsP2 = new ArrayList<>();
	private ArrayList<Card> followerCardsP1 = new ArrayList<>();
	private ArrayList<Card> followerCardsP2 = new ArrayList<>();
	public ArrayList<Card> tmpUndeads = new ArrayList<>();
	private int fractionPointsP1;
	private int fractionPointsP2;

	public String roundWinner;
	public Card followerCardP1;
	public Card followerCardP2;

	
	
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
				System.out.println("Methode Deal in Table: "+cardsTable.size());
			}
		}
//		System.out.println("Deal:");
//		System.out.print("P1:");
//		System.out.print(cardsP1.size());
//		for(Card c: cardsP1) {
//			
//			System.out.print(c.toString());
//		}
//		System.out.println();
//		System.out.print("P2:");
//		System.out.print(cardsP2.size());
//		for(Card c: cardsP2) {
//			
//			System.out.print(c.toString());
//		}
//		System.out.println();
//		System.out.print("Table:");
//		System.out.print(cardsTable.size());
//		for(Card c: cardsTable) {
//			
//			System.out.print(c.toString());
//		}
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
		roundWinner = ""; //eigentlich unnötig
		followerCardP1 = null; //eigentlich unnötig
		followerCardP2 = null; //eigentlich unnötig
		
		roundWinner = evaluateWinnerCard(cardP1, cardP2);
		addUndead(cardP1, cardP2, roundWinner);

		switch (roundWinner) {
		case "P1": followerCardP1 = actualTableCard;
				   followerCardsP1.add(followerCardP1);
				   followerCardP2 = getNextTableCard();
				   followerCardsP2.add(followerCardP2); break;
		case "P2": followerCardP2 = actualTableCard;
				   followerCardsP2.add(followerCardP2);
				   followerCardP1 = getNextTableCard();
				   followerCardsP1.add(followerCardP1); break;
		}
	}
	
	
	//Dave: Falls eine der beiden Karten ein Untoter ist oder beide, muss diese dem Spieler
	//auf den Punktestapel zugesandt werden der gewonnen hat.
	private String evaluateWinnerCard(Card cardP1, Card cardP2) {
		String win = "P1";
		//gibt den Sieger aus. Rückgabewert noch unbekannt, evtl. boolean, zu definieren. Eingabe auch
		//evtl. zuerst aus String noch Karte machen
		if(suitToString(cardP1).equals("goblin") && suitToString(cardP2).equals("knight") ||
				suitToString(cardP1).equals("knight") && suitToString(cardP2).equals("goblin")) {
			if(suitToString(cardP1).equals("knight")) return "P1";				
			else return "P2";
		}
		if(suitToString(cardP1).equals(suitToString(cardP2)) || 
			(!suitToString(cardP1).equals("double") && suitToString(cardP2).equals("double"))) {
			switch (cardP1.compareTo(cardP2)) {
			case  1: break;
			case  0: break;
			case -1: win = "P2"; break;
			}
		}
		return win;
	}
	
	/*
	 * David: Add "Undead" cards to the winners ArrayList
	 */
	private void addUndead(Card cardP1, Card cardP2, String roundWinner) {
		tmpUndeads.clear();
		if(suitToString(cardP1).equals("undead") || suitToString(cardP2).equals("undead")) {
			switch (roundWinner) {
			case "P1":	if(suitToString(cardP1).equals("undead")) undeadsP1.add(cardP1); tmpUndeads.add(cardP1);
						if(suitToString(cardP2).equals("undead")) undeadsP1.add(cardP2); tmpUndeads.add(cardP2);
						break;
			case "P2":  if(suitToString(cardP1).equals("undead")) undeadsP2.add(cardP1); tmpUndeads.add(cardP1);
						if(suitToString(cardP2).equals("undead")) undeadsP2.add(cardP2); tmpUndeads.add(cardP2);
						break;
			}
		}
	}
	
	/*
	 * David: Falls es einen Sieger gibt, wird der als String zurück gegeben
	 */
	public String winner() {
		String win = "NoWinner";
		gameWinner();
		if(fractionPointsP1 >= 3) return "P1isTheWinner";
		if(fractionPointsP2 >= 3) return "P2isTheWinner";
		return win;
	}
	
	/*
	 * David: Wertet den Sieger des Spiels aus
	 */
	private void gameWinner() {
		String gameWinner;
		ArrayList<Card> goblinP1 = new ArrayList<>();
		ArrayList<Card> goblinP2 = new ArrayList<>();
		ArrayList<Card> dwarfP1  = new ArrayList<>();
		ArrayList<Card> dwarfP2  = new ArrayList<>();
		ArrayList<Card> knightP1 = new ArrayList<>();
		ArrayList<Card> knightP2 = new ArrayList<>();
		ArrayList<Card> doubleP1 = new ArrayList<>();
		ArrayList<Card> doubleP2 = new ArrayList<>();
		
		for (Card card : followerCardsP1) {
			switch (suitToString(card)) {
			case "goblin": goblinP1.add(card); break;
			case "dwarf" : dwarfP1.add(card);  break;
			case "knigth": knightP1.add(card); break;
			case "double": doubleP1.add(card); break;
			}
		}
		
		for (Card card : followerCardsP2) {
			switch (suitToString(card)) {
			case "goblin": goblinP2.add(card); break;
			case "dwarf" : dwarfP2.add(card);  break;
			case "knigth": knightP2.add(card); break;
			case "double": doubleP2.add(card); break;
			}
		}
		
		addFractionPoint(winnerFraction(goblinP1, goblinP2));
		addFractionPoint(winnerFraction(dwarfP1, dwarfP2));
		addFractionPoint(winnerFraction(knightP1, knightP2));
		addFractionPoint(winnerFraction(doubleP1, doubleP2));
		addFractionPoint(winnerFraction(undeadsP1, undeadsP2));
	}
	
	/*
	 * David: Vergleicht die Anzahl Anhänger einer Fraktion und gibt den Spieler der gewonnen hat zurück
	 */
	private String winnerFraction(ArrayList<Card> cardsP1, ArrayList<Card> cardsP2) {
		String win = "NONE";
		if(cardsP1.size() > cardsP2.size()) return "P1";
		if(cardsP1.size() < cardsP2.size()) return "P2";
		if(cardsP1.size() == 0 && cardsP2.size() == 0) return "NONE";
		else
			switch (getHighestCard(cardsP1).compareTo(getHighestCard(cardsP2))) {
			case  1: win = "P1";	break;
			case  0: win = "NONE";	break; 
			case -1: win = "P2";	break;
			}
		return win;	
	}
	
	/*
	 * David: Fügt dem Sieger einer Fraktion einen Punkt hinzu
	 */
	private void addFractionPoint(String winner) {
		switch (winner) {
		case "P1"  : fractionPointsP1++; break;
		case "P2"  : fractionPointsP2++; break;
		case "NONE": break;
		}
	}
	
	/*
	 * David: Gibt die letzte Karte der Tischkarten zurück und löscht diese
	 */
	public Card getNextTableCard() {
        Card card = (cardsTable.size() > 0) ? cardsTable.remove(cardsTable.size()-1) : null;
		return card;
	}
	
	/*
	 * David: Sortiert die Karten und gibt die letzte Karte, welche die Höchste ist zurück
	 */
	private Card getHighestCard(ArrayList<Card> cards) {
		Collections.sort(cards);
        Card card = (cards.size() > 0) ? cards.remove(cards.size()-1) : null;
		return card;
	}
	
	//Dave: Wandelt die Karte in einen String und gibt nur den suit der Karte als String zurück
	private String suitToString(Card card) {
		String cardString = card.toString();
	    String[] tmp = cardString.split("\\_");
    	return tmp[0];
	}
	
	
}
