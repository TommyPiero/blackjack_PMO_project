package it.uniurb.blackjack.model.cards;

// interface that declare the methods of the shoe class
// this class will shape the concept of shoe, the big deck from which the cards will be drawn
public interface Shoe {
	// declaration of methods
	
	// method that get a card from the list of cards (it takes the first of the the first deck in the shoe)
	public Card drawCard();
	
	// getter method for remaining cards
	public int getRemainCards();
}
