//Class implemented by Samuel

package claim.client.view;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import claim.commons.Card;

//Warum extends Label? (SD)
public class CardLabel extends Label {
	public String cardNameAsString;
	
	public CardLabel() {
		super();
		//Wieder entfernen sobald setCard aufgerufen werden kann
		Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("claim/image/Backside.jpg"));
		ImageView imv = new ImageView(image);
		imv.setFitWidth(80);
		imv.maxWidth(80);
		imv.setFitHeight(115);
		imv.maxHeight(115);
		imv.setPreserveRatio(true);
		this.setGraphic(imv);
		//
	}

	public void setCard(Card card) {
		//String fileName = cardToFileName(card);
		Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("/Backside.jpg"));
		ImageView imv = new ImageView(image);
		imv.setFitWidth(80);
		imv.maxWidth(80);
		imv.setFitHeight(115);
		imv.maxHeight(115);
		imv.setPreserveRatio(true);
		this.setGraphic(imv);
	}

	/*
	 * private String cardToFileName(Card card) { String rank =
	 * card.getRank().toString(); String suit = card.getSuit().toString(); return
	 * rank + "_of_" + suit + "_FR" + ".jpg"; }
	 */

	public String getCardNameAsString() {
		return cardNameAsString;
	}

	public void setCardNameAsString(String cardNameAsString) {
		this.cardNameAsString = cardNameAsString;
	}
}
