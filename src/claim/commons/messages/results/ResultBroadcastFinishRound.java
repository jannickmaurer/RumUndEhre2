package claim.commons.messages.results;

import java.util.ArrayList;
import java.util.logging.Logger;

import claim.client.controller.Controller;
import claim.commons.Card;
import claim.commons.ServiceLocator;
import claim.commons.messages.Message;

// Created by Jannick: Message Server -> Client
// String: ResultFinishRound|Boolean|Username Winner|TableCard|Undead1|Undead2
//String: ResultFinishRound|Boolean|Username Winner|Card1|Card2|Card3

public class ResultBroadcastFinishRound extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getClientLogger();

	private String card1;
	private String card2;
	private String card3;
	private String winner;
	private Card followerCard1;
	private Card followerCard2;

	public ResultBroadcastFinishRound(boolean result) {
		super(new String[] { "ResultBroadcastFinishRound", Boolean.toString(result) });
	}

	public ResultBroadcastFinishRound(String[] content) {
		super(content);
		this.winner = content[2];
		this.card1 = content[3];
		if (content.length > 4) {
			this.card2 = content[4];
		}
		if (content.length > 5) {
			this.card3 = content[5];
		}

	}

	@Override
	public void process(Controller controller) {

		if (controller.getSecondRoundStarted()) {
			followerCard1 = new Card(card1);
			followerCard2 = new Card(card2);
		}

		if (controller.getUsername().equalsIgnoreCase(this.winner)) {
System.out.println("Ich habe gewonnen");

			if (controller.getSecondRoundStarted()) {
				// button enablen und karten speichern
				cardCheck(controller);
				controller.enableNextDuelButton();
				controller.clearMyCard();
				controller.clearOpponentCard();
			} else {
				if (card2 != null)
//					controller.getBoard().addUndead(new Card(card2));
					controller.getBoard().addCardToGroup(new Card(card2));
				if (card3 != null)
//					controller.getBoard().addUndead(new Card(card3));
					controller.getBoard().addCardToGroup(new Card(card2));

				controller.enableTableCardButton();
			}
			controller.setOnTurn(true);
			controller.updateGameEvaluation();
		} else {
			// Table Karte, die der Verlierer erhält, anzeigen
System.out.println("Ich habe verloren");

			if (controller.getSecondRoundStarted()) {
				cardCheck(controller);
				controller.clearMyCard();
				controller.clearOpponentCard();
			} else {
				controller.showNewFollowerCard(card1);
			}
			controller.setOnTurn(false);
			controller.updateGameEvaluation();
		}
		controller.setCardPlayed(false);
		controller.increasePlayedRounds();
	}

	@Override
	public void processIfFalse(Controller controller) {
		controller.somethingFailed();
	}

	private void cardCheck(Controller controller) {

		if (followerCard1.getSuit().toString().equals("dwarf") && !controller.getUsername().equals(this.winner)) {
			controller.getBoard().addDwarfCards(followerCard1);
		} else if (!followerCard1.getSuit().toString().equals("dwarf") && controller.getUsername().equals(this.winner)) {
			controller.getBoard().addCardToGroup(followerCard1);
		}

		if (followerCard2.getSuit().toString().equals("dwarf") && !controller.getUsername().equals(this.winner)) {
			controller.getBoard().addDwarfCards(followerCard2);
		} else if (!followerCard2.getSuit().toString().equals("dwarf") && controller.getUsername().equals(this.winner)) {
			controller.getBoard().addCardToGroup(followerCard2);
		}
	}

}
