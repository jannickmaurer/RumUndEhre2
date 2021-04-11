package claim.commons.messages.results;

import java.util.logging.Logger;

import claim.client.controller.Controller;
import claim.commons.ServiceLocator;
import claim.commons.messages.Message;

// Class implemented by Jannick: Message Server -> Client
public class ResultCreateAccount extends Message {
	
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getClientLogger();
	
	// Takes the result and creates new Object
	public ResultCreateAccount(boolean result) {
		super(new String[] {"ResultCreateAccount", Boolean.toString(result)});
	}
	
	public ResultCreateAccount(String[] content) {
		super(content);
	}
	
	@Override
	public void process(Controller controller) {
		controller.autoLogin();
	}
	
	@Override
	public void processIfFalse(Controller controller) {
		
	}
}
