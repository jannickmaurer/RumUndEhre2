package claim.commons.messages.results;

import java.util.ArrayList;
import java.util.logging.Logger;

import claim.client.controller.Controller;
import claim.commons.ServiceLocator;
import claim.commons.messages.Message;

// Class implemented by Jannick: Message Server -> Client
// ResultDealCards|result|FirstUser|Card1| ... Card13

public class ResultDealCards extends Message {
	
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getClientLogger();
	private String firstUser;
	
	private ArrayList<String> handCards = new ArrayList<>();
	
	// Takes the result and creates new Object
	public ResultDealCards(boolean result) {
		super(new String[] {"ResultDealCards", Boolean.toString(result)});
	}
	
	public ResultDealCards(String[] content) {
		super(content);
		firstUser = content[2];
		for(int i = 3; i < content.length; i++) {
			handCards.add(content[i]);
		}
	}
	
	@Override
	public void process(Controller controller) {
		if(controller.getReadyForSecondRound()) {
			controller.prepareSecondRound();
			controller.setSecondRoundStarted(true);
			controller.getView().getChatLayout().getLbDoubles().setVisible(true);
			controller.getView().getChatLayout().getWonDoubles().setVisible(true);
			controller.getView().getChatLayout().getLbDwarfs().setVisible(true);
			controller.getView().getChatLayout().getWonDwarfs().setVisible(true);
			controller.getView().getChatLayout().getLbGoblins().setVisible(true);
			controller.getView().getChatLayout().getWonGoblins().setVisible(true);
			controller.getView().getChatLayout().getLbKnights().setVisible(true);
			controller.getView().getChatLayout().getWonKnights().setVisible(true);
			
			// Artificial break in order to give the other Threads time to finish
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		controller.getBoard().addHandCards(handCards);
		controller.deal(controller.getBoard().getHandCards());
		controller.enableTableCardButton(firstUser);
		if(firstUser.equals(controller.getUsername())) {
			controller.setOnTurn(true);
			if(controller.getReadyForSecondRound()) {
				controller.getView().getGameLayout().getBtNextDuel().setDisable(false);
			}
		}
		// Player got its handcards and game can be started
	}
	
	@Override
	public void processIfFalse(Controller controller) {
		controller.somethingFailed();
	}
}
