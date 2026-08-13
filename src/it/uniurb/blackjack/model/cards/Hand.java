package it.uniurb.blackjack.model.cards;

import java.util.List;

public interface Hand {
	// declaration of methods
	
	// getter method for the score of the hand
	public int getScore();
	
	// getter method for the bet linked to the hand
	public double getBet();
	
	// getter method for the bet linked to the side bet
	public double getSideBet();
	
	// getter method for the list of cards
	public List<Card> getCards();
	
	// getter method for the state of the hand (stand, blackjack, active, bust)
	public HandState getHandState();
	
	// getter method for the derivation of the hand (from split or not)
	public boolean isFromSplit();
	
	// setter method for changing the state of the hand
	public void stopCards();
	
	// getter method that returns if a hand is bust or not
	public boolean isBust();
	
	// getter method that returns if a hand is Blackjack or not
	public boolean isBlackjack();
	
	// method that add a card to the hand
	public void takeCard(final Card card);
}
