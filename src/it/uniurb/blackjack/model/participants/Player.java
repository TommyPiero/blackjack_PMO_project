package it.uniurb.blackjack.model.participants;

import it.uniurb.blackjack.model.cards.Card;
import it.uniurb.blackjack.model.cards.Hand;
import it.uniurb.blackjack.model.cards.HandImpl;
import it.uniurb.blackjack.model.cards.HandState;
import it.uniurb.blackjack.model.cards.Shoe;

// class for the player of the game that implements participant
public class Player implements Participant {
	// declaration of the fields of the class
	private final String playerName; // name of the player
	private Hand         hand;	     // first hand of the player
	private Hand         splitHand;  // hand from the split
	private double       balance;    // balance of the player for the bets
	
	// constructor of the class
	public Player(final String name, final double balance) {
		this.playerName = name;
		this.balance = balance;
		this.hand = null;
		this.splitHand = null;
	}

	public String getName() {
		return(this.playerName);
	}

	public Hand getHand() {
		return(this.hand);
	}
	
	// getter method for the balance of the player
	public double getBalance() {
		return(this.balance);
	}
	
	// method that generates a new Hand
	public Hand newHand(final Shoe shoe, final double bet, final boolean isFromSplit, final double perfPairBet) {
		if (this.balance >= bet) {
			this.hand = new HandImpl(shoe, bet, isFromSplit, perfPairBet);
			this.balance -= bet;
		}
		else {
			throw new IllegalStateException("Player has not enough money");
		}
		return(this.hand);
	}
	
	// method that implements the hit move
	public void hit(final Shoe shoe) {
		if (this.hand.getHandState() == HandState.ACTIVE)
			this.hand.takeCard(shoe);
		else {
			throw new IllegalStateException("The hand is not more active");
		}
	}
	
	// method that implements the stand move
	public void stand() {
		this.hand.stopCards();
	}
	
	// method that implements the double down move
	public void doubleDown(final Shoe shoe) {
		// if there is enough money a card is taken
		if (this.balance >= this.hand.getBet()) {
			if (this.hand.getHandState() == HandState.ACTIVE) {
				this.hand.takeCard(shoe);
				this.balance -= this.hand.getBet();
				// if the new score is less than 21 setting the new state to stand 
				if (this.hand.getHandState() != HandState.BUST)
					this.hand.stopCards();
			}
			else {
				throw new IllegalStateException("The hand is not more active");
			}
		}
		else {
			throw new IllegalStateException("Player has not enough money");
		}
	}
	
	// method that implements the split move
	public void split(final Shoe shoe) {
		// if there is enough money, the hand is not already from a split and there are only two cards the hand is split
		if (this.balance >= this.hand.getBet() &&
			!this.hand.isFromSplit() &&
			this.hand.getCards().size() == 2) {
			if (this.hand.getHandState() == HandState.ACTIVE) {
				// declaration and initialization of local variables
				Card firstCard = this.hand.getCards().get(0);  // first card of the original hand
				Card secondCard = this.hand.getCards().get(1); // second card of the original hand
				Hand firstHand = new HandImpl(shoe, this.hand.getBet(), true, 0); // new hand from the split (set on true )

				// initialization of the second hand from the split
				this.splitHand = new HandImpl(shoe, this.hand.getBet(), true, 0);
			
				// decreasing the player's balance
				this.balance -= this.hand.getBet();
			
				// putting the first card of the original hand in the first split hand
				// and putting the second card of the original hand in the second split hand
				firstHand.getCards().add(firstCard);
				this.splitHand.getCards().add(secondCard);
			
				// new first split hand takes the place of the original hand
				this.hand = firstHand;
			
				// both the new split hands take a card from the shoe
				this.splitHand.takeCard(shoe);
				this.hand.takeCard(shoe);
			}
			else {
				throw new IllegalStateException("The hand is not more active");
			}
		}
		else {
			throw new IllegalStateException("Player has not enough money");
		}
	}
	
	// method that permit to give back money to the player if he wins a hand
	public void winTheBet(final double bet) {
		this.balance += bet;
	}
}
