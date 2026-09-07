package it.uniurb.blackjack.view;

import java.awt.Color;
import java.awt.event.ActionListener;

// interface that implements method for the view of the initialization menu
public interface BlackjackSwingInitView {
	// declaration of methods
	
	// method that asks the name of the player
	public String askName();
		
	// method that asks the balance of the player (max bet)
	public double askBalance();
		
	// method that asks the number of decks to use
	public int askNumDecks();
		
	// method that asks the dealer type
	public boolean askDealerType();
	
	// method that read the set name selected
	public String askCardSetType();
	
	// method that read the table color selected
	public Color askTableColor();
	
	// method that sets the listener for the confirm button
	public void setConfirmButtonListener(final ActionListener listener);
	
	// method used for showing error messages to the user
	public void showErrorMessage(final String string);
}
