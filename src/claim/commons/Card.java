package claim.commons;

import java.util.ArrayList;

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
	
   // Jannick: Added constructor for creating cards with String:
//    public Card(String card) {
//    	String[] temp = card.split("\\_");
//    	
//    	if(temp.length > 0) {
//	    	switch (temp[0]) {
//	          case "goblin": this.suit = Suit.Goblin; break;
//	          case "dwarf": this.suit = Suit.Dwarf;  break;
//	          case "undead": this.suit = Suit.Undead; break;
//	          case "double": this.suit = Suit.Double; break;
//	          case "knight": this.suit = Suit.Knight; break;
//	          }
//    	} else {
//    		this.suit = null;
//    		this.rank = null;
//    	
//    	}
//    	
//   
//    
//    	
//    }
    
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
    
    //
    
	public String toString() {
		return suit.toString() + "_" + rank.toString();
//		String rank = card.getRank().toString();
//		String suit = card.getSuit().toString();
//		return suit + "_" + rank;
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
