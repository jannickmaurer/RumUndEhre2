package claim.commons.messages;

import java.util.ArrayList;
import java.util.logging.Logger;

import claim.commons.Card;
import claim.commons.ServiceLocator;
import claim.commons.messages.results.ResultDealCards;
import claim.commons.messages.results.ResultStartSecondRound;
import claim.server.Account;
import claim.server.Client;
import claim.server.Playroom;

//Class implemented by Jannick: Message Client -> Server
//StartSecondRound|Token

public class StartSecondRound extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getServerLogger();
	
	private String token;

	public StartSecondRound(String[] content) {
		super(content);
		this.token = content[1];	
	}
	
	public void process(Client client) {
		Boolean result = false;
		if(this.token.equals(client.getToken())) {
			client.getPlayroom().getTable().setSecondRoundStarted(true);
			result = true;
			client.send(new ResultStartSecondRound(result));
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String[] temp = new String[] {"ResultDealCards", "true", client.getAccount().getUsername()};
			for(Account a : client.getPlayroom().getPlayers()) {
				
				ArrayList<String> cards = new ArrayList<>();
				for(Card c : a.getFollowerCards()) {
					cards.add(c.toString());
				}
				
				String[] content = Playroom.combineArrayAndArrayList(temp, cards);
				a.getClient().send(new ResultDealCards(content));
				
				a.getFollowerCards().clear();
				System.out.println("");
				System.out.print("HandCards bei " + a.getUsername() + ": ");
				for(Card c : a.getHandCards()) {
					System.out.print(c.toString());
				}
				
				
			}
			
			
			
			
		} else {
			client.send(new ResultStartSecondRound(result));
		}
	}
}
