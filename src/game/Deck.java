package game;

import java.util.ArrayList;
import java.util.Collections;

import javafx.beans.property.SimpleBooleanProperty;


/**
 * Deck object that has all the cards in the beginning of the game, and can be used to deal them
 * to players at different times during the game.
 * @author samuli
 *
 */
public class Deck {

	private ArrayList<Card> cards = new ArrayList<Card>();
	protected SimpleBooleanProperty deckEmpty = new SimpleBooleanProperty(false);

	
	public Deck() {
		for (int i = 3; i<16; i++) {
			for (int j = 0; j<4; j++) {
				cards.add(new Card(i,j));
			}
		}
	}


	/**
	 * Returns all the cards in the deck in a list
	 * @return list of cards in the deck
	 */
	public ArrayList<Card> getCards() {
		return cards;
	}


	/**
	 * Shuffles the deck
	 */
	public void shuffle() {
		Collections.shuffle(cards);
	}


	/**
	 * Deals one card from the deck
	 * @return card to be dealt
	 */
	public Card dealOneCard() {
		if (!cards.isEmpty()) {
			int index = cards.size()-1;
			Card temp = cards.get(index);
			cards.remove(index);
			return temp;
		}
		else {
			deckEmpty.set(true);
			return null;
		}
	}
}
