package claim.commons.messages.results;

import java.util.logging.Logger;

import claim.commons.messages.Message;
import claim.client.controller.Controller;
import claim.commons.ServiceLocator;

//Class implemented by Jannick: Message Server -> Client
//ResultGetNextTableCard|result|

public class ResultGetNextTableCard extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getClientLogger();

	public ResultGetNextTableCard(boolean result) {
		super(new String[] {"ResultGetNextTableCard", Boolean.toString(result)});
	}
	
	public ResultGetNextTableCard(String[] content) {
		super(content);
	}
	
	// Method to perform actions on Client Controller
	@Override
	public void process(Controller controller) {
//		controller.setOnTurn(true);
	}
	public void processIfFalse(Controller controller) {
		controller.somethingFailed();
	}

}
