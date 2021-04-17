package claim.commons.messages.results;

import java.util.ArrayList;

import claim.client.controller.Controller;
import claim.commons.messages.Message;

// Implemented by Jannick
// ResultSendHandCards|Boolean|Card1| ... Card 13
public class ResultSendHandCards extends Message {
	private ArrayList<String> handCards = new ArrayList<>();
	
	public ResultSendHandCards(boolean result) {
		super(new String[] {"ResultSendHandCards", Boolean.toString(result)});
	}
	
	public ResultSendHandCards(String[] content) {
		super(content);
		
		for(int i = 2; i < content.length; i++) {
			this.handCards.add(content[i]);
		}
	}
	
	@Override
	public void process(Controller controller) {
		controller.getBoard().setHandCards(this.handCards);
	}
	
	@Override
	public void processIfFalse(Controller controller) {

	}


}
