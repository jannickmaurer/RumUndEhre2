package claim.commons.messages.results;

import java.util.logging.Logger;

import claim.client.controller.Controller;
import claim.commons.ServiceLocator;
import claim.commons.messages.Message;

//implemented by Jannick
//Server -> Client: ResultBroadcastEvaluateWinner|Result|UsernameWinner

public class ResultBroadcastEvaluateWinner extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getClientLogger();

	private String winner;

	public ResultBroadcastEvaluateWinner(boolean result) {
		super(new String[] {"ResultBroadcastEvaluateWinner", Boolean.toString(result)});
	}
	public ResultBroadcastEvaluateWinner(String[] content) {
		super(content);
		if(content.length > 2) {
			this.winner = content[2];
		}
	}
	@Override
	public void process(Controller controller) {
		System.out.println("Winner is: " + this.winner);
		controller.winner(winner);
		
		//********TEST OUTPUTS
		System.out.println(controller.getUsername().toString()+"  Anzahl Doppelgänger: "+controller.getBoard().getNumOfDoubles());
		System.out.println(controller.getUsername().toString()+"  Anzahl Zwerge: "+controller.getBoard().getNumOfDwarfs());
		System.out.println(controller.getUsername().toString()+"  Anzahl Kobolde: "+controller.getBoard().getNumOfGoblins());
		System.out.println(controller.getUsername().toString()+"  Anzahl Ritter: "+controller.getBoard().getNumOfKnights());
		System.out.println(controller.getUsername().toString()+"  Anzahl Undead: "+controller.getBoard().getNumOfUndeads());
		//********END TEST OUTPUTS

	}
	
	@Override
	public void processIfFalse(Controller controller) {
		controller.somethingFailed();
	}

}
