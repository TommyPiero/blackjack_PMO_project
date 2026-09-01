package it.uniurb.blackjack.view;


import it.uniurb.blackjack.controller.TableState;
import it.uniurb.blackjack.model.game.OutcomeType;
import it.uniurb.blackjack.model.participants.Player;

// interface for the view of the game, it contains the principal methods that shows parts of the game
public interface BlackjackTextView {
	// declaration of methods

	// method that asks the name of the player
	public String askName();
	
	// method that asks the balance of the player (max bet)
	public double askBalance();
	
	// method that asks the number of decks to use
	public int askNumDecks();
	
	// method that asks the dealer type
	public boolean askDealerType(); 
	
	// method that shows and asks chips (5, 10, 25, 50) for the bet
	public double askChips(final double balance);
	
	// method that asks a side bet to the player
	public double askSideBet();
	
	// method that asks for insurance if the round permits it
	public boolean askInsuranceWithTimeOut();
	
	// method that shows the table (cards of player and dealer)
	public void showStartTable(final TableState tableState);

	// method that incrementally shows the new table with updates of cards
	public void showNextTable(final TableState tableState);
	
	// method that shows the final table with all the uncovered card
	public void showFinalTable(final TableState tableState);
	
	// method that shows and asks the possible moves with a time out of 20 seconds
	public String askMoveWithTimeOut();
	
	// method that shows the outcome of sideBets
	public void showSideBet(final TableState tableState, final double winMoney);
	
	// method that shows the outcome of insurance
	public void showInsuranceOutcome(final boolean isDealBlackjack, final double winMoney);
	
	// method that shows the outcome of the round
	public void showOutcome(final TableState tableState, final double winBet, final OutcomeType outcome, final int i);
	
	// method that asks the player if he wants to play another round
	public boolean askForNewRound(final Player player);

	// method used for showing error messages to the user
	public void showErrorMessage(final String string);
}
