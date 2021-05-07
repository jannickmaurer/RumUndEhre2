package claim.commons.messages.results;

import java.util.logging.Logger;

import claim.client.controller.Controller;
import claim.commons.ServiceLocator;
import claim.commons.messages.Message;

// Created by Jannick: Message Server -> Client
// String in case winner: ResultFinishRound|Boolean|Username Winner|Undead1|Undead2
// String in case loser: ResultFinishRound|Boolean|Username Winner|TableCardLoser

public class ResultFinishRound extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getClientLogger();
	

	public ResultFinishRound(boolean result) {
		super(new String[] {"ResultFinishRound", Boolean.toString(result)});
	}
	public ResultFinishRound(String[] content) {
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
