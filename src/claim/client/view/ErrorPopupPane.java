//Class implemented by Samuel

package claim.client.view;

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
		VBox v1 = new VBox();
		v1.setId("VBox");
		v1.getChildren().addAll(lblError, btBackError);
		this.add(v1, 0, 0);
		
		this.setAlignment(Pos.TOP_CENTER);
		this.setHgap(20);
		this.setVgap(10);
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