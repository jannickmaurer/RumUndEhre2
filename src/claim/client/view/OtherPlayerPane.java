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
	private Label lbName = new Label("Name");
	//private Label lbPoints = new Label("Punkte");
	private HBox hboxDecks = new HBox();
	//private HBox hboxPoints = new HBox();
	private CardLabel clScoreDeck = new CardLabel();
	private CardLabel clFollowerDeck = new CardLabel();
	
	//private Label lbPointsPlayer = new Label("0");

	//Konstruktor
	public OtherPlayerPane() {		
		VBox vboxScoreDeck = new VBox();
		Label lbScoreDeck = new Label("Score Deck");
		clScoreDeck.setDeck();
		vboxScoreDeck.setAlignment(Pos.CENTER);
		vboxScoreDeck.getChildren().addAll(clScoreDeck, lbScoreDeck);
		
		VBox vboxFollowerDeck = new VBox();
		Label lbFollowerDeck = new Label("Follower Deck");
		clFollowerDeck.setDeck();
		vboxFollowerDeck.setAlignment(Pos.CENTER);
		vboxFollowerDeck.getChildren().addAll(clFollowerDeck, lbFollowerDeck);
		
		hboxDecks.getChildren().addAll(vboxScoreDeck, vboxFollowerDeck);
		hboxDecks.setAlignment(Pos.CENTER);
		hboxDecks.setSpacing(10);
		
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
}