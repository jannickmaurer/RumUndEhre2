package claim.commons.messages;

import java.util.logging.Logger;

import claim.commons.ServiceLocator;
import claim.commons.messages.results.ResultLogin;
import claim.server.Account;
import claim.server.Client;

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
	
	public void process(Client client) {
		Boolean result = false;
		if(Account.checkLogin(username, password) == true) {
			account = Account.getAccount(username);
			client.setAccount(account);
			token = account.getToken();
			client.setToken(token);
			result = true;
			String[] content = new String[] {"ResultLogin", Boolean.toString(result), token, this.username};
			client.send(new ResultLogin(content));
		} else {
			client.send(new ResultLogin(result));
		}
	}


}
