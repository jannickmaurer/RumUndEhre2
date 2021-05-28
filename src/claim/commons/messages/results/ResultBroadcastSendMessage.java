package claim.commons.messages.results;

import java.util.logging.Logger;

import claim.client.controller.Controller;
import claim.commons.ServiceLocator;
import claim.commons.messages.Message;

//Class implemented by Jannick: Message Server -> Client
//ResultBroadcastSendMessage|Result|Username|Message

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
		controller.updateChatText(this.username + ":\n" + this.message + "\n");
	}
	
	@Override
	public void processIfFalse(Controller controller) {
		controller.somethingFailed();
	}

}
