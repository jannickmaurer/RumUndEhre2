package claim.client.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Logger;

import claim.commons.messages.Message;
import claim.commons.messages.Ping;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import claim.commons.ServiceLocator;

public class Model {
	private static ServiceLocator serviceLocator = ServiceLocator.getServiceLocator();
	private static Logger logger = serviceLocator.getClientLogger();
	private Socket socket = null;
	private Thread t;
	
	// Properties to work with in Controller or View:
	private SimpleStringProperty lastReceivedMessage = new SimpleStringProperty();
	private SimpleBooleanProperty connected = new SimpleBooleanProperty(false);
	

	
	// Connect to Server and directly send a new Ping Message
	// Takes IP Address as String and Port as int
	public void connect(String ipAdress, int port) throws Exception, IOException {
//		try {
		socket = new Socket(ipAdress, port);

		Runnable r = new Runnable() {
			public void run() {
				Message msg = new Ping(new String[] { "Ping", null });
				try {
					msg.send(socket);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				while (!socket.isClosed()) {
					try {
						BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						String msgText = in.readLine(); // Will wait here for complete line
						logger.info("Model received String: " + msgText);
						lastReceivedMessage.setValue(msgText);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};
		t = new Thread(r);
		t.start();
	}
	
	public void setConnected(Boolean connected) {
		this.connected.set(connected);
		logger.info("Client is Connected");
	}

	public SimpleStringProperty getLastReceivedMessage() {
		return this.lastReceivedMessage;
	}
	
	

}
