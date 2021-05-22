package claim.commons.messages.results;

import java.util.logging.Logger;

import claim.client.controller.Controller;
import claim.commons.ServiceLocator;
import claim.commons.messages.Message;

// Created by Jannick: Message Server -> Client
// String: ResultStartSecondRound|Boolean

public class ResultStartSecondRound extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getClientLogger();
	
	public ResultStartSecondRound(boolean result) {
		super(new String[] {"ResultStartSecondRound", Boolean.toString(result)});
	}
	public ResultStartSecondRound(String[] content) {
		super(content);
		
	}
	@Override
	public void process(Controller controller) {
		controller.setSecondRoundStarted(true);
	}
	
	@Override
	public void processIfFalse(Controller controller) {
		controller.somethingFailed();
	}

}
