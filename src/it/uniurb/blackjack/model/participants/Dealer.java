package it.uniurb.blackjack.model.participants;

import it.uniurb.blackjack.model.cards.Card;
import it.uniurb.blackjack.model.cards.Hand;

// interface that declare the methods of the class dealer
// this class will shape the concept of the dealer of the game
public interface Dealer {

	// getter method for dealer's name
	public String getName();
	
	// getter method for dealer's hand
	public Hand getHand();

	// getter method for the hit on soft 17 configuration of the dealer
	public boolean hitOnSoft();
	
	// method that prepares the dealer for a new round
	public void newRound();

	// method that implements the hit move for the dealer
	public void hit(final Card card);
	
	// method that verifies if the dealer is in game following game rules
	public boolean isInGame(final boolean dealerHitSoft);

	// getter method for the uncovered card of the dealer (the second one)
	public Card getUncoveredCard();
	
	// getter method for the covered card of the dealer (the first one)
	public Card getCoveredCard();
}
