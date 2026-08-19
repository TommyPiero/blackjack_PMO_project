package it.uniurb.blackjack.controller;

import it.uniurb.blackjack.model.game.Blackjack;
import it.uniurb.blackjack.view.BlackjackSwingMainView;

public class BlackjackSwingController implements BlackjackController {

	// declaration of class' fields
	Blackjack blackjack; // model of the application
	BlackjackSwingMainView view;  // view of the application
	
	// class' constructor
	public BlackjackSwingController(final Blackjack blackjack, final BlackjackSwingMainView view) {
		this.blackjack = blackjack;
		this.view = view;
		
		this.view.setConfirmButtonListener(e -> startGame());
	}
	
	public void startGame() {
		// starting a new session

		// declaration of local variables
		String  playerName;    // name of the player
		double  playerBalance; // starting balance of the player
		int     numDecks;      // number of decks to use
		boolean isSoftDealer;  // flag that record if the dealer is a soft dealer or not
		
		try {
			// asking for the number of decks and the type of the dealer
			numDecks = this.view.askNumDecks();
			isSoftDealer = this.view.askDealerType();
			playerName = this.view.askName();
			playerBalance = this.view.askBalance();
			
			if (playerName.isEmpty()) {
                throw new IllegalArgumentException("Compile all the fields");
            }
			
			if (playerBalance <= 0) {
                throw new IllegalArgumentException("Starting balance can't be zero!");
            }
            if (numDecks < 2 || numDecks > 8) {
                throw new IllegalArgumentException("The number of decks must be between 2 and 8!");
            }
			
			this.blackjack.configureGame(numDecks, isSoftDealer);
			this.blackjack.startGame(playerName, playerBalance);
			
		} catch (NumberFormatException e) {
			view.showErrorMessage("You have to insert a number for the balance!");
		} catch (IllegalArgumentException e) {
			this.view.showErrorMessage("Error:" + e.getMessage());
		}
	}

}
