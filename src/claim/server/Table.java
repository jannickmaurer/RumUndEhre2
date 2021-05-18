package claim.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import claim.commons.Card;
import claim.commons.Card.Rank;
import claim.commons.Card.Suit;
import claim.commons.messages.results.ResultBroadcastEvaluateWinner;
import claim.commons.messages.results.ResultBroadcastFinishRound;
import claim.commons.messages.results.ResultSendCard;
import javafx.beans.property.SimpleIntegerProperty;

public class Table {
	private ArrayList<Account> players = new ArrayList<>();
	private SimpleIntegerProperty playedCards = new SimpleIntegerProperty();
	private Account firstPlayer = null;

	private DeckOfCards deck;
	public ArrayList<Card> tableCards = new ArrayList<>();
//	public ArrayList<Card> followerCardsP1 = new ArrayList<>();
//	public ArrayList<Card> followerCardsP2 = new ArrayList<>();
	public ArrayList<Card> tmpUndeads = new ArrayList<>();
	private int fractionPointsP1;
	private int fractionPointsP2;
	public Card actualTableCard;

	public String roundWinner;
	public Card followerCardP1;
	public Card followerCardP2;
	private String undeadString;


	public Table() {
//		super();
	}
	public Table(ArrayList<Account> players) {
		for(Account a : players) {
			this.players.add(a);
//		this.players = players;
			System.out.println("Player zu Table added: " + a.getUsername());
		}
	
		playedCards.set(0);
		playedCards.addListener((o, OldValue, NewValue) -> {
			if(NewValue.intValue() > 1) {
//				try {
//					Thread.sleep(2000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				finishRound();
				
			}
		
		});
//		super();
	}
	/*
	 * TODO: ACHTUNG tableCards und tableCards, einer muss weg --> tableCards behalten
	 * - Wie versendet Jannick die Tischkarten?? sollte immer über getTableCards laufen, damit
	 *   die gespielte Karte gelöscht wird
	 * - Soll für die offene Tischkarte eine eigene Card auf dem Table eröffnet werden bevor diese 
	 *   versendet wir? Könnte ich bruachen, ansonsten muss ich sie bekommen
	 */


	
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
					
					if (i == 0) players.get(0).addHandCard(card);
					else players.get(1).addHandCard(card);
				}
			} else {
				System.out.println("Methode deal: Wert von getCardsRemaining nur für Tischkarten: "+ deck.getCardsRemaining());
				for (int j = 0; j < 26; j++) {
					System.out.println("Table: Methode deal: "+deck.getCardsRemaining());
					card = deck.dealCard();
					tableCards.add(card);
				}

				System.out.println("Methode Deal in Table: "+ tableCards.size());

			}
		}
	}
	
	/*
	 * TODO: Karte bekommen mit Jannick anschauen, ob die allenfalls als string kommt und gewandelt
	 * werden muss.
	 * ---------------------
	 * Rückgabe mit Jannick anschauen:
	 * - Mit was für einem Datentyp gebe ich den Sieger zurück?? 
	 * - Wer oder wie greifen wir aud die tmpUndeads zu, respektive macht jannick das direkt oder
	 *   muss ich noch eine zugriffs methode schreiben. 
	 */
	
	public void finishRound() {//, Card actualTableCard entfernt//evtl.auslesen aus Array
		roundWinner = ""; //eigentlich unnötig
		followerCardP1 = null; //eigentlich unnötig
		followerCardP2 = null; //eigentlich unnötig
		int winner = 0;

		
		
		System.out.println("Karte übergeben: " + players.get(0).getPlayedCard());
		System.out.println("Karte übergeben: " + players.get(1).getPlayedCard());
		roundWinner = evaluateWinnerCard(players.get(0).getPlayedCard(), players.get(1).getPlayedCard());
		addUndead(players.get(0).getPlayedCard(), players.get(1).getPlayedCard(), roundWinner);

		Card tmp = getNextTableCard();
		switch (roundWinner) {
		case "P1": followerCardP1 = actualTableCard;					
				   players.get(0).getFollowerCards().add(followerCardP1);
				   followerCardP2 = tmp;
				   players.get(1).getFollowerCards().add(followerCardP2); break; 
		case "P2": followerCardP2 = actualTableCard;
				   players.get(1).getFollowerCards().add(followerCardP2);
				   followerCardP1 = tmp;
				   players.get(0).getFollowerCards().add(followerCardP1); 
				   winner = 1; break;

		}	

//		for(Account a : players) {
//		String temp = getNextTableCard().toString();
		for(int i = 0; players.size() > i; i++) {
		   if(undeadString.equalsIgnoreCase("None")) {
			   String[] content = {"ResultBroadcastFinishRound", "true", players.get(winner).getUsername(), tmp.toString()};
			   players.get(i).getClient().send(new ResultBroadcastFinishRound(content));
		   } else {
			   String[] content = {"ResultBroadcastFinishRound", "true", players.get(winner).getUsername(), tmp.toString(), undeadString};
			   players.get(i).getClient().send(new ResultBroadcastFinishRound(content));
		   }
		}

		
		this.playedCards.set(0);
		this.firstPlayer = null;
		
		for(Account a: players) {
			a.clearPlayedCard();
		}
		

	}	
	
	//Dave: Falls eine der beiden Karten ein Untoter ist oder beide, muss diese dem Spieler
	//auf den Punktestapel zugesandt werden der  hat.
	public String evaluateWinnerCard(Card cardP1, Card cardP2) {
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
			case "P1":	if(suitToString(cardP1).equals("undead")) players.get(0).addUndeadCard(cardP1); tmpUndeads.add(cardP1);
						if(suitToString(cardP2).equals("undead")) players.get(0).addUndeadCard(cardP2); tmpUndeads.add(cardP2);
						break;
			case "P2":  if(suitToString(cardP1).equals("undead")) players.get(1).addUndeadCard(cardP1); tmpUndeads.add(cardP1);
						if(suitToString(cardP2).equals("undead")) players.get(1).addUndeadCard(cardP2); tmpUndeads.add(cardP2);
						break;
			}
		}
		undeadString = "None";
		switch(tmpUndeads.size()) {
		case 0: undeadString = "None"; break;
		case 1: undeadString = tmpUndeads.get(0).toString(); break;
		case 2: undeadString = tmpUndeads.get(0).toString()+"|"+tmpUndeads.get(1); break;
		}
	}
	
	/*
	 * David: Falls es einen Sieger gibt, wird der als String zurück gegeben
	 */
	public String winner() {
		String win = "NoWinner";
		gameWinner();
		if(fractionPointsP1 >= 3) return players.get(0).getUsername();
		if(fractionPointsP2 >= 3) return players.get(1).getUsername();
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
		
		for (Card card : players.get(0).getFollowerCards()) {//Changed from followerCardsP1
			switch (suitToString(card)) {
			case "goblin": goblinP1.add(card); break;
			case "dwarf" : dwarfP1.add(card);  break;
			case "knigth": knightP1.add(card); break;
			case "double": doubleP1.add(card); break;
			}
		}
		
		for (Card card : players.get(1).getFollowerCards()) {//Changed from followerCardsP2
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
		addFractionPoint(winnerFraction(players.get(0).getUndeadCards(), players.get(1).getUndeadCards()));
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
		
		//Siegerauswertung direkt. Neue Message muss noch erstellt werden
		
//        if(tableCards.isEmpty()) {
//        	String win = winner();
//        	System.out.println("Table | getNextTableCard() | der Siger des gesamten Spiels ist: "+win);
//    		for(Account a : players) {
//    			String[] content = {"ResultSendGameWinner", "true", win};
//    			a.getClient().send(new ResultSendGameWinner(content));
//    		}
//        }

        Card card = (tableCards.size() > 0) ? tableCards.remove(tableCards.size()-1) : null;
        actualTableCard = card;
        System.out.println("Get next Table Card: " + card.toString());
        System.out.println("Size Table Cards: " + tableCards.size());
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
	
	//Jannick: calls the getNextTableCard() method and sends it to both clients
	public void sendTableCard() {
		String temp = getNextTableCard().toString();
		for(Account a : players) {
			String[] content = {"ResultSendCard", "true", "TableCard", temp};
			a.getClient().send(new ResultSendCard(content));
		}
	}
	
	//Jannick: calls the winner() method and sends a new message with the winner to both clients
	public void evaluateWinner() {
		String[] content = {"ResultBroadcastEvaluateWinner", "true", winner()};
		for(Account a : players) {
			a.getClient().send(new ResultBroadcastEvaluateWinner(content));
		}
		
		
	}
	
	//Jannick: Stoppt das Schwitzen
	public void stopGame(Account a) {
		removeAccount(a);
		this.playedCards.set(0);
	}
	
	public void removeAccount(Account a) {
		for(int i = 0; i < players.size(); i++) {
			if(a.getUsername().equalsIgnoreCase(players.get(i).getUsername())) {
				players.remove(i);
			}
	}
		
		
//		for(Account ac : players) {
//			if(ac.getUsername().equals(a.getUsername())) {
//				players.remove(ac);
//			}
//		}
	}
	
	public SimpleIntegerProperty getPlayedCards() {
		return playedCards;
	}
	public void setPlayedCards(SimpleIntegerProperty playedCards) {
		this.playedCards = playedCards;
	}
	
	public void increasePlayedCards() {
		this.playedCards.set(this.playedCards.get() + 1);
	}
	public Account getFirstPlayer() {
		return firstPlayer;
	}
	public void setFirstPlayer(Account firstPlayer) {
		this.firstPlayer = firstPlayer;
	}
	
	
}
