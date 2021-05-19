package claim.commons.messages.results;

import java.util.logging.Logger;

import claim.client.controller.Controller;
import claim.commons.ServiceLocator;
import claim.commons.messages.Message;

//implemented by Jannick
//Server -> Client: ResultBroadcastSendMessage|Result|username|message

public class ResultBroadcastSendMessage extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getClientLogger();

	private String username;
	private String message;

	public ResultBroadcastSendMessage(boolean result) {
		super(new String[] {"ResultBroadcastSendMessage", Boolean.toString(result)});
	}
	public ResultBroadcastSendMessage(String[] content) {
		super(content);
		this.username = content[2];
		this.message = content[3];
	}
	@Override
	public void process(Controller controller) {
		// Text in TextArea updaten mit: username + ": " + message
		controller.updateChatText(this.username + ": " + this.message);
	}
	
	@Override
	public void processIfFalse(Controller controller) {
		controller.somethingFailed();
	}

}
