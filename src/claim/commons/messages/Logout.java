package claim.commons.messages;

import java.util.logging.Logger;

import claim.commons.ServiceLocator;
import claim.commons.messages.results.ResultLogout;
import claim.server.Client;

public class Logout extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getServerLogger();
	
	private String token;

	public Logout(String[] content) {
		super(content);
		this.token = content[1];	
	}
	
	public void process(Client client) {
		boolean result = false;
		if(this.token.equals(client.getToken())) {
			client.setToken(null); 
			client.setAccount(null); 
			result = true;
		}
		client.send(new ResultLogout(result));
	}
}
