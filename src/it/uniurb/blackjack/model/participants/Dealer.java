package it.uniurb.blackjack.model.participants;

import it.uniurb.blackjack.model.cards.Card;
import it.uniurb.blackjack.model.cards.Hand;
import it.uniurb.blackjack.model.cards.HandImpl;
import it.uniurb.blackjack.model.cards.HandState;

// class that create the model of the dealer of the game
public class Dealer implements Participant {
	// declaration of the fields of the class
	private String name; // the name of the dealer
	private Hand hand;   // hand of the dealer
	
	// constructor of the class
	public Dealer() {
		this.name = "Dealer";
		this.hand = null;
	}

	public String getName() {
		return(this.name);
	}

	public Hand getHand(final int n) {
		return(this.hand);
	}

	// method that prepares the dealer for a new round
	public void newRound() {
		// initialization of the hand object
		this.hand = new HandImpl(0, false, 0);
	}

	// method that implements the hit move for the dealer
	public void hit(final Card card, final Hand hand) {
		if (hand.getHandState() == HandState.ACTIVE)
			hand.takeCard(card);
		else {
			throw new IllegalStateException("The hand is not more active");
		}
	}
	
	// method that verifies if the dealer is in game following game rules
	// (for the moment the dealer is set for standing on 17 in every situation)
	public boolean isInGame() {
		// declaration of local variables
		boolean isInGame = true;
		
		if (isInGame &&
			this.hand.getScore() >= 17) {
			this.hand.stopCards();
			isInGame = false;
		}
		
		return(isInGame);
		
	}
}
