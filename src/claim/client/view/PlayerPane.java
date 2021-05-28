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
	//private Label lbPoints = new Label("Punkte");
	//private Label lbScoreDeck = new Label();
	//private Label lbFollowerDeck = new Label();
	private HBox hboxCards = new HBox();
	//private HBox hboxDecks = new HBox();
	//private HBox hboxPoints = new HBox();
	//private CardLabel clScoreDeck = new CardLabel();
	//private CardLabel clFollowerDeck = new CardLabel();
	
	//private GridPane pointsGrid = new GridPane();
	//private Label lbPointsPlayer = new Label("0");
	
	private Button btLogout = new Button();

	private ArrayList<CardLabel> cardLabels;

	//Konstruktor
	public PlayerPane() {
		// Add child nodes
		cardLabels = new ArrayList<>();

		// Add CardLabels zu Beginn damit diese Ansprechbar sind (Hervorheben etc.)for the cards
		for (int i = 0; i < 13; i++) {
			CardLabel cl = new CardLabel();
			hboxCards.getChildren().add(cl);
			hboxCards.setSpacing(10);
			cardLabels.add(cl);
			cl.setDisable(true);
		}
		
		hboxCards.setAlignment(Pos.CENTER);
				
		//pointsGrid.add(lbPoints, 0, 0);
		//pointsGrid.add(lbPointsPlayer, 2, 0);
		
		//VBox vboxScoreDeck = new VBox();
		
		//clScoreDeck.setDeck();
		//vboxScoreDeck.setAlignment(Pos.CENTER);
		//vboxScoreDeck.getChildren().addAll(clScoreDeck, lbScoreDeck);
		
		//VBox vboxFollowerDeck = new VBox();
		
		//clFollowerDeck.setDeck();
		//vboxFollowerDeck.setAlignment(Pos.CENTER);
		//vboxFollowerDeck.getChildren().addAll(clFollowerDeck, lbFollowerDeck);
		
		//hboxDecks.getChildren().addAll(vboxScoreDeck, vboxFollowerDeck);
		//hboxDecks.setAlignment(Pos.CENTER);
		//hboxDecks.setSpacing(30);
		
		//hboxPoints.getChildren().addAll(lbPoints, lbPointsPlayer);
		//hboxPoints.setAlignment(Pos.CENTER);
		//hboxPoints.setSpacing(10);
		
		this.getChildren().addAll(lbName, hboxCards, btLogout);

		this.setId("player");
		this.setAlignment(Pos.CENTER);
	}
	
	
	
	//Karte entfernen wenn eine gespielt wird (noch auszubauen)
	/*
	 * public void updatePlayerDisplay(ArrayList<Card> handCards, String playedCard)
	 * { ArrayList<Card> handCardsList = new ArrayList<>(); handCardsList =
	 * handCards;
	 * 
	 * CardLabel clToRemove = new CardLabel(); for(CardLabel cl : cardLabels) {
	 * if(cl.getCardNameAsString().equals(playedCard)) { clToRemove = cl; } }
	 * cardLabels.remove(clToRemove); hboxCards.getChildren().remove(clToRemove);
	 * 
	 * for (int i = 0; i < handCardsList.size(); i++) { Card card = null; CardLabel
	 * cl = (CardLabel) hboxCards.getChildren().get(i);
	 * cl.setCard(handCardsList.get(i));
	 * cl.setCardNameAsString(handCardsList.get(i).toString()); } }
	 */
	
	//Getter & Setter
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