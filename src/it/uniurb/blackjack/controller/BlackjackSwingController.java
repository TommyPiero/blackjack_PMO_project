package it.uniurb.blackjack.controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import it.uniurb.blackjack.model.cards.Card;
import it.uniurb.blackjack.model.game.Blackjack;
import it.uniurb.blackjack.model.game.HandFields;
import it.uniurb.blackjack.model.game.HandOutcome;
import it.uniurb.blackjack.model.game.MoveType;
import it.uniurb.blackjack.model.game.OutcomeType;
import it.uniurb.blackjack.view.AudioManager;
import it.uniurb.blackjack.view.BlackjackFrame;
import it.uniurb.blackjack.view.BlackjackFrameImpl;
import it.uniurb.blackjack.view.BlackjackSwingBetView;
import it.uniurb.blackjack.view.BlackjackSwingInitView;
import it.uniurb.blackjack.view.BlackjackSwingTableView;

public class BlackjackSwingController {

	// declaration of class' fields
	Blackjack          blackjack;  // model of the application
	BlackjackFrame     mainFrame;  // view of the application
	
	private int        activeHand; // field that records the actual active hand
	
	// class' constructor
	public BlackjackSwingController(final Blackjack blackjack, final BlackjackFrameImpl view) {
		this.blackjack = blackjack;
		this.mainFrame = view;
		this.activeHand = 0;
		AudioManager.startMenuMusic("menu_music.wav"); // add menu music file
		
		this.mainFrame.getInitScreen().setConfirmButtonListener(e -> onConfirmSetup());
        this.mainFrame.getBetScreen().setPlaceBetsListener(e -> onConfirmBet());
        
        this.mainFrame.getTableScreen().setHitListener(e -> onMove(MoveType.HIT));
        this.mainFrame.getTableScreen().setStandListener(e -> onMove(MoveType.STAND));
        this.mainFrame.getTableScreen().setDoubleListener(e -> onMove(MoveType.DOUBLE_DOWN));
        this.mainFrame.getTableScreen().setSplitListener(e -> onMove(MoveType.SPLIT));
        this.mainFrame.getBetScreen().setBackButtonListener(e -> onBackToInit());
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
		this.mainFrame.getTableScreen().showNewRoundDialog(() -> {
						this.mainFrame.getTableScreen().stopMoveTimer();
		            	this.mainFrame.getBetScreen().updateBalanceDisplay(this.blackjack.getPlayerBalance());
		            	this.mainFrame.showBetScreen();
		            	AudioManager.startMenuMusic("menu_music.wav");
		        });
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
	private void onMove(final MoveType move) {
		try {
			// if the hand is in game, play the move
			if (this.blackjack.getPlayerHands().get(activeHand).isInGame()) {
				this.blackjack.makeMove(move, activeHand);
				// timer stops because the player pressed a button or because ended his run
				this.mainFrame.getTableScreen().stopMoveTimer();
				
				// if the player takes a card, make sounds
				if (move == MoveType.HIT ||
					move == MoveType.DOUBLE_DOWN ||
					move == MoveType.SPLIT) {
	                Card newCard = this.blackjack.getPlayerCards(activeHand).getLast();
	                this.mainFrame.getTableScreen().revealSinglePlayerCard(newCard, this::afterMoveCompleted);
	            } else {
	                afterMoveCompleted();
	            }
			} else {
				afterMoveCompleted();
			}
		} catch (IllegalStateException e) {
			this.mainFrame.getTableScreen().showErrorMessage("Error: " + e.getMessage());
		}			
	}
	
	// method that choose what to do after the player stands or bust
	private void afterMoveCompleted() {
	    updateTable();
	    if (!this.blackjack.getPlayerHands().get(activeHand).isInGame()) {
	        moveToNextHand();
	    } else {
	        startPlayerMoveTimer();
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
					playDealerHandDelay();
				} else {
					checkInsuranceAndShowOutcome();
				}
	    	}
	    }
	}
	
	// method that plays dealer hand with delay
	private void playDealerHandDelay() {
		// declaration and initialization of local variables
		List<Card> allDealerCards = this.blackjack.getDealerHand().cards(); // list of dealer cards
	    Card dealerUncovered = this.blackjack.getDealerUncovCard();         // dealer's uncovered card
	    Card dealerCovered = this.blackjack.getDealerCovCard();             // dealer's covered card
	    int delayMs = 1600;											        // delay in ms for drawing cards
	    final int[] currentCardIndex = {2};									// current card index: array for moving inside the lambda expression
	    Timer dealerTimer = new Timer(delayMs, null);				  	    // timer for drawing cards with delay
	    
	    // showing the covered card with sound
	    this.mainFrame.getTableScreen().updateDealerCards(dealerUncovered, true, dealerCovered,List.of(dealerUncovered, dealerCovered));
	    this.mainFrame.getTableScreen().updateDealerScore(this.blackjack.getDealerCovCard().getBlackjackValue() + this.blackjack.getDealerUncovCard().getBlackjackValue());
	    AudioManager.playSound("deal_card.wav");
	    
	    // check outcome if dealer has only two cards
	    if (allDealerCards.size() <= 2) {
	        updateTable();
	        checkInsuranceAndShowOutcome();
	        return;
	    }
	    
	    dealerTimer.addActionListener(e -> {
	        int index = currentCardIndex[0]; 										    // current index: starting from the third card
	        int actualScore = this.blackjack.getDealerCovCard().getBlackjackValue() +
	        		          this.blackjack.getDealerUncovCard().getBlackjackValue();	// actual score of dealer hand (cov + uncov)
	        
	        if (index < allDealerCards.size()) {
	        	
	        	// taking the sub list of cards, the last one is the one of the index
	            List<Card> visibleCards = allDealerCards.subList(0, index + 1);
	          
	            actualScore += visibleCards.getLast().getBlackjackValue();
	            
	            // showing the new card and playing the sound
	            this.mainFrame.getTableScreen().updateDealerCards(dealerUncovered, true, dealerCovered, visibleCards);
	            this.mainFrame.getTableScreen().updateDealerScore(actualScore);
	            AudioManager.playSound("deal_card.wav");
	            
	            currentCardIndex[0]++;
	        } else {
	            dealerTimer.stop();
	            updateTable();
	            checkInsuranceAndShowOutcome();
	        }
	    });
	    
	    dealerTimer.setRepeats(true);
	    dealerTimer.setInitialDelay(delayMs);
	    dealerTimer.start();
	}
	
	// method that updates the table after moves
	private void updateTable() {
		// declaration and initialization of local variables
		BlackjackSwingTableView table = this.mainFrame.getTableScreen(); // table screen
		
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
        	// declaration and initialization of local variables
            double mainBet = Double.parseDouble(betView.getMainBetText()); // main bet of the player
            double sideBet = Double.parseDouble(betView.getSideBetText()); // side bet of the player
                        
            // stopping the menu music
            AudioManager.stopMenuMusic();;
            
            this.blackjack.startRound(mainBet, sideBet);
            
            List<Card> playerCards = this.blackjack.getPlayerCards(0);     // list of player cards
            Card dealerUncovered = this.blackjack.getDealerUncovCard();    // dealer uncovered card
            
            // resetting the active hand to zero
            this.activeHand = 0;
            
            // resetting the dealer score showed
            this.mainFrame.getTableScreen().updateDealerScore(0);
            
            // playing sound that simulates the pushing of chips
            AudioManager.playSound("push_chips.wav");
            
            this.mainFrame.showTableScreen();
            
            this.mainFrame.getTableScreen().revealStartingCards(playerCards, dealerUncovered, () -> {
                updateTable();
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
            });
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
		BlackjackSwingInitView init = this.mainFrame.getInitScreen(); // player initialization screen
		
        try {
        	// declaration and initialization of local variables
            int numDecks = init.askNumDecks();									  // number of decks for the game
            boolean isSoftDealer = init.askDealerType();						  // boolean for the type of the dealer
            String playerName = init.askName();									  // player's name
            double playerBalance = init.askBalance();							  // player's balance
            String cardSetType = this.mainFrame.getInitScreen().askCardSetType(); // type of card set
            Color  tableColor = this.mainFrame.getInitScreen().askTableColor();   // color of the game table
            
            // throwing an exception if the playerName's field is empty
            if (playerName.isEmpty())
                throw new IllegalArgumentException("Compile all the fields");
            // throwing an exception if the playerBalance's field is filled with a number equal or less to zero
            if (playerBalance <= 0)
                throw new IllegalArgumentException("Starting balance can't be zero!");
            // throwing an exception if the number of decks is a not valid number
            if (numDecks < 2 || numDecks > 8)
                throw new IllegalArgumentException("The number of decks must be between 2 and 8!");
            
            this.blackjack.configureGame(numDecks, isSoftDealer);
            this.blackjack.startGame(playerName, playerBalance);
            
            this.mainFrame.getTableScreen().updateSettings(numDecks, isSoftDealer);
            this.mainFrame.getTableScreen().setCardSetType(cardSetType);
            this.mainFrame.getTableScreen().setTableColor(tableColor);
            
            this.mainFrame.getBetScreen().updateBalanceDisplay(this.blackjack.getPlayerBalance());
            this.mainFrame.showBetScreen();
            
        } catch (NumberFormatException e) {
            init.showErrorMessage("You have to insert a number for the balance!");
        } catch (IllegalArgumentException e) {
            init.showErrorMessage("Error: " + e.getMessage());
        }
	}
	
	// method that checks insurance an shows the final outcome
	private void checkInsuranceAndShowOutcome() {
	    if (this.blackjack.getPlayer().isInsured()) {
	        this.mainFrame.getTableScreen().showInsuranceOutcome(
	                this.blackjack.verifyInsurance(),
	                this::showFinalOutcome);
	    } else {
	        showFinalOutcome();
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
	        
	        // playing the winning or losing sound
		    if (outcome == OutcomeType.PLAY_WIN || outcome == OutcomeType.PLAY_BJ) {
		        AudioManager.playSound("win_sound.wav");
		    } else if (outcome == OutcomeType.PLAY_LOSE) {
		        AudioManager.playSound("lose_sound.wav");
		    } else {
		    	AudioManager.playSound("draw_sound.wav");
		    }
	    }

	    this.mainFrame.getTableScreen().showMainBetOutcome(results, this::askForNewRound);
	}
}
