//Class implemented by Samuel

package claim.client.view;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import claim.commons.Card;

//Warum extends Label? (SD)
public class CardLabel extends Label {
	public String cardNameAsString;
	
	//Warum super?
	public CardLabel() {
		super();
	}
	
	//Aussehen des Decks definieren
	public void setDeck() {
		Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("claim/image/Backside.jpg"));
		ImageView imv = new ImageView(image);
		imv.setFitWidth(80);
		imv.maxWidth(80);
		imv.setFitHeight(115);
		imv.maxHeight(115);
		imv.setPreserveRatio(true);
		this.setGraphic(imv);
	}
	
	//ZUM TESTEN SD
	public void setCard() {
		Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("claim/image/undead_2.jpg"));
		ImageView imv = new ImageView(image);
		imv.setFitWidth(80);
		imv.maxWidth(80);
		imv.setFitHeight(115);
		imv.maxHeight(115);
		imv.setPreserveRatio(true);
		this.setGraphic(imv);
	}
	
	//Aussehen der Karte definieren
	public void setCard(Card card) {
		String fileName = cardToFileName(card);
		Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("claim/image/" + fileName));
		ImageView imv = new ImageView(image);
		imv.setFitWidth(80);
		imv.maxWidth(80);
		imv.setFitHeight(115);
		imv.maxHeight(115);
		imv.setPreserveRatio(true);
		this.setGraphic(imv);
	}
	
	private String cardToFileName(Card card) {
		String rank = card.getRank().toString();
		String suit = card.getSuit().toString();
		return suit + "_" + rank + ".jpg";
	}

	public String getCardNameAsString() {
		return cardNameAsString;
	}

	public void setCardNameAsString(String cardNameAsString) {
		this.cardNameAsString = cardNameAsString;
	}
}
