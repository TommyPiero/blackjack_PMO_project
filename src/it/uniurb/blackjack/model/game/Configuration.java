package it.uniurb.blackjack.model.game;

public interface Configuration {
	// declaration of methods
	
	// getter method for the number of decks configuration
	public int getNumDecks();
	
	// getter method for the state of the dealer (hit  or stand on soft 17)
	public boolean isDealerHitSoft();
}
