package it.uniurb.blackjack.model.cards;

public record CardFields(
		int nominalValue,
		int blackjackValue,
		Suit cardSuit,
		CardColor cardColor) {
}
