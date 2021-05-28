package claim.commons.messages;

import java.util.logging.Logger;

import claim.commons.ServiceLocator;
import claim.commons.messages.results.ResultLogout;
import claim.commons.messages.results.ResultPlayerLoggedOut;
import claim.server.Account;
import claim.server.Client;

//Class implemented by Jannick: Message Client -> Server
//Logout|Token

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
		if (this.token.equals(client.getToken())) {
			client.getPlayroom().stopGame(client.getAccount());
			client.getAccount().clearAccount();
			client.setToken(null);
			client.setAccount(null);
			client.setLoggedIn(false);
			client.getClients().remove(client);

			if (!client.getPlayroom().getTable().isGameEndedSuccessfully()) {
				if (!client.getPlayroom().getPlayers().isEmpty()) {
					for (Account a : client.getPlayroom().getPlayers()) {
						a.getClient().send(new ResultPlayerLoggedOut(true));
					}
				}
			}
			result = true;
			logger.info("Account logged out, remaining players on Server: ");
			for (Account ac : client.getPlayroom().getPlayers()) {
				logger.info(ac.toString() + " remaining on Server");
			}
			client.setPlayroom(null);

		}
		client.send(new ResultLogout(result));
	}
}
