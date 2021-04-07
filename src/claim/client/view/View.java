package claim.client.view;

import claim.client.model.Model;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

//Class implemented by Samuel
public class View {
	
	//Warum protected, private, public? (SD)
	protected Stage primaryStage;
	private Model model;
	public BorderPane root;
	private Scene scene;
	
	private Label lblPort = new Label("Hallo");
	
	public View(Stage primaryStage, Model model) {
		this.primaryStage = primaryStage;
		this.model = model;
		
		root = new BorderPane();
		root.setCenter(lblPort);
		
		scene = new Scene(root, 960, 635);
		
		primaryStage.setScene(scene);
	}

	public void start() {
		primaryStage.show();
	}

}
