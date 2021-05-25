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
	//nach senden sieger darf spieler, gegner alles inaktivieren
	
	//**********INPUT DAVID
//	private boolean secondRoundStarted;
	private String followerCard1;
	private String followerCard2;
	//**********INPUT DAVID
	
	public ResultBroadcastFinishRound(boolean result) {
		super(new String[] {"ResultBroadcastFinishRound", Boolean.toString(result)});
	}
	public ResultBroadcastFinishRound(String[] content) {
		super(content);
		this.winner = content[2];
		this.TableCard = content[3];
		
		
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
		
		if(content.length > 4) {
			undeads.add(new Card(content[4]));
			
		}
		if(content.length > 5) {
			undeads.add(new Card(content[5]));
		}
	}
	
	@Override
	public void process(Controller controller) {
		if(controller.getUsername().equalsIgnoreCase(this.winner)) {
			// Runde gewonnen
			System.out.println("Ich habe gewonnen");
			
//			if(secondRoundStarted) {
//				
//				
//				
//				
//				
//			}else {
				
				if(!undeads.isEmpty()) {
					for(Card c : undeads) {
						controller.getBoard().addUndead(c);
					}
				}
//			}
			// Button next Table Card auf enabled
			controller.enableTableCardButton();
			controller.setOnTurn(true);
		}else {
			// Table Karte, die der Verlierer erhält, anzeigen
			System.out.println("Ich habe verloren");
			controller.showNewFollowerCard(TableCard);
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
