package claim.server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

import claim.commons.ServiceLocator;
import claim.commons.messages.Message;
import claim.server.Client;
import claim.server.Account;



//Class implemented by Jannick - some concepts inspired by class material
public class Client {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getServerLogger();
	
	private static final ArrayList<Client> clients = new ArrayList<>();
	private Account account = null;
	private String token = null;
	private Socket socket;
	private boolean clientReachable = true;
	private Playroom playroom;
	private Boolean loggedIn;
	
	public Client(Socket socket) {
		this.socket = socket;
		
		//Thread listening to incoming messages from clients
		Runnable r = new Runnable() {

			@Override
			public void run() {
				try {
					while(clientReachable) {
						Message msg = Message.receive(socket);
						if(msg != null) {
							logger.info("Server received message: " + msg.toString());
							msg.process(Client.this);
						} else {
							logger.warning("Empty Message");
						}
					}
				} catch (Exception e) {
					logger.info("Client deisconnected");
					clientReachable = false;
				}
			}
		};
		Thread t = new Thread(r);
		t.start();
	}
	
	//send a new message to this client
		public void send(Message msg) {
			try {
				msg.send(socket);	
				logger.info("Server sent message: " + msg.toString());
			} catch (Exception e) { //TBD: Why not IOException?
				e.printStackTrace();
				this.token = null;
				this.clientReachable = false;
			}
		}
	
	//Method to add a new client to the arrayList
	public static void add(Client client) {
		synchronized (clients) {
			clients.add(client);
		}
	}

	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public static ArrayList<Client> getClients() {
		return clients;
	}
	
	public static Client getClient(String username) {
		synchronized (clients) {
			for (Client c : clients) {
//				if (c.getAccount() != null && c.getName().equals(username)) return c;
			}
		}
		return null;
	}

	public boolean isClientReachable() {
		return clientReachable;
	}

	public void setClientReachable(boolean clientReachable) {
		this.clientReachable = clientReachable;
	}

	public Playroom getPlayroom() {
		return playroom;
	}

	public void setPlayroom(Playroom playroom) {
		this.playroom = playroom;
	}

	public Boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(Boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	

}
