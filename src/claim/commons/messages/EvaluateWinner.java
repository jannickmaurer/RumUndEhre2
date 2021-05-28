package claim.commons.messages;

import java.util.logging.Logger;

import claim.commons.ServiceLocator;
import claim.commons.messages.results.ResultEvaluateWinner;
import claim.server.Client;

//Class implemented by Jannick: Message Client -> Server
//EvaluateWinner|Token|

public class EvaluateWinner extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getServerLogger();

	private String token;

	public EvaluateWinner(String[] content) {
		super(content);
		this.token = content[1];
	}

	public void process(Client client) {
		Boolean result = false;
		if (this.token.equals(client.getToken())) {
			client.getPlayroom().getTable().evaluateWinner();
			result = true;
		}
		client.send(new ResultEvaluateWinner(result));

	}
}
