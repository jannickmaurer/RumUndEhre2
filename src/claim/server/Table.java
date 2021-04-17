package claim.server;

import java.util.ArrayList;

public class Table {
	
	// Cards on the table - player's cards are stored in Account object
	private ArrayList<String> tableCards = new ArrayList<>();

	private Account playerOne;
	private Account playerTwo;
	
	public Table(Account playerOne, Account playerTwo) {
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
	}
}
