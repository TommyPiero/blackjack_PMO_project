package it.uniurb.blackjack.controller;

import it.uniurb.blackjack.model.game.Blackjack;
import it.uniurb.blackjack.view.BlackjackTextView;

// class that implements a controller for a command line version of the game
public class BlackjackTextController implements BlackjackController {
	// declaration of the fields of the class
	private Blackjack blackjack;    // model of the game
	private BlackjackTextView view; // view of the game in a command line version
	
	// constructor of the class
	public BlackjackTextController(final Blackjack blackjack, final BlackjackTextView view) {
		this.blackjack = blackjack;
		this.view = view;
	}

	public void startGame() {
		// declaration of local variables
		String playerName = this.view.askName(); // name of the player
		double balance = this.view.askBalance(); // starting balance of the player
		// setting the player with this values
		this.blackjack.getPlayer().initPlayer(playerName, balance);
		// starting a new round for dealer and player
		this.blackjack.startRound(this.view.askChips(), this.view.askSideBet());
		// printing the first version of the table
		this.view.showStartTable(this.blackjack.getPlayer(), this.blackjack.getDealer());
		
		// WIP sequence of events for the round
	}

	public void endGame() {

	}

}
