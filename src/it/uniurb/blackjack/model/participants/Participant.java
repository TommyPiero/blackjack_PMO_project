package it.uniurb.blackjack.model.participants;

import it.uniurb.blackjack.model.cards.Hand;
import it.uniurb.blackjack.model.cards.Shoe;

public interface Participant {
	// declaration of methods
	
	// getter method for the name of the participant
	public String getName();
	
	// getter method for the hand of the participant
	public Hand getHand();
	
	// method for getting a card 
	public void hit(final Shoe shoe);
}
