package claim.client;

import java.util.ArrayList;

import claim.server.Card;

// Implemented by Jannick
// Waiting for Card creation with String

public class Board {
	private ArrayList<Card> handCards;
	private ArrayList<Card> pointCards;
	private ArrayList<Card> followerCards;
	

	public Board() {
		this.pointCards = new ArrayList<>();
		this.followerCards = new ArrayList<>();
	}
	
	public void setHandCards(ArrayList<String> handCards) {
		this.handCards = new ArrayList<>();
		
	}
	
	public void addPointCard(String pointCard) {
	
	}
	public void addFollowerCard(String followerCard) {
		
	}
	
 }


