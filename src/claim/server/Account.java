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

	// transient in order to not save the variables in accounts file
	private transient String token;
	private transient Table table;
	private transient Client client;
	private transient Card playedCard;
	private transient ArrayList<Card> handCards;
	private transient ArrayList<Card> followerCards;
	private transient ArrayList<Card> undeadCards;

	public Account(String username, String password) {
		this.username = username;
		this.password = password;
		handCards = new ArrayList<>();
		followerCards = new ArrayList<>();
		undeadCards = new ArrayList<>();

		logger.info("New Account created: " + this.toString());
	}

	// Add Account to List of Accounts - Synchronized bc several Clients are running
	// at the same time
	public static void add(Account ac) {
		synchronized (accounts) {
			accounts.add(ac);
			saveAccounts();
		}
	}

	// Generates a random token for the client (must not be in this class, but
	// doesn't matter)
	// source:
	// https://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string
	public String getToken() {
		String uuid = UUID.randomUUID().toString();
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
					if (account.password.equals(password)) {
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
				if (account == i.next())
					i.remove();
			}
		}
	}

	// Save accounts to file
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
				// First: create temporary account object read from the file
				Account account = (Account) in.readObject();
				// Second: Create new account object with using username & password
				// This way, the transient variables get created again, too
				// Maybe not the smartest way, but works well
				Account ac = new Account(account.getUsername(), account.getPassword());
				accounts.add(ac);
				logger.info("Loaded account " + ac.getUsername());
			}
		} catch (Exception e) {
			logger.severe("Unable to read accounts: " + e.getMessage());
		}
	}


	// Clear account after logging out
	public void clearAccount() {
		this.token = null;
		this.table = null;
		this.client = null;
		this.playedCard = null;
		if (!this.handCards.isEmpty())
			this.handCards.clear();
		if (!this.followerCards.isEmpty())
			this.followerCards.clear();
		if (!this.undeadCards.isEmpty())
			this.undeadCards.clear();
		logger.info("Account : " + this.username + " cleared");
	}


	public void addHandCard(Card handCard) {
		this.handCards.add(handCard);
	}

	public void addFollowerCard(Card followerCard) {
		this.followerCards.add(followerCard);
		// ********START TEST
//		if (this.getFollowerCards().size() == 13) {
//			String out = "AccountNAME: " + this.username + ": ";
//			for (Card c : followerCards) {
//				out = out + " | " + c.toString();
//			}
//			System.out.println(out);
//		}
		// *******END TEST
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
		this.playedCard = playedCard;
	}

	public void clearPlayedCard() {
		this.playedCard = null;

	}

	public ArrayList<Card> getUndeadCards() {
		return undeadCards;
	}

}
