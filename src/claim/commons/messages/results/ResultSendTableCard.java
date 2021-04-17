package claim.commons.messages.results;

import java.util.ArrayList;

import claim.client.controller.Controller;
import claim.commons.messages.Message;

// Implemented by Jannick
// ResultSendTableCard|Boolean|Card
public class ResultSendTableCard extends Message {
	private String card;
	
	public ResultSendTableCard(boolean result) {
		super(new String[] {"ResultSendTableCard", Boolean.toString(result)});
	}
	
	public ResultSendTableCard(String[] content) {
		super(content);
		if(content.length > 2) this.card = content[2];
	}
	
	@Override
	public void process(Controller controller) {
		
	}
	
	@Override
	public void processIfFalse(Controller controller) {

	}


}
