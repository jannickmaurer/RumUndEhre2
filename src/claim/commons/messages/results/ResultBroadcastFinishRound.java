package claim.commons.messages.results;

import java.util.ArrayList;
import java.util.logging.Logger;

import claim.client.controller.Controller;
import claim.commons.Card;
import claim.commons.ServiceLocator;
import claim.commons.messages.Message;

// Created by Jannick: Message Server -> Client
// String: ResultFinishRound|Boolean|Username Winner|TableCard|Undead1|Undead2

public class ResultBroadcastFinishRound extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getClientLogger();
	
	private String winner;
	private String TableCard;
	private ArrayList<Card> undeads = new ArrayList<>();
	

	public ResultBroadcastFinishRound(boolean result) {
		super(new String[] {"ResultBroadcastFinishRound", Boolean.toString(result)});
	}
	public ResultBroadcastFinishRound(String[] content) {
		super(content);
		this.winner = content[2];
		this.TableCard = content[3];
		if(content.length > 3) {
			undeads.add(new Card(content[4]));
		}
		if(content.length > 4) {
			undeads.add(new Card(content[5]));
		}
		
	}
	@Override
	public void process(Controller controller) {
		if(controller.getUsername().equalsIgnoreCase(this.winner)) {
			// Runde gewonnen
			if(!undeads.isEmpty()) {
				for(Card c : undeads) {
					controller.getBoard().addUndead(c);
				}
			}
		
		} else {
			// Table Karte, die der Verlierer erhält, anzeigen
			
		}
		
		
	
	}
	
	@Override
	public void processIfFalse(Controller controller) {
		controller.somethingFailed();
	}

}
