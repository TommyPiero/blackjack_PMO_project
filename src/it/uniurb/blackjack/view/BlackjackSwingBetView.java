package it.uniurb.blackjack.view;

import java.awt.event.ActionListener;

// interface for the class that will implement the panel for the bets
public interface BlackjackSwingBetView {
	// declaration of methods
	
	// method that updates in real time the counter of the balance
    public void updateBalanceDisplay(final double currentBalance);
    
    // getter method for the main bet text
    public String getMainBetText();
    
    // getter method for the side bet text
    public String getSideBetText();
    
    // setter method for the listener
    public void setPlaceBetsListener(final ActionListener listener);
}
