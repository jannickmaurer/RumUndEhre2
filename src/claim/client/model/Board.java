package claim.client.model;

import java.util.ArrayList;

import claim.commons.Card;

// Implemented by Jannick
// All logic realted to game, cards, etc. on client side
public class Board {
	
	private ArrayList<Card> handCards;
	private ArrayList<Card> followerCards;
	private ArrayList<Card> pointCards;//Was sind PointCArds?????
	private ArrayList<Card> playableHandCards;//Dave

	
	public Board() {
		handCards = new ArrayList<>();
		followerCards = new ArrayList<>();
		pointCards = new ArrayList<>();
	}
	
	public void addHandCards(ArrayList<String> handCards) {
		for(String s : handCards) {
			this.handCards.add(new Card(s));
		}
		System.out.println("Handcards Created: ");
		for (Card c : this.handCards) {
			System.out.println(c.toString());
		}
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

	public ArrayList<Card> getPointCards() {
		return pointCards;
	}

	public void setPointCards(ArrayList<Card> pointCards) {
		this.pointCards = pointCards;
	}
	
	
	//ToDo: Im controller die Methoden EvalutatePlayableHandCards aufrufen und nach dem spielen
	//      die gebrauchte Karte löschen
	
	//
	
	
	//Dave: Entfernt die gespielte Karte aus den HandCards. Return, damit nur 1 Mal eine Karte
	//entfernt wird
	public void removePlayedCard(Card removeCard) {
		for (int i = 0; i < handCards.size()-1; i++) {
			if(suitToString(handCards.get(i)) == suitToString(removeCard)) {
				if(handCards.get(i).compareTo(removeCard) == 0) {
					handCards.remove(i);
					return;
				}
			}
		}
	}
	
	//Dave: Fügt die spielbaren Karten der ArrayList playableHandCards hinzu
	public void evaluatePlayableHandCards(Card opponentCard) {
		sameSuitAsOpponent(opponentCard);
		if(playableHandCards.isEmpty()) {
			for(Card card : handCards) {
				playableHandCards.add(card);
			}
		}
	}
	
	//Dave: Alle  spielbaren Karten werden den playableHandCards hinzugefügt sofern die eigenen 
	//Handkarten die gleiche Farbe oder einen Doppelgänger haben
	public void sameSuitAsOpponent(Card opponentCard) {
		if(suitToString(opponentCard) == "double") {
			for(int i = 0; i < handCards.size()-1; i++) {
				if(suitToString(handCards.get(i)) == "double") playableHandCards.add(handCards.get(i));
			}
		}else {
			for(int i = 0; i < handCards.size()-1; i++) {
				if(suitToString(opponentCard) == suitToString(handCards.get(i)) || 
				   suitToString(handCards.get(i)) == "double") {
					playableHandCards.add(handCards.get(i));
				}
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
