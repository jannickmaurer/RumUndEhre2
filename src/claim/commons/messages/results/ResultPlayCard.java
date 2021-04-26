package claim.commons.messages.results;

import java.util.logging.Logger;

import claim.commons.messages.Message;
import claim.client.controller.Controller;
import claim.commons.ServiceLocator;

// Created by Jannick: Server -> Client Message
// ResultPlayCard|result|Card
public class ResultPlayCard extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getClientLogger();
	String card;

	public ResultPlayCard(boolean result) {
		super(new String[] {"ResultPlayCard", Boolean.toString(result)});
	}
	
	public ResultPlayCard(String[] content) {
		super(content);
		this.card = content[2];
	}
	
	@Override
	public void process(Controller controller) {
		
	}
	public void processIfFalse(Controller controller) {
		controller.somethingFailed();
	}

}
