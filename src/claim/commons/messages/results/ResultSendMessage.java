package claim.commons.messages.results;

import java.util.logging.Logger;

import claim.client.controller.Controller;
import claim.commons.ServiceLocator;
import claim.commons.messages.Message;

//Class implemented by Jannick: Message Server -> Client
//ResultSendCard|result

public class ResultSendMessage extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getClientLogger();
	
	public ResultSendMessage(boolean result) {
		super(new String[] {"ResultSendMessage", Boolean.toString(result)});
	}
	public ResultSendMessage(String[] content) {
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
