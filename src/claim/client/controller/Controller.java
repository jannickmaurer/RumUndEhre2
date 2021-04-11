package claim.client.controller;

import java.util.logging.Logger;

import claim.client.model.Model;
import claim.client.view.View;
import claim.commons.ServiceLocator;
import claim.commons.messages.Message;
import claim.commons.messages.results.ResultPing;


public class Controller {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getServerLogger();
	private Model model;
	private View view;

	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
		
		view.getBtConnect().setOnAction(event -> connect());
		
		view.getBtStart().setOnAction(e -> {
			view.getRoot().setCenter(view.loginLayout);
			view.getStage().setTitle("Login");
		});
		
		view.getBtRegistration().setOnAction(e -> {
			view.getRoot().setCenter(view.registrationLayout);
			view.getStage().setTitle("Registration");
		});
		
		view.getBtBack().setOnAction(e -> {
			view.getRoot().setCenter(view.loginLayout);
			view.getStage().setTitle("Login");
		});
		
		// When Model receives a new Message, the Value of the SimpleString Property "LastReceivedMessage" Changes
		// This Method looks at this change and creates the respective Message Object
		model.getLastReceivedMessage().addListener((o, oldValue, newValue) -> {
			if (!newValue.equals("")) {
				// Takes the String the Model received from the Server and puts it into a String Array
				String[] content = newValue.split("\\|");
				for (int i = 0; i < content.length; i++) {
					content[i] = content[i].trim();
				}
				// Creates a new Message with the String Array
				createMessage(content);
				// Empties the Value of the StringProperty
				model.getLastReceivedMessage().setValue("");
			}
		});
	}

	// Does the same thing as the Message Class on Server's Side
	// It is done here, because we want to trigger actions on the client's GUI
	private void createMessage(String[] content) {
		Message msg;
		if (content[0].equals("ResultPing")) { msg = new ResultPing(content);
			if (!msg.isFalse()) msg.process(Controller.this);
			if (msg.isFalse()) msg.processIfFalse(Controller.this);
		}
	}


	// Methods for triggering Methods in Model by clicking a Button in View
	private void connect() {
		try {
			this.model.connect("127.0.0.1", 3333);
		} catch (Exception e) {
			logger.warning("Server Down");
		}
	}



	// Getter & Setter
	public Model getModel() {
		return model;
	}
	public View getView() {
		return view;
	}

}
