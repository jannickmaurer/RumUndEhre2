//Class implemented by Samuel

package claim.client.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ChatPane extends VBox  {
	private TextArea txtMessages = new TextArea();
	private TextField tfMessage = new TextField();
	private Button btnSend = new Button();
	private ScrollPane scrollPane = new ScrollPane();
	private GridPane gameEvaluation = new GridPane();
	private Label lbEvaluation = new Label();
	private Label lbGoblins = new Label();
	private Label wonGoblins = new Label("0");
	private Label lbDwarfs = new Label();
	private Label wonDwarfs = new Label("0");
	private Label lbUndeads = new Label();
	private Label wonUndeads = new Label("0");
	private Label lbDoubles = new Label();
	private Label wonDoubles = new Label("0");
	private Label lbKnights = new Label();
	private Label wonKnights = new Label("0");
	
	//Konstruktor
	public ChatPane() {
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);
		scrollPane.setContent(txtMessages);
		txtMessages.setWrapText(true);
		txtMessages.setEditable(false);
		txtMessages.setMouseTransparent(true);
		txtMessages.setId("ChatText");
		tfMessage.setId("TextFieldMessage");
		btnSend.setDisable(true);
		
		gameEvaluation.add(lbEvaluation, 0, 0, 2, 1);
		gameEvaluation.add(lbGoblins, 0, 1);
		gameEvaluation.add(wonGoblins, 1, 1);
		gameEvaluation.add(lbDwarfs, 0, 2);
		gameEvaluation.add(wonDwarfs, 1, 2);
		gameEvaluation.add(lbUndeads, 0, 3);
		gameEvaluation.add(wonUndeads, 1, 3);
		gameEvaluation.add(lbDoubles, 0, 4);
		gameEvaluation.add(wonDoubles, 1, 4);
		gameEvaluation.add(lbKnights, 0, 5);
		gameEvaluation.add(wonKnights, 1, 5);
		gameEvaluation.setId("GameEvaluation");
		
		this.getChildren().addAll(scrollPane, tfMessage, btnSend, gameEvaluation);
		VBox.setVgrow(scrollPane, Priority.ALWAYS);
		this.prefWidth(10);
		this.setId("Chat");
		this.setAlignment(Pos.CENTER);
	}

	//Getter & Setter
	public TextArea getTxtMessages() {
		return txtMessages;
	}

	public void setTxtMessages(TextArea txtMessages) {
		this.txtMessages = txtMessages;
	}

	public TextField getTfMessage() {
		return tfMessage;
	}

	public void setTfMessage(TextField tfMessage) {
		this.tfMessage = tfMessage;
	}

	public Button getBtnSend() {
		return btnSend;
	}

	public void setBtnSend(Button btnSend) {
		this.btnSend = btnSend;
	}

	public ScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(ScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public GridPane getGameEvaluation() {
		return gameEvaluation;
	}

	public void setGameEvaluation(GridPane gameEvaluation) {
		this.gameEvaluation = gameEvaluation;
	}

	public Label getLbEvaluation() {
		return lbEvaluation;
	}

	public void setLbEvaluation(Label lbEvaluation) {
		this.lbEvaluation = lbEvaluation;
	}

	public Label getLbGoblins() {
		return lbGoblins;
	}

	public void setLbGoblins(Label lbGoblins) {
		this.lbGoblins = lbGoblins;
	}

	public Label getLbDwarfs() {
		return lbDwarfs;
	}

	public void setLbDwarfs(Label lbDwarfs) {
		this.lbDwarfs = lbDwarfs;
	}

	public Label getLbUndeads() {
		return lbUndeads;
	}

	public void setLbUndeads(Label lbUndeads) {
		this.lbUndeads = lbUndeads;
	}

	public Label getLbDoubles() {
		return lbDoubles;
	}

	public Label getWonGoblins() {
		return wonGoblins;
	}

	public void setWonGoblins(Label wonGoblins) {
		this.wonGoblins = wonGoblins;
	}

	public Label getWonDwarfs() {
		return wonDwarfs;
	}

	public void setWonDwarfs(Label wonDwarfs) {
		this.wonDwarfs = wonDwarfs;
	}

	public Label getWonUndeads() {
		return wonUndeads;
	}

	public void setWonUndeads(Label wonUndeads) {
		this.wonUndeads = wonUndeads;
	}

	public Label getWonDoubles() {
		return wonDoubles;
	}

	public void setWonDoubles(Label wonDoubles) {
		this.wonDoubles = wonDoubles;
	}

	public Label getWonKnights() {
		return wonKnights;
	}

	public void setWonKnights(Label wonKnights) {
		this.wonKnights = wonKnights;
	}

	public void setLbDoubles(Label lbDoubles) {
		this.lbDoubles = lbDoubles;
	}

	public Label getLbKnights() {
		return lbKnights;
	}

	public void setLbKnights(Label lbKnights) {
		this.lbKnights = lbKnights;
	}
}
