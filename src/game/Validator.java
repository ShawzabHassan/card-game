package game;

/**
 * Utility class that has two methods for checking if a card is allowed to play in a certain
 * situations of the game.
 * @author samuli
 *
 */
public class Validator {

	/**
	 * Checks if the card can be used to clear the table
	 * @param cardValue value of the card
	 * @return true if the card fits the conditions, false if not
	 */
	public static boolean isClearingCard(int cardValue) {
		return (cardValue == 10 || cardValue == 14);
	}
	
	
	/**
	 * Checks if a card can be added to table
	 * @param cardValue Card to be checked
	 * @param tableCard Current table card
	 * @return true if the card can be added, false if not
	 */
	public static boolean isAllowedToPlay(int cardValue, int tableCard) {
		if (cardValue == 15) return true;
		if (isClearingCard(cardValue) && tableCard == 0) return true;
		if (cardValue < tableCard) return false;
		if (cardValue > 10 && tableCard < 7) return false;
		if (cardValue == 14 && tableCard < 10) return false;
		return true;
	}
}
