//Class implemented by Samuel

package claim.client.view;

import java.util.Locale;

import claim.client.model.Model;
import claim.commons.ServiceLocator;
import claim.commons.Translator;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class View {
	ServiceLocator sl = ServiceLocator.getServiceLocator();

	// Warum protected, private, public? (SD)
	protected Stage primaryStage;
	protected Stage errorStage;
	protected Stage gameOverStage;
	protected Stage winnerStage;
	private Model model;
	public BorderPane root;
	private Scene scene;

	Menu menuFileLanguage = new Menu();

	// Layouts für unterschiedliche Spielsituationen
	public ConnectPane connectLayout = new ConnectPane();
	public LoginPane loginLayout = new LoginPane();
	public RegistrationPane registrationLayout = new RegistrationPane();
	public GamePane gameLayout = new GamePane();
	public ChatPane chatLayout = new ChatPane();

	public ErrorPopupPane errorPopupLayout = new ErrorPopupPane();
	public Popup errorPopUp = new Popup();

	public GameOverPopupPane gameOverPopupLayout = new GameOverPopupPane();
	public Popup gameOverPopUp = new Popup();

	public WinnerPopupPane winnerPopupLayout = new WinnerPopupPane();
	public Popup winnerPopUp = new Popup();

	// Elemente aus Connect Layout ansprechen
	private Label lbPort = connectLayout.getLbPort();
	private Label lbIP = connectLayout.getLbIP();
	private TextField tfPort = connectLayout.getTfPort();
	private TextField tfIP = connectLayout.getTfIP();
	private Button btConnect = connectLayout.getBtConnect();
	private Button btStart = connectLayout.getBtStart();

	// Elemente aus Login Layout ansprechen
	private TextField tfUsername = loginLayout.getTfUsername();
	private PasswordField pfPassword = loginLayout.getPfPassword();
	private Button btLogin = loginLayout.getBtLogin();
	private Button btRegistration = loginLayout.getBtRegistration();

	// Elemente aus Registration Layout ansprechen
	private TextField tfNewUsername = registrationLayout.getTfNewUsername();
	private PasswordField pfnewPassword = registrationLayout.getPfNewPassword();
	private Button btCreateAccount = registrationLayout.getBtCreateAccount();
	private Button btBack = registrationLayout.getBtBack();

	// Elemente aus Game Layout ansprechen
	// private Label lbScoreDeckP1 = gameLayout.getLbScoreDeckP1();
	// private Label lbFollowerDeckP1 = gameLayout.getLbFollowerDeckP1();
	private Label lbOpponent = gameLayout.getLbOpponent();
	private Label lbOpponentCard = gameLayout.getLbOpponentCard();
	private Label lbMyCard = gameLayout.getLbMyCard();
	private Label lbNewFollowerDeck = gameLayout.getLbNewFollowerDeck();
	private Label lbCardsDeck = gameLayout.getLbCardsDeck();
	// private Label lbScoreDeckP2 = gameLayout.getLbScoreDeckP2();
	// private Label lbFollowerDeckP2 = gameLayout.getLbFollowerDeckP2();
	private Button btLogout = gameLayout.getBtLogout();
	private Button btEvaluateWinner = gameLayout.getBtEvaluateWinner();
	private Button btStartRoundTwo = gameLayout.getBtStartRoundTwo();
	private Button btNextDuel = gameLayout.getBtNextDuel();

	// Elemente aus Chat Layout ansprechen
	private Button btSend = chatLayout.getBtnSend();
	private Label lbEvaluation = chatLayout.getLbEvaluation();
	private Label lbGoblins = chatLayout.getLbGoblins();
	private Label wonGoblins = chatLayout.getWonGoblins();
	private Label lbDwarfs = chatLayout.getLbDwarfs();
	private Label wonDwarfs = chatLayout.getWonDwarfs();
	private Label lbUndeads = chatLayout.getLbUndeads();
	private Label wonUndeads = chatLayout.getWonUndeads();
	private Label lbDoubles = chatLayout.getLbDoubles();
	private Label wonDoubles = chatLayout.getWonDoubles();
	private Label lbKnights = chatLayout.getLbKnights();
	private Label wonKnights = chatLayout.getWonKnights();

	// Elemente aus Error Layout ansprechen
	private Label lblError = errorPopupLayout.getLblError();
	private Button btnBackError = errorPopupLayout.getBtBackError();

	// Elemente aus GameOver Layout ansprechen
	private Label lblOver = gameOverPopupLayout.getLblOver();
	private Button btLogoutGameOver = gameOverPopupLayout.getBtLogout();

	// Elemente aus Winner Layout ansprechen
	private Label lblWinner = winnerPopupLayout.getLblWinner();
	private Label lblWinnerName = winnerPopupLayout.getLblWinnerName();
	private Button btLogoutWinner = winnerPopupLayout.getBtLogout();

	public View(Stage primaryStage, Model model) {
		this.primaryStage = primaryStage;
		this.model = model;

		root = new BorderPane();
		root.setId("root");

		for (Locale locale : sl.getLocales()) {
			MenuItem language = new MenuItem(locale.getLanguage());
			menuFileLanguage.getItems().add(language);
			language.setOnAction(event -> {
				sl.getConfiguration().setLocalOption("Language", locale.getLanguage());
				sl.setTranslator(new Translator(locale.getLanguage()));
				updateTexts();
			});
		}

		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(menuFileLanguage);

		// Buttons Send, Login und CreateAccount erst verfügbar wenn beide Textfelder
		// Eingaben haben
		btLogin.disableProperty().bind(tfUsername.textProperty().isEmpty().or(pfPassword.textProperty().isEmpty()));
		btCreateAccount.disableProperty()
				.bind(tfNewUsername.textProperty().isEmpty().or(pfnewPassword.textProperty().isEmpty()));
		chatLayout.getBtnSend().disableProperty().bind(chatLayout.getTfMessage().textProperty().isEmpty());

		// Error Popups bereitstellen
		errorPopUp.getContent().add(errorPopupLayout);
		errorPopUp.setAutoHide(false);

		gameOverPopUp.getContent().add(gameOverPopupLayout);
		gameOverPopUp.setAutoHide(false);

		winnerPopUp.getContent().add(winnerPopupLayout);
		winnerPopUp.setAutoHide(false);

		chatLayout.setPrefWidth(300);
		chatLayout.getBtnSend().setPrefWidth(300);
		chatLayout.getTfMessage().setPrefWidth(300);

		root.setCenter(connectLayout);
		root.setTop(menuBar);

		scene = new Scene(root, 1100, 733);
		scene.getStylesheets().add(getClass().getResource("Client.css").toExternalForm());

		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		primaryStage.setTitle("Connect");
	}

	public void start() {
		primaryStage.show();
		updateTexts();
	}

	// Texte anpassen an die ausgewählte Sprache
	protected void updateTexts() {
		Translator t = ServiceLocator.getServiceLocator().getTranslator();

		// Menu
		this.menuFileLanguage.setText(t.getString("program.menu.file.language"));

		// Text
		connectLayout.getLbIP().setText(t.getString("label.IPAdress"));
		connectLayout.getLbPort().setText(t.getString("label.port"));
		loginLayout.getTfUsername().setPromptText(t.getString("label.username"));
		loginLayout.getPfPassword().setPromptText(t.getString("label.password"));
		registrationLayout.getTfNewUsername().setPromptText(t.getString("label.username"));
		registrationLayout.getPfNewPassword().setPromptText(t.getString("label.password"));
		errorPopupLayout.getLblError().setText(t.getString("label.error"));
		gameOverPopupLayout.getLblOver().setText(t.getString("label.over"));
		winnerPopupLayout.getLblWinner().setText(t.getString("label.winner"));
		// gameLayout.getLbScoreDeckP1().setText(t.getString("label.scoredeck"));
		// gameLayout.getLbFollowerDeckP1().setText(t.getString("label.followerdeck"));
		gameLayout.getLbOpponent().setText(t.getString("label.opponent"));
		gameLayout.getLbOpponentCard().setText(t.getString("label.opponentcard"));
		gameLayout.getLbMyCard().setText(t.getString("label.mycard"));
		gameLayout.getLbNewFollowerDeck().setText(t.getString("label.newfollowerdeck"));
		gameLayout.getLbCardsDeck().setText(t.getString("label.cardsdeck"));
		// gameLayout.getLbScoreDeckP2().setText(t.getString("label.scoredeck"));
		// gameLayout.getLbFollowerDeckP2().setText(t.getString("label.followerdeck"));
		chatLayout.getLbEvaluation().setText(t.getString("label.gameevaluation"));
		chatLayout.getLbGoblins().setText(t.getString("label.goblins"));
		chatLayout.getLbDwarfs().setText(t.getString("label.dwarfs"));
		chatLayout.getLbUndeads().setText(t.getString("label.undeads"));
		chatLayout.getLbDoubles().setText(t.getString("label.doubles"));
		chatLayout.getLbKnights().setText(t.getString("label.knights"));

		// Other controls
		connectLayout.getBtConnect().setText(t.getString("button.run"));
		connectLayout.getBtStart().setText(t.getString("button.start"));
		loginLayout.getBtLogin().setText(t.getString("button.login"));
		loginLayout.getBtRegistration().setText(t.getString("button.registration"));
		registrationLayout.getBtCreateAccount().setText(t.getString("button.registration"));
		registrationLayout.getBtBack().setText(t.getString("button.back"));
		gameLayout.getBtLogout().setText(t.getString("button.logout"));
		gameLayout.getBtEvaluateWinner().setText(t.getString("button.evaluatewinner"));
		gameLayout.getBtStartRoundTwo().setText(t.getString("button.roundtwo"));
		gameLayout.getBtNextTableCard().setText(t.getString("button.next"));
		gameLayout.getBtNextDuel().setText(t.getString("button.nextduel"));
		chatLayout.getBtnSend().setText(t.getString("button.send"));
		errorPopupLayout.getBtBackError().setText(t.getString("button.back"));
		gameOverPopupLayout.getBtLogout().setText(t.getString("button.logout"));
		winnerPopupLayout.getBtLogout().setText(t.getString("button.logout"));
	}

	public void showErrorPopUp() {
		BorderPane newroot = new BorderPane();
		newroot.setCenter(errorPopupLayout);
		Scene newScene = new Scene(newroot, 700, 333);
		newScene.getStylesheets().add(getClass().getResource("Client.css").toExternalForm());

		errorStage = new Stage();
		errorStage.initModality(Modality.WINDOW_MODAL);
		errorStage.initStyle(StageStyle.UNDECORATED);
		errorStage.initOwner(primaryStage);
		errorStage.setScene(newScene);
		errorStage.show();
	}

	public void showGameOverPopUp() {
		BorderPane newroot = new BorderPane();
		newroot.setCenter(gameOverPopupLayout);
		Scene newScene = new Scene(newroot, 700, 333);
		newScene.getStylesheets().add(getClass().getResource("Client.css").toExternalForm());

		gameOverStage = new Stage();
		gameOverStage.initModality(Modality.WINDOW_MODAL);
		gameOverStage.initStyle(StageStyle.UNDECORATED);
		gameOverStage.initOwner(primaryStage);
		gameOverStage.setScene(newScene);
		gameOverStage.show();
	}

	public void showWinnerPopUp() {
		BorderPane newroot = new BorderPane();
		newroot.setCenter(winnerPopupLayout);
		Scene newScene = new Scene(newroot, 700, 333);
		newScene.getStylesheets().add(getClass().getResource("Client.css").toExternalForm());

		winnerStage = new Stage();
		winnerStage.initModality(Modality.WINDOW_MODAL);
		winnerStage.initStyle(StageStyle.UNDECORATED);
		winnerStage.initOwner(primaryStage);
		winnerStage.setScene(newScene);
		winnerStage.show();
	}

	// Getter & Setter
	public Stage getStage() {
		return primaryStage;
	}

	public Stage getErrorStage() {
		return errorStage;
	}

	public void setErrorStage(Stage errorStage) {
		this.errorStage = errorStage;
	}

	public Stage getGameOverStage() {
		return gameOverStage;
	}

	public void setGameOverStage(Stage gameOverStage) {
		this.gameOverStage = gameOverStage;
	}

	public Stage getWinnerStage() {
		return winnerStage;
	}

	public void setWinnerStage(Stage winnerStage) {
		this.winnerStage = winnerStage;
	}

	public Label getLblWinner() {
		return lblWinner;
	}

	public void setLblWinner(Label lblWinner) {
		this.lblWinner = lblWinner;
	}

	public Button getBtLogoutWinner() {
		return btLogoutWinner;
	}

	public void setBtLogoutWinner(Button btLogoutWinner) {
		this.btLogoutWinner = btLogoutWinner;
	}

	public Label getLblOver() {
		return lblOver;
	}

	public void setLblOver(Label lblOver) {
		this.lblOver = lblOver;
	}

	public Button getBtLogoutGameOver() {
		return btLogoutGameOver;
	}

	public void setBtLogoutGameOver(Button btLogoutGameOver) {
		this.btLogoutGameOver = btLogoutGameOver;
	}

	public BorderPane getRoot() {
		return root;
	}

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

	public Button getBtStart() {
		return btStart;
	}

	public void setBtStart(Button btStart) {
		this.btStart = btStart;
	}

	public Button getBtConnect() {
		return this.btConnect;
	}

	public void setBtConnect(Button btConnect) {
		this.btConnect = btConnect;
	}

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

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public void setBtRegistration(Button btRegistration) {
		this.btRegistration = btRegistration;
	}

	public TextField getTfNewUsername() {
		return tfNewUsername;
	}

	public void setTfNewUsername(TextField tfNewUsername) {
		this.tfNewUsername = tfNewUsername;
	}

	public PasswordField getPfnewPassword() {
		return pfnewPassword;
	}

	public void setPfnewPassword(PasswordField pfnewPassword) {
		this.pfnewPassword = pfnewPassword;
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

	public Button getBtLogout() {
		return btLogout;
	}

	public void setBtLogout(Button btLogout) {
		this.btLogout = btLogout;
	}

	public Label getLblError() {
		return lblError;
	}

	public void setLblError(Label lblError) {
		this.lblError = lblError;
	}

	public Button getBtnBackError() {
		return btnBackError;
	}

	public void setBtnBackError(Button btnBackError) {
		this.btnBackError = btnBackError;
	}

	public GamePane getGameLayout() {
		return gameLayout;
	}

	public void setGameLayout(GamePane gameLayout) {
		this.gameLayout = gameLayout;
	}

	public Label getLbOpponentCard() {
		return lbOpponentCard;
	}

	public void setLbOpponentCard(Label lbOpponentCard) {
		this.lbOpponentCard = lbOpponentCard;
	}

	public Label getLbMyCard() {
		return lbMyCard;
	}

	public void setLbMyCard(Label lbMyCard) {
		this.lbMyCard = lbMyCard;
	}

	public Label getLbNewFollowerDeck() {
		return lbNewFollowerDeck;
	}

	public void setLbNewFollowerDeck(Label lbNewFollowerDeck) {
		this.lbNewFollowerDeck = lbNewFollowerDeck;
	}

	public Label getLbCardsDeck() {
		return lbCardsDeck;
	}

	public void setLbCardsDeck(Label lbCardsDeck) {
		this.lbCardsDeck = lbCardsDeck;
	}

	public Button getBtEvaluateWinner() {
		return btEvaluateWinner;
	}

	public void setBtEvaluateWinner(Button btEvaluateWinner) {
		this.btEvaluateWinner = btEvaluateWinner;
	}

	public Label getLblWinnerName() {
		return lblWinnerName;
	}

	public void setLblWinnerName(Label lblWinnerName) {
		this.lblWinnerName = lblWinnerName;
	}

	public Button getBtStartRoundTwo() {
		return btStartRoundTwo;
	}

	public void setBtStartRoundTwo(Button btStartRoundTwo) {
		this.btStartRoundTwo = btStartRoundTwo;
	}

	public Button getBtNextDuel() {
		return btNextDuel;
	}

	public void setBtNextDuel(Button btNextDuel) {
		this.btNextDuel = btNextDuel;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public ChatPane getChatLayout() {
		return chatLayout;
	}

	public void setChatLayout(ChatPane chatLayout) {
		this.chatLayout = chatLayout;
	}

	public Label getLbEvaluation() {
		return lbEvaluation;
	}

	public void setLbEvaluation(Label lbEvaluation) {
		this.lbEvaluation = lbEvaluation;
	}

	public Label getLbGoblins() {
		return lbGoblins;
	}

	public void setLbGoblins(Label lbGoblins) {
		this.lbGoblins = lbGoblins;
	}

	public Label getWonGoblins() {
		return wonGoblins;
	}

	public void setWonGoblins(Label wonGoblins) {
		this.wonGoblins = wonGoblins;
	}

	public Label getLbDwarfs() {
		return lbDwarfs;
	}

	public void setLbDwarfs(Label lbDwarfs) {
		this.lbDwarfs = lbDwarfs;
	}

	public Label getWonDwarfs() {
		return wonDwarfs;
	}

	public void setWonDwarfs(Label wonDwarfs) {
		this.wonDwarfs = wonDwarfs;
	}

	public Label getLbUndeads() {
		return lbUndeads;
	}

	public void setLbUndeads(Label lbUndeads) {
		this.lbUndeads = lbUndeads;
	}

	public Label getWonUndeads() {
		return wonUndeads;
	}

	public void setWonUndeads(Label wonUndeads) {
		this.wonUndeads = wonUndeads;
	}

	public Label getLbDoubles() {
		return lbDoubles;
	}

	public void setLbDoubles(Label lbDoubles) {
		this.lbDoubles = lbDoubles;
	}

	public Label getWonDoubles() {
		return wonDoubles;
	}

	public void setWonDoubles(Label wonDoubles) {
		this.wonDoubles = wonDoubles;
	}

	public Label getLbKnights() {
		return lbKnights;
	}

	public void setLbKnights(Label lbKnights) {
		this.lbKnights = lbKnights;
	}

	public Label getWonKnights() {
		return wonKnights;
	}

	public void setWonKnights(Label wonKnights) {
		this.wonKnights = wonKnights;
	}

	public ErrorPopupPane getErrorPopupLayout() {
		return errorPopupLayout;
	}

	public void setErrorPopupLayout(ErrorPopupPane errorPopupLayout) {
		this.errorPopupLayout = errorPopupLayout;
	}

}
