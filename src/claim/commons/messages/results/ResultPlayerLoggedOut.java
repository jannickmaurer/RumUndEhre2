package claim.commons.messages.results;

import java.util.logging.Logger;

import claim.commons.messages.Message;
import claim.client.controller.Controller;
import claim.commons.ServiceLocator;

//Class implemented by Jannick: Message Server -> Client
//ResultPlayerLoggedOut|result|Card

public class ResultPlayerLoggedOut extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getClientLogger();

	public ResultPlayerLoggedOut(boolean result) {
		super(new String[] { "ResultPlayerLoggedOut", Boolean.toString(result) });
	}

	public ResultPlayerLoggedOut(String[] content) {
		super(content);
	}

	// Method to perform actions on Client Controller
	@Override
	public void process(Controller controller) {
		controller.gameOver();
	}

	public void processIfFalse(Controller controller) {

	}

}
