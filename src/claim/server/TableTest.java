package claim.server;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import claim.commons.Card;
import claim.commons.Card.Rank;
import claim.commons.Card.Suit;

class TableTest {

	
	
	@Test
	@DisplayName("Test finishRound")
	void test() {

		Card cardP1 = new Card(Suit.Knight, Rank.Seven, true);
		Card cardP2 = new Card(Suit.Double, Rank.Eight, true);
		
		Table table = new Table();		
		table.actualTableCard = new Card(Suit.Knight, Rank.Two, true); //Aktuelle Tischkarte
		table.tableCards.add(new Card(Suit.Dwarf, Rank.Four, true));//Tischkarte aus Stapel
		
		table.finishRound(cardP1, cardP2);
		
        System.out.println(table.followerCardP1);
        System.out.println(table.followerCardP2);
        
	}
	
	@Test
	@DisplayName("Test winner String")
	void testEvaluateWinnerCard() {
		String output;
		Table table = new Table();
//		output = table.evaluateWinnerCard(new Card(Suit.Goblin, Rank.Six, true), new Card(Suit.Knight, Rank.Two, true));
//		output = table.evaluateWinnerCard(new Card(Suit.Goblin, Rank.Six, true), new Card(Suit.Double, Rank.Seven, true));
		output = table.evaluateWinnerCard(new Card(Suit.Knight, Rank.Six, true), new Card(Suit.Double, Rank.Six, true));
		assertEquals("P1", output);
		System.out.println("EvaluateWinnerCard "+output);
		
	}
	
	
	@Test
	@DisplayName("Test winner String")
	void testWinner() {
		Table table = new Table();		

		
		String win = table.winner();
//		System.out.println(win);
		
		table.tableCards.add(new Card(Suit.Double, Rank.One, true));
		table.tableCards.add(new Card(Suit.Double, Rank.Two, true));
		table.tableCards.add(new Card(Suit.Double, Rank.Three, true));
		table.tableCards.add(new Card(Suit.Double, Rank.Four, true));
		table.tableCards.add(new Card(Suit.Double, Rank.Five, true));
		
		while(table.tableCards.size() > 0) {
			System.out.println("TableCards: "+table.getNextTableCard());
		}
		
		
		
		
		
		table.followerCardsP1.add(new Card(Suit.Double, Rank.One, true));
		table.followerCardsP1.add(new Card(Suit.Double, Rank.One, true));
		table.followerCardsP1.add(new Card(Suit.Goblin, Rank.One, true));
		table.followerCardsP1.add(new Card(Suit.Goblin, Rank.One, true));
		table.followerCardsP1.add(new Card(Suit.Goblin, Rank.One, true));
		table.followerCardsP1.add(new Card(Suit.Knight, Rank.One, true));
		table.followerCardsP1.add(new Card(Suit.Knight, Rank.One, true));
		table.followerCardsP1.add(new Card(Suit.Knight, Rank.One, true));
		table.followerCardsP1.add(new Card(Suit.Undead, Rank.One, true));
		table.followerCardsP1.add(new Card(Suit.Undead, Rank.One, true));
		table.followerCardsP1.add(new Card(Suit.Dwarf, Rank.One, true));
		table.followerCardsP1.add(new Card(Suit.Dwarf, Rank.One, true));
		table.followerCardsP1.add(new Card(Suit.Dwarf, Rank.One, true));
		
		table.followerCardsP2.add(new Card(Suit.Double, Rank.One, true));
		table.followerCardsP2.add(new Card(Suit.Goblin, Rank.One, true));
		table.followerCardsP2.add(new Card(Suit.Goblin, Rank.One, true));
		table.followerCardsP2.add(new Card(Suit.Goblin, Rank.One, true));
		table.followerCardsP2.add(new Card(Suit.Goblin, Rank.One, true));
		table.followerCardsP2.add(new Card(Suit.Goblin, Rank.One, true));
		table.followerCardsP2.add(new Card(Suit.Knight, Rank.One, true));
		table.followerCardsP2.add(new Card(Suit.Knight, Rank.One, true));
		table.followerCardsP2.add(new Card(Suit.Knight, Rank.One, true));
		table.followerCardsP2.add(new Card(Suit.Dwarf, Rank.One, true));
		table.followerCardsP2.add(new Card(Suit.Dwarf, Rank.One, true));
		table.followerCardsP2.add(new Card(Suit.Dwarf, Rank.One, true));
		table.followerCardsP2.add(new Card(Suit.Undead, Rank.One, true));
	}
	

}
