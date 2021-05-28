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
	public ArrayList<Account> players = new ArrayList<>(); 						//private		
	private SimpleIntegerProperty playedCards = new SimpleIntegerProperty();
	public Account firstPlayer = null;											//private
		
	private DeckOfCards deck;
	public ArrayList<Card> tableCards = new ArrayList<>();
//	public ArrayList<Card> followerCardsP1 = new ArrayList<>();
//	public ArrayList<Card> followerCardsP2 = new ArrayList<>();
	public ArrayList<Card> tmpUndeads = new ArrayList<>();
	private int fractionPointsP1;
	private int fractionPointsP2;
	public Card actualTableCard;

	public String roundWinner;
//	public Card followerCardP1;
//	public Card followerCardP2;
	private String undeadString;
	private boolean secondRoundStarted = false;



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
		playedCards.addListener((o, OldValue, NewValue) -> {
			if(NewValue.intValue() > 1) {
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
//		String gameWinner;
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
	 * David: Vergleicht die Anzahl Anhänger einer Fraktion und gibt den Spieler der gewonnen hat zurück
	 */
	private String winnerFraction(ArrayList<Card> cardsP1, ArrayList<Card> cardsP2) {
		String win = "NONE";
		
		for(Card c1 :cardsP1) {
System.out.println("WinnerFraction K1 :"+c1.toString());}
System.out.println("");
		for(Card c2 :cardsP2) {
System.out.println("WinnerFraction K2 :"+c2.toString());}

		
		if(cardsP1.size() > cardsP2.size()) {
System.out.println("IF STATMENT P1");
			return "P1";
		}
		if(cardsP1.size() < cardsP2.size()) {
System.out.println("IF STATMENT P2");
			return "P2";
		}
		if(cardsP1.size() == 0 && cardsP2.size() == 0) {
System.out.println("IF STATMENT NONE");
			return "NONE";
		}
		else 
			switch (getHighestCard(cardsP1).compareTo(getHighestCard(cardsP2))) {
			case  1: win = "P1";	break;
			case  0: win = "NONE";	break; 
			case -1: win = "P2";	break;
			}
System.out.println("ELSE höhere karte sieger :"+win);
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
	

	
	
	public void finishRound() {
		roundWinner = ""; //eigentlich unnötig
//		followerCardP1 = null; //eigentlich unnötig
//		followerCardP2 = null; //eigentlich unnötig
		String playedCardString = "";
		Boolean oneDwarf = false;
		Card playedTableCard = actualTableCard;
		int win;
		
		if(!secondRoundStarted) {
			tCard = getNextTableCard(); // Generiert einen Fehler in der zweiten Runde! getNextTableCard braucht es in der 2. Runde nicht
		}
		
		if(secondRoundStarted) {
			if(players.get(0).getPlayedCard().getSuit().toString().equals("dwarf") || 
					players.get(1).getPlayedCard().getSuit().toString().equals("dwarf")) {
				
	System.out.println("IF 2 ROUND: ");
	//*******TEST LINE
	for(Account p : players) System.out.println("Accountname des Spielers: "+p.getUsername().toString());	
	//*******END TEST LINE

	
				ArrayList<Card> dwarfs = new ArrayList<>();
				
				
				if(players.get(0).getPlayedCard().getSuit().toString().equals("dwarf")) {
					dwarfs.add(players.get(0).getPlayedCard());
					oneDwarf = true;
	System.out.println("IF 2 Round + dwarfIF 1");
				}
				if(players.get(1).getPlayedCard().getSuit().toString().equals("dwarf")) {
					dwarfs.add(players.get(1).getPlayedCard());
//					oneDwarf = true;
	System.out.println("IF 2 Round + dwarfIF 2");
				}

				
				switch(dwarfs.size()) {					
				case 1: if(oneDwarf) {
							if(players.get(0).getPlayedCard().getSuit().toString().equals("dwarf")) {
								playedCardString = players.get(1).getPlayedCard().toString()+"|"+dwarfs.get(0).toString();
							}else {
								playedCardString = players.get(0).getPlayedCard().toString()+"|"+dwarfs.get(0).toString();
							}
	System.out.println("SWITCH CASE1");
						}else {
							if(players.get(0).getPlayedCard().getSuit().toString().equals("dwarf")) {
								playedCardString = players.get(1).getPlayedCard().toString()+"|"+dwarfs.get(0).toString();
							}else {
								playedCardString = players.get(0).getPlayedCard().toString()+"|"+dwarfs.get(0).toString();
							}	System.out.println("SWITCH CASE2");
						}
						break;
				case 2: playedCardString = dwarfs.get(0).toString()+"|"+dwarfs.get(1).toString(); break;
				}
	System.out.println("IF 2 Round + SWITCH vorbei + playedCardString: "+playedCardString);


				
				if(players.get(0).getUsername().equals(firstPlayer.getUsername())) {
	System.out.println("IF 2 ROUND + IF: ");
					win = evaluateWinCard(players.get(0).getPlayedCard(), players.get(1).getPlayedCard());
				}else {
	System.out.println("IF 2 ROUND + ELSE");
					win = (evaluateWinCard(players.get(1).getPlayedCard(), players.get(0).getPlayedCard())) * -1;
				}
	System.out.println("Nach IF ELSE  + Sieger (1 oder -1): "+win);
				addFCards(win, dwarfs);
				
				
			}else {
	System.out.println("ELSE 2 ROUND: ");

				if(players.get(0).getUsername().equals(firstPlayer.getUsername())) {
	System.out.println("ELSE 2 ROUND + IF: ");
					win = evaluateWinCard(players.get(0).getPlayedCard(), players.get(1).getPlayedCard());
				}else {
	System.out.println("ELSE 2 ROUND + ELSE: ");
					win = (evaluateWinCard(players.get(1).getPlayedCard(), players.get(0).getPlayedCard())) * -1;
				}
				
	System.out.println("Nach IF ELSE  + Sieger (1 oder -1): "+win);
				
				playedCardString = players.get(0).getPlayedCard().toString()+"|"+players.get(1).getPlayedCard().toString();
	System.out.println("Nach PlayedCardString + String: "+playedCardString);
				addFCards(win);
	System.out.println("Nach AddFCards : + win "+ win);
	System.out.println("1"+win);
//				win = (win * (-1));
//				win *= (-1);
	System.out.println("2: "+win);
			}
			
			if(win ==  1) win =0;
			if(win == -1) win *= (-1); //anpassung auf bestehende Mehtoden, deshalb sonst message ohne funktion

	System.out.println("Sieger Account: "+players.get(win).getUsername());

	
	
	
			//Angepasste Finish Round Message
			for(int i = 0; players.size() > i; i++) {
			   String[] content = {"ResultBroadcastFinishRound", "true", players.get(win).getUsername(), playedCardString};
			   players.get(i).getClient().send(new ResultBroadcastFinishRound(content));
			}
			
			
			
			
				
		}else { 	
			if(players.get(0).getUsername().equals(firstPlayer.getUsername())) {
	System.out.println("1 Runde IF");
				win = evaluateWinCard(players.get(0).getPlayedCard(), players.get(1).getPlayedCard());
			}else {
	System.out.println("1 Runde ELSE");
				win = (evaluateWinCard(players.get(1).getPlayedCard(), players.get(0).getPlayedCard())) * -1;
			}
			addFCards(win, playedTableCard, tCard);	
			addUndeads(players.get(0).getPlayedCard(), players.get(1).getPlayedCard(), win);
			

			
			if(win ==  1) win =0;
			if(win == -1) win *= (-1); //anpassung auf bestehende Mehtoden, deshalb sonst message ohne funktion


	System.out.println("Sieger Account: "+players.get(win).getUsername());
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
			if(tableCards.size() == 0) {
				for(Account p : players) {
					String outPut = "Spieler: "+p.getUsername().toString();
					for(int i = 0; i < p.getFollowerCards().size()-1; i++) {
						outPut = (outPut+" | "+p.getFollowerCards().get(i).toString());
					}
					System.out.println(outPut);
				}
			}
		//*********END TEST OUTPUT
	}	
	
	public int evaluateWinCard(Card cardP1, Card cardP2) {
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
	
	private void addFCards(int win, Card tableC, Card nextTC) {	
		switch (win) {
		case  1:  	players.get(0).getFollowerCards().add(tableC);
					players.get(1).getFollowerCards().add(nextTC); break;
			
//					followerCardP1 = tableC;	
//					players.get(0).getFollowerCards().add(followerCardP1);
//					followerCardP2 = nextTC;
//					players.get(1).getFollowerCards().add(followerCardP2); break; 
		case -1: 	players.get(1).getFollowerCards().add(tableC);
					players.get(0).getFollowerCards().add(nextTC); break;
			
			
//					followerCardP2 = tableC;
//					players.get(1).getFollowerCards().add(followerCardP2);
//					followerCardP1 = nextTC;
//					players.get(0).getFollowerCards().add(followerCardP1); break;
		}
	}
	
	//TEsten Klammern bei Else zu entfernen
	private void addFCards(int win, ArrayList<Card> dwarfs) {
		if(dwarfs.size() == 2) {
System.out.println("Bei Fehlern mit Zwerg in addFCCards 2te variante");
			switch (win) {
			case  1: for(Card dwarf : dwarfs) {
					 players.get(1).getFollowerCards().add(dwarf);} break; 
			case -1: for(Card dwarf : dwarfs) {
					 players.get(0).getFollowerCards().add(dwarf);} break; 
			}
		}else {
			switch (win) {
			case  1: if(players.get(0).getPlayedCard().getSuit().toString().equals("undead")) {
						players.get(0).getUndeadCards().add(players.get(0).getPlayedCard());
					 }else {players.get(0).getFollowerCards().add(players.get(0).getPlayedCard());
					 }
					 players.get(1).getFollowerCards().add(dwarfs.get(0)); break;
			case -1: if(players.get(1).getPlayedCard().getSuit().toString().equals("undead")) {
						players.get(1).getUndeadCards().add(players.get(1).getPlayedCard());
					 }else {players.get(1).getFollowerCards().add(players.get(1).getPlayedCard());
					 }
					 players.get(0).getFollowerCards().add(dwarfs.get(0)); break;
			}
		}
	}
	
	private void addFCards(int win) {
		switch(win) {
		case  1: players.get(0).addFollowerCard(players.get(0).getPlayedCard());
			     players.get(0).addFollowerCard(players.get(1).getPlayedCard()); 
 System.out.println("addFCards mit 1 Argument (win) Case 1; Karte player1, Karte player2: "+win+"  "+players.get(0).getPlayedCard().toString()+"  "+players.get(1).getPlayedCard().toString());
 					break;
		case -1: players.get(1).addFollowerCard(players.get(0).getPlayedCard());
				 players.get(1).addFollowerCard(players.get(1).getPlayedCard()); 
 System.out.println("addFCards mit 1 Argument (win) Case 1; Karte player1, Karte player2: "+win+"  "+players.get(0).getPlayedCard().toString()+"  "+players.get(1).getPlayedCard().toString());
				 break;
		}
System.out.println("addFCards mit 1 Argument (win) nach Ausführung: "+win);

	}
	
	private void addUndeads(Card cardP1, Card cardP2, int win) {
		tmpUndeads.clear();
		if(suitToString(cardP1).equals("undead") || suitToString(cardP2).equals("undead")) {
			switch (win) {
			case  1: if(suitToString(cardP1).equals("undead")) {players.get(0).addUndeadCard(cardP1); tmpUndeads.add(cardP1);}
					 if(suitToString(cardP2).equals("undead")) {players.get(0).addUndeadCard(cardP2); tmpUndeads.add(cardP2);}
 System.out.println("Untote zu Account: "+players.get(0).getUsername()+" hinzugefügt");break;
			case -1: if(suitToString(cardP1).equals("undead")) {players.get(1).addUndeadCard(cardP1); tmpUndeads.add(cardP1);}
					 if(suitToString(cardP2).equals("undead")) {players.get(1).addUndeadCard(cardP2); tmpUndeads.add(cardP2);}
 System.out.println("Untote zu Account: "+players.get(1).getUsername()+" hinzugefügt");break;
			}
		}
		undeadString = "None";
		for(Card c : tmpUndeads)  
		switch(tmpUndeads.size()) {
		case 0: undeadString = "None"; System.out.println("undeadString "+undeadString); break;
		case 1: undeadString = tmpUndeads.get(0).toString(); System.out.println("undeadString "+undeadString); break;
		case 2: undeadString = tmpUndeads.get(0).toString()+"|"+tmpUndeads.get(1).toString(); System.out.println("undeadString "+undeadString); break;
		}
	}
	
	//Dave: Wandelt die Karte in einen String und gibt nur den suit der Karte als String zurück
	public String suitToString(Card card) {
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
	public boolean isSecondRoundStarted() {
		return secondRoundStarted;
	}
	public void setSecondRoundStarted(boolean secondRoundStarted) {
		this.secondRoundStarted = secondRoundStarted;
	}
}
