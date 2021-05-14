package claim.commons.messages;

import java.util.logging.Logger;

import claim.commons.Card;
import claim.commons.ServiceLocator;
import claim.commons.messages.results.ResultBroadcastJoinPlayroom;
import claim.commons.messages.results.ResultBroadcastStartRoundOne;
import claim.commons.messages.results.ResultEvaluateWinner;
import claim.commons.messages.results.ResultGetNextTableCard;
import claim.commons.messages.results.ResultLogin;
import claim.commons.messages.results.ResultPlayCard;
import claim.commons.messages.results.ResultSendCard;
import claim.server.Account;
import claim.server.Client;
import claim.server.Playroom;


// Implemented by Jannick
// GetNextTableCard|token

public class EvaluateWinner extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getServerLogger();
	
	private String token;
	private String winner;

	public EvaluateWinner(String[] content) {
		super(content);
		this.token = content[1];
	}
	

	public void process(Client client) {
		Boolean result = false;
		if(this.token.equals(client.getToken())) {
		client.getPlayroom().getTable().evaluateWinner();
		result = true;
		}
		client.send(new ResultEvaluateWinner(result));
		
	}	
}


