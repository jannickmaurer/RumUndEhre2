package claim.commons.messages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Logger;

import claim.client.controller.Controller;
import claim.commons.ServiceLocator;
import claim.server.Client;

// Created by Jannick
public abstract class Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getServerLogger();
	
	private String[] content;

	public Message(String[] content) {
		this.content = content;
		logger.info("Message created:" + this.toString());
	}
	
	//Send new Messages, can be runned after creating a new Message object
	public void send(Socket socket) throws IOException {
		OutputStreamWriter out;
		out = new OutputStreamWriter(socket.getOutputStream());
		out.write(this.toString() + "\n");
		out.flush();
	}
	
	//Receive new Messages, can be runned after creating a new Message object
	public static Message receive(Socket socket) {
		BufferedReader in;
		Message msg = null;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// Takes the String which comes from the sender
			String msgText = in.readLine(); // Will wait here for complete line

			// Breaks the String back into an Array
			String[] content;
			content = msgText.split("\\|");
			
			for (int i = 0; i < content.length; i++) {
				content[i] = content[i].trim();
			}
			
			//Following section creates the specific Message object by looking at the first part of the String Array
			if (content[0].equals("Ping")) msg = new Ping(content);
			if (content[0].equals("CreateAccount")) msg = new CreateAccount(content);
			if (content[0].equals("Login")) msg = new Login(content);
//			if (content[0].equals("CreatePlayroom")) msg = new CreatePlayroom(content);
//			if (content[0].equals("ListPlayrooms")) msg = new ListPlayrooms(content);
//			if (content[0].equals("Logout")) msg = new Logout(content);
//			if (content[0].equals("DeleteAccount")) msg = new DeleteAccount(content);
//			if (content[0].equals("DeletePlayroom")) msg = new DeletePlayroom(content);
//			if (content[0].equals("JoinPlayroom")) msg = new JoinPlayroom(content);
//			if (content[0].equals("SendMessage")) msg = new SendMessage(content);
//			if (content[0].equals("Text")) msg = new Text(content);
//			if (content[0].equals("LeavePlayroom")) msg = new LeavePlayroom(content);
//			if (content[0].equals("StartGame")) msg = new StartGame(content);
//			if (content[0].equals("EndGame")) msg = new EndGame(content);
//			if (content[0].equals("SendTableCard")) msg = new SendTableCard(content);
//			if (content[0].equals("Disconnect")) msg = new Disconnect(content);
//			if (content[0].equals("SendTrumpf")) msg = new SendTrumpf(content);
//			if (content[0].equals("StartRound")) msg = new StartRound(content);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	@Override
	public String toString() {
		return String.join("|", content);
	}

	//Process Method because all child Classes must implement it
	public void process(Client client) {
		// TODO Auto-generated method stub
		
	}

	public void process(Controller controller) {
		// TODO Auto-generated method stub
	}
	public void processIfFalse(Controller controller) {
		// TODO Auto-generated method stub
	}
	
	// Checks if Message processing was OK with checking vor True or False
	public Boolean isTrue() {
		return this.content[1].equalsIgnoreCase("True");
	}
	public Boolean isFalse() {
		return this.content[1].equalsIgnoreCase("False");
	}

}
