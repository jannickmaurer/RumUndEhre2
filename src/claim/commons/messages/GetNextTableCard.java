package claim.commons.messages;

import java.util.logging.Logger;

import claim.commons.ServiceLocator;
import claim.commons.messages.results.ResultGetNextTableCard;
import claim.server.Client;

//Class implemented by Jannick: Message Client -> Server
//GetNextTableCard|Token

public class GetNextTableCard extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getServerLogger();
	
	private String token;

	public GetNextTableCard(String[] content) {
		super(content);
		this.token = content[1];
		
	}
	

	public void process(Client client) {
		Boolean result = false;
		if(this.token.equals(client.getToken())) {
		client.getPlayroom().getTable().sendTableCard();
		result = true;
		}
		client.send(new ResultGetNextTableCard(result));
	}	
}


