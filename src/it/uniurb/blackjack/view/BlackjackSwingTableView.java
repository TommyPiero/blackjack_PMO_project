package it.uniurb.blackjack.view;

import java.awt.event.ActionListener;
import java.util.List;

import it.uniurb.blackjack.model.cards.Card;

// interface for the methods that will show the game table
public interface BlackjackSwingTableView {
	// declaration of methods
	
	// method that updates dealer's cards
	public void updateDealerCards(final Card uncoveredCard, final boolean showCoveredCard, final Card coveredCard);
		
	// method that updates dealer's score
	public void updateDealerScore(final int score);
	
	// method that updates player's cards
	public void updatePlayerCards(final List<Card> cards, final int score);
		
	// method that updates balance
	public void updateBalance(final double balance);
	
	// method that updates bets
	public void updateBet(final double bet, final double sideBet);
		
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
