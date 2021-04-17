//Class implemented by Samuel

package claim.client.view;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GamePane extends BorderPane {
	private HBox hboxMiddleCards = new HBox();
	private CardLabel clCardsDeck = new CardLabel();
	private CardLabel clNewFollowerDeck = new CardLabel();
	
	//Layouts für unterschiedliche Areas
	public PlayerPane playerLayout = new PlayerPane();
	public OtherPlayerPane otherPlayerLayout = new OtherPlayerPane();
	
	//Elemente aus Player Layout ansprechen
	private Label lbName = playerLayout.getLbName();
	private Label lbPoints = playerLayout.getLbPoints();
	private Label lbPointsPlayer = playerLayout.getLbPointsPlayer();
	private ArrayList cardLabels = playerLayout.getCardLabels();
	
	//Elemente aus Other Player Layout ansprechen
	private Label lbOtherName = otherPlayerLayout.getLbName();
	private Label lbOtherPoints = otherPlayerLayout.getLbPoints();
	private Label lbOtherPointsPlayer = otherPlayerLayout.getLbPointsPlayer();
	
	private Button btLogout = new Button();
	private VBox vbPlayer = new VBox();
	
	//Konstruktor
	public GamePane() {
		
		VBox vboxCardsDeck = new VBox();
		Label lbCardsDeck = new Label("Cards Deck");
		vboxCardsDeck.getChildren().addAll(clCardsDeck, lbCardsDeck);
		
		VBox vboxNewFollowerDeck = new VBox();
		Label lbNewFollowerDeck = new Label("New Follower Deck");
		vboxNewFollowerDeck.getChildren().addAll(clNewFollowerDeck, lbNewFollowerDeck);
		
		hboxMiddleCards.getChildren().addAll(vboxCardsDeck, vboxNewFollowerDeck);
		hboxMiddleCards.setSpacing(10);
		vbPlayer.getChildren().addAll(playerLayout, btLogout);
			
		this.setTop(otherPlayerLayout);
		this.setCenter(hboxMiddleCards);
		this.setBottom(vbPlayer);
	}
		
	//Getter & Setter
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


	public ArrayList getCardLabels() {
		return cardLabels;
	}


	public void setCardLabels(ArrayList cardLabels) {
		this.cardLabels = cardLabels;
	}
	
}
