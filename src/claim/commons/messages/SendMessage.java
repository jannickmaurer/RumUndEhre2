package claim.commons.messages;

import java.util.logging.Logger;

import claim.commons.ServiceLocator;
import claim.commons.messages.results.ResultBroadcastSendMessage;
import claim.commons.messages.results.ResultSendMessage;
import claim.server.Account;
import claim.server.Client;

//Class implemented by Jannick: Message Client -> Server
//SendMessage|Token|Message

public class SendMessage extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getServerLogger();
	
	private String message;
	private String token;

	public SendMessage(String[] content) {
		super(content);
		this.token = content[1];
		this.message = content[2];		
	}
	
	public void process(Client client) {
		Boolean result = false;
		if(this.token.equals(client.getToken())) {
			String[] content = new String[] {"ResultBroadcastSendMessage", "true", client.getAccount().getUsername(), this.message};
			for(Account a : client.getPlayroom().getPlayers()) {
				a.getClient().send(new ResultBroadcastSendMessage(content));
			}
			result = true;
		}
		client.send(new ResultSendMessage(result));
	}
}
