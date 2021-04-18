package claim.commons.messages.results;

import java.util.logging.Logger;

import claim.commons.messages.Message;
import claim.client.controller.Controller;
import claim.commons.ServiceLocator;

// Created by Jannick: Server -> Client Message
// ResultPlacCard|result
public class ResultPlayCard extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getClientLogger();

	public ResultPlayCard(boolean result) {
		super(new String[] {"ResultPlayCard", Boolean.toString(result)});
	}
	
	public ResultPlayCard(String[] content) {
		super(content);
	}
	
	@Override
	public void process(Controller controller) {

	}
	public void processIfFalse(Controller controller) {
		controller.somethingFailed();
	}

}
