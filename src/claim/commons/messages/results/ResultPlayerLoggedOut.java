package claim.commons.messages.results;

import java.util.logging.Logger;

import claim.commons.messages.Message;
import claim.client.controller.Controller;
import claim.commons.ServiceLocator;

// Created by Jannick: Server -> Client Message
// ResultPlayerLoggedOut|result
public class ResultPlayerLoggedOut extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getClientLogger();

	public ResultPlayerLoggedOut(boolean result) {
		super(new String[] {"ResultPlayerLoggedOut", Boolean.toString(result)});
	}
	
	public ResultPlayerLoggedOut(String[] content) {
		super(content);
	}
	
	// Method to perform actions on Client Controller
	@Override
	public void process(Controller controller) {
		// Spiel auf GUI unterbrechen und Button zum Logout anzeigen
	}
	public void processIfFalse(Controller controller) {
	
	}

}
