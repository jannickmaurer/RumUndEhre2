package claim.client.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ChatPane extends VBox  {
	private TextArea txtMessages = new TextArea();
	private TextField tfMessage = new TextField();
	private Button btnSend = new Button();
	private ScrollPane scrollPane = new ScrollPane();
	
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
		tfMessage.setId("TextFieldMessage");
		btnSend.setDisable(true);
		
		this.getChildren().addAll(scrollPane, tfMessage, btnSend);
		VBox.setVgrow(scrollPane, Priority.ALWAYS);
		this.prefWidth(20);
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
	
}
