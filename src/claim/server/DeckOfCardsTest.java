package claim.server;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import claim.commons.Card;

class DeckOfCardsTest {

	@Test
	void testShuffle() {
		DeckOfCards d = new DeckOfCards();
		for(Card c : d.cards) {
			System.out.println("Erstellte Karte: "+c);
		}
	}

}
