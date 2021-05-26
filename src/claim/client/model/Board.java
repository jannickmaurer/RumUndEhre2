package claim.client.model;

import java.util.ArrayList;
import java.util.Collections;

import claim.commons.Card;

// Implemented by Jannick
// All logic realted to game, cards, etc. on client side
public class Board {
	
	public ArrayList<Card> handCards;
	private ArrayList<Card> followerCards;
//	private ArrayList<Card> pointCards;
	private ArrayList<Card> undeadCards;
	private ArrayList<Card> dwarfCards;
	private ArrayList<Card> goblinCards;
	private ArrayList<Card> knightCards;
	private ArrayList<Card> doubleCards;
	

	public Board() {
		handCards = new ArrayList<>();
		followerCards = new ArrayList<>();
//		pointCards = new ArrayList<>();
		undeadCards = new ArrayList<>();
		dwarfCards = new ArrayList<>();
		goblinCards = new ArrayList<>();
		knightCards = new ArrayList<>();
		doubleCards = new ArrayList<>();
		
	}
	
	public void addHandCards(ArrayList<String> handCards) {
		for(String s : handCards) {
			this.handCards.add(new Card(s));
		}
		sortHandCards();
	//	setPlayableHC();//evtl löschen sollte überflüssig sein
		
		System.out.println("Handcards Created: ");
		for (Card card : this.handCards) {
			System.out.println(card.toString());
		}
	}
	
	/*
	 * David Schürch
	 * Entfernt die gespielte Karte aud der ArrayList Handcards
	 */
	public void removePlayedCard(Card removeCard) {
		for (int i = 0; i < handCards.size(); i++) {
			if(suitToString(handCards.get(i)).equals(suitToString(removeCard))) {
				if(handCards.get(i).compareTo(removeCard) == 0) {
					handCards.remove(i);
					setPlayableHC();
					return;
				}
			}
		}
	}
	
	public void setPlayableHC(){
		for(Card card : handCards) {
			card.setPlayable(true);
		}
	}

	public void setPlayableHC(Card opponentCard) {
		boolean hasPlayableCards = false;
		for(Card card : handCards) {
			card.setPlayable(false);
		}
		if(suitToString(opponentCard).equals("double")) {
			for(int i = 0; i < handCards.size(); i++) {
				if(suitToString(handCards.get(i)).equals("double")) {
					handCards.get(i).setPlayable(true);
					hasPlayableCards = true;
				}
			}
		}else {
			for(int i = 0; i < handCards.size(); i++) {
				if(suitToString(opponentCard).equals(suitToString(handCards.get(i)))){
					handCards.get(i).setPlayable(true);
					hasPlayableCards = true;
				}
			}
			if(hasPlayableCards) {
				for(int i = 0; i < handCards.size(); i++) {
					if(suitToString(handCards.get(i)).equals("double")){
						handCards.get(i).setPlayable(true);
					}
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
	private String suitToString(Card card) {
		String cardString = card.toString();
	    String[] tmp = cardString.split("\\_");
    	return tmp[0];
	}
	
	/*
	 * David Schürch
	 * Sortiert bei Aufruf die Handkarten des Spielers 
	 */
	private void sortHandCards() {
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
		if(dwarfCards.isEmpty()  == false) for (Card card : dwarfCards)  handCards.add(card);
		if(undeadCards.isEmpty() == false) for (Card card : undeadCards) handCards.add(card);
		if(doubleCards.isEmpty() == false) for (Card card : doubleCards) handCards.add(card);
		if(knightCards.isEmpty() == false) for (Card card : knightCards) handCards.add(card);
	}

	public ArrayList<Card> getHandCards() {
		return handCards;
	}

	public void setHandCards(ArrayList<Card> handCards) {
		this.handCards = handCards;
	}

	public ArrayList<Card> getFollowerCards() {
		return followerCards;
	}

	public void setFollowerCards(ArrayList<Card> followerCards) {
		this.followerCards = followerCards;
	}

//	public ArrayList<Card> getPointCards() {
//		return pointCards;
//	}
//
//	public void setPointCards(ArrayList<Card> pointCards) {
//		this.pointCards = pointCards;
//	}
	public void addUndead(Card undead) {
		this.undeadCards.add(undead);
	}
	
	public ArrayList<Card> getDwarfCards() {
		return dwarfCards;
	}

	public void addDwarfCards(Card dwarf) {
		this.dwarfCards.add(dwarf);
		System.out.println("Card added: " + dwarf.toString());
	}
	
	public void addCardToGroup(Card follower) {
		switch(follower.getSuit().toString()) {
		case "goblin": goblinCards.add(follower); break;
		case "undead": undeadCards.add(follower); break;
		case "double": doubleCards.add(follower); break;
		case "knight": knightCards.add(follower); break;
		}
		System.out.println("Card added: " + follower.toString());
	}
	
	public int getNumOfGoblins() {
		return goblinCards.size();
	}
	
	public int getNumOfDwarfs() {
		return goblinCards.size();
	}
	
	public int getNumOfUndeads() {
		return goblinCards.size();
	}
	
	public int getNumOfDoubles() {
		return goblinCards.size();
	}
	
	public int getNumOfKnights() {
		return goblinCards.size();
	}
}
