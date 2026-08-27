package it.uniurb.blackjack.controller;

import java.util.ArrayList;
import java.util.List;

import it.uniurb.blackjack.model.game.Blackjack;
import it.uniurb.blackjack.model.game.HandFields;
import it.uniurb.blackjack.model.game.HandOutcome;
import it.uniurb.blackjack.model.game.MoveType;
import it.uniurb.blackjack.model.game.OutcomeType;
import it.uniurb.blackjack.view.BlackjackFrame;
import it.uniurb.blackjack.view.BlackjackSwingBetView;
import it.uniurb.blackjack.view.BlackjackSwingMainView;
import it.uniurb.blackjack.view.BlackjackSwingTableViewImpl;

public class BlackjackSwingController {

	// declaration of class' fields
	Blackjack blackjack;       // model of the application
	BlackjackFrame mainFrame;  // view of the application
	
	private int activeHand;    // field that records the actual active hand
	
	// class' constructor
	public BlackjackSwingController(final Blackjack blackjack, final BlackjackFrame view) {
		this.blackjack = blackjack;
		this.mainFrame = view;
		this.activeHand = 0;
		
		this.mainFrame.getInitScreen().setConfirmButtonListener(e -> onConfirmSetup());
        this.mainFrame.getBetScreen().setPlaceBetsListener(e -> onConfirmBet());
        
        this.mainFrame.getTableScreen().setHitListener(e -> onMove(MoveType.HIT));
        this.mainFrame.getTableScreen().setStandListener(e -> onMove(MoveType.STAND));
        this.mainFrame.getTableScreen().setDoubleListener(e -> onMove(MoveType.DOUBLE_DOWN));
        this.mainFrame.getTableScreen().setSplitListener(e -> onMove(MoveType.SPLIT));
        this.mainFrame.getBetScreen()
        .setBackButtonListener(e -> onBackToInit());
	}
	
	// method that asks the player for insurance
	private void askForInsurance() {
		// shows the dialog for the player insurance
		this.mainFrame.getTableScreen().showInsuranceTimerDialog(20,
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
	
	// method that permits to force a stand when the player's timer ends
	private void forceStand() {
		onMove(MoveType.STAND);
	}
	
	// method that permit to start the player timer for a move
	private void startPlayerMoveTimer() {
		this.mainFrame.getTableScreen().startMoveTimer(20, this::forceStand);
	}
	
	// method that permits to make a move and save the changes
	private void onMove(MoveType move) {
		try {
			// if the hand is in game, play the move
			if (this.blackjack.getPlayerHands().get(activeHand).isInGame()) {
				this.blackjack.makeMove(move, activeHand);
				// timer stops because the player pressed a button or because ended his run
				this.mainFrame.getTableScreen().stopMoveTimer();
			}
			updateTable();
			// if the hand is not more in game after the move, change the hand
			if (!this.blackjack.getPlayerHands().get(activeHand).isInGame()) {
				moveToNextHand();
			}
		} catch (IllegalStateException e) {
			this.mainFrame.getTableScreen().showErrorMessage("Error: " + e.getMessage());
		}			
	}

	// method that permits to move to the next hand
	private void moveToNextHand() {

	    if ((this.activeHand + 1) < (this.blackjack.getPlayerHands().size())) {

	        this.activeHand++;

	        updateTable();

	    } else {

	        // terminated all hands
	    	if (!this.blackjack.getPlayerHands().get(activeHand).isInGame()) {
				if (!this.blackjack.getPlayerHands().get(activeHand).isBust()) {
					this.blackjack.playDealerHand();
				}
				updateTable();
				if (this.blackjack.getPlayer().isInsured()) {
					this.mainFrame.getTableScreen().showInsuranceOutcome(
							this.blackjack.verifyInsurance(),
							this::showFinalOutcome);
				} else {
					showFinalOutcome();
				}
	    	}
	    }
	}
	
	// method that updates the table after moves
	private void updateTable() {
		// declaration and initialization of local variables
		BlackjackSwingTableViewImpl table = this.mainFrame.getTableScreen(); // table screen
		
		// updating values for balance and bets
		table.updateBalance(this.blackjack.getPlayerBalance());
		table.updateBet(this.blackjack.getPlayerBet(0), this.blackjack.getPlayerSideBet());
		
		// hands update
	    table.updatePlayerHands(this.blackjack.getPlayerHands(), this.activeHand);

	    // dealer's card: show the covered one only if the round is finished
	    boolean roundFinished = this.blackjack.isFinished();
	    table.updateDealerCards(
	        this.blackjack.getDealerUncovCard(),
	        roundFinished,
	        roundFinished ? this.blackjack.getDealerCovCard() : null,
	        this.blackjack.getDealerHand().cards()
	    );
	    
	    // updating the dealer's score
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
            
            // resetting the active hand to zero
            this.activeHand = 0;
            
            this.mainFrame.showTableScreen();
                        
            updateTable();
            
            // starting the player timer
            startPlayerMoveTimer();
            
            // showing the screen for the result of side bets
            if (this.blackjack.getPlayerSideBet() != 0) {
            	this.mainFrame.getTableScreen().showSideBetOutcome(this.blackjack.verifySideBet(), this.blackjack.getPerfPairLevel());
            }
            // showing the screen that asks for insurance if the uncovered card is an ace
            if (this.blackjack.getDealerUncovCard().isAnAce()) {
            	askForInsurance();
            }
            
            // if the hand is a blackjack finish instantly the player's turn
            if (this.blackjack.getPlayerHands().get(0).isBlackjack()) {
            	this.blackjack.playDealerHand();
            	updateTable();
            	showFinalOutcome();
            }
            
        } catch (NumberFormatException e) {
            betView.showErrorMessage("You have to insert a number for the bets!");
        } catch (IllegalArgumentException e) {
            betView.showErrorMessage("Error: " + e.getMessage());
        }
	}

	// method that permits to get back to the starting screen
	private void onBackToInit() {
	    this.mainFrame.showInitScreen();;
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
            String cardSetType = this.mainFrame.getInitScreen().askCardSetType();
            
            if (playerName.isEmpty())
                throw new IllegalArgumentException("Compile all the fields");
            if (playerBalance <= 0)
                throw new IllegalArgumentException("Starting balance can't be zero!");
            if (numDecks < 2 || numDecks > 8)
                throw new IllegalArgumentException("The number of decks must be between 2 and 8!");
            
            this.blackjack.configureGame(numDecks, isSoftDealer);
            this.blackjack.startGame(playerName, playerBalance);
            
            this.mainFrame.getTableScreen().updateSettings(numDecks, isSoftDealer);
            this.mainFrame.getTableScreen().setCardSetType(cardSetType);
            
            this.mainFrame.getBetScreen().updateBalanceDisplay(this.blackjack.getPlayerBalance());
            this.mainFrame.showBetScreen();
            
        } catch (NumberFormatException e) {
            init.showErrorMessage("You have to insert a number for the balance!");
        } catch (IllegalArgumentException e) {
            init.showErrorMessage("Error: " + e.getMessage());
        }
	}
	
	// method that shows the final outcome
	private void showFinalOutcome() {
		// declaration of local variables
		List<HandFields> hands = this.blackjack.getPlayerHands(); // list of player's hands
	    List<HandOutcome> results = new ArrayList<>();            // list of hand's results

	    for (int i = 0; i < hands.size(); i++) {
	        double wonMoney = this.blackjack.verifyFinalOutcome(i);
	        OutcomeType outcome = this.blackjack.getOutcome();
	        results.add(new HandOutcome(hands.get(i).bet(), wonMoney, outcome));
	    }

	    this.mainFrame.getTableScreen().showMainBetOutcome(results, this::askForNewRound);
	}
}
