package it.uniurb.blackjack.model.cards;

public interface Shoe {
	// declaration of methods
	
	// method that get a card from the list of cards (it takes the first of the the first deck in the shoe)
	public Card drawCard();
	
	// getter method for remaining cards
	public int getRemainCards();
}
