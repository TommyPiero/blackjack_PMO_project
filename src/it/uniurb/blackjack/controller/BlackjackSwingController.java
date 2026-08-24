package it.uniurb.blackjack.controller;

import it.uniurb.blackjack.model.game.Blackjack;
import it.uniurb.blackjack.model.game.MoveType;
import it.uniurb.blackjack.view.BlackjackFrame;
import it.uniurb.blackjack.view.BlackjackSwingBetView;
import it.uniurb.blackjack.view.BlackjackSwingMainView;
import it.uniurb.blackjack.view.BlackjackSwingTableViewImpl;

public class BlackjackSwingController implements BlackjackController {

	// declaration of class' fields
	Blackjack blackjack;       // model of the application
	BlackjackFrame mainFrame;  // view of the application
	
	// class' constructor
	public BlackjackSwingController(final Blackjack blackjack, final BlackjackFrame view) {
		this.blackjack = blackjack;
		this.mainFrame = view;
		
		this.mainFrame.getInitScreen().setConfirmButtonListener(e -> onConfirmSetup());
        this.mainFrame.getBetScreen().setPlaceBetsListener(e -> onConfirmBet());
        
        this.mainFrame.getTableScreen().setHitListener(e -> onMove(MoveType.HIT));
        this.mainFrame.getTableScreen().setStandListener(e -> onMove(MoveType.STAND));
        this.mainFrame.getTableScreen().setDoubleListener(e -> onMove(MoveType.DOUBLE_DOWN));
        this.mainFrame.getTableScreen().setSplitListener(e -> onMove(MoveType.SPLIT));
        
        
	}
	
	// method that asks the player for insurance
	private void askForInsurance() {
		// shows the dialog for the player insurance
		this.mainFrame.getTableScreen().showInsuranceDialog(
				() -> {
					this.blackjack.getPlayer().insure();
					updateTable();
		});
	}
	
	// method that permits to ask for a new round
	private void askForNewRound() {
	    // shows the dialog for a new round and then on yes shows the bet screen again
		this.mainFrame.getTableScreen().showNewRoundDialog(
		        () -> {
		            this.mainFrame.getBetScreen().updateBalanceDisplay(
		                this.blackjack.getPlayerBalance()
		            );
		            this.mainFrame.showBetScreen();
		        }
		    );
	}
	
	private void onMove(MoveType move) {
		// for now we always act on hand 0: multi-hand handling (after a split) will be added later
		// declaration and initialization of local variables
		int activeHand = 0; // active hand

		try {
			if (this.blackjack.getPlayerHands().get(activeHand).isInGame()) {
				this.blackjack.makeMove(move, activeHand);
			}
			updateTable();
		} catch (IllegalStateException e) {
			this.mainFrame.getTableScreen().showErrorMessage("Error: " + e.getMessage());
		}
		
		if (!this.blackjack.getPlayerHands().get(activeHand).isInGame()) {
			if (!this.blackjack.getPlayerHands().get(activeHand).isBust()) {
				this.blackjack.playDealerHand();
			}
			updateTable();
			if (this.blackjack.getPlayer().isInsured()) {
				this.mainFrame.getTableScreen().showInsuranceOutcome(
						this.blackjack.verifyInsurance(),
						() -> this.mainFrame.getTableScreen().showMainBetOutcome(
								this.blackjack.verifyFinalOutcome(0),
								this.blackjack.getOutcome(),
								this::askForNewRound));
			} else {
				this.mainFrame.getTableScreen().showMainBetOutcome(
						this.blackjack.verifyFinalOutcome(0),
						this.blackjack.getOutcome(),
						this::askForNewRound);
			}
				
		}
			
	}

	private void updateTable() {
		// declaration and initialization of local variables
		BlackjackSwingTableViewImpl table = this.mainFrame.getTableScreen(); // table screen
		
		// updating values for balance and bets
		table.updateBalance(this.blackjack.getPlayerBalance());
		table.updateBet(this.blackjack.getPlayerBet(0), this.blackjack.getPlayerSideBet());
		
		// cards update
		// player's cards (hand 0, for now)
	    table.updatePlayerCards(this.blackjack.getPlayerCards(0), this.blackjack.getPlayerScore(0));

	    // dealer's card: show the covered one only if the round is finished
	    boolean roundFinished = this.blackjack.isFinished();
	    table.updateDealerCards(
	        this.blackjack.getDealerUncovCard(),
	        roundFinished,
	        roundFinished ? this.blackjack.getDealerCovCard() : null,
	        this.blackjack.getDealerHand().cards()
	    );
	    
	    table.updateDealerScore(roundFinished ? this.blackjack.getDealerHand().score() :
	    									    this.blackjack.getDealerStartHandScore());
	}

	// method for getting bets
	private void onConfirmBet() {
		// declaration and initialization of local variables
		BlackjackSwingBetView betView = this.mainFrame.getBetScreen(); // bets screen
		
        try {
            double mainBet = Double.parseDouble(betView.getMainBetText());
            double sideBet = Double.parseDouble(betView.getSideBetText());
                        
            this.blackjack.startRound(mainBet, sideBet);
            
            this.mainFrame.showTableScreen();
                        
            updateTable();
            
            // showing the screen for the result of side bets
            if (this.blackjack.getPlayerSideBet() != 0) {
            	this.mainFrame.getTableScreen().showSideBetOutcome(this.blackjack.verifySideBet(), this.blackjack.getPerfPairLevel());
            }
            // showing the screen that asks for insurance if the uncovered card is an ace
            if (this.blackjack.getDealerUncovCard().isAnAce()) {
            	askForInsurance();
            }
            
            if (this.blackjack.getPlayerHands().get(0).isBlackjack()) {
            	this.blackjack.playDealerHand();
            	updateTable();
            	this.mainFrame.getTableScreen().showMainBetOutcome(this.blackjack.verifyFinalOutcome(0),
            			                                           this.blackjack.getOutcome(),
            			                                           this::askForNewRound);
            }
            
        } catch (NumberFormatException e) {
            betView.showErrorMessage("You have to insert a number for the bets!");
        } catch (IllegalArgumentException e) {
            betView.showErrorMessage("Error: " + e.getMessage());
        }
	}

	// method for initializing player and game
	private void onConfirmSetup() {
		// declaration and initialization of local variables
		BlackjackSwingMainView init = this.mainFrame.getInitScreen(); // player initialization screen
		
        try {
            int numDecks = init.askNumDecks();
            boolean isSoftDealer = init.askDealerType();
            String playerName = init.askName();
            double playerBalance = init.askBalance();
            
            if (playerName.isEmpty())
                throw new IllegalArgumentException("Compile all the fields");
            if (playerBalance <= 0)
                throw new IllegalArgumentException("Starting balance can't be zero!");
            if (numDecks < 2 || numDecks > 8)
                throw new IllegalArgumentException("The number of decks must be between 2 and 8!");
            
            this.blackjack.configureGame(numDecks, isSoftDealer);
            this.blackjack.startGame(playerName, playerBalance);

            this.mainFrame.getBetScreen().updateBalanceDisplay(this.blackjack.getPlayerBalance());
            this.mainFrame.showBetScreen();
            
        } catch (NumberFormatException e) {
            init.showErrorMessage("You have to insert a number for the balance!");
        } catch (IllegalArgumentException e) {
            init.showErrorMessage("Error: " + e.getMessage());
        }
	}
}
