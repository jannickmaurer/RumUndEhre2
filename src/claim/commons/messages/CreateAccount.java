package claim.commons.messages;

import java.util.logging.Logger;

import claim.commons.ServiceLocator;
import claim.commons.messages.results.ResultCreateAccount;
import claim.server.Client;
import claim.server.Account;

// Class implemented by Jannick: Message Client -> Server
// CreateAccount|Username|Password
public class CreateAccount extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getServerLogger();

	private String username;
	private String password;
	private final int MINUNLENGTH = 1; // TBD
	private final int MINPWLENGTH = 1; // TBD

	public CreateAccount(String[] content) {
		super(content);
		this.username = content[1];
		this.password = content[2];
	}

	public void process(Client client) {
		boolean result = false;
		if (username != null && Account.getAccount(username) == null && username.length() >= MINUNLENGTH) {
			if (password != null && password.length() >= MINPWLENGTH) {
				Account newAccount = new Account(username, password);
				Account.add(newAccount);
				result = true;
			}
		}
		// Creates new Result Message with the result
		client.send(new ResultCreateAccount(result));
	}
}
