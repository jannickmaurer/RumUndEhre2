package claim.commons.messages.results;

import java.util.ArrayList;
import java.util.logging.Logger;

import claim.client.controller.Controller;
import claim.commons.Card;
import claim.commons.ServiceLocator;
import claim.commons.messages.Message;

// Created by Jannick: Message Server -> Client
// String: ResultFinishRound|Boolean|Username Winner|TableCard|Undead1|Undead2
//String: ResultFinishRound|Boolean|Username Winner|Card1|Card2|Card3


public class ResultBroadcastFinishRound extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getClientLogger();
	
	private String card1;
	private String card2;
	private String card3;
	
	
	private String winner; 
//	private String TableCard;
	private ArrayList<Card> undeads = new ArrayList<>();
	//nach senden sieger darf spieler, gegner alles inaktivieren
	
	//**********INPUT DAVID
	private boolean secondRoundStarted;
	private Card followerCard1;
	private Card followerCard2;
	//**********INPUT DAVID
	
	public ResultBroadcastFinishRound(boolean result) {
		super(new String[] {"ResultBroadcastFinishRound", Boolean.toString(result)});
	}
	public ResultBroadcastFinishRound(String[] content) {
		super(content);
		this.winner = content[2];
		this.card1 = content[3];
		if(content.length > 4) {
			this.card2 = content[4];
		}
		if(content.length > 5) {
			this.card3 = content[5];
		}
		
		
		//**********INPUT DAVID ++ Variablen oben
//		this.secondRoundStarted =  content[3];

//		
//		if(Controller.getSecondRoundStarted()) {
//			this.followerCard1 = content[4];
//			this.followerCard2 = content[5];
//			
//		}else {
//			this.TableCard = content[4];
//			if(content.length > 5) {
//				undeads.add(new Card(content[5]));
//				
//			}
//			if(content.length > 6) {
//				undeads.add(new Card(content[6]));
//			}
//		}
		//**********INPUT DAVID
		
	
	}
	
	@Override
	public void process(Controller controller) {
		
		if(controller.getSecondRoundStarted()) {
			followerCard1 = new Card(card1);
			followerCard2 = new Card(card2);
		}
		
		
		
		if(controller.getUsername().equalsIgnoreCase(this.winner)) {
			// Runde gewonnen
			System.out.println("Ich habe gewonnen");
			
			if(controller.getSecondRoundStarted()) {
				// button enablen und karten speichern
				// TBD: Add card1 and card2
				
				
				
			} else {
				if(card2 != null) controller.getBoard().addUndead(new Card(card2));
				if(card3 != null) controller.getBoard().addUndead(new Card(card3));
				controller.enableTableCardButton();
//				
//				if(!undeads.isEmpty()) {
//					for(Card c : undeads) {
//						controller.getBoard().addUndead(c);
//					}
//				}
			}
			
			controller.setOnTurn(true);
		} else {
			// Table Karte, die der Verlierer erhält, anzeigen
			System.out.println("Ich habe verloren");
			
			if(controller.getSecondRoundStarted()) {
				
				//Zwerge scheiss
			} else {
				controller.showNewFollowerCard(card1);
			}
			
			controller.setOnTurn(false);
		}
		controller.setCardPlayed(false);
		controller.increasePlayedRounds();
	}
	
	@Override
	public void processIfFalse(Controller controller) {
		controller.somethingFailed();
	}

}
