package it.uniurb.blackjack.model.cards;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// class that implements the interface Shoe
public class ShoeImpl implements Shoe {
	// declaration of fields of the class
	private List<Card> cards; // list of cards, a set of decks (from 2 to 8) that represent a shoe
	
	// constructor of the class
	public ShoeImpl(final int numDecks) {
		// throwing an exception if the number of decks doesn't respect the correct bounds (2-8)
		if (numDecks < 2 ||
			numDecks > 8)
			throw new IllegalArgumentException("Not valid number of decks");
		
		
		this.cards = new LinkedList<Card>();
		
		// creation of the shoe based on the number of the configuration
		for (int i = 0;
			 (i < numDecks);
			 i++) {
			// all different 52 cards added to the deck for the correct number of times
			for (Suit suit: Suit.values()) {
				for (int j = 1;
					 (j <= 13);
					 j++) {
					this.cards.add(new CardImpl(j, suit));
				}
			}
		}
		// shuffling the shoe
		this.shuffleShoe();
	}

	// private method that shuffle the shoe
	private void shuffleShoe() {
		// shuffling the decks between them
		Collections.shuffle(cards);
	}

	public Card drawCard() {
		// declaration of local variables
		Card cardToGive;
		
		// if the shoe is empty it throws an error
		if (this.cards.isEmpty())
			throw new IllegalStateException("The shoe is empty");
		// taking the first card of the deck
		cardToGive = this.cards.get(0);
				
		// removing the card from the deck
		this.cards.remove(0);
				
		return(cardToGive);
	}

	public int getRemainCards() {
		return(this.cards.size());
	}

}
