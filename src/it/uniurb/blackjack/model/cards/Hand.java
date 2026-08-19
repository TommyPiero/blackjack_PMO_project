package it.uniurb.blackjack.model.cards;

import java.util.List;

// interface that declare the methods of the hand class
// this class will shape the concept of hand, with the drawn cards and all the states belonging to them
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
	
	// getter method for soft aces (the hand has a soft ace or not)
	public boolean hasSoftAce();
	
	// getter method for the derivation of the hand (from split or not)
	public boolean isFromSplit();
	
	// setter method for changing the state of the hand to stand
	public void stopCards();
	
	// getter method that calculates the level of a perfect pair (perfect, colored, mixed, no perfect pair)
	public PerfectPairs perfectPairCalc();
	
	// getter method that returns if a hand is bust or not
	public boolean isBust();
	
	// getter method that returns if a hand is Blackjack or not
	public boolean isBlackjack();
	
	// getter method that returns if a hand is stand or not
	public boolean isStand();
	
	// method that shows if a hand is still in game
	public boolean isInGame(); 
	
	// method that add a card to the hand
	public void takeCard(final Card card);
	
	// method that doubles the bet
	public void doubleBet();
}
