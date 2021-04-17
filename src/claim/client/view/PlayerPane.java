//Class implemented by Samuel

package claim.client.view;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PlayerPane extends VBox {
	private Label lbName = new Label("Name");
	private Label lbPoints = new Label("Punkte");
	private HBox hboxCards = new HBox();
	private HBox hboxDecks = new HBox();
	private CardLabel clScoreDeck = new CardLabel();
	private CardLabel clFollowerDeck = new CardLabel();
	
	private GridPane pointsGrid = new GridPane();
	private Label lbPointsPlayer = new Label("0");
	
	private ArrayList<CardLabel> cardLabels = new ArrayList<>();

	//Konstruktor
	public PlayerPane() {
		pointsGrid.add(lbPoints, 0, 0);
		pointsGrid.add(lbPointsPlayer, 2, 0);
		
		VBox vboxScoreDeck = new VBox();
		Label lbScoreDeck = new Label("Score Deck");
		vboxScoreDeck.getChildren().addAll(clScoreDeck, lbScoreDeck);
		
		VBox vboxFollowerDeck = new VBox();
		Label lbFollowerDeck = new Label("Follower Deck");
		vboxFollowerDeck.getChildren().addAll(clFollowerDeck, lbFollowerDeck);
		
		// Add CardLabels for the cards
		for (int i = 0; i < 13; i++) {
			CardLabel cl = new CardLabel();
			cl.setDisable(true);
			
			hboxCards.getChildren().add(cl);
			hboxCards.setSpacing(10);
			cardLabels.add(cl);
		}
		
		hboxDecks.getChildren().addAll(vboxScoreDeck, vboxFollowerDeck);
		hboxDecks.setSpacing(10);
		this.getChildren().addAll(lbName, pointsGrid, hboxCards, hboxDecks);
	}
	
	//Getter & Setter
	public Label getLbName() {
		return lbName;
	}

	public void setLbName(Label lbName) {
		this.lbName = lbName;
	}

	public Label getLbPoints() {
		return lbPoints;
	}

	public void setLbPoints(Label lbPoints) {
		this.lbPoints = lbPoints;
	}

	public Label getLbPointsPlayer() {
		return lbPointsPlayer;
	}

	public void setLbPointsPlayer(Label lbPointsPlayer) {
		this.lbPointsPlayer = lbPointsPlayer;
	}

	public ArrayList<CardLabel> getCardLabels() {
		return cardLabels;
	}

	public void setCardLabels(ArrayList<CardLabel> cardLabels) {
		this.cardLabels = cardLabels;
	}
}