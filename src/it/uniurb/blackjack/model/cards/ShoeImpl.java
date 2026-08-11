package it.uniurb.blackjack.model.cards;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// class that implements the interface Shoe
public class ShoeImpl implements Shoe {
	// declaration of fields of the class
	private List<Deck> decks; // list of decks (from 2 to 8) that represent a shoe
	
	// constructor of the class
	public ShoeImpl(final int numDecks) {
		this.decks = new LinkedList<Deck>();
		
		// creation of the shoe based on the number of the configuration
		for (int i = 0;
			 (i < numDecks);
			 i++)
			this.decks.add(new DeckImpl());
		
		// shuffling the shoe
		this.shuffleShoe();
	}

	public Deck getDeck(int n) {
		return(this.decks.get(n));
	}
	
	// private method that shuffle the shoe
	private void shuffleShoe() {
		// shuffling the decks between them
		Collections.shuffle(decks);
	}

	public Card giveCard() {
		// taking the first card of the first deck of the shoe
		return(this.decks.get(0).getCard());
	}

}
