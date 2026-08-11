package it.uniurb.blackjack.model.cards;

public interface Card {
	// declaration of methods
	
	// getter method for the nominal value of the card
	public int getNominalValue();
	
	// getter method for the suit of the card
	public Suit getSuit();
	
	// getter method for the color of the card (red or black)
	public String getColor();
	
	// getter method for the BJ value of the card
	public int getBlackjackValue(final int handScore);
}
