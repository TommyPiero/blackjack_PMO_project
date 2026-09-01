package it.uniurb.blackjack.model.cards;

// fields of a general game card
public record CardFields(
		int nominalValue,
		int blackjackValue,
		Suit cardSuit,
		CardColor cardColor) {
}
