package claim.client.controller;

import java.util.ArrayList;
import java.util.logging.Logger;

import claim.client.model.Board;
import claim.client.model.Model;
import claim.client.view.View;
import claim.commons.Card;
import claim.commons.ServiceLocator;
import claim.commons.messages.Message;
import claim.commons.messages.results.ResultBroadcastEvaluateWinner;
import claim.commons.messages.results.ResultBroadcastFinishRound;
import claim.commons.messages.results.ResultBroadcastJoinPlayroom;
import claim.commons.messages.results.ResultBroadcastSendMessage;
import claim.commons.messages.results.ResultCreateAccount;
import claim.commons.messages.results.ResultDealCards;
import claim.commons.messages.results.ResultDeleteAccount;
import claim.commons.messages.results.ResultEvaluateWinner;
import claim.commons.messages.results.ResultGetNextTableCard;
import claim.commons.messages.results.ResultLogin;
import claim.commons.messages.results.ResultLogout;
import claim.commons.messages.results.ResultPing;
import claim.commons.messages.results.ResultPlayCard;
import claim.commons.messages.results.ResultPlayerLoggedOut;
import claim.commons.messages.results.ResultSendCard;
import claim.commons.messages.results.ResultSendMessage;
import claim.commons.messages.results.ResultStartSecondRound;
import claim.client.view.PlayerPane;
import claim.client.view.CardLabel;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.Event;

// Created by Samuel & Jannick
public class Controller {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getServerLogger();
	private Model model;
	private View view;
	private String username = null;
	private Board board;
	private Boolean onTurn = false;
	private Boolean cardPlayed = false;
	private Boolean readyForSecondRound = false;
	private SimpleIntegerProperty playedRounds = new SimpleIntegerProperty();
	private Boolean secondRoundStarted = false;

	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
		board = new Board();
		this.playedRounds.set(0);

		// Set event handler on buttons

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

		view.getBtLogoutGameOver().setOnAction(e -> {
			logout();
			view.gameOverPopUp.hide();
		});

		view.getBtLogoutWinner().setOnAction(e -> {
			logout();
			view.winnerPopUp.hide();
		});

		view.getGameLayout().getMiddleGameLayout().getBtNextTableCard().setOnAction(e -> {
			this.getNextTableCard();
		});

		view.getGameLayout().getMiddleGameLayout().getBtEvaluateWinner().setOnAction(e -> {
			this.evaluateWinner();
		});

		view.getGameLayout().getMiddleGameLayout().getBtStartRoundTwo().setOnAction(e -> {
			this.startSecondRound();
		});

		view.getGameLayout().getMiddleGameLayout().getBtNextDuel().setOnAction(e -> {
			this.enableHandCards();
			this.disableNextDuelButton();
		});

		view.getBtnBackError().setOnAction(e -> {
			view.errorPopUp.hide();
		});

		view.chatLayout.getBtnSend().setOnAction(e -> {
			sendMessage();
		});

		// JM
		// When Model receives a new Message, the Value of the SimpleString Property
		// "LastReceivedMessage" Changes
		// This Method looks at this change and creates the respective Message Object
		model.getLastReceivedMessage().addListener((o, oldValue, newValue) -> {
			if (!newValue.equals("")) {
				// Takes the String the Model received from the Server and puts it into a String
				// Array
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

		// JM & SD
		// Looks for number of played rounds and initiates second round
		this.playedRounds.addListener((o, oldValue, newValue) -> {
			if (newValue.intValue() == 13) {
				readyForSecondRound = true;
				updateChatText("SYSTEM" + ":\n" + "BLABLA" + "\n");
				if (onTurn) {
					if (secondRoundStarted) {
						view.getGameLayout().getMiddleGameLayout().getBtEvaluateWinner().setVisible(true);
						view.getGameLayout().getMiddleGameLayout().getBtEvaluateWinner().setDisable(false);
						view.getGameLayout().getMiddleGameLayout().getBtNextDuel().setDisable(true);
					}
					// Button anzeigen
					view.getGameLayout().getMiddleGameLayout().getBtNextTableCard().setDisable(true);
					view.getGameLayout().getMiddleGameLayout().getBtStartRoundTwo().setVisible(true);
				}
				this.playedRounds.set(0);

			}
		});

		// JM
		// Sends respective messages when window gets closed
		view.getStage().setOnCloseRequest((event) -> {
			if (model.isConnected()) {
				if (username != null) {
					model.logout();
				} else {
					model.disconnect();
				}
			}
			Platform.exit();
			System.exit(0);
//			model.closeSocket();
		});
	}

	// JM
	// Does the same thing as the Message Class on Server's Side -> Creates new Message object
	// It is done here, because we want to trigger actions on the client's GUI
	private void createMessage(String[] content) {
		Message msg;
		if (content[0].equals("ResultPing")) {
			msg = new ResultPing(content);
			if (!msg.isFalse())
				msg.process(Controller.this);
			if (msg.isFalse())
				msg.processIfFalse(Controller.this);
		}
		if (content[0].equals("ResultCreateAccount")) {
			msg = new ResultCreateAccount(content);
			if (!msg.isFalse())
				msg.process(Controller.this);
			if (msg.isFalse())
				msg.processIfFalse(Controller.this);
		}
		if (content[0].equals("ResultLogin")) {
			msg = new ResultLogin(content);
			if (!msg.isFalse())
				msg.process(Controller.this);
			if (msg.isFalse())
				msg.processIfFalse(Controller.this);
		}
		if (content[0].equals("ResultLogout")) {
			msg = new ResultLogout(content);
			if (!msg.isFalse())
				msg.process(Controller.this);
			if (msg.isFalse())
				msg.processIfFalse(Controller.this);
		}
		if (content[0].equals("ResultDeleteAccount")) {
			msg = new ResultDeleteAccount(content);
			if (!msg.isFalse())
				msg.process(Controller.this);
			if (msg.isFalse())
				msg.processIfFalse(Controller.this);
		}
		if (content[0].equals("ResultBroadcastJoinPlayroom")) {
			msg = new ResultBroadcastJoinPlayroom(content);
			if (!msg.isFalse())
				msg.process(Controller.this);
			if (msg.isFalse())
				msg.processIfFalse(Controller.this);
		}
//		if (content[0].equals("ResultBroadcastStartRoundOne")) {
//			msg = new ResultBroadcastStartRoundOne(content);
//			if (!msg.isFalse())
//				msg.process(Controller.this);
//			if (msg.isFalse())
//				msg.processIfFalse(Controller.this);
//		}
		if (content[0].equals("ResultDealCards")) {
			msg = new ResultDealCards(content);
			if (!msg.isFalse())
				msg.process(Controller.this);
			if (msg.isFalse())
				msg.processIfFalse(Controller.this);
		}
		if (content[0].equals("ResultPlayCard")) {
			msg = new ResultPlayCard(content);
			if (!msg.isFalse())
				msg.process(Controller.this);
			if (msg.isFalse())
				msg.processIfFalse(Controller.this);
		}
		if (content[0].equals("ResultSendCard")) {
			msg = new ResultSendCard(content);
			if (!msg.isFalse())
				msg.process(Controller.this);
			if (msg.isFalse())
				msg.processIfFalse(Controller.this);
		}
		if (content[0].equals("ResultBroadcastFinishRound")) {
			msg = new ResultBroadcastFinishRound(content);
			if (!msg.isFalse())
				msg.process(Controller.this);
			if (msg.isFalse())
				msg.processIfFalse(Controller.this);
		}
		if (content[0].equals("ResultBroadcastEvaluateWinner")) {
			msg = new ResultBroadcastEvaluateWinner(content);
			if (!msg.isFalse())
				msg.process(Controller.this);
			if (msg.isFalse())
				msg.processIfFalse(Controller.this);
		}
		if (content[0].equals("ResultEvaluateWinner")) {
			msg = new ResultEvaluateWinner(content);
			if (!msg.isFalse())
				msg.process(Controller.this);
			if (msg.isFalse())
				msg.processIfFalse(Controller.this);
		}
		if (content[0].equals("ResultPlayerLoggedOut")) {
			msg = new ResultPlayerLoggedOut(content);
			if (!msg.isFalse())
				msg.process(Controller.this);
			if (msg.isFalse())
				msg.processIfFalse(Controller.this);
		}
		if (content[0].equals("ResultBroadcastSendMessage")) {
			msg = new ResultBroadcastSendMessage(content);
			if (!msg.isFalse())
				msg.process(Controller.this);
			if (msg.isFalse())
				msg.processIfFalse(Controller.this);
		}
		if (content[0].equals("ResultSendMessage")) {
			msg = new ResultSendMessage(content);
			if (!msg.isFalse())
				msg.process(Controller.this);
			if (msg.isFalse())
				msg.processIfFalse(Controller.this);
		}
		if (content[0].equals("ResultGetNextTableCard")) {
			msg = new ResultGetNextTableCard(content);
			if (!msg.isFalse())
				msg.process(Controller.this);
			if (msg.isFalse())
				msg.processIfFalse(Controller.this);
		}
		if (content[0].equals("ResultStartSecondRound")) {
			msg = new ResultStartSecondRound(content);
			if (!msg.isFalse())
				msg.process(Controller.this);
			if (msg.isFalse())
				msg.processIfFalse(Controller.this);
		}

	}

	// JM
	// Methods for triggering Methods to send messages in Model by clicking a Button in View
	// Get Values from User Input where needed
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

	public void evaluateWinner() {
		model.evaluateWinner();
	}

	public void playCard() {
		model.playCard("knight_1");
	}

	public void getNextTableCard() {
		model.getNextTableCard();
	}

	public void sendMessage() {
		model.sendMessage(view.chatLayout.getTfMessage().getText());
		view.chatLayout.getTfMessage().setText("");
	}

	public void startSecondRound() {
		model.startSecondRound();
	}

	// SD
	// Prepare second round on client side
	public void prepareSecondRound() {
		clearMyCard();
		clearOpponentCard();
		clearMiddle();
		resetHandCards();
		view.getBtStartRoundTwo().setVisible(false);
	}

	// SD
	public void loginSuccess() {
		// Runnable um dynamische Anzeige zu gewährleisten
		Platform.runLater(new Runnable() {
			public void run() {
				view.getRoot().setCenter(view.gameLayout);
				view.getRoot().setRight(view.chatLayout);
				view.getGameLayout().getLbName().setText(username);
				view.getStage().setTitle("Game");
				view.getTfUsername().setText("");
				view.getPfPassword().setText("");
			}
		});
		logger.info("Login Successful");
	}
	
	// SD
	public void logoutSuccess() {
		Platform.runLater(new Runnable() {
			public void run() {
				view.getRoot().setCenter(view.loginLayout);
				view.getRoot().setRight(null);
				view.getStage().setTitle("Login");

				if (secondRoundStarted) {
					clearMyCard();
					clearOpponentCard();
					disableNextDuelButton();
					view.getGameLayout().getBtEvaluateWinner().setVisible(false);
					view.getGameLayout().getBtEvaluateWinner().setDisable(true);
					view.getGameLayout().getMiddleGameLayout().getTableCardsDeck().getChildren().clear();
					view.getGameLayout().getMiddleGameLayout().getNewFollowerCard().getChildren().clear();
					CardLabel cl8 = new CardLabel();
					cl8.setDeck();
					view.getGameLayout().getMiddleGameLayout().getTableCardsDeck().getChildren()
							.add(view.getGameLayout().getMiddleGameLayout().getLbCardsDeck());
					view.getGameLayout().getMiddleGameLayout().getTableCardsDeck().getChildren().add(cl8);
					view.getGameLayout().getMiddleGameLayout().getTableCardsDeck().getChildren()
							.add(view.getGameLayout().getMiddleGameLayout().getBtNextTableCard());
					view.getGameLayout().getMiddleGameLayout().getBtNextTableCard().setVisible(true);
					CardLabel cl9 = new CardLabel();
					cl9.setDeck();
					view.getGameLayout().getMiddleGameLayout().getNewFollowerCard().getChildren()
							.add(view.getGameLayout().getMiddleGameLayout().getLbNewFollowerDeck());
					view.getGameLayout().getMiddleGameLayout().getNewFollowerCard().getChildren().add(cl9);
					view.getGameLayout().getMiddleGameLayout().getNewFollowerCard().getChildren()
							.add(view.getGameLayout().getMiddleGameLayout().getBtStartRoundTwo());
					view.getGameLayout().getMiddleGameLayout().getBtStartRoundTwo().setVisible(false);
					playedRounds.set(0);
					setSecondRoundStarted(false);
					setReadyForSecondRound(false);
				} else {
					clearMyCard();
					clearOpponentCard();
					clearTableCard();
					clearFollowerCard();
					disableTableCardButton();
					view.getBtStartRoundTwo().setVisible(false);
					playedRounds.set(0);
					setSecondRoundStarted(false);
					setReadyForSecondRound(false);
				}
				resetHandCards();
				view.getChatLayout().getWonUndeads().setText("0");
				view.getChatLayout().getLbDoubles().setVisible(false);
				view.getChatLayout().getWonDoubles().setVisible(false);
				view.getChatLayout().getLbDwarfs().setVisible(false);
				view.getChatLayout().getWonDwarfs().setVisible(false);
				view.getChatLayout().getLbGoblins().setVisible(false);
				view.getChatLayout().getWonGoblins().setVisible(false);
				view.getChatLayout().getLbKnights().setVisible(false);
				view.getChatLayout().getWonKnights().setVisible(false);
				view.getChatLayout().getTxtMessages().clear();
				view.getGameLayout().getPlayerLayout().getLbName().setText("");
				view.getGameLayout().getOtherPlayerLayout().getLbName().setText("");
			}
		});
		logger.info("Logout Successful");
	}

	//SD
	public void somethingFailed() {
		Platform.runLater(new Runnable() {
			public void run() {
				view.errorPopUp.show(view.getStage());
			}
		});
	}

	//SD
	public void gameOver() {
		Platform.runLater(new Runnable() {
			public void run() {
				view.gameOverPopUp.show(view.getStage());
			}
		});
	}

	//SD
	public void winner(String winner) {
		Platform.runLater(new Runnable() {
			public void run() {
				view.winnerPopUp.show(view.getStage());
				view.getLblWinnerName().setText(winner);
			}
		});
	}

	//SD
	public void updateOpponent(String username2) {
		Platform.runLater(new Runnable() {
			public void run() {
				view.getGameLayout().getLbOtherName().setText(username2);
			}
		});

	}

	// SD - Karten zu Beginn des Spiels austeilen
	public void deal(ArrayList<Card> handCards) {
//		board.sortHandCards(); //Handkarten werden noch sortiert //auskommentiert David
		Platform.runLater(new Runnable() {
			public void run() {
				for (int i = 0; i < handCards.size(); i++) {
					Card card = null;
					// Was macht diese Zeile? -SD
					CardLabel cl = (CardLabel) view.getGameLayout().getPlayerLayout().getHboxCards().getChildren()
							.get(i);
					cl.setCard(handCards.get(i));
					cl.setCardNameAsString(handCards.get(i).toString());
				}
			}
		});
		// SD
		for (int i = 0; i < view.getGameLayout().getPlayerLayout().getCardLabels().size(); i++) {
			view.getGameLayout().getPlayerLayout().getCardLabels().get(i).setOnMouseReleased(this::sendTableCard);
		}

		// SD
		for (int i = 0; i < view.getGameLayout().getPlayerLayout().getCardLabels().size(); i++) {
			view.getGameLayout().getPlayerLayout().getCardLabels().get(i).setOnMouseEntered(this::highlightCard);
		}

		// SD
		for (int i = 0; i < view.getGameLayout().getPlayerLayout().getCardLabels().size(); i++) {
			view.getGameLayout().getPlayerLayout().getCardLabels().get(i).setOnMouseExited(this::delightCard);
		}
	}

	// SD
	private void sendTableCard(Event event) {
		CardLabel cl = (CardLabel) event.getSource();
		model.playCard(cl.getCardNameAsString());
		// board.removePlayedCard(new Card(cl.getCardNameAsString())); //NEU GESPIELTE
		// KARTE WIRD GELÖSCHT
	}

	// SD - Karten entfernen
	public void updatePlayerPane(String playedCard) {
		Platform.runLater(new Runnable() {
			public void run() {
				CardLabel clToRemove = new CardLabel();
				for (CardLabel cl : view.getGameLayout().getPlayerLayout().getCardLabels()) {
					if (cl.getCardNameAsString().equals(playedCard)) {
						clToRemove = cl;
					}

				}
				view.getGameLayout().getPlayerLayout().getCardLabels().remove(clToRemove);
				view.getGameLayout().getPlayerLayout().getHboxCards().getChildren().remove(clToRemove);

				updateGameDisplay(playedCard);
			}
		});
	}

	// public void updatePlayerPane(String playedCard) {
	// Platform.runLater(new Runnable() {
	// public void run() {
	// CardLabel clToRemove = new CardLabel();
	// int found = 0;
	// for(CardLabel cl : view.getGameLayout().getPlayerLayout().getCardLabels()) {
	// while(found < 1) {
	// if(cl.getCardNameAsString().equals(playedCard)) {
	// clToRemove = cl;
	// found = 1;
	// System.out.println("Karte gefunden!");
	// }
	// }
	// }
	// view.getGameLayout().getPlayerLayout().getCardLabels().remove(clToRemove);
	// view.getGameLayout().getPlayerLayout().getHboxCards().getChildren().remove(clToRemove);
	//
	// updateGameDisplay(playedCard);
	// }
	// });
	// }

	// SD - Gespielte Karten in der Mitte anzeigen
	public void updateGameDisplay(String playedCard) {
		// view.getGameLayout().getMiddleGameLayout().getPlayedCards().getChildren().clear();

		CardLabel cl1 = new CardLabel();
		cl1.setCard(playedCard);
		view.getGameLayout().getMiddleGameLayout().getPlayedCards().getChildren().remove(2);
		view.getGameLayout().getMiddleGameLayout().getPlayedCards().getChildren().add(2, cl1);
	}

	//SD
	public void otherPlayerCard(String card) {
		board.setPlayableHC(new Card(card)); // NEU Handkarten werden spielbar gesetzt
		Platform.runLater(new Runnable() {
			public void run() {
				CardLabel cl2 = new CardLabel();
				cl2.setCard(card);
				view.getGameLayout().getMiddleGameLayout().getPlayedCards().getChildren().remove(1);
				view.getGameLayout().getMiddleGameLayout().getPlayedCards().getChildren().add(1, cl2);
			}
		});
	}

	//SD
	public void tableCard(String card) {
		Platform.runLater(new Runnable() {
			public void run() {
				CardLabel cl3 = new CardLabel();
				cl3.setCard(card);
				view.getGameLayout().getMiddleGameLayout().getTableCardsDeck().getChildren().remove(1);
				view.getGameLayout().getMiddleGameLayout().getTableCardsDeck().getChildren().add(1, cl3);
			}
		});
	}

	//SD
	public void clearMyCard() {
		Platform.runLater(new Runnable() {
			public void run() {
				CardLabel cl4 = new CardLabel();
				cl4.setDeck();
				view.getGameLayout().getMiddleGameLayout().getPlayedCards().getChildren().remove(1);
				view.getGameLayout().getMiddleGameLayout().getPlayedCards().getChildren().add(1, cl4);
			}
		});
	}

	//SD
	public void clearOpponentCard() {
		Platform.runLater(new Runnable() {
			public void run() {
				CardLabel cl4 = new CardLabel();
				cl4.setDeck();
				view.getGameLayout().getMiddleGameLayout().getPlayedCards().getChildren().remove(2);
				view.getGameLayout().getMiddleGameLayout().getPlayedCards().getChildren().add(2, cl4);
			}
		});
	}

	//SD
	public void clearTableCard() {
		Platform.runLater(new Runnable() {
			public void run() {
				CardLabel cl4 = new CardLabel();
				cl4.setDeck();
				view.getGameLayout().getMiddleGameLayout().getTableCardsDeck().getChildren().remove(1);
				view.getGameLayout().getMiddleGameLayout().getTableCardsDeck().getChildren().add(1, cl4);
			}
		});
	}

	//SD
	public void showNewFollowerCard(String tableCard) {
		Platform.runLater(new Runnable() {
			public void run() {
				CardLabel cl5 = new CardLabel();
				cl5.setCard(tableCard);
				view.getGameLayout().getMiddleGameLayout().getNewFollowerCard().getChildren().remove(1);
				view.getGameLayout().getMiddleGameLayout().getNewFollowerCard().getChildren().add(1, cl5);
			}
		});
	}

	//SD
	public void clearFollowerCard() {
		Platform.runLater(new Runnable() {
			public void run() {
				CardLabel cl6 = new CardLabel();
				cl6.setDeck();
				view.getGameLayout().getMiddleGameLayout().getNewFollowerCard().getChildren().remove(1);
				view.getGameLayout().getMiddleGameLayout().getNewFollowerCard().getChildren().add(1, cl6);
			}
		});
	}

	//SD
	public void clearMiddle() {
		Platform.runLater(new Runnable() {
			public void run() {
				view.getGameLayout().getMiddleGameLayout().getTableCardsDeck().getChildren().clear();
				view.getGameLayout().getMiddleGameLayout().getNewFollowerCard().getChildren().clear();
				view.getGameLayout().getMiddleGameLayout().getTableCardsDeck().getChildren().add(view.getBtNextDuel());
				view.getGameLayout().getMiddleGameLayout().getTableCardsDeck().getChildren()
						.add(view.getBtEvaluateWinner());
			}
		});
	}

	//SD
	public void clearRoundTwo() {
		Platform.runLater(new Runnable() {
			public void run() {
				view.getGameLayout().getMiddleGameLayout().getTableCardsDeck().getChildren().clear();
			}
		});
	}

	//SD
	public void resetHandCards() {
		Platform.runLater(new Runnable() {
			public void run() {
				view.getGameLayout().getPlayerLayout().getHboxCards().getChildren().clear();
				view.getGameLayout().getPlayerLayout().getCardLabels().clear();
				for (int i = 0; i < 13; i++) {
					CardLabel cl = new CardLabel();
					view.getGameLayout().getPlayerLayout().getHboxCards().getChildren().add(cl);
					view.getGameLayout().getPlayerLayout().getHboxCards().setSpacing(10);
					view.getGameLayout().getPlayerLayout().getCardLabels().add(cl);
					cl.setDisable(true);
				}
			}
		});
	}

	// SD
	public void highlightCard(Event event) {
		CardLabel cl = (CardLabel) event.getSource();
		cl.setStyle("-fx-effect: dropshadow(three-pass-box, black, 3, 0.2, 0, 0);");
	}

	// SD
	public void delightCard(Event event) {
		CardLabel cl = (CardLabel) event.getSource();
		cl.setStyle("-fx-effect: dropshadow(three-pass-box, black, 0, 0, 0, 0);");
	}

	//SD
	public void enableTableCardButton(String firstUser) {
		if (firstUser.equals(this.getUsername())) {
			view.getGameLayout().getMiddleGameLayout().getBtNextTableCard().setDisable(false);
		}
	}

	//SD
	public void enableNextDuelButton() {
		view.getGameLayout().getMiddleGameLayout().getBtNextDuel().setDisable(false);
	}

	//SD
	public void enableTableCardButton() {
		view.getGameLayout().getMiddleGameLayout().getBtNextTableCard().setDisable(false);
	}

	//SD
	public void disableTableCardButton() {
		view.getGameLayout().getMiddleGameLayout().getBtNextTableCard().setDisable(true);
	}

	//SD
	public void disableNextDuelButton() {
		view.getGameLayout().getMiddleGameLayout().getBtNextDuel().setDisable(true);
	}

	//SD
	public void enableHandCards() {
		if (onTurn) {
			board.setPlayableHC(); // DS Testweise auskommentiert
			for (int i = 0; i < board.getHandCards().size(); i++) {
				if (board.getHandCards().get(i).getPlayable())
					for (CardLabel cl : view.getGameLayout().getPlayerLayout().getCardLabels()) {
						if (cl.getCardNameAsString().equals(board.getHandCards().get(i).toString())) {
							cl.setDisable(false);
						}
					}
			}
		}
	}

	//SD
	public void disableHandCards() {
		for (CardLabel cl : view.getGameLayout().getPlayerLayout().getCardLabels()) {
			cl.setDisable(true);
		}
	}

	//SD
	public void enablePlayableHC() {
		for (int i = 0; i < board.getHandCards().size(); i++) {
			if (board.getHandCards().get(i).getPlayable())
				for (CardLabel cl : view.getGameLayout().getPlayerLayout().getCardLabels()) {
					if (cl.getCardNameAsString().equals(board.getHandCards().get(i).toString())) {
						cl.setDisable(false);
					}
				}
		}
	}

	//SD
	public void updateChatText(String text) {
		Platform.runLater(new Runnable() {
			public void run() {
				view.chatLayout.getTxtMessages().appendText(text + "\n");
			}
		});
	}

	//SD & JM
	// Update number of pointcards per fraction
	public void updateGameEvaluation() {
		Platform.runLater(new Runnable() {
			public void run() {
				String goblins = String.valueOf(board.getNumOfGoblins());
				if (board.getNumOfGoblins() > 0) {
					String temp = ": ";
					for (Card c : board.getGoblinCards()) {
						temp = temp + c.getRank().toString() + " | ";
					}
					goblins = goblins + temp;
				}
				view.chatLayout.getWonGoblins().setText(goblins);
				
				String dwarfs = String.valueOf(board.getNumOfDwarfs());
				if (board.getNumOfDwarfs() > 0) {
					String temp = ": ";
					for (Card c : board.getDwarfCards()) {
						temp = temp + c.getRank().toString() + " | ";
					}
					dwarfs = goblins + temp;
				}
				view.chatLayout.getWonDwarfs().setText(dwarfs);
				
				String undeads = String.valueOf(board.getNumOfUndeads());
				if (board.getNumOfUndeads() > 0) {
					String temp = ": ";
					for (Card c : board.getUndeadCards()) {
						temp = temp + c.getRank().toString() + " | ";
					}
					undeads = undeads + temp;
				}
				view.chatLayout.getWonUndeads().setText(undeads);
				
				String knights = String.valueOf(board.getNumOfKnights());
				if (board.getNumOfKnights() > 0) {
					String temp = ": ";
					for (Card c : board.getKnightCards()) {
						temp = temp + c.getRank().toString() + " | ";
					}
					knights = goblins + temp;
				}
				view.chatLayout.getWonKnights().setText(knights);
				
				String doubles = String.valueOf(board.getNumOfDoubles());
				if (board.getNumOfDoubles() > 0) {
					String temp = ": ";
					for (Card c : board.getDoubleCards()) {
						temp = temp + c.getRank().toString() + " | ";
					}
					doubles = goblins + temp;
				}
				view.chatLayout.getWonDoubles().setText(doubles);
			}
		});
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

	public Boolean getOnTurn() {
		return onTurn;
	}

	public void setOnTurn(Boolean onTurn) {
		this.onTurn = onTurn;
	}

	public Boolean getCardPlayed() {
		return cardPlayed;
	}

	public void setCardPlayed(Boolean cardPlayed) {
		this.cardPlayed = cardPlayed;
	}

	public SimpleIntegerProperty getPlayedRounds() {
		return playedRounds;
	}

	public void setPlayedRounds(SimpleIntegerProperty playedRounds) {
		this.playedRounds = playedRounds;
	}

	public void increasePlayedRounds() {
		this.playedRounds.set(this.playedRounds.get() + 1);
	}

	public Boolean getSecondRoundStarted() {
		return secondRoundStarted;
	}

	public void setSecondRoundStarted(Boolean secondRoundStarted) {
		this.secondRoundStarted = secondRoundStarted;
	}
	public Boolean getReadyForSecondRound() {
		return readyForSecondRound;
	}

	public void setReadyForSecondRound(Boolean readyForSecondRound) {
		this.readyForSecondRound = readyForSecondRound;
	}

}
