//Class implemented by Samuel

package claim.client.view;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

//Layout für Server-Connection
public class ConnectPane extends GridPane {
	private Label lbPort = new Label();
	private Label lbIP = new Label();
	private TextField tfPort = new TextField("1111");
	private TextField tfIP = new TextField("127.0.0.1");
	private Button btConnect = new Button();
	private Button btStart = new Button();

	// Konstruktor
	public ConnectPane() {
		btStart.setDisable(true);
		this.add(lbPort, 0, 1);
		this.add(tfPort, 1, 1);
		this.add(lbIP, 0, 2);
		this.add(tfIP, 1, 2);
		this.add(btConnect, 0, 3);
		this.add(btStart, 1, 3);

		this.setId("connect");
		this.setAlignment(Pos.CENTER);
		this.setHalignment(lbIP, HPos.RIGHT);
		this.setHalignment(lbPort, HPos.RIGHT);
	}

	// Getter & Setter
	public Label getLbPort() {
		return lbPort;
	}

	public void setLbPort(Label lbPort) {
		this.lbPort = lbPort;
	}

	public Label getLbIP() {
		return lbIP;
	}

	public void setLbIP(Label lbIP) {
		this.lbIP = lbIP;
	}

	public TextField getTfPort() {
		return tfPort;
	}

	public void setTfPort(TextField tfPort) {
		this.tfPort = tfPort;
	}

	public TextField getTfIP() {
		return tfIP;
	}

	public void setTfIP(TextField tfIP) {
		this.tfIP = tfIP;
	}

	public Button getBtConnect() {
		return btConnect;
	}

	public void setBtConnect(Button btConnect) {
		this.btConnect = btConnect;
	}

	public Button getBtStart() {
		return btStart;
	}

	public void setBtStart(Button btStart) {
		this.btStart = btStart;
	}

}
