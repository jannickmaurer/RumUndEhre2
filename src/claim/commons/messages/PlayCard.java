package claim.commons.messages;

import java.util.logging.Logger;

import claim.commons.Card;
import claim.commons.ServiceLocator;
import claim.commons.messages.results.ResultPlayCard;
import claim.commons.messages.results.ResultSendCard;
import claim.server.Account;
import claim.server.Client;

//Class implemented by Jannick: Message Client -> Server
//PlayCard|Token|Card

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
		if (this.token.equals(client.getToken())) {
			client.getAccount().setPlayedCard(new Card(card));
			if (client.getPlayroom().getTable().getPlayedCards().get() < 1) {
				client.getPlayroom().getTable().setFirstPlayer(client.getAccount());
			}

			for (Account a : client.getPlayroom().getPlayers()) {
				if (!a.getUsername().equalsIgnoreCase(client.getAccount().getUsername())) {
					String[] content = new String[] { "ResultSendCard", "true", "HandCard", this.card };
					a.getClient().send(new ResultSendCard(content));
				}
			}

			result = true;
			String[] temp = new String[] { "ResultPlayCard", Boolean.toString(result), this.card };
			client.send(new ResultPlayCard(temp));
			client.getPlayroom().getTable().increasePlayedCards();

		} else {
			client.send(new ResultPlayCard(result));
		}
	}
}
