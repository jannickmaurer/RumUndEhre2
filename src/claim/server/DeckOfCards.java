package claim.server;

import java.util.ArrayList;
import java.util.Collections;

import claim.commons.Card;
import claim.commons.Card.Rank;
import claim.commons.Card.Suit;
import javafx.beans.property.SimpleIntegerProperty;

public class DeckOfCards {
	private final ArrayList<Card> cards = new ArrayList<>();
    private final SimpleIntegerProperty cardsRemaining = new SimpleIntegerProperty();


	public DeckOfCards() {
		shuffle();
	} 
	
    public SimpleIntegerProperty getCardsRemainingProperty() {
        return cardsRemaining;
    }
    //Wahrscheinlich brauch ich den getter nicht
    public int getCardsRemaining() {
    	return cardsRemaining.get();
    }

    /**
     * David Schürch
     * Shuffle creates all Cards without knight zero and knight one. It creates the goblin zero five times
     *  r///////mit equals anstelle == oder !=
     */
	public void shuffle() {
		cards.clear();
		for (Card.Suit suit : Card.Suit.values()) {
			for (Card.Rank rank : Card.Rank.values()) {
//				if(!suit.toString().equalsIgnoreCase("knight") && (!rank.toString().equalsIgnoreCase("zero") || !rank.toString().equalsIgnoreCase("one"))) {
//					if(suit.toString().equalsIgnoreCase("goblin") && rank.toString().equalsIgnoreCase("zero")){
//						for(int i=0; i<4; i++) {
//							Card card = new Card(suit, rank);
//							cards.add(card);
//						}
//					}
					Card card = new Card(suit, rank);
					cards.add(card);
					
//				}	
			}
		}
		for(int i = 0; i<4; i++) {
			cards.add(new Card(Suit.Goblin, Rank.Zero));
		}
		for(int i = 0; i < cards.size(); i++) {
			if(cards.get(i).getSuit() == Suit.Knight && cards.get(i).getRank() == Rank.Zero){
				cards.remove(i);
			} 
			if(cards.get(i).getSuit() == Suit.Knight && cards.get(i).getRank() == Rank.One){
				cards.remove(i);
			}
		}
		Collections.shuffle(cards);
		System.out.println("Method: DeckOfCards: Print all created Cards");
		System.out.println(cards.size());
		Collections.sort(cards);
		for(Card card : cards) {
		System.out.println(card.toString());
		}
        cardsRemaining.setValue(cards.size());

	}
	
    public Card dealCard() {
        Card card = (cards.size() > 0) ? cards.remove(cards.size()-1) : null;
        cardsRemaining.setValue(cards.size());
        return card;
    }
    
    
    
}
