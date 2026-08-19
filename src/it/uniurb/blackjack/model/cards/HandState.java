package it.uniurb.blackjack.model.cards;

// enum for the four possible states of a hand 
public enum HandState {
	STAND,     // The hand hasn't reached the score of 21 but he can't draw other cards
	BLACKJACK, // The hand has reached the score of 21 with only two cards
	ACTIVE,    // The hand is still active, it hasn't reached the score of 21 and other cards can be added
	BUST;      // The hand has surpassed the score of 21
}
