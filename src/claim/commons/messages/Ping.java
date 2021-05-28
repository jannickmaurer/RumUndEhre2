package claim.commons.messages;

import claim.commons.messages.results.ResultPing;
import claim.server.Client;

//Class implemented by Jannick: Message Client -> Server
//Ping

public class Ping extends Message {
	
	// Constructor uses Message constructor 
	public Ping(String[] content) {
		super(content);
	}
	
	// Process methods runs process on Server side -> only sends a new ResultPing Message
	@Override
	public void process(Client client) {
		client.send(new ResultPing(true));
	}

}
