package claim.client.model;

import java.util.ArrayList;

import claim.commons.Card;

// Implemented by Jannick
// All logic realted to game, cards, etc. on client side
public class Board {
	
	private ArrayList<Card> handCards;
	private ArrayList<Card> followerCards;
	private ArrayList<Card> pointCards;
	
	public Board() {
		handCards = new ArrayList<>();
		followerCards = new ArrayList<>();
		pointCards = new ArrayList<>();
	}
	
	public void addHandCards(ArrayList<String> handCards) {
//		for(String s : handCards) {
//			this.handCards.add(new Card(s));
//		}
		
		System.out.println("HandCards added: ");
		for(String s : handCards) {
			System.out.println(s);
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
	
	
}
