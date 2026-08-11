package it.uniurb.blackjack.model.cards;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// class that implements the interface Deck
public class DeckImpl implements Deck {
	// declaration of class' fields
	private List<Card> cards; // list of 52 different cards that represent a single deck
	
	// constructor of the class
	public DeckImpl() {
		
		this.cards = new LinkedList<Card>();
		
		// all different 52 cards added to the deck
		for (Suit suit: Suit.values()) {
			for (int i = 1;
				 (i <= 13);
				 i++) {
				this.cards.add(new CardImpl(i, suit));
			}
		}
		
		// shuffling the deck
		this.shuffleDeck();
	}
	
	// private method that shuffle a single deck composed by 52 cards
	private void shuffleDeck() {
		Collections.shuffle(cards);
	}
	
	public Card getCard() {
		// declaration of local variables
		Card cardToGive;
		
		// taking the first card of the deck
		cardToGive = this.cards.get(0);
		
		// removing the card from the deck
		this.cards.remove(0);
		
		return(cardToGive);
	}
}
