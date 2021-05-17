package claim.client;

import claim.client.controller.Controller;
import claim.client.model.Model;
import claim.client.view.View;
import claim.commons.ServiceLocator;
import claim.client.Claim;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

//Class implemented by Samuel
public class Claim extends Application {
	private Model model;
	private Controller controller;
	private View view;
	
	private static Claim claimProgram;
	private ServiceLocator serviceLocator;
	
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
		
		model.initialize();
		serviceLocator = ServiceLocator.getServiceLocator();
		view.start();
	}

	@Override
    public void init() {
        if (claimProgram == null) {
            claimProgram = this;
        } else {
            Platform.exit();
        }
    }
	
	protected static Claim getClaimProgram() {
        return claimProgram;
    }

}
