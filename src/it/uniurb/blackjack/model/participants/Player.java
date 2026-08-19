package it.uniurb.blackjack.model.participants;

import it.uniurb.blackjack.model.cards.Card;
import it.uniurb.blackjack.model.cards.Hand;

// interface that declare the methods of the class player
// this class will shape the concept of the player of the game
public interface Player {

	// getter method for player's name
	public String getName();

	// getter method for player's hands
	public Hand getHand(final int n);
	
	// getter method for the balance of the player
	public double getBalance();
	
	// getter method for the number of hands
	public int getNumHands();
	
	// getter method for the insurance (the player is insured or not)
	public boolean isInsured();
	
	// method that initialize a new player
	public void initPlayer(final String name, final double balance);
	
	// setter method that permit the player to insure
	public void insure();
	
	// method that prepares the player for the new round
	public void newRound(final double bet, final double sideBet);
	
	// method that implements the hit move
	public void hit(final Card card, final Hand hand);
	
	// method that implements the stand move
	public void stand(final Hand hand);
	
	// method that implements the double down move
	public void doubleDown(final Card card, final Hand hand);
	
	// method that implements the split move
	public void split(final Card cardOne, final Card cardTwo, final Hand hand);
	
	// method that permit to give back money to the player if he wins a hand using a multiplier
	public double winTheBet(final double multiplier, final int numHand);
	
	// method that permit to give back money to the player if he wins a side bet using a multiplier
	public double winTheSideBet(final int multiplier);
	
	// method that permit to give back money to the player if he's insured and the dealer has a hand with a BJ
	public double winInsurance();
}
