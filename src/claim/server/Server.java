package claim.server;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

import claim.commons.ServiceLocator;

// Class implemented by Jannick - some concepts inspired by class material
public class Server {
	
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getServerLogger();
	
	private static String directory = "";
	private static int port = 0;
	
	public static void main(String[] args) {
	
		if (args.length > 1) {
			directory = args[1];
		}
		
		System.out.println("Welcome to Claims - powered by Run und Ehre 2.0");
		System.out.println("This time, we won't f*ck up");
		// Creates a new Scanner object and takes the keyboard input for the port number, only numbers allowed
		try (Scanner scan = new Scanner(System.in)){
			while(port < 1024 || port > 65535) {
				System.out.println("Please enter a port number (1024 - 65535) ");
				String input = scan.nextLine();
				try {
					port = Integer.parseInt(input);
				} catch (NumberFormatException e) {
					System.out.println("Only numbers, please");
				}
			}
			// Creates a new ListenerThread Object while handing over the port number
			try {
				ListenerThread lt = new ListenerThread(port);
				lt.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
			logger.info("Server started on port: " + port + " You may now connect a client");
//			DeckOfCards dc = new DeckOfCards();
//			Playroom pr  = new Playroom();
//			Playroom.add(pr);
			Table table = new Table();
			table.deal();
//			logger.info("New Playroom added: " + pr.toString());
		}
				
	}
	
//	DeckOfCards deck = new DeckOfCards();
	

}
