package claim.commons.messages.results;

import java.util.logging.Logger;

import claim.client.controller.Controller;
import claim.commons.Card;
import claim.commons.ServiceLocator;
import claim.commons.messages.Message;

//Class implemented by Jannick: Message Server -> Client
//ResultEvaluateWinner|result|

public class ResultEvaluateWinner extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getClientLogger();

	// Takes the result and creates new Object
	public ResultEvaluateWinner(boolean result) {
		super(new String[] { "ResultEvaluateWinner", Boolean.toString(result) });
	}

	public ResultEvaluateWinner(String[] content) {
		super(content);
	}

	@Override
	public void process(Controller controller) {
	}

	@Override
	public void processIfFalse(Controller controller) {
		controller.somethingFailed();
	}
}
