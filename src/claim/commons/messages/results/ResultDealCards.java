package claim.commons.messages.results;

import java.util.ArrayList;
import java.util.logging.Logger;

import claim.client.controller.Controller;
import claim.commons.ServiceLocator;
import claim.commons.messages.Message;

// Class implemented by Jannick: Message Server -> Client
// ResultDealCards|result|Card1| ... Card13
public class ResultDealCards extends Message {
	
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getClientLogger();
	
	private ArrayList<String> handCards = new ArrayList<>();
	
	// Takes the result and creates new Object
	public ResultDealCards(boolean result) {
		super(new String[] {"ResultDealCards", Boolean.toString(result)});
	}
	
	public ResultDealCards(String[] content) {
		super(content);
		for(int i = 2; i < content.length; i++) {
			handCards.add(content[i]);
		}
	}
	
	@Override
	public void process(Controller controller) {
		controller.getBoard().addHandCards(handCards);
		controller.deal();
		// Player got its handcards and game can be started
	}
	
	@Override
	public void processIfFalse(Controller controller) {
		controller.somethingFailed();
	}
}
