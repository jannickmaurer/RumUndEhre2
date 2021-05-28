//Class implemented by Samuel

package claim.client.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

//Layout für Login
public class LoginPane extends VBox {
	private TextField tfUsername = new TextField();
	private PasswordField pfPassword = new PasswordField();
	private Button btLogin = new Button();
	private Button btRegistration = new Button();

	// Konstruktor
	public LoginPane() {
		this.getChildren().addAll(tfUsername, pfPassword, btLogin, btRegistration);

		this.setId("login");
		this.setAlignment(Pos.CENTER);
	}

	// Getter & Setter
	public TextField getTfUsername() {
		return tfUsername;
	}

	public void setTfUsername(TextField tfUsername) {
		this.tfUsername = tfUsername;
	}

	public PasswordField getPfPassword() {
		return pfPassword;
	}

	public void setPfPassword(PasswordField pfPassword) {
		this.pfPassword = pfPassword;
	}

	public Button getBtLogin() {
		return btLogin;
	}

	public void setBtLogin(Button btLogin) {
		this.btLogin = btLogin;
	}

	public Button getBtRegistration() {
		return btRegistration;
	}

	public void setBtRegistration(Button btRegistration) {
		this.btRegistration = btRegistration;
	}
}
