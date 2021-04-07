package claim.server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

import claim.commons.ServiceLocator;


//Class implemented by Jannick - some concepts inspired by class material
public class Client {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getServerLogger();
	
	private static final ArrayList<Client> clients = new ArrayList<>();
//	private Account account = null;
	private String token = null;
	private Socket socket;
	private boolean clientReachable = true;
//	private Playroom playroom;
	
	public Client(Socket socket) {
		this.socket = socket;
		
		//Thread listening to incoming messages from clients
		Runnable r = new Runnable() {

			@Override
			public void run() {
				try {
					while(clientReachable) {
						//Messaging TBD
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		};
		Thread t = new Thread(r);
		t.start();
	}
	
	//Method to add a new client to the arrayList
	public static void add(Client client) {
		synchronized (clients) {
			clients.add(client);
		}
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
	
}
