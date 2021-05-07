package claim.client.controller;

import java.util.ArrayList;
import java.util.logging.Logger;

import claim.client.model.Board;
import claim.client.model.Model;
import claim.client.view.View;
import claim.commons.Card;
import claim.commons.ServiceLocator;
import claim.commons.messages.Message;
import claim.commons.messages.results.ResultBroadcastFinishRound;
import claim.commons.messages.results.ResultBroadcastJoinPlayroom;
import claim.commons.messages.results.ResultBroadcastStartRoundOne;
import claim.commons.messages.results.ResultCreateAccount;
import claim.commons.messages.results.ResultDealCards;
import claim.commons.messages.results.ResultDeleteAccount;
import claim.commons.messages.results.ResultGetNextTableCard;
import claim.commons.messages.results.ResultLogin;
import claim.commons.messages.results.ResultLogout;
import claim.commons.messages.results.ResultPing;
import claim.commons.messages.results.ResultPlayCard;
import claim.commons.messages.results.ResultSendCard;
import claim.client.view.PlayerPane;
import claim.client.view.CardLabel;
import javafx.application.Platform;
import javafx.event.Event;

// Created by Samuel & Jannick
public class Controller {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getServerLogger();
	private Model model;
	private View view;
	private String username;
	private Board board;

	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
		board = new Board();
		
		view.getBtConnect().setOnAction(event -> connect());
		
		view.getBtStart().setOnAction(e -> {
			view.getRoot().setCenter(view.loginLayout);
			view.getStage().setTitle("Login");
		});
		
		view.getBtLogin().setOnAction(event -> {
			login();
		});
		
		view.getBtRegistration().setOnAction(e -> {
			view.getRoot().setCenter(view.registrationLayout);
			view.getStage().setTitle("Registration");
		});
		
		view.getBtCreateAccount().setOnAction(event -> {
			createAccount();
		});
		
		view.getBtBack().setOnAction(e -> {
			view.getRoot().setCenter(view.loginLayout);
			view.getStage().setTitle("Login");
			view.getTfNewUsername().setText("");
			view.getPfnewPassword().setText("");
		});
		
		view.getBtLogout().setOnAction(e -> {
			logout();
		});
		
		view.getBtnBackError().setOnAction(e -> {
			view.errorPopUp.hide();
		});
		
		//SD
		for (int i = 0; i < view.getGameLayout().getPlayerLayout().getCardLabels().size(); i++) {
			view.getGameLayout().getPlayerLayout().getCardLabels().get(i).setOnMouseReleased(this::sendTableCard);
		}
		
		//SD
		for (int i = 0; i < view.getGameLayout().getPlayerLayout().getCardLabels().size(); i++) {
			view.getGameLayout().getPlayerLayout().getCardLabels().get(i).setOnMouseEntered(this::highlightCard);
		}
		
		//SD
		for (int i = 0; i < view.getGameLayout().getPlayerLayout().getCardLabels().size(); i++) {
			view.getGameLayout().getPlayerLayout().getCardLabels().get(i).setOnMouseExited(this::delightCard);
		}
		
		// When Model receives a new Message, the Value of the SimpleString Property "LastReceivedMessage" Changes
		// This Method looks at this change and creates the respective Message Object
		model.getLastReceivedMessage().addListener((o, oldValue, newValue) -> {
			if (!newValue.equals("")) {
				// Takes the String the Model received from the Server and puts it into a String Array
				String[] content = newValue.split("\\|");
				for (int i = 0; i < content.length; i++) {
					content[i] = content[i].trim();
				}
				// Creates a new Message with the String Array
				createMessage(content);
				// Empties the Value of the StringProperty
				model.getLastReceivedMessage().setValue("");
			}
		});
	}

	// Does the same thing as the Message Class on Server's Side
	// It is done here, because we want to trigger actions on the client's GUI
	private void createMessage(String[] content) {
		Message msg;
		if (content[0].equals("ResultPing")) { msg = new ResultPing(content);
			if (!msg.isFalse()) msg.process(Controller.this);
			if (msg.isFalse()) msg.processIfFalse(Controller.this);
		}
		if (content[0].equals("ResultCreateAccount")) { msg = new ResultCreateAccount(content);
			if (!msg.isFalse()) msg.process(Controller.this);
			if (msg.isFalse()) msg.processIfFalse(Controller.this);
		}
		if (content[0].equals("ResultLogin")) { msg = new ResultLogin(content);
			if (!msg.isFalse()) msg.process(Controller.this);
			if (msg.isFalse()) msg.processIfFalse(Controller.this);
		}	
		if (content[0].equals("ResultLogout")) { msg = new ResultLogout(content);
			if (!msg.isFalse()) msg.process(Controller.this);
			if (msg.isFalse()) msg.processIfFalse(Controller.this);
		}	
		if (content[0].equals("ResultDeleteAccount")) { msg = new ResultDeleteAccount(content);
			if (!msg.isFalse()) msg.process(Controller.this);
			if (msg.isFalse()) msg.processIfFalse(Controller.this);
		}
		if (content[0].equals("ResultBroadcastJoinPlayroom")) { msg = new ResultBroadcastJoinPlayroom(content);
			if (!msg.isFalse()) msg.process(Controller.this);
			if (msg.isFalse()) msg.processIfFalse(Controller.this);
		}		
		if (content[0].equals("ResultBroadcastStartRoundOne")) { msg = new ResultBroadcastStartRoundOne(content);
			if (!msg.isFalse()) msg.process(Controller.this);
			if (msg.isFalse()) msg.processIfFalse(Controller.this);
		}	
		if (content[0].equals("ResultDealCards")) { msg = new ResultDealCards(content);
		if (!msg.isFalse()) msg.process(Controller.this);
		if (msg.isFalse()) msg.processIfFalse(Controller.this);
	}	
		if (content[0].equals("ResultPlayCard")) { msg = new ResultPlayCard(content);
		if (!msg.isFalse()) msg.process(Controller.this);
		if (msg.isFalse()) msg.processIfFalse(Controller.this);
	}	
		if (content[0].equals("ResultSendCard")) { msg = new ResultSendCard(content);
		if (!msg.isFalse()) msg.process(Controller.this);
		if (msg.isFalse()) msg.processIfFalse(Controller.this);
	}		
		if (content[0].equals("ResultBroadcastFinishRound")) { msg = new ResultBroadcastFinishRound(content);
		if (!msg.isFalse()) msg.process(Controller.this);
		if (msg.isFalse()) msg.processIfFalse(Controller.this);
	}	
		if (content[0].equals("ResultGetNextTableCard")) { msg = new ResultGetNextTableCard(content);
		if (!msg.isFalse()) msg.process(Controller.this);
		if (msg.isFalse()) msg.processIfFalse(Controller.this);
	}	
		
		
		
		
		
	}

	// Methods for triggering Methods in Model by clicking a Button in View, get Values from User Input
	private void connect() {
		try {
			this.model.connect(view.getTfIP().getText(), Integer.parseInt(view.getTfPort().getText()));
		} catch (Exception e) {
			logger.warning("Server Down");
		}
	}
	
	private void createAccount() {
		model.createAccount(view.getTfNewUsername().getText(), view.getPfnewPassword().getText());
	}
	
	public void login() {
		model.login(view.getTfUsername().getText(), view.getPfPassword().getText());
	}
	
	public void autoLogin() {
		model.login(view.getTfNewUsername().getText(), view.getPfnewPassword().getText());
		view.getTfNewUsername().setText("");
		view.getPfnewPassword().setText("");
	}
	
	public void logout() {
		model.logout();
	}
	
	public void deleteAccount() {
		model.deleteAccount();
	}
	
	public void startRoundOne() {
		model.startRoundOne();
	}

	
	public void playCard() {
		model.playCard("knight_1");
	}
	public void getNextTableCard() {
		model.getNextTableCard();
	}

	
	// Messages Success Handling
	public void loginSuccess() {
		//Runnable um dynamische Anzeige zu gewährleisten
		Platform.runLater(new Runnable() {
			public void run() {
				view.getRoot().setCenter(view.gameLayout);
				view.getStage().setTitle("Game");
				view.getTfUsername().setText("");
				view.getPfPassword().setText("");
			}
		});
		logger.info("Login Successful");
	}
	public void logoutSuccess() {
		Platform.runLater(new Runnable() {
			public void run() {
				view.getRoot().setCenter(view.loginLayout);
				view.getStage().setTitle("Login");
			}
		});
		logger.info("Logout Successful");
	}
	
	public void somethingFailed() {
		Platform.runLater(new Runnable() {
			public void run() {
				view.errorPopUp.show(view.getStage());
			}
		});
	}
	
	//SD - Karten zu Beginn des Spiels austeilen
	public void deal(ArrayList<Card> handCards) {
		Platform.runLater(new Runnable() {
			public void run() {
				for (int i = 0; i < handCards.size(); i++) {
					Card card = null;
					//Was macht diese Zeile? -SD
					CardLabel cl = (CardLabel) view.getGameLayout().getPlayerLayout().getHboxCards().getChildren().get(i);
					cl.setCard(handCards.get(i));
					cl.setCardNameAsString(handCards.get(i).toString());
				}
			}
		});
	}
	
	//SD
	private void sendTableCard(Event event) {
		CardLabel cl = (CardLabel) event.getSource();
		model.playCard(cl.getCardNameAsString());
	}
	
	//SD - Karten entfernen
	public void updatePlayerPane(String playedCard) {
		Platform.runLater(new Runnable() {
			public void run() {
				CardLabel clToRemove = new CardLabel();
				for(CardLabel cl : view.getGameLayout().getPlayerLayout().getCardLabels()) {
					if(cl.getCardNameAsString().equals(playedCard)) {
						clToRemove = cl;
					}
				}
				view.getGameLayout().getPlayerLayout().getCardLabels().remove(clToRemove);
				view.getGameLayout().getPlayerLayout().getHboxCards().getChildren().remove(clToRemove);
				
				updateGameDisplay(playedCard);
			}
		});
	}
	
	//SD - Gespielte Karten in der Mitte anzeigen
	public void updateGameDisplay(String playedCard) {
		//view.getGameLayout().getMiddleGameLayout().getPlayedCards().getChildren().clear();
		
		CardLabel cl1 = new CardLabel();
		cl1.setCard(playedCard);
		view.getGameLayout().getMiddleGameLayout().getPlayedCards().add(cl1, 0, 2);
	}
	
	public void otherPlayerCard(String card) {
		Platform.runLater(new Runnable() {
			public void run() {
				CardLabel cl2 = new CardLabel();
				cl2.setCard(card);
				view.getGameLayout().getMiddleGameLayout().getPlayedCards().add(cl2, 0, 1);
			}
		});
	}
	
	public void tableCard(String card) {
		Platform.runLater(new Runnable() {
			public void run() {
				CardLabel cl3 = new CardLabel();
				cl3.setCard(card);
				view.getGameLayout().getMiddleGameLayout().getVboxCardsDeck().getChildren().add(cl3);
			}
		});
	}
	
	//SD
	public void highlightCard(Event event) {
		CardLabel cl = (CardLabel) event.getSource();
		cl.setStyle("-fx-effect: dropshadow(three-pass-box, black, 3, 0.2, 0, 0);");
	}
	
	//SD
	public void delightCard(Event event) {
		CardLabel cl = (CardLabel) event.getSource();
		cl.setStyle("-fx-effect: dropshadow(three-pass-box, black, 0, 0, 0, 0);");
	}

	// Getter & Setter
	public Model getModel() {
		return model;
	}
	public View getView() {
		return view;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public String getUsername() {
		return username;
	}

}
