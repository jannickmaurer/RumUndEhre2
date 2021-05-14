//Class implemented by Samuel

package claim.client.view;

import java.util.ArrayList;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MiddleGamePane extends HBox {
	private HBox hboxMiddleCards = new HBox();
	private GridPane playedCards = new GridPane();
	private GridPane tableCardsDeck = new GridPane();
	private GridPane newFollowerCard = new GridPane();

	private Button btNextTableCard = new Button("Next");

	private CardLabel clNewFollowerDeck = new CardLabel();

	//Konstruktor
	public MiddleGamePane() {		
		Label lbCardsDeck = new Label("Table Card");
		
		Label lbNewFollowerDeck = new Label("New Follower Card");
		clNewFollowerDeck.setDeck();
		
		Label lbOpponentCard = new Label("Opponent card");
		Label lbMyCard = new Label("My Card");
		
		Rectangle r1 = new Rectangle();
		r1.setWidth(92);
		r1.setHeight(135);
		r1.setArcWidth(10);
		r1.setArcHeight(10);
		r1.setFill(Color.rgb(244, 238, 232));
		
		Rectangle r2 = new Rectangle();
		r2.setWidth(92);
		r2.setHeight(135);
		r2.setArcWidth(10);
		r2.setArcHeight(10);
		r2.setFill(Color.rgb(244, 238, 232));
		
		playedCards.add(lbOpponentCard, 0, 0);
		playedCards.add(r1, 0, 1);
		playedCards.add(r2, 0, 2);
		playedCards.add(lbMyCard, 0, 3);
		playedCards.setAlignment(Pos.CENTER);
		playedCards.setHalignment(r1, HPos.CENTER);
		playedCards.setHalignment(r2, HPos.CENTER);
		playedCards.setHalignment(lbMyCard, HPos.CENTER);
		playedCards.setId("playedCards");
		
		tableCardsDeck.add(lbCardsDeck, 0, 0);
		tableCardsDeck.add(btNextTableCard, 0, 2);
		tableCardsDeck.setAlignment(Pos.CENTER);
		tableCardsDeck.setHalignment(lbCardsDeck, HPos.CENTER);
		tableCardsDeck.setId("nextTableCard");

		newFollowerCard.add(lbNewFollowerDeck, 0, 0);
		newFollowerCard.add(clNewFollowerDeck, 0, 1);
		newFollowerCard.setAlignment(Pos.CENTER);
		newFollowerCard.setHalignment(clNewFollowerDeck,HPos.CENTER);
		newFollowerCard.setId("newFollowerCard");

		this.getChildren().addAll(playedCards, tableCardsDeck, newFollowerCard);
		this.setId("middleGame");
		this.setAlignment(Pos.CENTER);
		}

	//Getter & Setter
	public GridPane getTableCardsDeck() {
		return tableCardsDeck;
	}

	public void setTableCardsDeck(GridPane tableCardsDeck) {
		this.tableCardsDeck = tableCardsDeck;
	}

	public Button getBtNextTableCard() {
		return btNextTableCard;
	}

	public void setBtNextTableCard(Button btNextTableCard) {
		this.btNextTableCard = btNextTableCard;
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