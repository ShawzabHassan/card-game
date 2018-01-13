package game;

import java.util.ArrayList;
import java.util.Random;

/**
 * Player that has the same functionality as the regular player, but in addition the ability
 * to select an action based on the table card and cards in hand, albeit randomly and without
 * any deeper strategy.
 * @author samuli
 *
 */
public class Cpu extends Player {
	private Random rand = new Random();	


	/**
	 * Selects the cards for cpu player to play, according to the top card currently on table
	 * @param topCard value of the top card currently on the table
	 */
	public void selectCards(int topCard) {
		ArrayList<Integer> cardValues = new ArrayList<Integer>();
		for (Card k : getCards()) {
			if (!cardValues.contains(k.getValue()) 
					&& Validator.isAllowedToPlay(k.getValue(), topCard)) {
				cardValues.add(k.getValue());
			} 
		}
		if (!cardValues.isEmpty()) {
			int randomSeed = randomInt(0,cardValues.size()-1);
			int randomAmount = randomInt(1,4);
			int amountChosen = 0;
			for (Card k : getCards()) {
				if (k.getValue() == cardValues.get(randomSeed)) {
					if (Validator.isClearingCard(k.getValue()) && getSelectedCards().contains(k)
							|| amountChosen >= randomAmount) break;
					select(k);
					amountChosen++;
				}
			}
		}
	}


	/**
	 * Generates a random number
	 * @param min minimum number to be generated, inclusive
	 * @param max maximum number to be generated, inclusive
	 * @return the random number
	 */
	private int randomInt(int min, int max) {
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
}
