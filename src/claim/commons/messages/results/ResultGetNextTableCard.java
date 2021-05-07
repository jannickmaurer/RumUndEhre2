package claim.commons.messages.results;

import java.util.logging.Logger;

import claim.commons.messages.Message;
import claim.client.controller.Controller;
import claim.commons.ServiceLocator;

// Created by Jannick: Server -> Client Message
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
		//Button disablen
	}
	public void processIfFalse(Controller controller) {
		controller.somethingFailed();
	}

}
