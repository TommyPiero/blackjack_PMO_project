package it.uniurb.blackjack.model.cards;

// enum for the levels of perfect pairs
public enum PerfectPairs {
	HIGH_PERF_PAIR, // exactly the same card
	MID_PERF_PAIR,  // same nominal value and same color
	LOW_PERF_PAIR,  // same nominal value and different colors
	NO_PERF_PAIR;   // different nominal values
}
