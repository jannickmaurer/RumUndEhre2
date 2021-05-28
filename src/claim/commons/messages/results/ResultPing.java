package claim.commons.messages.results;

import java.util.logging.Logger;

import claim.commons.messages.Message;
import claim.client.controller.Controller;
import claim.commons.ServiceLocator;

//Class implemented by Jannick: Message Server -> Client
//ResultPing|Result

public class ResultPing extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getClientLogger();

	public ResultPing(boolean result) {
		super(new String[] { "ResultPing", Boolean.toString(result) });
	}

	public ResultPing(String[] content) {
		super(content);
	}

	// Method to perform actions on Client Controller
	@Override
	public void process(Controller controller) {
		controller.getModel().setConnected(true);
		controller.getView().getBtStart().setDisable(false);
	}

	public void processIfFalse(Controller controller) {
		controller.getModel().setConnected(false);
		controller.somethingFailed();
	}

}
