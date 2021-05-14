package claim.commons.messages.results;

import java.util.ArrayList;
import java.util.logging.Logger;

import claim.client.controller.Controller;
import claim.commons.ServiceLocator;
import claim.commons.messages.Message;

// Class implemented by Jannick: Message Server -> Client
// ResultDealCards|result|FirstUser|Card1| ... Card13
public class ResultDealCards extends Message {
	
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getClientLogger();
	private String firstUser;
	
	private ArrayList<String> handCards = new ArrayList<>();
	
	// Takes the result and creates new Object
	public ResultDealCards(boolean result) {
		super(new String[] {"ResultDealCards", Boolean.toString(result)});
	}
	
	public ResultDealCards(String[] content) {
		super(content);
		firstUser = content[2];
		for(int i = 3; i < content.length; i++) {
			handCards.add(content[i]);
		}
	}
	
	@Override
	public void process(Controller controller) {
		controller.getBoard().addHandCards(handCards);
		controller.deal(controller.getBoard().getHandCards());
		// Player got its handcards and game can be started
	}
	
	@Override
	public void processIfFalse(Controller controller) {
		controller.somethingFailed();
	}
}
