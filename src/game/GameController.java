package game;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;


/**
 * GUI controller class. 
 * TODO: implementing card animations for cpu players' moves,
 * currently only text logs are shown.
 * @author samuli
 *
 */
public class GameController implements Initializable {

	@FXML private Label label;
	@FXML private Label logLabel;
	@FXML private FlowPane playerCardsPane;
	@FXML private FlowPane tablePane;
	@FXML private Button playButton;

	@FXML private void handlePelaa() { pelaa(); }	
	@FXML private void handleNostaPakka() { nostaPakka(); }
	@FXML private void handleKokeile() { kokeilePakasta(); } 

	Game game;
	VisibleCard topCard;
	SimpleBooleanProperty gameOver;
	SimpleBooleanProperty deckEmpty;
	SimpleIntegerProperty currentPlayer;

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		playerCardsPane.setMaxWidth(750);
		playerCardsPane.setVgap(5);
		playerCardsPane.setHgap(-55);
		tablePane.setHgap(10);
		logLabel.setTextFill(Color.WHITE);

		VisibleCard taka = new VisibleCard("back");
		topCard = new VisibleCard();
		tablePane.getChildren().addAll(taka, topCard);

		game = new Game();

		gameOver = new SimpleBooleanProperty();
		deckEmpty = new SimpleBooleanProperty();
		currentPlayer = new SimpleIntegerProperty();
		gameOver.bind(game.gameOver);
		deckEmpty.bind(game.deckEmpty);
		currentPlayer.bind(game.currentPlayer);
		gameOver.addListener(e -> { peliOhi(); });
		deckEmpty.addListener(e -> { taka.setVisible(false); });
		gameOver.addListener(e -> { round(); });
		
		

		updateTable();
		
	}


	private void peliOhi() { 
		label.setText("Game over! \n player " + currentPlayer.get() + " won." );
		game.getHumanCards().clear();
		updateTable();
	}


	private void pelaa() {
		game.humanTurn();
		if (currentPlayer.get() != 0) { //TODO: change to an event
			round();
		}
		updateTable();
	}


	private void kokeilePakasta() {
		game.tryTopCard();
		round();
	}


	private void nostaPakka() {
		game.takeAllCards();
		round();
	}


	/**
	 * Updates player's hand and the table
	 */
	private void updateTable() {
		playerCardsPane.getChildren().clear();
		for (Card k : game.getHumanCards()) {
			createVisibleCard(k);
		}
		teeTarkistukset();
		topCard.setCard(game.showTopCard());
	}


	/**
	 * Method for showing a text log for cpu moves, if card animations aren't available
	 */
	private void logRound() {
		HashMap<Integer, ArrayList<String>> cardsFromRound = game.getCardsFromRound();
		logLabel.setText("");
		for (int player : cardsFromRound.keySet()) {
			if (cardsFromRound.get(player).contains("0")) {
				logLabel.setText(logLabel.getText() + "Player" + player + " takes a card from the deck. \n");
				if (cardsFromRound.get(player).contains("-1")) {
					logLabel.setText(logLabel.getText() + "Player" + player + " takes all cards from the table. \n");
				} else {
					logLabel.setText(logLabel.getText() + "Player" + player + " plays: " + cardsFromRound.get(player).get(1));
				}
			} else {
				for (String card : cardsFromRound.get(player)) {
					logLabel.setText(logLabel.getText() + "Player " + player + " plays: " + card +"\n");
					if (card.startsWith("10") || card.startsWith("14")) {
						logLabel.setText(logLabel.getText() + "The table is cleared \n");
					}
				}
			}

			logLabel.setText(logLabel.getText() + "\n --------------------------- \n");
		}
	}


	/**
	 * Updates the GUI after each round
	 */
	private void round() {
		game.cpuRound();
		updateTable();
		logRound();
	}


	/**
	 * Makes a visible card corresponding to a card object, and adds a listener for selecting
	 * the card
	 * @param c card object which the visible card is based on
	 * @return Visible card object
	 */
	private VisibleCard createVisibleCard(Card c) {
		VisibleCard kGUI = new VisibleCard(c.toString());
		playerCardsPane.getChildren().add(kGUI);
		kGUI.setOnMouseClicked(e -> { valitse(c, kGUI); });
		return kGUI;
	}


	/**
	 * Adds a card to the list of selected cards and changes the visible card object
	 * accordingly
	 * @param c card to be selected
	 * @param kGUI visible card object bound to the card
	 */
	public void valitse(Card c, VisibleCard kGUI) {
		kGUI.select();
		game.selectCard(c);
		teeTarkistukset();
	}


	/**
	 * Checks if player is allowed to play, and changes the play button accordingly
	 */
	private void teeTarkistukset() {
		if (game.checkSelection()) {
			playButton.setDisable(false);
		} else { playButton.setDisable(true); }
	}

}
