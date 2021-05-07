package claim.commons.messages.results;

import java.util.ArrayList;
import java.util.logging.Logger;

import claim.client.controller.Controller;
import claim.commons.Card;
import claim.commons.ServiceLocator;
import claim.commons.messages.Message;

// Created by Jannick: Message Server -> Client
// String in case winner: ResultFinishRound|Boolean|Username Winner|Undead1|Undead2
// String in case loser: ResultFinishRound|Boolean|Username Winner|TableCardLoser

public class ResultFinishRound extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getClientLogger();
	
	private String winner;
	private String TableCard;
	private ArrayList<Card> undeads = new ArrayList<>();
	

	public ResultFinishRound(boolean result) {
		super(new String[] {"ResultFinishRound", Boolean.toString(result)});
	}
	public ResultFinishRound(String[] content) {
		super(content);
		this.winner = content[2];
		
	}
	@Override
	public void process(Controller controller) {
		if(controller.getUsername().equalsIgnoreCase(this.winner)) {
		
		}
		
		
	
	}
	
	@Override
	public void processIfFalse(Controller controller) {
		controller.somethingFailed();
	}

}
