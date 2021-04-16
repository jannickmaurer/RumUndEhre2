//Class implemented by Samuel

package claim.client.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class GamePane extends BorderPane {
	private Label lbArea1 = new Label("AREA1");
	private Label lbArea2 = new Label("AREA2");
	private Label lbArea3 = new Label("AREA3");
	private Button btLogout = new Button();
	private VBox vbTest = new VBox();
	
	//Konstruktor
		public GamePane() {
			vbTest.getChildren().addAll(lbArea3, btLogout);
			
			this.setTop(lbArea1);
			this.setCenter(lbArea2);
			this.setBottom(vbTest);
		}
		

		//Getter & Setter
		public Button getBtLogout() {
			return btLogout;
		}

		public void setBtLogout(Button btLogout) {
			this.btLogout = btLogout;
		}
		
}
