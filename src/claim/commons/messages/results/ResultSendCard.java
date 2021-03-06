package claim.commons.messages.results;

import java.util.ArrayList;
import java.util.logging.Logger;

import claim.client.controller.Controller;
import claim.commons.ServiceLocator;
import claim.commons.messages.Message;
import claim.commons.Card;

// Class implemented by Jannick: Message Server -> Client
// ResultSendCard|result|Reason|Card
// Possible Reasons: HandCard, FollowerCard, PointCard, TableCard

public class ResultSendCard extends Message {

	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getClientLogger();

	private String reason;
	private String card;

	// Takes the result and creates new Object
	public ResultSendCard(boolean result) {
		super(new String[] { "ResultSendCard", Boolean.toString(result) });
	}

	public ResultSendCard(String[] content) {
		super(content);
		this.reason = content[2];
		this.card = content[3];
	}

	@Override
	public void process(Controller controller) {
		switch (this.reason) {
		// What happens when which card is being sent by the Server?
		case "HandCard":
			controller.otherPlayerCard(card);
			if (!controller.getCardPlayed()) {
				controller.setOnTurn(true);
				controller.getBoard().setPlayableHC(new Card(card));
				controller.enablePlayableHC();
			}
			break;
		case "FollowerCard":
			break;
		case "PointCard":
			break;
		case "TableCard":
			controller.tableCard(card);
			controller.enableHandCards();
			controller.disableTableCardButton();
			controller.clearFollowerCard();
			controller.clearMyCard();
			controller.clearOpponentCard();
			break;
		}

	}

	@Override
	public void processIfFalse(Controller controller) {
		controller.somethingFailed();
	}
}
