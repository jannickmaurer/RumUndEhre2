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

// Implemented by David & Jannick -> represents the functionality we need for a Claims game 
	/*
	 * David & Jannick: 
	 */

public class Table {
	private ArrayList<Account> players = new ArrayList<>(); 							
	private SimpleIntegerProperty playedCards = new SimpleIntegerProperty();
	private Account firstPlayer = null;											
	private DeckOfCards deck;
	private ArrayList<Card> tableCards = new ArrayList<>();
	private ArrayList<Card> tmpUndeads = new ArrayList<>();
	private int fractionPointsP1;
	private int fractionPointsP2;
	private Card actualTableCard;
	private String undeadString;
	private boolean secondRoundStarted = false;
	private boolean gameEndedSuccessfully = false;

	private Card tCard;

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
		// As soon as two cards are played, round will be evaluated
		playedCards.addListener((o, OldValue, NewValue) -> {
			if(NewValue.intValue() > 1) {
				//Artificial break in order to give the clients time to process previous messages
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finishRound();
			}
		});
//		super();
	}

	/*
	 * David: Creates and shares cards
	 */
	// David: Gibt bei den ersten zwei Aufrufen 13 Karten zurück und beim dritten Aufruf die restlichen 26 Karten
	public void deal() {
		deck = new DeckOfCards();
		Card card;
		for (int i = 0; i < 3; i++) {
			if (deck.getCardsRemaining() > 26) {
				for (int j = 0; j < 13; j++) {
					card = deck.dealCard();
					if (i == 0) players.get(0).addHandCard(card);
					else players.get(1).addHandCard(card);
				}
			} else {
				for (int j = 0; j < 26; j++) {
					card = deck.dealCard();
					tableCards.add(card);
				}
			}
		}
	}

	// David: Wertet den Sieger des Spiels aus am Ende aller Runden aus und gibt den Accountnamen des Siegers zurück.
	public String winner() {
		String win = "NoWinner";
		gameWinner();
		if(fractionPointsP1 >= 3) return players.get(0).getUsername();
		if(fractionPointsP2 >= 3) return players.get(1).getUsername();
		return win;
	}
	
	/*
	 * David: Sortiert die gewonnenen Karten in die verschiedenen Fraktionen. Danach wird die Sieger Evaluierung und
	 * das Hinzufügen der eventuellen Siegerpunkte aufgerufen
	 */
	private void gameWinner() {
///**********DELETE THIS START
		for(Account p : players) {
			System.out.println("");
			System.out.println(p.getUsername()); 
		
			for(Card c : p.getFollowerCards()) {
				System.out.print(c.toString() + " | ");
			}
		}
///**********DELETE THIS END
		ArrayList<Card> goblinP1 = new ArrayList<>();
		ArrayList<Card> goblinP2 = new ArrayList<>();
		ArrayList<Card> dwarfP1  = new ArrayList<>();
		ArrayList<Card> dwarfP2  = new ArrayList<>();
		ArrayList<Card> knightP1 = new ArrayList<>();
		ArrayList<Card> knightP2 = new ArrayList<>();
		ArrayList<Card> doubleP1 = new ArrayList<>();
		ArrayList<Card> doubleP2 = new ArrayList<>();
		
		for (Card card : players.get(0).getFollowerCards()) {
			switch (suitToString(card)) {
			case "goblin": goblinP1.add(card); break;
			case "dwarf" : dwarfP1.add(card);  break;
			case "knight": knightP1.add(card); break;
			case "double": doubleP1.add(card); break;
			}
		}
		
		for (Card card : players.get(1).getFollowerCards()) {
			switch (suitToString(card)) {
			case "goblin": goblinP2.add(card); break;
			case "dwarf" : dwarfP2.add(card);  break;
			case "knight": knightP2.add(card); break;
			case "double": doubleP2.add(card); break;
			}
		}
//*******START TEST 
	System.out.println("Table; gameWinner(): Ausgabe der Karten der Accounts zum Testen");
	String p1 = players.get(0).getUsername().toString();
	String p2 = players.get(1).getUsername().toString();

	for(Card g1 : goblinP1) {
		p1 = p1 +" | "+g1.toString();
	}
	for(Card g1 : dwarfP1) {
		p1 = p1 +" | "+g1.toString();
	}
	for(Card g1 : knightP1) {
		p1 = p1 +" | "+g1.toString();
	}
	for(Card g1 : doubleP1) {
		p1 = p1 +" | "+g1.toString();
	}
	for(Card g1 : players.get(0).getUndeadCards()) {
		p1 = p1 +" | "+g1.toString();
	}
	
	for(Card g2 : goblinP2) {
		p2 = p2 +" | "+g2.toString();
	}
	for(Card g2 : dwarfP2) {
		p2 = p2 +" | "+g2.toString();
	}
	for(Card g2 : knightP2) {
		p2 = p2 +" | "+g2.toString();
	}
	for(Card g2 : doubleP2) {
		p2 = p2 +" | "+g2.toString();
	}
	for(Card g2 : players.get(1).getUndeadCards()) {
		p2 = p2 +" | "+g2.toString();
	}
System.out.println(p1);	
System.out.println(p2);		
	
//*******START TEST 	
		addFractionPoint(winnerFraction(goblinP1, goblinP2));
		addFractionPoint(winnerFraction(dwarfP1, dwarfP2));
		addFractionPoint(winnerFraction(knightP1, knightP2));
		addFractionPoint(winnerFraction(doubleP1, doubleP2));
		addFractionPoint(winnerFraction(players.get(0).getUndeadCards(), players.get(1).getUndeadCards()));
	}
	
	/*
	 * David: Vergleicht die Anzahl Anhänger einer Fraktion und gibt den Spieler der gewonnen hat zurück.
	 * Im Fall, das beide Spieler gleich viele Karten haben wird ausgewertet wer die höchste hat. Der ist dann auch Sieger 
	 * dieser Fraktion. Falls z.B: beide keine Karte von der selben Fraktion hat gibt es keinen Sieger.
	 */
	private String winnerFraction(ArrayList<Card> cardsP1, ArrayList<Card> cardsP2) {
		String win = "NONE";
		if(cardsP1.size() > cardsP2.size()) return "P1"; //{
//			return "P1";
//		}
		if(cardsP1.size() < cardsP2.size()) return "P2";//{
//			return "P2";
//		}
		if(cardsP1.size() == 0 && cardsP2.size() == 0) {
			return "NONE";
		} else {
			switch (getHighestCard(cardsP1).compareTo(getHighestCard(cardsP2))) {
			case  1: win = "P1";	break;
			case  0: win = "NONE";	break; 
			case -1: win = "P2";	break;
			}
		}
		return win;	
	}
	
	
	// David: Fügt dem Sieger einer Fraktion einen Punkt hinzu, falls es kein Unentschieden gibt.
	private void addFractionPoint(String winner) {
		switch (winner) {
		case "P1"  : fractionPointsP1++; break;
		case "P2"  : fractionPointsP2++; break;
		case "NONE": break;
		}
	}
	
	// David: Gibt die letzte Karte der Tischkarten zurück und löscht diese
	public Card getNextTableCard() {
	    Card card = (tableCards.size() > 0) ? tableCards.remove(tableCards.size()-1) : null;
        actualTableCard = card;
		return card;
	}
		
	// David: Sortiert die Karten und gibt die letzte Karte, welche die Höchste ist zurück
	private Card getHighestCard(ArrayList<Card> cards) {
		Collections.sort(cards);
        Card card = (cards.size() > 0) ? cards.remove(cards.size()-1) : null;
		return card;
	}
	
	// DS
	/*
	 * David: Die Methode ermittelt den Gewinner am Ende jeder Runde aus den zwei Spielern. Sie löst die Übermittelung an die 
	 * Clients mit der Rückmeldung aus.
	 */
	public void finishRound() {
		String playedCardString = "";
		Card playedTableCard = actualTableCard;
		int win;
		
		if(!secondRoundStarted) {
			tCard = getNextTableCard(); 
		}
		
		if(secondRoundStarted) {
			if(players.get(0).getPlayedCard().getSuit().toString().equals("dwarf") || 
					players.get(1).getPlayedCard().getSuit().toString().equals("dwarf")) {
				
				ArrayList<Card> dwarfs = new ArrayList<>();		
				
				if(players.get(0).getPlayedCard().getSuit().toString().equals("dwarf")) {
					dwarfs.add(players.get(0).getPlayedCard());
				}
				if(players.get(1).getPlayedCard().getSuit().toString().equals("dwarf")) {
					dwarfs.add(players.get(1).getPlayedCard());
				}
				
				switch(dwarfs.size()) {					
				case 1: if(players.get(0).getPlayedCard().getSuit().toString().equals("dwarf")) {
							playedCardString = players.get(1).getPlayedCard().toString()+"|"+dwarfs.get(0).toString();
						}
						if(players.get(1).getPlayedCard().getSuit().toString().equals("dwarf")) {
							playedCardString = players.get(0).getPlayedCard().toString()+"|"+dwarfs.get(0).toString();
						} break;
				case 2: playedCardString = dwarfs.get(0).toString()+"|"+dwarfs.get(1).toString(); break;
				}			
				if(players.get(0).getUsername().equals(firstPlayer.getUsername())) {
					win = evaluateWinCard(players.get(0).getPlayedCard(), players.get(1).getPlayedCard());
				}else {
					win = (evaluateWinCard(players.get(1).getPlayedCard(), players.get(0).getPlayedCard())) * -1;
				}
				addFollowerCards(win, dwarfs);				
				
			}else {
				if(players.get(0).getUsername().equals(firstPlayer.getUsername())) {
					win = evaluateWinCard(players.get(0).getPlayedCard(), players.get(1).getPlayedCard());
				}else {
					win = (evaluateWinCard(players.get(1).getPlayedCard(), players.get(0).getPlayedCard())) * -1;
				}				
				playedCardString = players.get(0).getPlayedCard().toString()+"|"+players.get(1).getPlayedCard().toString();
				addFollowerCards(win);
			}
			
			if(win ==  1) win =0;
			if(win == -1) win *= (-1); 

			for(int i = 0; players.size() > i; i++) {
			   String[] content = {"ResultBroadcastFinishRound", "true", players.get(win).getUsername(), playedCardString};
			   players.get(i).getClient().send(new ResultBroadcastFinishRound(content));
			}
				
		}else { 	
			if(players.get(0).getUsername().equals(firstPlayer.getUsername())) {
				win = evaluateWinCard(players.get(0).getPlayedCard(), players.get(1).getPlayedCard());
			}else {
				win = (evaluateWinCard(players.get(1).getPlayedCard(), players.get(0).getPlayedCard())) * -1;
			}
			addFollowerCards(win, playedTableCard, tCard);	
			addUndeads(players.get(0).getPlayedCard(), players.get(1).getPlayedCard(), win);
			
			if(win ==  1) win =0;
			if(win == -1) win *= (-1); 
			
			for(int i = 0; players.size() > i; i++) {
				if(undeadString.equalsIgnoreCase("None")) {
					String[] content = {"ResultBroadcastFinishRound", "true", players.get(win).getUsername(), tCard.toString()};
					players.get(i).getClient().send(new ResultBroadcastFinishRound(content));
				} else {
					String[] content = {"ResultBroadcastFinishRound", "true", players.get(win).getUsername(), tCard.toString(), undeadString};
					players.get(i).getClient().send(new ResultBroadcastFinishRound(content));
				}

			}
		}
		this.playedCards.set(0);
		this.firstPlayer = null;
		
		for(Account a: players) {
			a.clearPlayedCard();
		}
		//*****Test OUTPUT
//			if(tableCards.size() == 0) {
//				for(Account p : players) {
//					String outPut = "Spieler: "+p.getUsername().toString();
//					for(int i = 0; i < p.getFollowerCards().size()-1; i++) {
//						outPut = (outPut+" | "+p.getFollowerCards().get(i).toString());
//					}
//					System.out.println(outPut);
//				}
//			}
		//*********END TEST OUTPUT
			System.out.println("**************************");
			System.out.print("Status nach FinishRound: ");
//*********entfernen			
			for(Account p: players) {
				System.out.println("");
				System.out.print("FollowerCards von " + p.getUsername() + ": ");
				for(Card c : p.getFollowerCards()) {
					System.out.print(c.toString() + " | ");
				}
				System.out.println("");
				System.out.print("Undead Cards von " + p.getUsername() + ": ");
				for(Card c : p.getUndeadCards()) {
					System.out.print(c.toString() + " | ");
				}
			}
			System.out.println("");
			System.out.println("**************************");
			
//*********entfernen ENDE
			
	}	
	
	//David: Evaluiert welche Karte gewonnen hat und gibt je nach Kartenposition 1 oder -1 zurück
	private int evaluateWinCard(Card cardP1, Card cardP2) {
		int win = 1;		
		if(suitToString(cardP1).equals("goblin") && suitToString(cardP2).equals("knight") ||
				suitToString(cardP1).equals("knight") && suitToString(cardP2).equals("goblin")) {
			if(suitToString(cardP1).equals("knight")) return 1;				
			else return -1;
		}
		if(suitToString(cardP1).equals(suitToString(cardP2)) || 
			(!suitToString(cardP1).equals("double") && suitToString(cardP2).equals("double"))) {
			switch (cardP1.compareTo(cardP2)) {
			case  1: break;
			case  0: break;
			case -1: win = -1; break;
			}
		}
		return win;
	}
	
	//David: Fügt in Runde 1 jedem Spieler die gewonnene Karte hinzu
	private void addFollowerCards(int win, Card tableC, Card nextTC) {	
		switch (win) {
		case  1:  	players.get(0).getFollowerCards().add(tableC);
					players.get(1).getFollowerCards().add(nextTC); break;
		case -1: 	players.get(1).getFollowerCards().add(tableC);
					players.get(0).getFollowerCards().add(nextTC); break;
		}
	}
	
	/*
	 * David: Fügt in Runde 2 jedem Spieler die gewonnenen Karten hinzu, falls ein Zwerg gespielt wurde.
	 * Falls noch eine Untotenkarte dabei ist, wird die separat zu den Untotenkarten hinzugefügt.
	 */
	private void addFollowerCards(int win, ArrayList<Card> dwarfs) {
		if(dwarfs.size() == 2) {
			switch (win) {
			case  1: for(Card dwarf : dwarfs) {
					 players.get(1).getFollowerCards().add(dwarf);} break; 
			case -1: for(Card dwarf : dwarfs) {
					 players.get(0).getFollowerCards().add(dwarf);} break; 
			}
		}else {
			switch (win) {
			case  1: if(players.get(0).getPlayedCard().getSuit().toString().equals("undead") || 
						players.get(1).getPlayedCard().getSuit().toString().equals("undead")) {
						if(players.get(0).getPlayedCard().getSuit().toString().equals("undead")) {
							players.get(0).getUndeadCards().add(players.get(0).getPlayedCard());
						} else {
							players.get(0).getUndeadCards().add(players.get(1).getPlayedCard());
						}
				 	 } else {
				 		 if(!players.get(0).getPlayedCard().getSuit().toString().equals("dwarf")) {
				 			 players.get(0).getFollowerCards().add(players.get(0).getPlayedCard());
				 		 } else {
				 			 players.get(0).getFollowerCards().add(players.get(1).getPlayedCard());
				 		 }
				 	 }
					 players.get(1).getFollowerCards().add(dwarfs.get(0)); break;

			case -1: if(players.get(0).getPlayedCard().getSuit().toString().equals("undead") || 
						players.get(1).getPlayedCard().getSuit().toString().equals("undead")) {
						if(players.get(0).getPlayedCard().getSuit().toString().equals("undead")) {
							players.get(1).getUndeadCards().add(players.get(0).getPlayedCard());
						} else {
							players.get(1).getUndeadCards().add(players.get(1).getPlayedCard());
						}
					 } else {
						 if(!players.get(0).getPlayedCard().getSuit().toString().equals("dwarf")) {
							 players.get(1).getFollowerCards().add(players.get(0).getPlayedCard());
						 } else {
							 players.get(1).getFollowerCards().add(players.get(1).getPlayedCard());
						 }
					 }
					 players.get(0).getFollowerCards().add(dwarfs.get(0)); break;
			}
		}
	}
	
	/*
	 * David: Fügt in Runde 2 dem Sieger beide gewonnenen Karten hinzu, sofern kein Zwerg gespielt wurde. 
	 * Die Untoten werden separat zu den Untoten hinzugefügt und nicht zu den normalen Followerkarten.
	 */
	private void addFollowerCards(int win) {		
		if(players.get(0).getPlayedCard().getSuit().toString().equals("undead") ||
			players.get(1).getPlayedCard().getSuit().toString().equals("undead")) {
			tmpUndeads.clear();
			Boolean one = false;
			if(players.get(0).getPlayedCard().getSuit().toString().equals("undead")) tmpUndeads.add(players.get(0).getPlayedCard());
			if(players.get(1).getPlayedCard().getSuit().toString().equals("undead")) {
				tmpUndeads.add(players.get(1).getPlayedCard());
				one = true;
			}
			switch(tmpUndeads.size()) {
			case 1: if(!one) {
						if(win == 1) {
							players.get(0).addFollowerCard(players.get(1).getPlayedCard());
							players.get(0).addUndeadCard(tmpUndeads.get(0));
						} else {
							players.get(1).addFollowerCard(players.get(1).getPlayedCard());
							players.get(1).addUndeadCard(tmpUndeads.get(0));
						}
					} else {
						 if(win == 1) {
							players.get(0).addFollowerCard(players.get(0).getPlayedCard());
							players.get(0).addUndeadCard(tmpUndeads.get(0));
						 } else {
							players.get(1).addFollowerCard(players.get(0).getPlayedCard());
							players.get(1).addUndeadCard(tmpUndeads.get(0));
						 }
					} 
					break;	
			case 2: if(win == 1) {
						for(Card u : tmpUndeads) {
							players.get(0).addUndeadCard(u);;
						}
					} else {
						for(Card u : tmpUndeads) {
							players.get(1).addUndeadCard(u);
						}
					} 
					break;
			}
		} else {
			switch(win) {
			case  1: players.get(0).addFollowerCard(players.get(0).getPlayedCard());
					 players.get(0).addFollowerCard(players.get(1).getPlayedCard()); break;
			case -1: players.get(1).addFollowerCard(players.get(0).getPlayedCard());
					 players.get(1).addFollowerCard(players.get(1).getPlayedCard()); break;
			}
		}
	}
	
	//David: Die Untoten werden im Fall das sie vorhanden sind den Untotenkarten hinzugefügt
	private void addUndeads(Card cardP1, Card cardP2, int win) {
		tmpUndeads.clear();
		if(suitToString(cardP1).equals("undead") || suitToString(cardP2).equals("undead")) {
			switch (win) {
			case  1: if(suitToString(cardP1).equals("undead")) {players.get(0).addUndeadCard(cardP1); tmpUndeads.add(cardP1);}
					 if(suitToString(cardP2).equals("undead")) {players.get(0).addUndeadCard(cardP2); tmpUndeads.add(cardP2);} break;
			case -1: if(suitToString(cardP1).equals("undead")) {players.get(1).addUndeadCard(cardP1); tmpUndeads.add(cardP1);}
					 if(suitToString(cardP2).equals("undead")) {players.get(1).addUndeadCard(cardP2); tmpUndeads.add(cardP2);} break;
			}
		}
	
		undeadString = "None";
		switch(tmpUndeads.size()) {
		case 0: break;
		case 1: undeadString = tmpUndeads.get(0).toString(); break;
		case 2: undeadString = tmpUndeads.get(0).toString()+"|"+tmpUndeads.get(1).toString(); break;
		}
	}
	
	//David: Wandelt die Karte in einen String und gibt nur den suit der Karte als String zurück
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
		this.gameEndedSuccessfully = true;
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
	}
	
	// Getter & Setter
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
	public boolean isSecondRoundStarted() {
		return secondRoundStarted;
	}
	public void setSecondRoundStarted(boolean secondRoundStarted) {
		this.secondRoundStarted = secondRoundStarted;
	}

	public boolean isGameEndedSuccessfully() {
		return gameEndedSuccessfully;
	}
	public void setGameEndedSuccessfully(boolean gameEndedSuccessfully) {
		this.gameEndedSuccessfully = gameEndedSuccessfully;
	}
}
