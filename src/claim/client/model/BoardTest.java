package claim.client.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import claim.commons.Card;
import claim.commons.Card.Rank;
import claim.commons.Card.Suit;

class BoardTest {

	@Test
	void test() {
		Board b = new Board();

		b.handCards.add(new Card(Suit.Goblin, Rank.Zero, true));
		b.handCards.add(new Card(Suit.Goblin, Rank.Zero, true));
		b.handCards.add(new Card(Suit.Double, Rank.Three, false));
		b.handCards.add(new Card(Suit.Double, Rank.Four, true));
		b.handCards.add(new Card(Suit.Goblin, Rank.Zero, true));
		
		Card c = new Card(Suit.Goblin, Rank.Zero, true);
		b.removePlayedCard(c);
		
//		System.out.println("Gespielte Karte: "+c);
//		for(int i= 0; i < 4; i++) {
//			System.out.println("Handkarten: "+i+" "+b.handCards.get(i));
//		}
		
		b.setPlayableHC(c);
		for(int i= 0; i < 4; i++) {
			System.out.println("Handkarten: "+i+" "+b.handCards.get(i));
			System.out.println("Handkarten Playable? "+i+" "+b.handCards.get(i).getPlayable());
		}
	}

}
