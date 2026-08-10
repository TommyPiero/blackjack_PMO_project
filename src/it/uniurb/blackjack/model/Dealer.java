package it.uniurb.blackjack.model;

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

	public Hand getHand() {
		return(this.hand);
	}

	// method that generates a new hand from zero
	public Hand newHand(final Shoe shoe) {
		// initialization of the hand object
		this.hand = new HandImpl(shoe, 0, false);
		// i take two cards
		this.hand.takeCard(shoe);
		this.hand.takeCard(shoe);
		
		return(this.hand);
	}

	// method that implements the hit move for the dealer
	public void hit(Shoe shoe) {
		if (this.hand.getHandState() == HandState.ACTIVE)
			this.hand.takeCard(shoe);
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
