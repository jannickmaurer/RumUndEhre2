//Class implemented by Samuel

package claim.client.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

//Layout für Registration
public class RegistrationPane extends VBox {
	private TextField tfNewUsername = new TextField();
	private PasswordField pfNewPassword = new PasswordField();
	private Button btCreateAccount = new Button("Create Account");
	private Button btBack = new Button("Back");

	//Konstruktor
		public RegistrationPane() {
			this.getChildren().addAll(tfNewUsername, pfNewPassword, btCreateAccount, btBack);
			
			this.setAlignment(Pos.CENTER);
		}
		
		//Getter & Setter
		public TextField getTfNewUsername() {
			return tfNewUsername;
		}

		public void setTfNewUsername(TextField tfNewUsername) {
			this.tfNewUsername = tfNewUsername;
		}

		public PasswordField getPfNewPassword() {
			return pfNewPassword;
		}

		public void setPfNewPassword(PasswordField pfNewPassword) {
			this.pfNewPassword = pfNewPassword;
		}

		public Button getBtCreateAccount() {
			return btCreateAccount;
		}

		public void setBtCreateAccount(Button btCreateAccount) {
			this.btCreateAccount = btCreateAccount;
		}

		public Button getBtBack() {
			return btBack;
		}

		public void setBtBack(Button btBack) {
			this.btBack = btBack;
		}
	
}
