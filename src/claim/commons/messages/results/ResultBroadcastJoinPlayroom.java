package claim.commons.messages.results;

import java.util.logging.Logger;

import claim.client.controller.Controller;
import claim.commons.ServiceLocator;
import claim.commons.messages.Message;

//implemented by Jannick
//Server -> Client: ResultJoinPlayroom|Result|Username

public class ResultBroadcastJoinPlayroom extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getClientLogger();

	private String username;

	public ResultBroadcastJoinPlayroom(boolean result) {
		super(new String[] {"ResultJoinPlayroom", Boolean.toString(result)});
	}
	public ResultBroadcastJoinPlayroom(String[] content) {
		super(content);
		if(content.length > 2) {
			this.username = content[2];
		}
	}
	@Override
	public void process(Controller controller) {
		System.out.println("Second Player joined: " + this.username);
		// Show username of opposite player in GUI
	}
	
	@Override
	public void processIfFalse(Controller controller) {
		controller.somethingFailed();
	}

}
