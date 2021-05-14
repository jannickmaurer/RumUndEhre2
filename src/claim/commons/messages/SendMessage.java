package claim.commons.messages;

import java.util.logging.Logger;

import claim.commons.Card;
import claim.commons.ServiceLocator;
import claim.commons.messages.results.ResultBroadcastJoinPlayroom;
import claim.commons.messages.results.ResultBroadcastSendMessage;
import claim.commons.messages.results.ResultBroadcastStartRoundOne;
import claim.commons.messages.results.ResultLogin;
import claim.commons.messages.results.ResultPlayCard;
import claim.commons.messages.results.ResultSendCard;
import claim.commons.messages.results.ResultSendMessage;
import claim.server.Account;
import claim.server.Client;
import claim.server.Playroom;


//Implemented by Jannick
// SendMessage|token|message

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
