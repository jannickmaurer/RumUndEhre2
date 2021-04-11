package claim.client.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import claim.commons.messages.CreateAccount;
import claim.commons.messages.DeleteAccount;
import claim.commons.messages.Login;
import claim.commons.messages.Logout;
import claim.commons.messages.Message;
import claim.commons.messages.Ping;
import claim.commons.Configuration;
import claim.commons.Translator;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import claim.commons.ServiceLocator;

public class Model {
	private static ServiceLocator serviceLocator = ServiceLocator.getServiceLocator();
	private static Logger logger = serviceLocator.getClientLogger();
	private Socket socket = null;
	private Thread t;
	
	// Properties to work with in Controller or View:
	private SimpleStringProperty lastReceivedMessage = new SimpleStringProperty();
	private SimpleBooleanProperty connected = new SimpleBooleanProperty(false);
	private SimpleStringProperty token = new SimpleStringProperty();
	

	
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
	
	// Methods to create Messages
	public void createAccount(String username, String password) {
		String[] content = new String[] { "CreateAccount", username, password };
		Message msg = new CreateAccount(content);
		try {
			msg.send(socket);
			logger.info("Client tries to send message: " + msg.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void login(String username, String password) {
		String[] content = new String[] { "Login", username, password };
		Message msg = new Login(content);
		try {
			msg.send(socket);
			logger.info("Client tries to send message: " + msg.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void logout() {
		String[] content = new String[] { "Logout", this.token.getValue() };
		Message msg = new Logout(content);
		try {
			msg.send(socket);
			logger.info("Client tries to send message: " + msg.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void deleteAccount() {
		String[] content = new String[] { "DeleteAccount", this.token.getValue() };
		Message msg = new DeleteAccount(content);
		try {
			msg.send(socket);
			logger.info("Client tries to send message: " + msg.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public void setConnected(Boolean connected) {
		this.connected.set(connected);
		logger.info("Client is Connected");
	}

	public SimpleStringProperty getLastReceivedMessage() {
		return this.lastReceivedMessage;
	}

	public void initialize() {
		new Thread(initializer).start();
	}

	public void setToken(String token) {
		this.token.set(token);
		logger.info("Client set token to: " + this.token.getValue());		
	}
	
	//Analog Chatroom Project at FHNW 2019
		final Task<Void> initializer = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				this.updateProgress(1, 6);

				// Create the service locator to hold our resources
				serviceLocator = ServiceLocator.getServiceLocator();
				this.updateProgress(2, 6);

				// Initialize the resources in the service locator
				serviceLocator.setClientLogger(configureLogging());
				this.updateProgress(3, 6);

				serviceLocator.setConfiguration(new Configuration());
				this.updateProgress(4, 6);

				String language = serviceLocator.getConfiguration().getOption("Language");
				serviceLocator.setTranslator(new Translator(language));
				this.updateProgress(5, 6);

				// ... more resources would go here...
				this.updateProgress(6, 6);

				return null;
			}
			
			private Logger configureLogging() {
				Logger rootLogger = Logger.getLogger("");
				rootLogger.setLevel(Level.FINEST);

				// By default there is one handler: the console
				Handler[] defaultHandlers = Logger.getLogger("").getHandlers();
				defaultHandlers[0].setLevel(Level.INFO);

				// Add our logger
				Logger ourLogger = Logger.getLogger(serviceLocator.getAPP_NAME());
				ourLogger.setLevel(Level.FINEST);

				// Add a file handler, putting the rotating files in the tmp directory
				try {
					Handler logHandler = new FileHandler("%t/" + serviceLocator.getAPP_NAME() + "_%u" + "_%g" + ".log", 1000000,
							9);
					logHandler.setLevel(Level.FINEST);
					ourLogger.addHandler(logHandler);
				} catch (Exception e) { // If we are unable to create log files
					throw new RuntimeException("Unable to initialize log files: " + e.toString());
				}

				return ourLogger;
			}
		};

}
