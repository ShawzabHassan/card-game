package game;

import java.util.ArrayList;


/**
 * Player that can take cards and give them away.
 * @author samuli
 *
 */
public class Player {

	private ArrayList<Card> cards = new ArrayList<Card>();
	private ArrayList<Card> selectedCards = new ArrayList<Card>();
	
	
	/**
	 * Adds cards to hand
	 * @param addedCards list of cards to be added
	 */
	public void takeCards(ArrayList<Card> addedCards) {
		cards.addAll(addedCards);
	}
	
	
	/**
	 * Adds a single card to hand
	 * @param addedCard card to be added
	 */
	public void takeCards(Card addedCard) {
		if (addedCard != null) cards.add(addedCard);
	}
	
	
	/**
	 * Takes a card from hand, so it can be hit to the table
	 * @return card to be hit
	 */
	public ArrayList<Card> hitCards() {
		ArrayList<Card> temp = new ArrayList<Card>();
		temp.addAll(selectedCards);
		cards.removeAll(selectedCards);
		selectedCards.clear();
		return temp;
	}
	
	
	/**
	 * Adds a card to the list of selected cards
	 * @param selected card to be added
	 */
	public void select(Card selected) {
		if (cards.contains(selected) && !selectedCards.contains(selected)) selectedCards.add(selected);
		else if (cards.contains(selected) && selectedCards.contains(selected)) selectedCards.remove(selected);
	}
	
	
	/**
	 * Returns player's cards in a list
	 * @return cards in a list
	 */
	public ArrayList<Card> getCards() {
		return cards;
	}
	
	
	/**
	 * Returns all the selected cards in a list
	 * @return selected cards
	 */
	public ArrayList<Card> getSelectedCards() {
		return selectedCards;
	}
	
	
	public void selectCards(int topCard) {
		
	}
}
