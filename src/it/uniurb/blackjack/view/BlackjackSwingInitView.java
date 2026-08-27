package it.uniurb.blackjack.view;

import java.awt.Color;
import java.awt.event.ActionListener;

// interface that implements method for the view of the initialization menu
public interface BlackjackSwingInitView {
	// declaration of methods
	
	// method that read the name field
	public String askName();
	
	// method that read the balance field 
	public double askBalance();
	
	// method that read the number of decks field
	public int askNumDecks();
	
	// method that read the dealer type field
	public boolean askDealerType();
	
	// method that read the set name selected
	public String askCardSetType();
	
	// method that read the table color selected
	public Color askTableColor();
	
	// method that sets the listener for the confirm button
	public void setConfirmButtonListener(final ActionListener listener);
	
	// method that shows an error message
	public void showErrorMessage(final String string);
}
