//Class implemented by Samuel

package claim.client.view;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class GamePane extends GridPane {

	// Layouts für unterschiedliche Areas
	public PlayerPane playerLayout = new PlayerPane();
	public OtherPlayerPane otherPlayerLayout = new OtherPlayerPane();
	public MiddleGamePane middleGameLayout = new MiddleGamePane();

	// Elemente aus Player Layout ansprechen
	private Label lbName = playerLayout.getLbName();
	private ArrayList cardLabels = playerLayout.getCardLabels();
	private Button btLogout = playerLayout.getBtLogout();

	// Elemente aus Middle Game Layout ansprechen
	private Label lbOpponentCard = middleGameLayout.getLbOpponentCard();
	private Label lbMyCard = middleGameLayout.getLbMyCard();
	private Label lbNewFollowerDeck = middleGameLayout.getLbNewFollowerDeck();
	private Label lbCardsDeck = middleGameLayout.getLbCardsDeck();
	private Button btNextTableCard = middleGameLayout.getBtNextTableCard();
	private Button btEvaluateWinner = middleGameLayout.getBtEvaluateWinner();
	private Button btStartRoundTwo = middleGameLayout.getBtStartRoundTwo();
	private Button btNextDuel = middleGameLayout.getBtNextDuel();

	// Elemente aus Other Player Layout ansprechen
	private Label lbOpponent = otherPlayerLayout.getLbOpponent();
	private Label lbOtherName = otherPlayerLayout.getLbName();

	private VBox vbPlayer = new VBox();

	// Konstruktor
	public GamePane() {

		this.add(otherPlayerLayout, 0, 0);
		this.add(middleGameLayout, 0, 1);
		this.add(playerLayout, 0, 2);

		GridPane.setVgrow(middleGameLayout, Priority.ALWAYS);
		this.setId("game");
		this.setAlignment(Pos.CENTER);
	}

	// Getter & Setter
	public Button getBtLogout() {
		return btLogout;
	}

	public void setBtLogout(Button btLogout) {
		this.btLogout = btLogout;
	}

	public Label getLbName() {
		return lbName;
	}

	public void setLbName(Label lbName) {
		this.lbName = lbName;
	}

	public ArrayList getCardLabels() {
		return cardLabels;
	}

	public void setCardLabels(ArrayList cardLabels) {
		this.cardLabels = cardLabels;
	}

	public PlayerPane getPlayerLayout() {
		return playerLayout;
	}

	public void setPlayerLayout(PlayerPane playerLayout) {
		this.playerLayout = playerLayout;
	}

	public MiddleGamePane getMiddleGameLayout() {
		return middleGameLayout;
	}

	public void setMiddleGameLayout(MiddleGamePane middleGameLayout) {
		this.middleGameLayout = middleGameLayout;
	}

	public Label getLbOtherName() {
		return lbOtherName;
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

	public Label getLbNewFollowerDeck() {
		return lbNewFollowerDeck;
	}

	public void setLbNewFollowerDeck(Label lbNewFollowerDeck) {
		this.lbNewFollowerDeck = lbNewFollowerDeck;
	}

	public Label getLbCardsDeck() {
		return lbCardsDeck;
	}

	public void setLbCardsDeck(Label lbCardsDeck) {
		this.lbCardsDeck = lbCardsDeck;
	}

	public Button getBtNextTableCard() {
		return btNextTableCard;
	}

	public void setBtNextTableCard(Button btNextTableCard) {
		this.btNextTableCard = btNextTableCard;
	}

	public Button getBtEvaluateWinner() {
		return btEvaluateWinner;
	}

	public void setBtEvaluateWinner(Button btEvaluateWinner) {
		this.btEvaluateWinner = btEvaluateWinner;
	}

	public void setLbOtherName(Label lbOtherName) {
		this.lbOtherName = lbOtherName;
	}

	public Button getBtStartRoundTwo() {
		return btStartRoundTwo;
	}

	public void setBtStartRoundTwo(Button btStartRoundTwo) {
		this.btStartRoundTwo = btStartRoundTwo;
	}

	public Button getBtNextDuel() {
		return btNextDuel;
	}

	public void setBtNextDuel(Button btNextDuel) {
		this.btNextDuel = btNextDuel;
	}

	public OtherPlayerPane getOtherPlayerLayout() {
		return otherPlayerLayout;
	}

	public void setOtherPlayerLayout(OtherPlayerPane otherPlayerLayout) {
		this.otherPlayerLayout = otherPlayerLayout;
	}

	public Label getLbOpponent() {
		return lbOpponent;
	}

	public void setLbOpponent(Label lbOpponent) {
		this.lbOpponent = lbOpponent;
	}
}
