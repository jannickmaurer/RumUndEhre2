package claim.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import java.util.logging.Logger;

import claim.commons.Card;
import claim.commons.ServiceLocator;




// Implemented by Jannick: Represents a Player's Account (User)
public class Account implements Serializable {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getServerLogger(); 
	
	private static final ArrayList<Account> accounts = new ArrayList<>();
	
	private final String username;
	private final String password;
	private transient String token;
	private transient Table table;
	private transient Client client;
	private transient Card playedCard;
	
	private transient ArrayList<Card> handCards = new ArrayList<>();
	private transient ArrayList<Card> followerCards = new ArrayList<>();
	private transient ArrayList<Card> undeadCards = new ArrayList<>();
	

	public Account(String username, String password) {
		this.username = username;
		this.password = password;
		logger.info("New Account created: " + this.toString());
	}
	
	// Add Account to List of Accounts - Synchronized bc several Clients are running at the same time
	public static void add(Account ac) {
		synchronized(accounts) {
			accounts.add(ac);
			saveAccounts();
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
		return this.username;
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
	
	public static void saveAccounts() {
		File accountFile = new File(Server.getDirectory() + "accounts.sav");
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(accountFile))) {
			synchronized (accounts) {
				out.writeInt(accounts.size());
				for (Account account : accounts) {
					out.writeObject(account);
				}
				out.flush();
				out.close();
			}
		} catch (IOException e) {
			logger.severe("Unable to save accounts: " + e.getMessage());
		}
	}
	
	public static void readAccounts() {
		File accountFile = new File(Server.getDirectory() + "accounts.sav");
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(accountFile))) {
			int num = in.readInt();
			for (int i = 0; i < num; i++) {
				Account account = (Account) in.readObject();
				accounts.add(account);
				logger.info("Loaded account " + account.getUsername());
			}
		} catch (Exception e) {
			logger.severe("Unable to read accounts: " + e.getMessage());
		}
	}

//	public ArrayList<String> getHandCards() {
//		return handCards;
//	}
//	
//	public void addHandCard(String handCard) {
//		handCards.add(handCard);
//	}
//
//	public void setHandCards(ArrayList<String> handCards) {
//		this.handCards.clear();
//		this.handCards = handCards;
//	}
	
	public void addHandCard(Card handCard) {
		this.handCards.add(handCard);
	}
	public void addFollowerCard(Card followerCard) {
		this.followerCards.add(followerCard);
	}
	public void addUndeadCard(Card undeadCard) {
		this.undeadCards.add(undeadCard);
	}

	public ArrayList<Card> getFollowerCards() {
		return followerCards;
	}

	public void setFollowerCards(ArrayList<Card> followerCards) {
		this.followerCards = followerCards;
	}

	public ArrayList<Card> getPointCards() {
		return undeadCards;
	}

	public void setPointCards(ArrayList<Card> pointCards) {
		this.undeadCards = pointCards;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public ArrayList<Card> getHandCards() {
		return handCards;
	}

	public void setHandCards(ArrayList<Card> handCards) {
		this.handCards = handCards;
	}

	public static ArrayList<Account> getAccounts() {
		return accounts;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Card getPlayedCard() {
		return playedCard;
	}

	public void setPlayedCard(Card playedCard) {
		System.out.println("PlayedCard bei Account auf: " + playedCard.toString());
		this.playedCard = playedCard;
	}

	public void clearPlayedCard() {
		this.playedCard = null;
		
	}

	public ArrayList<Card> getUndeadCards() {
		return undeadCards;
	}
	
}
