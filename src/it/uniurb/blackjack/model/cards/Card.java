package it.uniurb.blackjack.model.cards;

// interface that declare the methods of the card class
// this class will shape the concept of single card from a deck
public interface Card {
	// declaration of methods
	
	// getter method for the nominal value of the card
	public int getNominalValue();
	
	// getter method for the suit of the card
	public Suit getSuit();
	
	// getter method for the color of the card (red or black)
	public CardColor getColor();
	
	// getter method for the BJ value of the card
	public int getBlackjackValue();
	
	// getter that returns true if the card is an ace
	public boolean isAnAce();
}
