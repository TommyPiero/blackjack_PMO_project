package it.uniurb.blackjack.controller;


import it.uniurb.blackjack.model.cards.Card;
import it.uniurb.blackjack.model.cards.PerfectPairs;
import it.uniurb.blackjack.model.game.Blackjack;
import it.uniurb.blackjack.model.game.MoveType;
import it.uniurb.blackjack.view.BlackjackView;

// class that implements a controller for a command line version of the game
public class BlackjackTextController {
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
					mainBet = this.view.askChips(this.blackjack.getPlayerBalance());
					sideBet = this.view.askSideBet();
					// setting a new round with the checked bets
					this.blackjack.startRound(mainBet, sideBet);
					isBetValid = true;
				} catch (IllegalArgumentException e) {
					this.view.showErrorMessage("Error: " + e.getMessage());
				}
			}
			// declaration and initialization of fields to pass to view's methods
			double playerSideBet = this.blackjack.getPlayerSideBet();               // side bet linked to the hand
			Card   dealerUncovCard = this.blackjack.getDealerUncovCard();           // uncovered dealer's card
			int    dealerStartHandScore = this.blackjack.getDealerStartHandScore(); // dealer's start hand score
			PerfectPairs perfPairLevel = this.blackjack.getPerfPairLevel();         // perfect pair level
			// starting table state
			TableState startTableState = new TableState(this.blackjack.getPlayerBalance(),
												   		playerSideBet,
												   		this.blackjack.getPlayerHands(),
												   		dealerUncovCard,
												   		dealerStartHandScore,
												   		perfPairLevel,
												   		this.blackjack.getNumPlayerHands(),
												   		this.blackjack.getDealerHand());
			
			// printing the first version of the table
			this.view.showStartTable(startTableState);
			// calculating and printing the outcome of the side bet
			this.view.showSideBet(startTableState, this.blackjack.verifySideBet());
			// asking for the insurance if the dealer as an ace as uncovered card
			if (startTableState.dealerUncoveredCard().isAnAce()) {
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
				 (i < this.blackjack.getNumPlayerHands());
				 i++) {
				while (this.blackjack.getPlayerHands().get(i).isInGame()) {
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
					// declaration and initialization of the record for the actual table
					TableState newTableState = new TableState(this.blackjack.getPlayerBalance(),
															  playerSideBet,
															  this.blackjack.getPlayerHands(),
															  dealerUncovCard,
															  dealerStartHandScore,
															  perfPairLevel,
					   										  this.blackjack.getNumPlayerHands(),
					   										  this.blackjack.getDealerHand());
					
					// showing the updated table
					this.view.showNextTable(newTableState);					
				}
			}
			// changing the turn
			this.blackjack.changeDealerTurn();
			
			// playing and showing the dealer turn
			this.blackjack.playDealerHand();
			
			// declaration and initialization of the record for the final table
			TableState finalTableState = new TableState(this.blackjack.getPlayerBalance(),
														playerSideBet,
														this.blackjack.getPlayerHands(),
														dealerUncovCard,
														dealerStartHandScore,
														perfPairLevel,
			   										    this.blackjack.getNumPlayerHands(),
			   										    this.blackjack.getDealerHand());
			
			this.view.showFinalTable(finalTableState);
			
			
			// evaluating the results and printing
			for (int i = 0;
				 (i < this.blackjack.getNumPlayerHands());
				 i++)
				 this.view.showOutcome(finalTableState, this.blackjack.verifyFinalOutcome(i), this.blackjack.getOutcome(), i);
			
			// asking the player for a new game
			playAgain = this.view.askForNewRound(this.blackjack.getPlayer());
		} while (playAgain &&
				 this.blackjack.getPlayerBalance() > 0);
	}

}
