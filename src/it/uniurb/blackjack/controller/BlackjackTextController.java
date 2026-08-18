package it.uniurb.blackjack.controller;

import it.uniurb.blackjack.model.game.Blackjack;
import it.uniurb.blackjack.model.game.MoveType;
import it.uniurb.blackjack.view.BlackjackView;

// class that implements a controller for a command line version of the game
public class BlackjackTextController implements BlackjackController {
	// declaration of the fields of the class
	private Blackjack blackjack;    // model of the game
	private BlackjackView view; // view of the game in a command line version
	
	// constructor of the class
	public BlackjackTextController(final Blackjack blackjack, final BlackjackView view) {
		this.blackjack = blackjack;
		this.view = view;
	}

	public void startGame() {
		// declaration of local variables
		String playerName = this.view.askName(); // name of the player
		double balance = this.view.askBalance(); // starting balance of the player
		boolean playAgain = true;                // flag for the player if he wants to play another round
		
		// setting a new game
		this.blackjack.configureGame(this.view.askNumDecks(), this.view.askDealerType());
		// setting the player with this values
		this.blackjack.getPlayer().initPlayer(playerName, balance);
	
		
		do {
			// starting a new round for dealer and player
			this.blackjack.startRound(this.view.askChips(), this.view.askSideBet());
			// printing the first version of the table
			this.view.showStartTable(this.blackjack.getPlayer(), this.blackjack.getDealer());
			// calculating and printing the outcome of the side bet
			this.view.showSideBet(this.blackjack.getPlayer(), this.blackjack.verifySideBet());
			for (int i = 0;
				 (i < this.blackjack.getPlayer().getNumHands());
				 i++) {
				while (this.blackjack.getPlayer().getHand(i).isInGame()) {
					String   input; // next move in string format
					MoveType move = null;  // next player move
					input = this.view.askMoves();
					// selecting the correct move based on the string
					switch (input) {
						case "+":
							move = MoveType.HIT;
							break;
						case "-":
							move = MoveType.STAND;
							break;
						case "x":
							move = MoveType.DOUBLE_DOWN;
							break;
						case "/":
							move = MoveType.SPLIT;
							break;
						default:
							break;
					}
					// making the move
					this.blackjack.makeMove(move, i);
					// showing the updated table
					this.view.showNextTable(this.blackjack.getPlayer(), this.blackjack.getDealer());
				}
			} 
			// changing the turn
			this.blackjack.changeDealerTurn();
			
			// playing and showing the dealer turn
			this.blackjack.playDealerHand();
			this.view.showFinalTable(this.blackjack.getPlayer(), this.blackjack.getDealer());
			
			
			// evaluating the results and printing
			for (int i = 0;
				 (i < this.blackjack.getPlayer().getNumHands());
				 i++)
				 this.view.showOutcome(this.blackjack.getOutcome(), this.blackjack.verifyFinalOutcome(i), this.blackjack.getPlayer());
			
			// asking the player for a new game
			playAgain = this.view.askForNewRound();
		} while (playAgain &&
				 this.blackjack.getPlayer().getBalance() > 0);
	}

}
