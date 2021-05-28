package claim.server;

import java.util.ArrayList;
import java.util.Collections;

import claim.commons.Card;
import claim.commons.Card.Rank;
import claim.commons.Card.Suit;
import javafx.beans.property.SimpleIntegerProperty;

// Implemented by David
/*
 * David: Der Source Code ist vom Pokerprojekt mit individuellen Anpassungen für Claim
 */

public class DeckOfCards {
	private final ArrayList<Card> cards = new ArrayList<>();
	private final SimpleIntegerProperty cardsRemaining = new SimpleIntegerProperty();

	public DeckOfCards() {
		shuffle();
	}

	// David: Shuffle generiert alle Karten und mischt diese nachdem sie erstellt
	// sind.
	public void shuffle() {
		cards.clear();
		for (Card.Suit suit : Card.Suit.values()) {
			for (Card.Rank rank : Card.Rank.values()) {
				Card card = new Card(suit, rank, true);
				cards.add(card);
			}
		}
		for (int i = 0; i < 4; i++) {
			cards.add(new Card(Suit.Goblin, Rank.Zero, true));
		}
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).getSuit() == Suit.Knight && cards.get(i).getRank() == Rank.Zero) {
				cards.remove(i);
			}
			if (cards.get(i).getSuit() == Suit.Knight && cards.get(i).getRank() == Rank.One) {
				cards.remove(i);
			}
		}
		Collections.shuffle(cards);
		cardsRemaining.setValue(cards.size());
	}

	// David: Gibt eine Karte vom Deck zurück und löscht diese
	public Card dealCard() {
		Card card = (cards.size() > 0) ? cards.remove(cards.size() - 1) : null;
		cardsRemaining.setValue(cards.size());
		return card;
	}

	public SimpleIntegerProperty getCardsRemainingProperty() {
		return cardsRemaining;
	}

	public int getCardsRemaining() {
		return cardsRemaining.get();
	}
}
