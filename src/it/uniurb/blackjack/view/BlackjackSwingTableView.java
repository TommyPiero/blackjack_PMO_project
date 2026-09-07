package it.uniurb.blackjack.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JPanel;

import it.uniurb.blackjack.model.cards.Card;
import it.uniurb.blackjack.model.cards.PerfectPairs;
import it.uniurb.blackjack.model.game.HandFields;
import it.uniurb.blackjack.model.game.HandOutcome;

// interface for the methods that will show the game table
public interface BlackjackSwingTableView {
	// declaration of methods
	
	// setter method for the card set type
	public void setCardSetType(final String cardSetType);
	
	// setter method for the table color
	public void setTableColor(final Color tableColor);
	
	// method that updates the main game settings
	public void updateSettings(final int numDecks, final boolean hitOnSoft);
	
	// method that updates dealer's cards
	public void updateDealerCards(final Card uncoveredCard, final boolean showCoveredCard, final Card coveredCard, final List<Card> dealerCards);
		
	// method that updates dealer's score
	public void updateDealerScore(final int score);
	
	// method that updates player's hands
	public void updatePlayerHands(final List<HandFields> hands, final int activeHand);
		
	// method that updates balance
	public void updateBalance(final double balance);
	
	// method that updates bets
	public void updateBet(final double bet, final double sideBet);
		
	// method that reveals cards with delay
	public void revealCardsWithDelay(final JPanel cardPanel, final List<Card> cards, final int delayMillis, final Runnable onComplete);
	
	// method that reveals starting cards
	public void revealStartingCards(final List<Card> playerCards, final Card dealerUncovered, final Runnable onComplete);
	
	// method that reveals a single player card
	public void revealSinglePlayerCard(final Card card, final Runnable onComplete);
	
	// method that reveals a single dealer card
	public void revealSingleDealerCard(final Card card, final Runnable onComplete);
	
	// method that shows a screen for the outcome of the side bet
	public void showSideBetOutcome(final double winMoney, final PerfectPairs sideBetLevel);
	
	// method that starts the timer for making a move
	public void startMoveTimer(final int seconds, final Runnable onTimeout);
	
	// method that stops the timer for making a move, used by the controller when the player press a button in time
	public void stopMoveTimer();
	
	// method that shows a screen for the outcome of the main bet
	public void showMainBetOutcome(final List<HandOutcome> outcomes, final Runnable onContinue);
	
	// method that shows the dialog that asks the player for a new round
	public void showNewRoundDialog(final Runnable onYes);
	
	// method that shows the dialog that asks the player for the insurance
	public void showInsuranceTimerDialog(final int seconds, final Runnable onYes);
	
	// method that shows the outcome of the insurance
	public void showInsuranceOutcome(final double winMoney, final Runnable onContinue);
	
	// method that set a listener for the hit button	
	public void setHitListener(final ActionListener listener);
		
	// method that set a listener for the stand button	
	public void setStandListener(final ActionListener listener);
	
	// method that set a listener for the double down button	
	public void setDoubleListener(final ActionListener listener);
	
	// method that set a listener for the split button	
	public void setSplitListener(final ActionListener listener);

	// method for showing errors
	public void showErrorMessage(final String string);
}
