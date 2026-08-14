package it.uniurb.blackjack.model.participants;

import java.util.LinkedList;
import java.util.List;

import it.uniurb.blackjack.model.cards.Card;
import it.uniurb.blackjack.model.cards.Hand;
import it.uniurb.blackjack.model.cards.HandImpl;
import it.uniurb.blackjack.model.cards.HandState;

// class for the player of the game that implements participant
public class Player implements Participant {
	// declaration of the fields of the class
	private String       playerName; // name of the player
	private List<Hand>   hands;		 // two possible ends for the player (one from the split)
	private double       balance;    // balance of the player for the bets
	
	// constructor of the class
	public Player() {
		this.hands = new LinkedList<Hand>();
	}

	public String getName() {
		return(this.playerName);
	}

	public Hand getHand(final int n) {
		// it returns the chosen number of the hand for the correct managements from other classes
		return(this.hands.get(n));
	}
	
	// getter method for the balance of the player
	public double getBalance() {
		return(this.balance);
	}
	
	// method that initialize a new player
	public void initPlayer(final String name, final double balance) {
		this.playerName = name;
		this.balance = balance;
	}
	
	// method that prepares the player for the new round
	public void newRound(final double bet, final double sideBet) {
		// throwing an exception if the bet is less or equal to zero
		if (bet <= 0)
			throw new IllegalArgumentException("The bet's value isn't enough, it must be higher than zero");
				
		// throwing an exception if the sideBet is less to zero
		if (sideBet < 0)
			throw new IllegalArgumentException("The side bet's value isn't enough, it must be higher or equal to zero");
		
		// declaration of local variables
		Hand newHand; // hand to add to the list
		
		// clearing the list of hands from the last round
		this.hands.clear();
		
		// initializing the new hand to add to the list
		newHand = new HandImpl(bet, false, sideBet);
		// adding the new hand to the list
		this.hands.add(newHand);
		// deduction of the sum of the bets from the balance
		this.balance -= (bet + sideBet);
	}
	
	// method that implements the hit move
	public void hit(final Card card, final Hand hand) {
		if (hand.getHandState() == HandState.ACTIVE)
			hand.takeCard(card);
		else {
			throw new IllegalStateException("The hand is not more active");
		}
	}
	
	// method that implements the stand move
	public void stand(final Hand hand) {
		hand.stopCards();
	}
	
	// method that implements the double down move
	public void doubleDown(final Card card, final Hand hand) {
		// if there is enough money a card is taken
		if (this.balance >= hand.getBet()) {
			if (hand.getHandState().equals(HandState.ACTIVE)) {
				hand.takeCard(card);
				balance -= hand.getBet();
				// if the new score is less than 21 setting the new state to stand 
				if (!(hand.isBust()))
					hand.stopCards(); // possible error to check
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
	public void split(final Card cardOne, final Card cardTwo, final Hand hand) {
		// if there is enough money, the hand is not already from a split and there are only two cards the hand is split
		if (this.balance >= hand.getBet() &&
			!hand.isFromSplit() &&
			hand.getCards().size() == 2) {
			if (hand.getHandState() == HandState.ACTIVE) {
				// declaration and initialization of local variables
				Card secondCard = hand.getCards().get(1);              // second card of the original hand
				Hand splitHand = new HandImpl(hand.getBet(), true, 0); // new hand from the split (set on true)

				// removing the second card from the first hand
				hand.getCards().remove(1);
				// adding the second card to the split hand
				splitHand.takeCard(secondCard);
			
				// decreasing the player's balance
				this.balance -= hand.getBet();
			
				// putting the cardOne in the original hand
				// and putting the cardTwo in the second split hand
				hand.takeCard(cardOne);
				splitHand.takeCard(cardTwo);
				// adding the new split hand to the list of hands
				this.hands.add(splitHand);
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
