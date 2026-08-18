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
			// getting the values of bet and side bet from the view catching possible errors
			
			// declaration of local variables
			double  mainBet = 0.0;          // chips for the normal bet
			double  sideBet = -1.0;         // chips for the side bet
			boolean isBetValid = false; // boolean value, checks if input is valid or not
			
			// getting main bet value
			while (!isBetValid) {
				try {
					// asking for the main bet and the side bet
					mainBet = this.view.askChips(this.blackjack.getPlayer());
					sideBet = this.view.askSideBet();
					// setting a new round with the checked bets
					this.blackjack.startRound(mainBet, sideBet);
					isBetValid = true;
				} catch (IllegalArgumentException e) {
					this.view.showErrorMessage("Error: " + e.getMessage());
				}
			}
			
			// printing the first version of the table
			this.view.showStartTable(this.blackjack.getPlayer(), this.blackjack.getDealer());
			// calculating and printing the outcome of the side bet
			this.view.showSideBet(this.blackjack.getPlayer(), this.blackjack.verifySideBet());
			// asking for the insurance if the dealer as an ace as uncovered card
			if (this.blackjack.getDealer().getUncoveredCard().isAnAce()) {
				try {
					if (this.view.askInsurance()) {
						this.blackjack.getPlayer().insure();
						this.blackjack.verifyInsurance();
					}
				} catch (IllegalStateException e) {
						this.view.showErrorMessage("Error: " + e.getMessage() + "\n you wont be able to insure!");
					}
			}

			for (int i = 0;
				 (i < this.blackjack.getPlayer().getNumHands());
				 i++) {
				while (this.blackjack.getPlayer().getHand(i).isInGame()) {
					String   input;               // next move in string format
					MoveType move = null;         // next player move
					boolean  isMoveValid = false; // bool that says if a move is valid or not
					
					while (!isMoveValid) {
						try {
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
							isMoveValid = true;
						} catch (IllegalStateException e) {
							this.view.showErrorMessage("Error: " + e.getMessage());
						}
					}
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
				 this.view.showOutcome(this.blackjack.verifyFinalOutcome(i), this.blackjack.getOutcome(), this.blackjack.getPlayer());
			
			// asking the player for a new game
			playAgain = this.view.askForNewRound(this.blackjack.getPlayer());
		} while (playAgain &&
				 this.blackjack.getPlayer().getBalance() > 0);
	}

}
