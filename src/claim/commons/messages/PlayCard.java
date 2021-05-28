package claim.commons.messages;

import java.util.logging.Logger;

import claim.commons.Card;
import claim.commons.ServiceLocator;
import claim.commons.messages.results.ResultBroadcastJoinPlayroom;
import claim.commons.messages.results.ResultBroadcastStartRoundOne;
import claim.commons.messages.results.ResultLogin;
import claim.commons.messages.results.ResultPlayCard;
import claim.commons.messages.results.ResultSendCard;
import claim.server.Account;
import claim.server.Client;
import claim.server.Playroom;


//Implemented by Jannick
// PlayCard|token|card

public class PlayCard extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getServerLogger();
	
	private String card;
	private String token;

	public PlayCard(String[] content) {
		super(content);
		this.token = content[1];
		this.card = content[2];		
	}
	

	public void process(Client client) {
		Boolean result = false;
		if(this.token.equals(client.getToken())) {
			client.getAccount().setPlayedCard(new Card(card));
			if(client.getPlayroom().getTable().getPlayedCards().get() < 1) {
				client.getPlayroom().getTable().setFirstPlayer(client.getAccount());
			}
//			client.getPlayroom().test();
			
			// Überarbeiten, muss auf die Accounts im Playroom prüfen!
			for(Account a : client.getPlayroom().getPlayers()) {
				if(!a.getUsername().equalsIgnoreCase(client.getAccount().getUsername())) {
					String[] content = new String[] {"ResultSendCard", "true", "HandCard", this.card};
					a.getClient().send(new ResultSendCard(content));
				}
			}
			
			
//			for(Client c : Client.getClients()) {
//				if(c != client) {
//					String[] content = new String[] {"ResultSendCard", "true", "HandCard", this.card};
//					c.send(new ResultSendCard(content));
//				}
//			}
			result = true;
			String[] temp = new String[] {"ResultPlayCard", Boolean.toString(result), this.card};
			client.send(new ResultPlayCard(temp));
//			
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			client.getPlayroom().getTable().increasePlayedCards();
			
		} else {
		client.send(new ResultPlayCard(result));
		}
	}
}
