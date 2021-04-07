package claim.client.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Model {
	private Socket socket = null;
	
//	public void connect(String ipAdress, int port) throws Exception, IOException {
////		try {
//		socket = new Socket(ipAdress, port);
//
//		Runnable r = new Runnable() {
//			public void run() {
//				Message msg = new Ping(new String[] { "Ping", null });
//				try {
//					msg.send(socket);
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
//				while (!socket.isClosed()) {
//					try {
//						BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//						String msgText = in.readLine(); // Will wait here for complete line
//						logger.info("Model received String: " + msgText);
//						lastReceivedMessage.setValue(msgText);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		};
//		t = new Thread(r);
//		t.start();
//		logger.info("Client Connected");
//	}

}
