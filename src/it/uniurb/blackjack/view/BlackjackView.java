package it.uniurb.blackjack.view;

import it.uniurb.blackjack.model.game.OutcomeType;
import it.uniurb.blackjack.model.participants.Dealer;
import it.uniurb.blackjack.model.participants.Player;

// interface for the view of the game, it contains the principal methods that shows parts of the game
public interface BlackjackView {
	// declaration of methods
	
	// method that asks the name of the player
	public String askName();
	
	// method that asks the balance of the player (max bet)
	public double askBalance();
	
	// method that shows and asks chips (5, 10, 25, 50) for the bet
	public double askChips(final Player player);
	
	// method that asks a side bet to the player
	public double askSideBet();
	
	// method that asks the number of decks to use
	public int askNumDecks();
	
	// method that asks the dealer type
	public boolean askDealerType(); 
	
	// method that asks for insurance if the round permits it
	public boolean askInsurance();
	
	// method that shows the table (cards of player and dealer)
	public void showStartTable(final Player player, final Dealer dealer);

	// method that incrementally shows the new table with updates of cards
	public void showNextTable(final Player player, final Dealer dealer);
	
	// method that shows the final table with all the uncovered card
	public void showFinalTable(final Player player, final Dealer dealer);
	
	// method that shows and asks the possible moves
	public String askMoves();
	
	// method that shows the outcome of sideBets
	public void showSideBet(final Player player, final double wonMoney);
	
	// method that shows the outcome of the round
	public void showOutcome(final double wonBet, final OutcomeType outcome, final Player player);
	
	// method that asks the player if he wants to play another round
	public boolean askForNewRound(final Player player);

	// method used for showing error messages to the user
	public void showErrorMessage(final String string);
}
