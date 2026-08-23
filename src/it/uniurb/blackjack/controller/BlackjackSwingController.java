package it.uniurb.blackjack.controller;

import it.uniurb.blackjack.model.game.Blackjack;
import it.uniurb.blackjack.view.BlackjackFrame;
import it.uniurb.blackjack.view.BlackjackSwingBetView;
import it.uniurb.blackjack.view.BlackjackSwingMainView;

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
	}
	
	// method for getting bets
	private void onConfirmBet() {
		// declaration and initialization of local variables
		BlackjackSwingBetView betView = this.mainFrame.getBetScreen();
        try {
            double mainBet = Double.parseDouble(betView.getMainBetText());
            double sideBet = Double.parseDouble(betView.getSideBetText());

            this.blackjack.startRound(mainBet, sideBet);
            // here will be the change of the screen to the table

        } catch (NumberFormatException e) {
        } catch (IllegalArgumentException e) {
        }
	}

	// method for initializing player and game
	private void onConfirmSetup() {
		BlackjackSwingMainView init = this.mainFrame.getInitScreen();
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

            // setup ok: aggiorno il saldo sulla prossima schermata e ci passo
            this.mainFrame.getBetScreen().updateBalanceDisplay(this.blackjack.getPlayerBalance());
            this.mainFrame.showBetScreen();
            
        } catch (NumberFormatException e) {
            init.showErrorMessage("You have to insert a number for the balance!");
        } catch (IllegalArgumentException e) {
            init.showErrorMessage("Error: " + e.getMessage());
        }
	}
}
