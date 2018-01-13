package game;

/**
 * A card object that has a numerical value and a suit.
 * @author samuli
 *
 */
public class Card implements Comparable<Card> {

	private final String[] suits = {"spades","hearts","clubs","diamonds"};
	private final String[] highCards = {"jack", "queen", "king", "ace", "2"};
	private int value;
	private String suit;


	public Card(int value, int suit) {
		this.value = value;
		this.suit = suits[suit];
	}
	
	
	public Card() {
		this.value = 0;
		this.suit = suits[0];
	}
	

	public int getValue() {
		return value;
	}

	
	public String getSuit() {
		return suit;
	}

	
	public String toString() {
		if (value == 0) return "0s";
		if (value > 10) return highCards[value-11] + " of " + suit; 
		return value + " of " + suit;
	}


	@Override
	public int compareTo(Card c) {
		return getValue() - c.getValue();
	}

}
