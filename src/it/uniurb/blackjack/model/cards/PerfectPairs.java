package it.uniurb.blackjack.model.cards;

// enum for the levels of perfect pairs
public enum PerfectPairs {
	PERF_PAIR, // exactly the same card
	COLOU_PAIR,  // same nominal value and same color
	MIX_PAIR,  // same nominal value and different colors
	NO_PAIR;   // different nominal values
}
