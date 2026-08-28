package it.uniurb.blackjack.view;

import java.awt.Color;
import java.awt.event.ActionListener;

// interface that implements method for the view of the initialization menu
public interface BlackjackSwingInitView extends BlackjackView {
	// declaration of methods
	
	// method that read the set name selected
	public String askCardSetType();
	
	// method that read the table color selected
	public Color askTableColor();
	
	// method that sets the listener for the confirm button
	public void setConfirmButtonListener(final ActionListener listener);
	
}
