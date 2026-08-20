package it.uniurb.blackjack.model.game;

import java.util.List;

import it.uniurb.blackjack.model.cards.Card;

public record HandFields(
		List<Card> cards,
		int score,
		double bet,
		boolean isBust,
		boolean isStand,
		boolean isInGame
) {}
