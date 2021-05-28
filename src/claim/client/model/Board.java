package claim.client.model;

import java.util.ArrayList;
import java.util.Collections;

import claim.commons.Card;

// Implemented by Jannick
// All logic realted to game, cards, etc. on client side
public class Board {
	
	public ArrayList<Card> handCards;
	private ArrayList<Card> followerCards;
	private ArrayList<Card> undeadCards;
	private ArrayList<Card> dwarfCards;
	private ArrayList<Card> goblinCards;
	private ArrayList<Card> knightCards;
	private ArrayList<Card> doubleCards;
	

	public Board() {
		handCards = new ArrayList<>();
		followerCards = new ArrayList<>();
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
		ArrayList<Card> goblins = new ArrayList<>();
		ArrayList<Card> dwarfs = new ArrayList<>();
		ArrayList<Card> undeads = new ArrayList<>();
		ArrayList<Card> doubles = new ArrayList<>();
		ArrayList<Card> knights = new ArrayList<>();
		
		for(Card card : handCards) {
			switch (suitToString(card)) {
			case "goblin": goblins.add(card); break;
			case "dwarf" : dwarfs.add(card);  break;
			case "undead": undeads.add(card); break;
			case "double": doubles.add(card); break;
			case "knight": knights.add(card); break;		
			}
		}
		
		Collections.sort(goblins);
		Collections.sort(dwarfs);
		Collections.sort(undeads);
		Collections.sort(doubles);
		Collections.sort(knights);
		
		handCards.clear();
		
		if(goblins.isEmpty() == false) for (Card card : goblins) handCards.add(card);
		if(dwarfs.isEmpty()  == false) for (Card card : dwarfs)  handCards.add(card);
		if(undeads.isEmpty() == false) for (Card card : undeads) handCards.add(card);
		if(doubles.isEmpty() == false) for (Card card : doubles) handCards.add(card);
		if(knights.isEmpty() == false) for (Card card : knights) handCards.add(card);
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
		System.out.println("Card added ToGroup (Board Zeile 180): " + follower.toString());
	}
	
	public void clearCards() {
		this.handCards.clear();
		this.followerCards.clear();
		this.undeadCards.clear();
		this.dwarfCards.clear();
		this.goblinCards.clear();
		this.knightCards.clear();
		this.doubleCards.clear();
	}
	
	public int getNumOfGoblins() {
		return goblinCards.size();
	}
	
	public int getNumOfDwarfs() {
		return dwarfCards.size();
	}
	
	public int getNumOfUndeads() {
		return undeadCards.size();
	}
	
	public int getNumOfDoubles() {
		return doubleCards.size();
	}
	
	public int getNumOfKnights() {
		return knightCards.size();
	}

	public ArrayList<Card> getUndeadCards() {
		return undeadCards;
	}

	public void setUndeadCards(ArrayList<Card> undeadCards) {
		this.undeadCards = undeadCards;
	}

	public ArrayList<Card> getGoblinCards() {
		return goblinCards;
	}

	public void setGoblinCards(ArrayList<Card> goblinCards) {
		this.goblinCards = goblinCards;
	}

	public ArrayList<Card> getKnightCards() {
		return knightCards;
	}

	public void setKnightCards(ArrayList<Card> knightCards) {
		this.knightCards = knightCards;
	}

	public ArrayList<Card> getDoubleCards() {
		return doubleCards;
	}

	public void setDoubleCards(ArrayList<Card> doubleCards) {
		this.doubleCards = doubleCards;
	}

	public void setDwarfCards(ArrayList<Card> dwarfCards) {
		this.dwarfCards = dwarfCards;
	}
	
}
