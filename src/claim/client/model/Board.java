package claim.client.model;

import java.util.ArrayList;
import java.util.Collections;

import claim.commons.Card;

// Implemented by Jannick
// All logic realted to game, cards, etc. on client side
public class Board {
	
	public ArrayList<Card> handCards;
	private ArrayList<Card> followerCards;
	private ArrayList<Card> pointCards;//Was sind PointCArds?????  Untote?
//	private ArrayList<Card> playableHandCards;//Dave	//Auskommentiert - NEU Playable
	private ArrayList<Card> undeadCards;
	
	public Board() {
		handCards = new ArrayList<>();
		followerCards = new ArrayList<>();
		pointCards = new ArrayList<>();
		undeadCards = new ArrayList<>();
	}
	
	public void addHandCards(ArrayList<String> handCards) {
		for(String s : handCards) {
			this.handCards.add(new Card(s));
		}
		sortHandCards();
		
		System.out.println("Handcards Created: ");
		for (Card card : this.handCards) {
			System.out.println(card.toString());
		}
	}

	public ArrayList<Card> getHandCards() {
		return handCards;
	}

	public void setHandCards(ArrayList<Card> handCards) {
		this.handCards = handCards;
//		this.handCards = sortHandCards(handCards); //HandKarten werden nach dem ausgeben noch sortiert
	}

	public ArrayList<Card> getFollowerCards() {
		return followerCards;
	}

	public void setFollowerCards(ArrayList<Card> followerCards) {
		this.followerCards = followerCards;
	}

	public ArrayList<Card> getPointCards() {
		return pointCards;
	}

	public void setPointCards(ArrayList<Card> pointCards) {
		this.pointCards = pointCards;
	}
	public void addUndead(Card undead) {
		this.undeadCards.add(undead);
	}
	
	
	//ToDo: Im controller die Methoden EvalutatePlayableHandCards aufrufen und nach dem spielen
	//      die gebrauchte Karte löschen
	//		sortHandCards einbinden in Code, bei setHandCards kurz mit Jannick schauen
	
	
	//Dave: Entfernt die gespielte Karte aus den HandCards. Return, damit nur 1 Mal eine Karte
	//entfernt wird
	public void removePlayedCard(Card removeCard) {
		for (int i = 0; i < handCards.size()-1; i++) {
			if(suitToString(handCards.get(i)).equals(suitToString(removeCard))) {
				if(handCards.get(i).compareTo(removeCard) == 0) {
					handCards.remove(i);
					return;
				}
			}
		}
	}
	
	//Dave: Fügt die spielbaren Karten der ArrayList playableHandCards hinzu
	//Auskommentiert - NEU Playable
	
//	public void evaluatePlayableHandCards(Card opponentCard) {
//		playableHandCards.clear();
//		sameSuitAsOpponent(opponentCard);
//		if(playableHandCards.isEmpty() == true) {
//			for(Card card : handCards) {
//				playableHandCards.add(card);
//			}
//		}
//	}
	
	
	//Dave: Alle  spielbaren Karten werden den playableHandCards hinzugefügt sofern die eigenen 
	//Handkarten die gleiche Farbe oder einen Doppelgänger haben
	//Auskommentiert - NEU Playable

//	public void sameSuitAsOpponent(Card opponentCard) {
//		if(suitToString(opponentCard).equals("double")) {
//			for(int i = 0; i < handCards.size()-1; i++) {
//				if(suitToString(handCards.get(i)).equals("double")) playableHandCards.add(handCards.get(i));
//			}
//		}else {
//			for(int i = 0; i < handCards.size()-1; i++) {
//				if(suitToString(opponentCard).equals(suitToString(handCards.get(i))) || 
//				   suitToString(handCards.get(i)).equals("double")) {
//					playableHandCards.add(handCards.get(i));
//				}
//			}
//		}
//	}
	
	public void setPlayableHC(Card opponentCard) {
		boolean hasPlayableCards = false;
		for(Card card : handCards) {
			card.setPlayable(false);
		}
		if(suitToString(opponentCard).equals("double")) {
			for(int i = 0; i < handCards.size()-1; i++) {
				if(suitToString(handCards.get(i)).equals("double")) {
					handCards.get(i).setPlayable(true);
					hasPlayableCards = true;
				}
			}
		}else {
			for(int i = 0; i < handCards.size()-1; i++) {
				if(suitToString(opponentCard).equals(suitToString(handCards.get(i))) || 
				   suitToString(handCards.get(i)).equals("double")) {
					handCards.get(i).setPlayable(true);
					hasPlayableCards = true;
				}
			}
		}
		if(hasPlayableCards == false) {
			for(Card card : handCards) {
				card.setPlayable(true);
			}
		}
	}
	
	//Dave: Wandelt die Karte in einen String und gibt nur den suit der Karte als String zurück
	public String suitToString(Card card) {
		String cardString = card.toString();
	    String[] tmp = cardString.split("\\_");
    	return tmp[0];
	}
	
	//Dave: Sortiert am Anfang die Handkarten, damit gleiche Fraktionen nebeneinander sind
	//Mit Jannick anschauen bzgl. aufruf This.handcards = handcards;
	public void sortHandCards() {
		ArrayList<Card> goblinCards = new ArrayList<>();
		ArrayList<Card> dwarfCards = new ArrayList<>();
		ArrayList<Card> undeadCards = new ArrayList<>();
		ArrayList<Card> doubleCards = new ArrayList<>();
		ArrayList<Card> knightCards = new ArrayList<>();
		
		for(Card card : handCards) {
			switch (suitToString(card)) {
			case "goblin": goblinCards.add(card); break;
			case "dwarf" : dwarfCards.add(card);  break;
			case "undead": undeadCards.add(card); break;
			case "double": doubleCards.add(card); break;
			case "knight": knightCards.add(card); break;		
			}
		}
		
		Collections.sort(goblinCards);
		Collections.sort(dwarfCards);
		Collections.sort(undeadCards);
		Collections.sort(doubleCards);
		Collections.sort(knightCards);
		
		handCards.clear();
		
		if(goblinCards.isEmpty() == false) for (Card card : goblinCards) handCards.add(card);
		if(dwarfCards.isEmpty() == false)  for (Card card : dwarfCards)  handCards.add(card);
		if(undeadCards.isEmpty() == false) for (Card card : undeadCards) handCards.add(card);
		if(doubleCards.isEmpty() == false) for (Card card : doubleCards) handCards.add(card);
		if(knightCards.isEmpty() == false) for (Card card : knightCards) handCards.add(card);
	}
	
	
}
