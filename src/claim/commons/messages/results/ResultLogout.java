package claim.commons.messages.results;

import java.util.logging.Logger;

import claim.client.controller.Controller;
import claim.commons.ServiceLocator;
import claim.commons.messages.Message;

//Class implemented by Jannick: Message Server -> Client
//ResultLogout|Result

public class ResultLogout extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getServerLogger();

	public ResultLogout(boolean result) {
		super(new String[] {"ResultLogout", Boolean.toString(result)});
	}
	
	public ResultLogout(String[] content) {
		super(content);
	}
	
	@Override
	public void process(Controller controller) {
		controller.setUsername(null);
		controller.getBoard().clearCards();
		controller.logoutSuccess();
	}

	public void processIfFalse(Controller controller) {
		controller.somethingFailed();
	}
}
