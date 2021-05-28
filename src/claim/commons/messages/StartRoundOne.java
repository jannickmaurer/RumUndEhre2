package claim.commons.messages;

import java.util.logging.Logger;

import claim.commons.ServiceLocator;
import claim.commons.messages.results.ResultLogout;
import claim.server.Client;

//Class implemented by Jannick: Message Client -> Server
//StartRoundOne|Token

public class StartRoundOne extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getServerLogger();
	
	private String token;

	public StartRoundOne(String[] content) {
		super(content);
		this.token = content[1];	
	}
	
	public void process(Client client) {
	}
}
