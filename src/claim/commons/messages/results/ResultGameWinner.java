package claim.commons.messages.results;

import java.util.logging.Logger;

import claim.client.controller.Controller;
import claim.commons.Card;
import claim.commons.ServiceLocator;

//Class implemented by Jannick: Message Server -> Client
//ResultGameWinner| true |GameWinnerAccountName
//Possible Reasons: HandCard, FollowerCard, PointCard, TableCard

public class ResultGameWinner {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getClientLogger();
	
	private String gameWinner;
	
	// Takes the result and creates new Object
	public ResultGameWinner(boolean result) {
		super(new String[] {"ResultGameWinner", Boolean.toString(result)});
	}
	
	public ResultGameWinner(String[] content) {
		super(content);
		this.gameWinner = content[2];
	}
	
	@Override
	public void process(Controller controller) {
		
	}
	
	@Override
	public void processIfFalse(Controller controller) {
		controller.somethingFailed();
	}
}
