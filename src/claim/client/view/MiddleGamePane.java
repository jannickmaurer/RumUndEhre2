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
	private VBox playedCards = new VBox();
	private VBox tableCardsDeck = new VBox();
	private VBox newFollowerCard = new VBox();
	
	private Label lbCardsDeck = new Label();
	private Label lbNewFollowerDeck = new Label();
	
	private Button btNextTableCard = new Button();
	private Button btEvaluateWinner = new Button();
	private Button btStartRoundTwo = new Button();
	private Label lbOpponentCard = new Label();
	private Label lbMyCard = new Label();

	private CardLabel clMyCard = new CardLabel();
	private CardLabel clOpponentCard = new CardLabel();
	private CardLabel clTableCardDeck = new CardLabel();
	private CardLabel clNewFollowerDeck = new CardLabel();

	//Konstruktor
	public MiddleGamePane() {		
				
		clMyCard.setDeck();
		clOpponentCard.setDeck();
		
		playedCards.getChildren().add(lbOpponentCard);
		playedCards.getChildren().add(clOpponentCard);
		playedCards.getChildren().add(clMyCard);
		playedCards.getChildren().add(lbMyCard);
		playedCards.setAlignment(Pos.CENTER);
		playedCards.setId("playedCards");
		
		clTableCardDeck.setDeck();
		
		tableCardsDeck.getChildren().add(lbCardsDeck);
		tableCardsDeck.getChildren().add(clTableCardDeck);
		tableCardsDeck.getChildren().add(btNextTableCard);
		btNextTableCard.setDisable(true);
		tableCardsDeck.setAlignment(Pos.CENTER);
		tableCardsDeck.setId("nextTableCard");

		clNewFollowerDeck.setDeck();
		
		newFollowerCard.getChildren().add(lbNewFollowerDeck);
		newFollowerCard.getChildren().add(clNewFollowerDeck);
		newFollowerCard.getChildren().add(btStartRoundTwo);
		btStartRoundTwo.setVisible(false);
		newFollowerCard.setAlignment(Pos.CENTER);
		newFollowerCard.setId("newFollowerCard");

		this.getChildren().addAll(playedCards, tableCardsDeck, newFollowerCard);
		this.setId("middleGame");
		this.setAlignment(Pos.CENTER);
	}

	//Getter & Setter

	public Button getBtNextTableCard() {
		return btNextTableCard;
	}

	public Label getLbCardsDeck() {
		return lbCardsDeck;
	}

	public void setLbCardsDeck(Label lbCardsDeck) {
		this.lbCardsDeck = lbCardsDeck;
	}

	public Label getLbNewFollowerDeck() {
		return lbNewFollowerDeck;
	}

	public void setLbNewFollowerDeck(Label lbNewFollowerDeck) {
		this.lbNewFollowerDeck = lbNewFollowerDeck;
	}

	public Label getLbOpponentCard() {
		return lbOpponentCard;
	}

	public void setLbOpponentCard(Label lbOpponentCard) {
		this.lbOpponentCard = lbOpponentCard;
	}

	public Label getLbMyCard() {
		return lbMyCard;
	}

	public void setLbMyCard(Label lbMyCard) {
		this.lbMyCard = lbMyCard;
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

	public VBox getPlayedCards() {
		return playedCards;
	}

	public void setPlayedCards(VBox playedCards) {
		this.playedCards = playedCards;
	}

	public VBox getTableCardsDeck() {
		return tableCardsDeck;
	}

	public void setTableCardsDeck(VBox tableCardsDeck) {
		this.tableCardsDeck = tableCardsDeck;
	}

	public VBox getNewFollowerCard() {
		return newFollowerCard;
	}

	public void setNewFollowerCard(VBox newFollowerCard) {
		this.newFollowerCard = newFollowerCard;
	}

	public Button getBtEvaluateWinner() {
		return btEvaluateWinner;
	}

	public void setBtEvaluateWinner(Button btEvaluateWinner) {
		this.btEvaluateWinner = btEvaluateWinner;
	}

	public CardLabel getClMyCard() {
		return clMyCard;
	}

	public void setClMyCard(CardLabel clMyCard) {
		this.clMyCard = clMyCard;
	}

	public CardLabel getClOpponentCard() {
		return clOpponentCard;
	}

	public void setClOpponentCard(CardLabel clOpponentCard) {
		this.clOpponentCard = clOpponentCard;
	}

	public CardLabel getClTableCardDeck() {
		return clTableCardDeck;
	}

	public void setClTableCardDeck(CardLabel clTableCardDeck) {
		this.clTableCardDeck = clTableCardDeck;
	}

	public Button getBtStartRoundTwo() {
		return btStartRoundTwo;
	}

	public void setBtStartRoundTwo(Button btStartRoundTwo) {
		this.btStartRoundTwo = btStartRoundTwo;
	}
	
}