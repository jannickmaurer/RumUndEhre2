package claim.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Logger;

import claim.commons.Card;
import claim.commons.ServiceLocator;
import claim.commons.messages.results.ResultDealCards;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Playroom implements Serializable {
	private static ServiceLocator sl = ServiceLocator.getServiceLocator();
	private static Logger logger = sl.getServerLogger();
	
	protected static final ArrayList<Playroom> playrooms = new ArrayList<>();
	protected SimpleIntegerProperty numberOfPlayers = new SimpleIntegerProperty();
	protected SimpleBooleanProperty gameStarted = new SimpleBooleanProperty();
	protected ArrayList<Account> players;
	private Table table;
	
	public Playroom() {
		players = new ArrayList<>();
		numberOfPlayers.set(0);
		gameStarted.addListener((o, OldValue, NewValue) -> {
		if(NewValue.booleanValue()) {
			table = new Table(this.players);
			table.deal();
			//TBD: Cards from account ArrayList
			for(Account a : players) {
				String[] temp = new String[] {"ResultDealCards", "true", players.get(0).getUsername()};
				ArrayList<String> cards = new ArrayList<>();
				for(Card c : a.getHandCards()) {
					cards.add(c.toString());
				}
				String[] content = combineArrayAndArrayList(temp, cards);
				a.getClient().send(new ResultDealCards(content));
			}
//			try {
//				Thread.sleep(1500);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			//SD - table.sendTableCard();
		}
	});
//		numberOfPlayers.addListener((o, OldValue, NewValue) -> {
//			if(NewValue.intValue() == 2) {
//				table = new Table();
//				for(Account a : players) {
//					a.getClient().send(new ResultBroadcastStartRoundOne(true));
//				}
//			}
//		});
	}
	
	public String[] combineArrayAndArrayList(String[] array, ArrayList<String> list) {
		String[] content = new String[array.length + list.size()];
		for (int i = 0; i < array.length; i++)
			content[i] = array[i];
		for (int i = 0; i < list.size(); i++)
			content[i + array.length] = list.get(i);
		return content;
	}
	
	public void addAccount(Account a) {
		if(numberOfPlayers.get() < 2) players.add(a);
		logger.info("Client added to Playroom: " + a.toString());
		logger.info("NUmber of Players in ArrayList: " + players.size());
		numberOfPlayers.set(numberOfPlayers.get()+1);
		logger.info("Number of Players in Playroom: " + numberOfPlayers.get() );
	}
	
	public static void add(Playroom playroom) {
		playrooms.add(playroom);

	}
	
	public static ArrayList<Playroom> getPlayrooms(){
		return playrooms;
	}

	public int getNumberOfPlayers() {
		return numberOfPlayers.get();
	}

	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers.set(numberOfPlayers);
	}

	public Boolean isGameStarted() {
		return gameStarted.getValue();
	}

	public void setGameStarted(Boolean gameStarted) {
		this.gameStarted.setValue(gameStarted);
	}

	public ArrayList<Account> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Account> players) {
		this.players = players;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public void setNumberOfPlayers(SimpleIntegerProperty numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}
	
	public void test() {
		System.out.println("Karte auf Playroom Account " + this.players.get(0).getPlayedCard());
		System.out.println("Karte auf Playroom Account " + this.players.get(1).getPlayedCard());
	}
	
	
}
