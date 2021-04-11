package claim.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import java.util.logging.Logger;

import claim.commons.ServiceLocator;


// Implemented by Jannick: Represents a Player's Account (User)
public class Account {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getServerLogger(); 
	
	private static final ArrayList<Account> accounts = new ArrayList<>();
	
	private final String username;
	private final String password;
	private String token;
	
	public Account(String username, String password) {
		this.username = username;
		this.password = password;
		logger.info("New Account created: " + this.toString());
	}
	
	// Add Account to List of Accounts - Synchronized bc several Clients are running at the same time
	public static void add(Account ac) {
		synchronized(accounts) {
			accounts.add(ac);
		}
	}
	
	// Generates a random token for the client (must not be in this class, but doesn't matter)
	// source: https://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string
		public String getToken() {
		String uuid = UUID.randomUUID().toString();
		System.out.println(uuid);
	    return uuid;
		}
	
	public String toString() {
		return "Username: " + this.username + " Password: " + this.password;
	}

	public static Account getAccount(String username) {
		synchronized (accounts) {
			for (Account account : accounts) {
				if (account.username.equals(username)) {
					return account;
				}
			}
		}
		return null;
	}

	public static boolean checkLogin(String username, String password) {
		synchronized (accounts) {
			for (Account account : accounts) {
				if (account.username.equals(username)) {
					if(account.password.equals(password)) {
						return true;
					}
				}
			}
		}	
		return false;
	}

	public static void remove(Account account) {
		synchronized (accounts) {
			for (Iterator<Account> i = accounts.iterator(); i.hasNext();) {
				if (account == i.next()) i.remove();
			}
		}
	}	
}
