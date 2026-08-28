package it.uniurb.blackjack.view;

// general interface with general methods and methods for the initialization
public interface BlackjackView {
	// declaration of methods
	
	// method that asks the name of the player
	public String askName();
	
	// method that asks the balance of the player (max bet)
	public double askBalance();
	
	// method that asks the number of decks to use
	public int askNumDecks();
	
	// method that asks the dealer type
	public boolean askDealerType(); 
	
	// method used for showing error messages to the user
	public void showErrorMessage(final String string);
}
