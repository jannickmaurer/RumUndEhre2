package claim.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import claim.commons.ServiceLocator;

//Class implemented by Jannick - some concepts inspired by class material
public class ListenerThread extends Thread {
	private final ServerSocket listener;
	private final int port;
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getServerLogger();
	
	// Constructor called by class Server, taking the port number as int and creates a new ServerSocket object
	public ListenerThread(int port) throws IOException {
		super();
		this.port = port;
		this.setName("ListenerThread");
		
		this.listener = new ServerSocket(port, 10, null);
		logger.info(this.getName() + " started");
	}
	
		@Override
	public void run() {
		while(true) {
			try {
				Socket socket = listener.accept();
				Client c = new Client(socket);
				Client.add(c);
				logger.info("ListenerThread started and Client " + c.toString() + "added");
			} catch ( Exception e) {
				e.printStackTrace();
			}
		}
	}
	

}
