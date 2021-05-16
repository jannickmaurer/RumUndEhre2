//Class implemented by Samuel

package claim.client.view;

import java.util.ArrayList;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OtherPlayerPane extends VBox {
	private Label lbName = new Label();
	//private Label lbPoints = new Label("Punkte");
	private Label lbScoreDeck = new Label();
	private Label lbFollowerDeck = new Label();
	private HBox hboxDecks = new HBox();
	//private HBox hboxPoints = new HBox();
	private CardLabel clScoreDeck = new CardLabel();
	private CardLabel clFollowerDeck = new CardLabel();
	
	//private Label lbPointsPlayer = new Label("0");

	//Konstruktor
	public OtherPlayerPane() {		
		VBox vboxScoreDeck = new VBox();
		
		clScoreDeck.setDeck();
		vboxScoreDeck.setAlignment(Pos.CENTER);
		vboxScoreDeck.getChildren().addAll(clScoreDeck, lbScoreDeck);
		
		VBox vboxFollowerDeck = new VBox();
		
		clFollowerDeck.setDeck();
		vboxFollowerDeck.setAlignment(Pos.CENTER);
		vboxFollowerDeck.getChildren().addAll(clFollowerDeck, lbFollowerDeck);
		
		hboxDecks.getChildren().addAll(vboxScoreDeck, vboxFollowerDeck);
		hboxDecks.setAlignment(Pos.CENTER);
		hboxDecks.setSpacing(30);
		
		//hboxPoints.getChildren().addAll(lbPoints, lbPointsPlayer);
		//hboxPoints.setAlignment(Pos.CENTER);
		//hboxPoints.setSpacing(10);
		
		this.getChildren().addAll(lbName, hboxDecks);
		
		this.setId("otherPlayer");
		this.setAlignment(Pos.CENTER);	
		}
	
	//Getter & Setter
	public Label getLbName() {
		return lbName;
	}

	public void setLbName(Label lbName) {
		this.lbName = lbName;
	}

	public Label getLbScoreDeck() {
		return lbScoreDeck;
	}

	public void setLbScoreDeck(Label lbScoreDeck) {
		this.lbScoreDeck = lbScoreDeck;
	}

	public Label getLbFollowerDeck() {
		return lbFollowerDeck;
	}

	public void setLbFollowerDeck(Label lbFollowerDeck) {
		this.lbFollowerDeck = lbFollowerDeck;
	}
}