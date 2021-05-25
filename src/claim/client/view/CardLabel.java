//Class implemented by Samuel

package claim.client.view;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import claim.commons.Card;

//Warum extends Label? (SD)
public class CardLabel extends Label {
	public String cardNameAsString;
	public ImageView imv1;
	public ImageView imv2;
	public ImageView imv3;
	public ImageView imv4;
	
	//Warum super?
	public CardLabel() {
		super();
	}
	
	//Aussehen des Decks definieren
	public void setDeck() {
		Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("claim/image/Backside.jpg"));
		imv1 = new ImageView(image);
		imv1.setFitWidth(70);
		imv1.maxWidth(80);
		imv1.minWidth(70);
		imv1.setFitHeight(100);
		imv1.maxHeight(115);
		imv1.minHeight(100);
		imv1.setPreserveRatio(true);
		this.setGraphic(imv1);
	}
	
	public void setTestDeck() {
		Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("claim/image/Backside.jpg"));
		imv4 = new ImageView(image);
		imv4.setPreserveRatio(true);
		this.setGraphic(imv4);
	}
	
	//Karte in der Mitte des Spielfelds
	public void setCard(String card) {
		Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("claim/image/" + card + ".jpg"));
		ImageView imv = new ImageView(image);
		imv.setFitWidth(70);
		imv.maxWidth(80);
		imv.minWidth(70);
		imv.setFitHeight(100);
		imv.maxHeight(115);
		imv.minHeight(100);
		imv.setPreserveRatio(true);
		this.setGraphic(imv);
	}
	
	//Aussehen der Karte definieren
	public void setCard(Card card) {
		String fileName = cardToFileName(card);
		Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("claim/image/" + fileName));
		ImageView imv = new ImageView(image);
		imv.setFitWidth(70);
		imv.maxWidth(80);
		imv.minWidth(70);
		imv.setFitHeight(100);
		imv.maxHeight(115);
		imv.minHeight(100);
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

	public ImageView getImv1() {
		return imv1;
	}

	public void setImv1(ImageView imv1) {
		this.imv1 = imv1;
	}

	public ImageView getImv2() {
		return imv2;
	}

	public void setImv2(ImageView imv2) {
		this.imv2 = imv2;
	}

	public ImageView getImv3() {
		return imv3;
	}

	public void setImv3(ImageView imv3) {
		this.imv3 = imv3;
	}

	public ImageView getImv4() {
		return imv4;
	}

	public void setImv4(ImageView imv4) {
		this.imv4 = imv4;
	}
}
