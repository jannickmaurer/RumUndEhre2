package claim.commons.messages;

import claim.commons.messages.results.ResultPing;
import claim.server.Client;

// Class implemented by Jannick: Message Client -> Server
// Disconnect|Token|
public class Disconnect extends Message {

	private String token;
	// Constructor uses Message constructor 
	public Disconnect(String[] content) {
		super(content);
		this.token = content[1];

	}
	
	// Process methods runs process on Server side -> only sends a new ResultPing Message
	@Override
	public void process(Client client) {
		if(token.equals("null")) {
			
		} else {
			client.setToken(null);
		}
		
	}

}
