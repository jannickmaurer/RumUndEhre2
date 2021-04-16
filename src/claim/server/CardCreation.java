package claim.server;

import java.util.ArrayList;
import java.util.Collections;

import javafx.beans.property.SimpleIntegerProperty;

public class CardCreation {
	private final ArrayList<Card> cards = new ArrayList<>();
    private final SimpleIntegerProperty cardsRemaining = new SimpleIntegerProperty();//test für Test number


	public CardCreation() {
		shuffle();
	}

    /**
     * David Schürch
     * Shuffle creates all Cards without knight zero and knight one. It creates the goblin zero five times
     */
	public void shuffle() {
		cards.clear();
		for (Card.Suit suit : Card.Suit.values()) {
			for (Card.Rank rank : Card.Rank.values()) {
				if(suit.toString() != "knight" && (rank.toString()!= "zero" || rank.toString() != "one")) {
					if(suit.toString() == "goblin" && rank.toString() == "zero"){
						for(int i=0; i<4; i++) {
							Card card = new Card(suit, rank);
							cards.add(card);
						}
					}
					Card card = new Card(suit, rank);
					cards.add(card);
				}	
			}
		}
		Collections.shuffle(cards);
	}
	
	public ArrayList<Card> getShuffledCard() {
		return cards;
	}
	
}
