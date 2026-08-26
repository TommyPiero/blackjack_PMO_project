package it.uniurb.blackjack.model.participants;

import java.util.LinkedList;
import java.util.List;

import it.uniurb.blackjack.model.cards.Card;
import it.uniurb.blackjack.model.cards.Hand;
import it.uniurb.blackjack.model.cards.HandImpl;

//implementation of the interface Player
public class PlayerImpl implements Player {
	
	// declaration of class' fields
	private String       playerName;     // name of the player
	private List<Hand>   hands;		     // two possible hands for the player (one from the split)
	private double       balance;        // balance of the player for the bets
	private boolean      isInsured;      // boolean value for the insurance    
	
	// class' constructor
	public PlayerImpl() {
		this.hands = new LinkedList<Hand>();
		this.isInsured = false;
	}

	public String getName() {
		return(this.playerName);
	}

	public Hand getHand(final int n) {
		// it returns the chosen number of the hand for the correct managements from other classes
		return(this.hands.get(n));
	}
	
	public double getBalance() {
		return(this.balance);
	}
	
	public int getNumHands() {
		return(this.hands.size());
	}
	
	public boolean isInsured() {
		return(this.isInsured);
	}
	
	public void initPlayer(final String name, final double balance) {
		this.playerName = name;
		this.balance = balance;
	}
	
	public void insure() {
		// checking if there's enough money
		if (this.balance < this.hands.get(0).getBet() / 2)
			throw new IllegalStateException("Not enough money for the insurance!");
		
		// paying half of the bet
		this.balance -= (this.hands.get(0).getBet() / 2);
		this.isInsured = true;
	}
	
	public void newRound(final double bet, final double sideBet) {
		// throwing an exception if the bet is less or equal to zero
		if (bet <= 0)
			throw new IllegalArgumentException("The bet's value isn't enough, it must be higher than zero!");
				
		// throwing an exception if the sideBet is less to zero
		if (sideBet < 0)
			throw new IllegalArgumentException("The side bet's value isn't enough, it must be higher or equal to zero!");
		
		if ((bet + sideBet) > this.balance)
			throw new IllegalArgumentException("The bet value is too high, not enough money remaining!");
		
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
		// insurance is set to false
		this.isInsured = false;
	}
	
	public void hit(final Card card, final Hand hand) {
		// taking a new card for the dealer
		hand.takeCard(card);
		
		// checking if the hand is bust and setting it
		if (hand.isBust());
	}
	
	public void stand(final Hand hand) {
		// this move sets the hand state to stand
		hand.stopCards();
	}
	
	public void doubleDown(final Card card, final Hand hand) {
		// if there is enough money a card is taken
		if (this.balance < hand.getBet()) {
			throw new IllegalStateException("Player has not enough money!");
		}
		
		// throwing an error if the card are more than two
		// double down can be done only in a hand with two cards
		if (hand.getCards().size() != 2 ) {
			throw new IllegalStateException("Double down can be executed only with two cards!");
		}
		
		// taking a new card and decreasing the balance of the value of the bet
		hand.takeCard(card);
		balance -= hand.getBet();
		// doubling the original bet
		hand.doubleBet();
		// if the new score is less than 21 setting the new state to stand 
		if (!(hand.isBust()))
			hand.stopCards();
	}
	
	public void split(final Card cardOne, final Card cardTwo, final Hand hand) {
		// throwing an exception if there is not enough money
		if (this.balance < hand.getBet()) {
			throw new IllegalStateException("Player has not enough money!");
		}
		// throwing an exception if the hand comes from a split
		if (hand.isFromSplit()) {
			throw new IllegalStateException("Can't split an hand that comes from a split!");
		}
		// throwing an exception if the hand has more than two cards
		if (hand.getCards().size() != 2) {
			throw new IllegalStateException("Can't split an hand with more than two cards!");
		}
		// throwing an error if the cards have different nominal value
		// split can be executed only with card with same nominal value
		if (hand.getCards().get(0).getNominalValue() != hand.getCards().get(1).getNominalValue()) {
			throw new IllegalStateException("Can't split an hand of two cards with different nominal value!");
		}
		
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
	
	public double winTheBet(final double multiplier, final int numHand) {
		// declaration of local variables
		double moneyBet = (this.getHand(numHand).getBet() * multiplier); // money won from the bet
		
		this.balance += moneyBet;
		
		return(moneyBet);
	}
	
	public double winTheSideBet(final int multiplier) {
		// declaration of local variables
		double moneySideBet = (this.getHand(0).getSideBet() * multiplier); // money won from the side bet
		
		this.balance += moneySideBet;
		
		return(moneySideBet);
	}
	
	public double winInsurance() {
		// declaration of local variables
		double moneyInsurance = (this.getHand(0).getBet()); // money won from the insurance
	
		this.balance += moneyInsurance;
		
		return(moneyInsurance);
	}
}
