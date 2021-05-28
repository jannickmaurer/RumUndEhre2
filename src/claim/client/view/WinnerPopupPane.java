//Class implemented by Samuel

package claim.client.view;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class WinnerPopupPane extends GridPane {	
	private Label lblWinner = new Label();
	private Label lblWinnerName = new Label();
	private Button btLogout = new Button();
	
	//Konstruktor
	public WinnerPopupPane() {
		
		this.add(lblWinner, 0, 0);
		this.add(lblWinnerName, 1, 0);
		this.add(btLogout, 0, 1);
		
		this.setId("popupError");
		lblWinner.setId("textOver");
		lblWinnerName.setId("textOver");
		this.setAlignment(Pos.CENTER);		
	}

	
	//Getter & Setter
	public Button getBtLogout() {
		return btLogout;
	}

	public void setBtLogout(Button btLogout) {
		this.btLogout = btLogout;
	}

	public Label getLblWinner() {
		return lblWinner;
	}

	public void setLblWinner(Label lblWinner) {
		this.lblWinner = lblWinner;
	}

	public Label getLblWinnerName() {
		return lblWinnerName;
	}

	public void setLblWinnerName(Label lblWinnerName) {
		this.lblWinnerName = lblWinnerName;
	}
	
}