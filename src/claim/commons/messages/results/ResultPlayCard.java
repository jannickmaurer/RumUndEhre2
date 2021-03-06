package claim.commons.messages.results;

import java.util.logging.Logger;

import claim.commons.messages.Message;
import claim.client.controller.Controller;
import claim.commons.Card;
import claim.commons.ServiceLocator;

//Class implemented by Jannick: Message Server -> Client
//ResultPlayCard|result|Card

public class ResultPlayCard extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getClientLogger();
	String card;

	public ResultPlayCard(boolean result) {
		super(new String[] { "ResultPlayCard", Boolean.toString(result) });
	}

	public ResultPlayCard(String[] content) {
		super(content);
		this.card = content[2];
	}

	@Override
	public void process(Controller controller) {
		controller.getBoard().removePlayedCard(new Card(card));
		controller.updatePlayerPane(card);
		controller.disableHandCards();
		controller.setOnTurn(false);
		controller.setCardPlayed(true);
	}

	public void processIfFalse(Controller controller) {
		controller.somethingFailed();
	}

}
