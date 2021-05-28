package claim.commons.messages.results;

import java.util.logging.Logger;

import claim.client.controller.Controller;
import claim.commons.ServiceLocator;
import claim.commons.messages.Message;


//Class implemented by Jannick: Message Server -> Client
//ResultLogin|Boolean|Token|Username|UsernameOpponent

public class ResultLogin extends Message {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getClientLogger();
	
	private String token;
	private String username;
	private String opponent;

	public ResultLogin(boolean result) {
		super(new String[] {"ResultLogin", Boolean.toString(result)});
	}
	public ResultLogin(String[] content) {
		super(content);
		if(content.length > 2) {
			this.token = content[2];
			this.username = content[3];
		}
		if(content.length > 4) {
			this.opponent = content[4];
		}
	}
	@Override
	public void process(Controller controller) {
		controller.loginSuccess();
		controller.getModel().setToken(this.token);
		controller.setUsername(username);
		if(opponent != null ) {
			controller.updateOpponent(opponent);
		}
	}
	
	@Override
	public void processIfFalse(Controller controller) {
		controller.somethingFailed();
	}
}
