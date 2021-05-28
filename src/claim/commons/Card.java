package claim.commons;

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
    
    public boolean Playable (){ 
    	return playable = true;
    }

    //  David: Boolean playable in ganzer Klasse hinzugefügt
    private Suit suit;
    private Rank rank;
    private Boolean playable;
	
   // Jannick: Added constructor for creating cards with String:
    public Card(String card) {
	    String[] temp = card.split("\\_");
	    if(temp.length > 0) {
		   	switch (temp[0]) {
		         case "goblin": this.suit = Suit.Goblin; break;
		         case "dwarf": this.suit = Suit.Dwarf;  break;
		         case "undead": this.suit = Suit.Undead; break;
		         case "double": this.suit = Suit.Double; break;
		         case "knight": this.suit = Suit.Knight; break;
	          }
		   	switch (temp[1]) {
			   	case "0" : this.rank = Rank.Zero; break;
			   	case "1" : this.rank = Rank.One; break;
			   	case "2" : this.rank = Rank.Two; break;
			   	case "3" : this.rank = Rank.Three; break;
			   	case "4" : this.rank = Rank.Four; break;
			   	case "5" : this.rank = Rank.Five; break;
			   	case "6" : this.rank = Rank.Six; break;
			   	case "7" : this.rank = Rank.Seven; break;
			   	case "8" : this.rank = Rank.Eight; break;
			   	case "9" : this.rank = Rank.Nine; break;
		   	}
	   	}
    }
    
    public Card(Suit suit, Rank rank, Boolean playable) {
        this.suit = suit;
        this.rank = rank;
        this.playable = playable;
    }

	public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }    
    
    public Boolean getPlayable() {
    	return playable;
    }
    
    public void setPlayable(Boolean playable) {
    	this.playable = playable;
    }
    
	public String toString() {
		return suit.toString() + "_" + rank.toString();
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
