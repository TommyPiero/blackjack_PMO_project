package it.uniurb.blackjack.model.participants;

import it.uniurb.blackjack.model.cards.Card;
import it.uniurb.blackjack.model.cards.Hand;
import it.uniurb.blackjack.model.cards.HandImpl;

// implementation of the interface Dealer
public class DealerImpl implements Dealer {
	
	// declaration of class' fields
	private String  name;      // the name of the dealer
	private Hand    hand;      // hand of the dealer
	private boolean hitOnSoft; // true if the dealer hits on soft 17, false otherwise
	
	// class' constructor
	public DealerImpl(final boolean hitOnSoft) {
		this.name = "Dealer";
		this.hand = null;
		this.hitOnSoft = hitOnSoft;
	}
	
	public String getName() {
		return(this.name);
	}

	public Hand getHand() {
		return(this.hand);
	}

	public boolean hitOnSoft() {
		return(this.hitOnSoft);
	}
	
	public void newRound() {
		// initialization of the hand object
		this.hand = new HandImpl(0, false, 0);
	}

	public void hit(final Card card) {
		// taking a new card
		this.hand.takeCard(card);
	}
	
	public boolean isInGame(final boolean dealerHitSoft) {
		// declaration of local variables
		boolean isInGame = true; // boolean value that record if the player is still in game
		
		// if the dealer is still in game and has a score over 17 he stops
		if (isInGame &&
			this.hand.getScore() > 17) {
			if (this.hand.getScore() < 21) {
				this.hand.stopCards();
			}
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

	public Card getUncoveredCard() {
		// getting the second card drawn
		return(this.hand.getCards().get(1));
	}
	
	public Card getCoveredCard() {
		// getting the first card drawn
		return(this.hand.getCards().get(0));
	}
}
