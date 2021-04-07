package claim.client;

import claim.client.controller.Controller;
import claim.client.model.Model;
import claim.client.view.View;
import javafx.application.Application;
import javafx.stage.Stage;

//Class implemented by Samuel
public class Claim extends Application {
	private Model model;
	private Controller controller;
	private View view;
	
	//JavaFX framework starten
	public static void main(String[] args) {
		launch(args);
	}
	
	//Aufgerufen von JavaFX
	@Override
	public void start(Stage primaryStage) throws Exception {
		model = new Model();
		view = new View(primaryStage, model);
		controller = new Controller(model,view);
		
		view.start();
	}

}
