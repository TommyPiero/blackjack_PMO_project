package it.uniurb.blackjack.model.game;

public record HandOutcome(
		double bet,
		double winMoney,
		OutcomeType outcome) {}
