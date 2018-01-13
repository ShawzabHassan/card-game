package game;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Main logic of the game. Has public methods for communicating with the GUI
 * @author samuli
 *
 */
public class Game {

	private ArrayList<Player> players = new ArrayList<Player>();
	private Deck deck = new Deck();
	private Table table = new Table();
	private Player humanPlayer;
	
	protected SimpleIntegerProperty currentPlayer = new SimpleIntegerProperty(0);
	protected SimpleBooleanProperty gameOver = new SimpleBooleanProperty(false);
	protected SimpleBooleanProperty deckEmpty = new SimpleBooleanProperty(false);
	
	private HashMap<Integer, ArrayList<String>> cardsDuringRound;
	private ArrayList<String> currentPlayerCards;
	
	
	public Game() {
		this(4);
	}


	/**
	 * Creates players and adds them to a list of players
	 * @param numberOfPlayers number of players to be created
	 */
	public Game(int numberOfPlayers) {
		humanPlayer = new Player();
		players.add(humanPlayer);
		for (int i = 1; i<numberOfPlayers; i++) {
			Cpu cpu = new Cpu();
			players.add(cpu);
		}
		deck.shuffle();
		dealCards();
		deckEmpty.bind(deck.deckEmpty);
	}


	/**
	 * Deals cards for all the players
	 */
	private void dealCards() {
		if (players.isEmpty()) return;
		int i = 0;
		int finishedPlayers = 0;

		while(!deck.getCards().isEmpty()) {
			Player p = players.get(i);
			if (p.getCards().size()<5) {
				p.takeCards(deck.dealOneCard());
			}
			else finishedPlayers++;
			i++;
			if (i>=players.size()) i = 0; 
			if (finishedPlayers >= players.size()) break;
		}
	}


	/**
	 * One round, during which each cpu player completes a turn
	 */
	public void cpuRound() {
		cardsDuringRound = new HashMap<Integer, ArrayList<String>>();
		for (int i=1; i<players.size(); i++) {
			currentPlayerCards = new ArrayList<String>();
			if (gameOver.get()) break;
			cpuTurn(players.get(i));
			currentPlayer.set(i);
			cardsDuringRound.put(currentPlayer.get(), currentPlayerCards);
		}
		currentPlayer.set(0);
	}
	
	
	/**
	 * Retruns all the cards that were played during the turn
	 * @return String representations of all the cards played during turn, sorted by player.
	 */
	public HashMap<Integer, ArrayList<String>> getCardsFromRound() {
		return cardsDuringRound;
	}


	/**
	 * Cpu player completes one turn
	 * @param p player in turn
	 */
	private void cpuTurn(Player p) {
		p.getSelectedCards().clear();
		p.selectCards(table.getTopCard().getValue());
		for (Card c : p.getSelectedCards()) {
			currentPlayerCards.add(c.toString());
		}
		if (!p.getSelectedCards().isEmpty()) {
			if (pelaa(p)) cpuTurn(p);
			return;
		} else {
			tryTopCard(p);
		}
	}


	/**
	 * Method for the human to complete a turn, accessible from the GUI
	 */
	public void humanTurn() {
		if(pelaa(humanPlayer)) return;
		currentPlayer.set(1);
	}
	
	
	/**
	 * Gets the human player's cards, so they can be selected from the GUI
	 * @return Human player's card objects in a list
	 */
	public ArrayList<Card> getHumanCards() {
		return humanPlayer.getCards();
	}


	/**
	 * Gets all the cards that are currently selected by the human player, so they can be used in the GUI
	 * @return List of the cards selected by the human player
	 */
	public ArrayList<Card> getHumanSelectedCards() {
		return humanPlayer.getSelectedCards();
	}


	/**
	 * Method for the human player to select cards from the GUI
	 * @param c card to be selected
	 */
	public void selectCard(Card c) {
		humanPlayer.select(c);
	}


	/**
	 * Human player adds all the table cards to their hand, accessible from the GUI
	 */
	public void takeAllCards() {
		humanPlayer.takeCards(table.giveAllCards());
	}


	/**
	 * Checks if player's card selection is allowed, and sends a message to the GUI
	 * @return true if selection is allowed, false if not
	 */
	public boolean checkSelection() {
		if (getHumanSelectedCards().isEmpty()) return false;
		ArrayList<Integer> values = new ArrayList<Integer>();
		for (Card k : getHumanSelectedCards()) {
			values.add(k.getValue());
		}
		int i = values.get(0);
		for (int value : values) {
			if (value != i) return false;
		}
		return Validator.isAllowedToPlay(i, table.getTopCard().getValue());
	}


	/**
	 * Method for the player to try the top card from the deck, using GUI
	 */
	public void tryTopCard() {
		tryTopCard(humanPlayer);
	}



	/**
	 * One game event, or one card being hit to the table by player
	 * @param p Player who plays a card
	 * @return true if player can continue their turn after this play, false if they cannot.
	 */
	private boolean pelaa(Player p) {
		boolean result = table.addToTable(p.hitCards());
		addMissingCards(p);
		if (deck.getCards().isEmpty() && p.getCards().isEmpty()) {
			gameOver.set(true);
		}
		return result;
	}


	

	/**
	 * Player takes the top card from the deck. If it can be played, it will be, and if not, player adds it and all
	 * table cards to their hand
	 * @param p player who takes the top card
	 */
	private void tryTopCard(Player p) {
		currentPlayerCards.add(""+0);
		Card k = deck.dealOneCard();
		if (k != null) {
			currentPlayerCards.add(k.toString());
			if (Validator.isAllowedToPlay(k.getValue(), table.getTopCard().getValue())) {
				table.addToTable(k);
			} else {
				p.takeCards(table.giveAllCards());
				p.takeCards(k);
				currentPlayerCards.add(""+(-1));
			}
		} else {
			p.takeCards(table.giveAllCards());
			currentPlayerCards.add(""+(-1));
		}
		addMissingCards(p);
	}


	/**
	 * Checks if the player has less than the minimum amount of cards, and gives them more if needed
	 * (and if the deck is not empty)
	 */
	private void addMissingCards(Player p) {
		if (deck.dealOneCard() == null) return ;
		while (p.getCards().size() < 5) {
			Card k = deck.dealOneCard();
			p.takeCards(k);
		}
	}


	/**
	 * Gives the code of the top card on the table to the GUI
	 * @return code of the top card from the table
	 */
	public String showTopCard() {
		return table.getTopCard().toString();
	}

}
