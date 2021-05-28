//Class implemented by Samuel

package claim.client.view;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class GameOverPopupPane extends GridPane {	
	private Label lblOver = new Label();
	private Button btLogout = new Button();
	
	//Konstruktor
	public GameOverPopupPane() {
		
		this.add(lblOver, 0, 0);
		this.add(btLogout, 0, 1);
		
		this.setId("popupError");
		lblOver.setId("textOver");
		this.setAlignment(Pos.CENTER);		
	}

	
	//Getter & Setter

	public Label getLblOver() {
		return lblOver;
	}

	public void setLblOver(Label lblOver) {
		this.lblOver = lblOver;
	}

	public Button getBtLogout() {
		return btLogout;
	}

	public void setBtLogout(Button btLogout) {
		this.btLogout = btLogout;
	}
	
}