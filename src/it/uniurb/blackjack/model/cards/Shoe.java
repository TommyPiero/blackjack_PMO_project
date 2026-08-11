package it.uniurb.blackjack.model.cards;

public interface Shoe {
	// declaration of methods
	
	// getter method for one deck of the shoe, it get in an int as parameter in input
	public Deck getDeck(int n);
	
	// method that get a card from the list of decks (it takes the first of the the first deck in the shoe)
	public Card giveCard();
}
