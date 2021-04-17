package claim.commons.messages.results;

import java.util.ArrayList;

import claim.client.controller.Controller;
import claim.commons.messages.Message;

// Implemented by Jannick: Server - Client Message
// ResultStartRoundOne|Boolean|Card1|Card2| ... Card26
public class ResultStartRoundOne extends Message {

	private String token;
	
	public ResultStartRoundOne(boolean result) {
		super(new String[] {"ResultStartRoundOne", Boolean.toString(result)});
	}
	public ResultStartRoundOne(String[] content) {
		super(content);
	
	}
	
	@Override
	public void process(Controller controller) {
		controller.startRoundOne();
	}
	
	@Override
	public void processIfFalse(Controller controller) {

	}


}
