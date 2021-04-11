package claim.client.view;

import claim.client.model.Model;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//Class implemented by Samuel
public class View {
	
	//Warum protected, private, public? (SD)
	protected Stage primaryStage;
	private Model model;
	public BorderPane root;
	private Scene scene;
	
	private Label lblPort = new Label("Hallo");
	private Button btnConnect = new Button("Connect");
	private Button btnCreateAccount = new Button("CreateAccount");
	private VBox vBox = new VBox();
	
	
	public View(Stage primaryStage, Model model) {
		this.primaryStage = primaryStage;
		this.model = model;
		vBox.getChildren().addAll(btnConnect, btnCreateAccount);
		
		root = new BorderPane();
		root.setCenter(vBox);
		
		scene = new Scene(root, 960, 635);
		
		primaryStage.setScene(scene);
	}

	public void start() {
		primaryStage.show();
	}
	
	public Button getBtnConnect() {
		return this.btnConnect;
	}
	public Button getBtnCreateAccount() {
		return this.btnCreateAccount;
	}

}
