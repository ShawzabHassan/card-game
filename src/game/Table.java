package game;

import java.util.ArrayList;


/**
 * Placeholder for cards where they are hit, and after that either thrown away completely or
 * given back to one player, if no other move is available.
 * @author samuli
 *
 */
public class Table {

	private ArrayList<Card> cards = new ArrayList<Card>();
	private Card topCard = new Card();
	
	/**
	 * Adds all the played cards to the table
	 * @param cardsInPlay cards to be added to the table
	 * @return true if player is allowed to continue, false if not
	 */
	public boolean addToTable(ArrayList<Card> cardsInPlay) {
		if (Validator.isClearingCard(cardsInPlay.get(0).getValue())) {
			clearTable();
			return true;
		}
		cards.addAll(cardsInPlay);
		topCard = cardsInPlay.get(0);
		return false;
	}
	
	
	/**
	 * adds a single card to the table
	 * @param cardInPlay card to be added
	 * @return true if player is allowed to continue, false if not
	 */
	public boolean addToTable(Card cardInPlay) {
		ArrayList<Card> cardsInPlay = new ArrayList<Card>();
		cardsInPlay.add(cardInPlay);
		return addToTable(cardsInPlay);
	}
	
	
	/**
	 * Gets the top card from the table
	 * @return the top card of the table
	 */
	public Card getTopCard() {
		return topCard;
	}
	
	
	/**
	 * Removes all the cards from the table
	 */
	private void clearTable() {
		cards.clear();
		topCard = new Card();
	}
	
	
	/**
	 * Gives all the cards back to the player
	 * @return list of cards to be given back
	 */
	public ArrayList<Card> giveAllCards() {
		ArrayList<Card> temp = new ArrayList<Card>();
		temp.addAll(cards);
		cards.clear();
		topCard = new Card();
		return temp;
	}
}
