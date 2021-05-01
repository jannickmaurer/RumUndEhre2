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
	private VBox vboxPlayedCards = new VBox();
	
	private CardLabel clCardsDeck = new CardLabel();
	private CardLabel clNewFollowerDeck = new CardLabel();

	//Konstruktor
	public MiddleGamePane() {		
		VBox vboxCardsDeck = new VBox();
		Label lbCardsDeck = new Label("Cards Deck");
		clCardsDeck.setDeck();
		vboxCardsDeck.setAlignment(Pos.CENTER);
		vboxCardsDeck.getChildren().addAll(clCardsDeck, lbCardsDeck);
		
		
		VBox vboxNewFollowerDeck = new VBox();
		Label lbNewFollowerDeck = new Label("New Follower Deck");
		clNewFollowerDeck.setDeck();
		vboxNewFollowerDeck.setAlignment(Pos.CENTER);
		vboxNewFollowerDeck.getChildren().addAll(clNewFollowerDeck, lbNewFollowerDeck);
		
		vboxPlayedCards.setAlignment(Pos.CENTER);
		hboxMiddleCards.getChildren().addAll(vboxPlayedCards, vboxCardsDeck, vboxNewFollowerDeck);
		hboxMiddleCards.setSpacing(10);
		
		this.add(hboxMiddleCards, 0, 0);
		
		this.setId("middleGame");
		this.setAlignment(Pos.CENTER);	
		}

	//Getter & Setter
	public HBox getHboxMiddleCards() {
		return hboxMiddleCards;
	}

	public void setHboxMiddleCards(HBox hboxMiddleCards) {
		this.hboxMiddleCards = hboxMiddleCards;
	}

	public VBox getVboxPlayedCards() {
		return vboxPlayedCards;
	}

	public void setVboxPlayedCards(VBox vboxPlayedCards) {
		this.vboxPlayedCards = vboxPlayedCards;
	}

	public CardLabel getClCardsDeck() {
		return clCardsDeck;
	}

	public void setClCardsDeck(CardLabel clCardsDeck) {
		this.clCardsDeck = clCardsDeck;
	}

	public CardLabel getClNewFollowerDeck() {
		return clNewFollowerDeck;
	}

	public void setClNewFollowerDeck(CardLabel clNewFollowerDeck) {
		this.clNewFollowerDeck = clNewFollowerDeck;
	}
	
}