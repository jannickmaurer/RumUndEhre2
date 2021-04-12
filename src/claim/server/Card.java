package claim.server;

/*
 * David Schürch
 * Code Source from Poker Project with individual changes.
 * Code creates a suit and a rank of a card. It can compare the rank of the card.
 */

public class Card implements Comparable<Card>{
	public enum Suit { Goblin, Dwarf, Undead, Double, Knight;
        @Override
        public String toString() {
            String suit = "";
            switch (this) {
            case Goblin: suit = "goblin"; break;
            case Dwarf:  suit = "dwarf";  break;
            case Undead: suit = "undead"; break;
            case Double: suit = "double"; break;
            case Knight: suit = "knight"; break;
            }
            return suit;
        }
	}
	
    public enum Rank { Zero, One, Two, Three, Four, Five, Six, Seven, Eight, Nine;
        @Override
        public String toString() {
            int ordinal = this.ordinal();
            return Integer.toString(ordinal);
        }
    }

    private final Suit suit;
    private final Rank rank;
	
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

	public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }
    
    @Override
    public String toString() {
        return rank.toString() + suit.toString();
    }
	
	@Override
	public int compareTo(Card o) {
		if(this.getRank().compareTo(o.getRank()) > 0 ) {
			return 1;
		} else if(this.getRank().compareTo(o.getRank()) < 0) {
				return -1;
			} 
		return 0;
	}	
}
