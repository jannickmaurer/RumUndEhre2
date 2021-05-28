//Class implemented by Samuel

package claim.client.view;

import java.util.ArrayList;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OtherPlayerPane extends HBox {
	private Label lbOpponent = new Label();
	private Label lbName = new Label();

	// Konstruktor
	public OtherPlayerPane() {

		this.getChildren().add(lbOpponent);
		this.getChildren().add(lbName);

		this.setId("otherPlayer");
		this.setAlignment(Pos.CENTER);
	}

	// Getter & Setter
	public Label getLbName() {
		return lbName;
	}

	public void setLbName(Label lbName) {
		this.lbName = lbName;
	}

	public Label getLbOpponent() {
		return lbOpponent;
	}

	public void setLbOpponent(Label lbOpponent) {
		this.lbOpponent = lbOpponent;
	}
}