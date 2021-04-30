//Class implemented by Samuel

package claim.client.view;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ErrorPopupPane extends GridPane {	
	private Label lblError = new Label();
	private Button btBackError = new Button();
	
	//Konstruktor
	public ErrorPopupPane() {
		
		this.add(lblError, 0, 0);
		this.add(btBackError, 0, 1);
		
		this.setId("popupError");
		lblError.setId("textError");
		this.setAlignment(Pos.CENTER);
		this.setHalignment(btBackError, HPos.CENTER);
		
	}
	
	//Getter & Setter
	public Label getLblError() {
		return lblError;
	}

	public void setLblError(Label lblError) {
		this.lblError = lblError;
	}

	public Button getBtBackError() {
		return btBackError;
	}

	public void setBtBackError(Button btBackError) {
		this.btBackError = btBackError;
	}
	
}