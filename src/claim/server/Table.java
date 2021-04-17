package claim.server;

import java.util.ArrayList;

<<<<<<< HEAD
import claim.commons.Card;

public class Table {
	
	private DeckOfCards deck;
	private ArrayList<Card> cardsP1 = new ArrayList<>();
	private ArrayList<Card> cardsP2 = new ArrayList<>();
	private ArrayList<Card> cardsTable = new ArrayList<>();
	
	
	
	
	//Entweder das so belassen oder in unsere Kontrollerklasse einfügen, respektive generieren
	/*
	 * TODO: TXT
	 */
	private void deal() {
		deck = new DeckOfCards();
		Card card;

		
		for (int i = 0; i < 3; i++) {
			if (deck.getCardsRemaining() > 26) {
				for (int j = 0; j < 13; j++) {
					card = deck.dealCard();
					if (i == 0) cardsP1.add(card);
					else cardsP2.add(card);
				}
			} else {
				card = deck.dealCard();
				cardsTable.add(card);
			}
		}
	}
	
	public String getPlayerCards() {
		return "hallo";
	}
	
	
//	private String cardToStringName(Card card) {
//		String rank = card.getRank().toString();
//		String suit = card.getSuit().toString();
//		return suit + "_" + rank;
//	}
=======
public class Table {
	
	// Cards on the table - player's cards are stored in Account object
	private ArrayList<String> tableCards = new ArrayList<>();

	private Account playerOne;
	private Account playerTwo;
	
	public Table(Account playerOne, Account playerTwo) {
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
	}
>>>>>>> branch 'main' of https://github.com/jannickmaurer/RumUndEhre2
}
