package it.uniurb.blackjack.model.participants;

import java.util.LinkedList;
import java.util.List;

import it.uniurb.blackjack.model.cards.Card;
import it.uniurb.blackjack.model.cards.Hand;
import it.uniurb.blackjack.model.cards.HandImpl;

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
	
	// getter for the number of hands
	public int getNumHands() {
		return(this.hands.size());
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
		
		if ((bet + sideBet) > this.balance)
			throw new IllegalArgumentException("The bet value is too high, not enough money remaining");
		
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
		// taking a new card for the dealer
		hand.takeCard(card);
		
		// checking if the hand is bust and setting it
		if (hand.isBust());
	}
	
	// method that implements the stand move
	public void stand(final Hand hand) {
		// this move sets the hand state to stand
		hand.stopCards();
	}
	
	// method that implements the double down move
	public void doubleDown(final Card card, final Hand hand) {
		// if there is enough money a card is taken
		if (this.balance < hand.getBet()) {
			throw new IllegalStateException("Player has not enough money");
		}
		// taking a new card and decreasing the balance of the value of the bet
		hand.takeCard(card);
		balance -= hand.getBet();
		// if the new score is less than 21 setting the new state to stand 
		if (!(hand.isBust()))
			hand.stopCards();
	}
	
	// method that implements the split move
	public void split(final Card cardOne, final Card cardTwo, final Hand hand) {
		// throwing an exception if there is not enough money
		if (this.balance < hand.getBet())
			throw new IllegalStateException("Player has not enough money");
		// throwing an exception if the hand comes from a split
		if (hand.isFromSplit())
			throw new IllegalStateException("Can't split an hand that comes from a split");
		// throwing an exception if the hand has more than two cards
		if (hand.getCards().size() != 2)
			throw new IllegalStateException("Can't split an hand with more than two cards");

		// declaration and initialization of local variables
		Card firstCard = hand.getCards().get(0);			    // first card of the original hand
		Card secondCard = hand.getCards().get(1);               // second card of the original hand
		Hand splitHand1 = new HandImpl(hand.getBet(), true, 0); // new first hand from the split (set on true)
		Hand splitHand2 = new HandImpl(hand.getBet(), true, 0); // new second hand from the split (set on true)
		
		// adding the first card to the first split hand and the second card to the second split hand
		splitHand1.takeCard(firstCard);
		splitHand2.takeCard(secondCard);
		// decreasing the player's balance
		this.balance -= hand.getBet();
			
		// putting the cardOne in the original hand
		// and putting the cardTwo in the second split hand
		splitHand1.takeCard(cardOne);
		splitHand2.takeCard(cardTwo);
		// clearing the list and adding the new two split hand to the list of hands
		this.hands.clear();
		this.hands.add(splitHand1);
		this.hands.add(splitHand2);
	}
	
	// method that permit to give back money to the player if he wins a hand using a multiplier
	public void winTheBet(final double multiplier, final int numHand) {
		this.balance += (this.getHand(numHand).getBet() * multiplier);
	}
	
	// method that permit to give back money to the player if he wins a side bet using a multiplier
	public void winTheSideBet(final int multiplier) {
		this.balance += (this.getHand(0).getSideBet() * multiplier);
	}
}
