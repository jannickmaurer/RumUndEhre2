//Class implemented by Samuel

package claim.client.view;

import java.util.ArrayList;

import claim.client.controller.Controller;
import claim.client.model.Board;
import claim.commons.Card;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PlayerPane extends VBox {
	private Label lbName = new Label();
	private HBox hboxCards = new HBox();

	private Button btLogout = new Button();

	private ArrayList<CardLabel> cardLabels;

	// Konstruktor
	public PlayerPane() {
		// Add child nodes
		cardLabels = new ArrayList<>();

		// Add CardLabels zu Beginn damit diese Ansprechbar sind (Hervorheben etc.)for
		// the cards
		for (int i = 0; i < 13; i++) {
			CardLabel cl = new CardLabel();
			hboxCards.getChildren().add(cl);
			hboxCards.setSpacing(10);
			cardLabels.add(cl);
			cl.setDisable(true);
		}

		hboxCards.setAlignment(Pos.CENTER);

		this.getChildren().addAll(lbName, hboxCards, btLogout);

		this.setId("player");
		this.setAlignment(Pos.CENTER);
	}


	// Getter & Setter
	public Button getBtLogout() {
		return btLogout;
	}

	public void setBtLogout(Button btLogout) {
		this.btLogout = btLogout;
	}

	public HBox getHboxCards() {
		return hboxCards;
	}

	public void setHboxCards(HBox hboxCards) {
		this.hboxCards = hboxCards;
	}

	public Label getLbName() {
		return lbName;
	}

	public void setLbName(Label lbName) {
		this.lbName = lbName;
	}

	public ArrayList<CardLabel> getCardLabels() {
		return cardLabels;
	}

	public void setCardLabels(ArrayList<CardLabel> cardLabels) {
		this.cardLabels = cardLabels;
	}
}