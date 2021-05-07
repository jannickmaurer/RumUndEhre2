//Class implemented by Samuel

package claim.client.view;

import java.util.ArrayList;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MiddleGamePane extends GridPane {
	private HBox hboxMiddleCards = new HBox();
	private VBox vboxCardsDeck = new VBox();
	private GridPane playedCards = new GridPane();
	
	private CardLabel clNewFollowerDeck = new CardLabel();

	//Konstruktor
	public MiddleGamePane() {		
		Label lbCardsDeck = new Label("Cards Deck");
		vboxCardsDeck.setAlignment(Pos.CENTER);
		vboxCardsDeck.getChildren().addAll(lbCardsDeck);
		
		
		VBox vboxNewFollowerDeck = new VBox();
		Label lbNewFollowerDeck = new Label("New Follower Deck");
		clNewFollowerDeck.setDeck();
		vboxNewFollowerDeck.setAlignment(Pos.CENTER);
		vboxNewFollowerDeck.getChildren().addAll(clNewFollowerDeck, lbNewFollowerDeck);
		
		Label lbOpponentCard = new Label("Opponent card");
		Label lbMyCard = new Label("My Card");
		
		playedCards.add(lbOpponentCard, 0, 0);
		playedCards.add(lbMyCard, 0, 3);
		
		playedCards.setId("playedCards");
		hboxMiddleCards.getChildren().addAll(playedCards, vboxCardsDeck, vboxNewFollowerDeck);
		hboxMiddleCards.setSpacing(10);
		
		this.add(hboxMiddleCards, 0, 0);
		
		this.setId("middleGame");
		this.setAlignment(Pos.CENTER);	
		}

	//Getter & Setter
	public VBox getVboxCardsDeck() {
		return vboxCardsDeck;
	}

	public void setVboxCardsDeck(VBox vboxCardsDeck) {
		this.vboxCardsDeck = vboxCardsDeck;
	}
	
	public HBox getHboxMiddleCards() {
		return hboxMiddleCards;
	}

	public void setHboxMiddleCards(HBox hboxMiddleCards) {
		this.hboxMiddleCards = hboxMiddleCards;
	}

	public CardLabel getClNewFollowerDeck() {
		return clNewFollowerDeck;
	}

	public void setClNewFollowerDeck(CardLabel clNewFollowerDeck) {
		this.clNewFollowerDeck = clNewFollowerDeck;
	}

	public GridPane getPlayedCards() {
		return playedCards;
	}

	public void setPlayedCards(GridPane playedCards) {
		this.playedCards = playedCards;
	}
	
}