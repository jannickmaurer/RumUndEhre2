package claim.commons.messages;

import java.util.logging.Logger;

import claim.commons.ServiceLocator;
import claim.commons.messages.results.ResultDeleteAccount;
import claim.server.Account;
import claim.server.Client;

//Class implemented by Jannick: Message Client -> Server
//DeleteAccount|Token|
public class DeleteAccount extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getClientLogger();

	private String token;

	public DeleteAccount(String[] content) {
		super(content);
		this.token = content[1];
	}

	public void process(Client client) {
		boolean result = false;
		if (this.token.equals(client.getToken())) {
			Account.remove(client.getAccount());
			client.setAccount(null);
			client.setToken(null);
			result = true;
		}
		client.send(new ResultDeleteAccount(result));

	}
}
