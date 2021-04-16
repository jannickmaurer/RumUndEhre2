package claim.commons.messages.results;

import java.util.logging.Logger;

import claim.client.controller.Controller;
import claim.commons.ServiceLocator;
import claim.commons.messages.Message;

public class ResultDeleteAccount extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getServerLogger();

	public ResultDeleteAccount(boolean result) {
		super(new String[] {"ResultDeleteAccount", Boolean.toString(result)});
	}
	
	public ResultDeleteAccount(String[] content) {
		super(content);
	}
	@Override
	public void process(Controller controller) {
		controller.setUsername(null);
		controller.logoutSuccess();
	}

	public void processIfFalse(Controller controller) {
		controller.somethingFailed();
	}
}
