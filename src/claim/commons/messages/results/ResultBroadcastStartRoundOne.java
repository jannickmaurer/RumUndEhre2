package claim.commons.messages.results;

import java.util.logging.Logger;

import claim.client.controller.Controller;
import claim.commons.ServiceLocator;
import claim.commons.messages.Message;

//implemented by Jannick
//Server -> Client: ResultJoinPlayroom|Result|Username

public class ResultBroadcastStartRoundOne extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getClientLogger();


	public ResultBroadcastStartRoundOne(boolean result) {
		super(new String[] {"ResultBroadcastStartRoundOne", Boolean.toString(result)});
	}
	public ResultBroadcastStartRoundOne(String[] content) {
		super(content);

	}
	@Override
	public void process(Controller controller) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Game started");
		controller.deal();
	// Start Game in GUI	
	}
	
	@Override
	public void processIfFalse(Controller controller) {
		controller.somethingFailed();
	}

}
