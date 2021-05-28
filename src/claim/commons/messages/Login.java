package claim.commons.messages;

import java.util.logging.Logger;

import claim.commons.ServiceLocator;
import claim.commons.messages.results.ResultBroadcastJoinPlayroom;
import claim.commons.messages.results.ResultLogin;
import claim.server.Account;
import claim.server.Client;
import claim.server.Playroom;

//Class implemented by Jannick: Message Client -> Server
//Login|Username|Password

public class Login extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getServerLogger();
	
	private String username;
	private String password;
	private Account account;
	private String token;

	public Login(String[] content) {
		super(content);
		this.username = content[1];
		this.password = content[2];		
	}
	
	// TBD: Playroom handling once it gets introduced
	public void process(Client client) {
		Boolean result = false;
		// If two players are already logged in, a third cannot join
		if(Account.checkLogin(username, password) && Playroom.getPlayrooms().get(0).getPlayers().size() < 2) {
			account = Account.getAccount(username);
			client.setAccount(account);
			account.setClient(client);
			token = account.getToken();
			client.setToken(token);
			client.setLoggedIn(true);
			Playroom.getPlayrooms().get(0).addAccount(client.getAccount());
			client.setPlayroom(Playroom.getPlayrooms().get(0));
			result = true;
			// If first player already there, send its username in order to show it in GUI
			if(client.getPlayroom().getPlayers().size() > 1) {
				String[] content = new String[] {"ResultLogin", Boolean.toString(result), token, this.username, client.getPlayroom().getPlayers().get(0).getUsername()};
				client.send(new ResultLogin(content));	
			} else {
				String[] content = new String[] {"ResultLogin", Boolean.toString(result), token, this.username};
				client.send(new ResultLogin(content));	
			};
			
			// Inform first player about me joined the playroom
			for(Client c : Client.getClients()) {
//				if(client.getPlayroom() == c.getPlayroom()) {
					if(c != client && client.isLoggedIn()) {
						String[] temp = new String[] {"ResultBroadcastJoinPlayroom", "true", this.username};
						c.send(new ResultBroadcastJoinPlayroom(temp));
					}
//				}
			}
			
			// If one player already there, with me it's two so -> Start game in playroom
			if(client.getPlayroom().getNumberOfPlayers() > 1) {
				Playroom.getPlayrooms().get(0).setGameStarted(true);
			}
			
		} else {
			client.send(new ResultLogin(result));
		}
	}
}
