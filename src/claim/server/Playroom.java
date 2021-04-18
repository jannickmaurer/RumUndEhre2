package claim.server;

import java.io.Serializable;
import java.util.ArrayList;

import claim.commons.messages.results.ResultDealCards;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Playroom implements Serializable {
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
			table = new Table();
			table.deal();
			//TBD: Cards from account ArrayList
			for(Account a : players) {
				String[] content = new String[] {"ResultDealCards", "true", "goblin_4","goblin_5","dwarf_4","goblin_3",
						"goblin_3","goblin_3","goblin_3","knight_3","goblin_3","goblin_3","undead_3","goblin_3","goblin_3"};
				a.getClient().send(new ResultDealCards(content));
			}
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
	
	public void addAccount(Account a) {
		if(numberOfPlayers.get() < 2) players.add(a);
		numberOfPlayers.set(numberOfPlayers.get()+1);
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
	
	
}
