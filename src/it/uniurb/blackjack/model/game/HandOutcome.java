package it.uniurb.blackjack.model.game;

// outcome of a general player's hand
public record HandOutcome(
		double bet,
		double winMoney,
		OutcomeType outcome	
) {}
