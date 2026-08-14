package it.uniurb.blackjack.model.participants;

import it.uniurb.blackjack.model.cards.Card;
import it.uniurb.blackjack.model.cards.Hand;
import it.uniurb.blackjack.model.cards.HandImpl;

// class that create the model of the dealer of the game
public class Dealer implements Participant {
	// declaration of the fields of the class
	private String  name;      // the name of the dealer
	private Hand    hand;      // hand of the dealer
	private boolean hitOnSoft; // true if the dealer hits on soft 17, false otherwise
	
	// constructor of the class
	public Dealer(final boolean hitOnSoft) {
		this.name = "Dealer";
		this.hand = null;
		this.hitOnSoft = hitOnSoft;
	}

	public String getName() {
		return(this.name);
	}

	public Hand getHand(final int n) {
		return(this.hand);
	}

	// getter method for the hit on soft 17 configuration of the dealer
	public boolean hitOnSoft() {
		return(this.hitOnSoft);
	}
	
	// method that prepares the dealer for a new round
	public void newRound() {
		// initialization of the hand object
		this.hand = new HandImpl(0, false, 0);
	}

	// method that implements the hit move for the dealer
	public void hit(final Card card) {
		// taking a new card
		this.hand.takeCard(card);
	}
	
	// method that verifies if the dealer is in game following game rules
	public boolean isInGame(final boolean dealerHitSoft) {
		// declaration of local variables
		boolean isInGame = true;
		
		// if the dealer is still in game and has a score over 17 he stops
		if (isInGame &&
			this.hand.getScore() > 17) {
			this.hand.stopCards();
			isInGame = false;
		}
		// if the dealer is still in game, has a score of 17 and doesn't hit on a soft 17 he stops
		else if (isInGame &&
				 this.hand.getScore() == 17 &&
				 !dealerHitSoft) {
			this.hand.stopCards();
			isInGame = false;
		}
		// if the dealer is still in game, has a score of 17 and in the hand there aren't soft aces
		else if (isInGame &&
				 this.hand.getScore() == 17 &&
				 !this.hand.hasSoftAce()) {
			this.hand.stopCards();
			isInGame = false;	
		}
	
		return(isInGame);
		
	}
}
