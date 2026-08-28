package it.uniurb.blackjack.controller;

import java.util.List;

import it.uniurb.blackjack.model.cards.Card;
import it.uniurb.blackjack.model.cards.PerfectPairs;
import it.uniurb.blackjack.model.game.HandFields;

// state of a table
public record TableState(
		double playerBalance,           
	    double playerSideBet,          
	    List<HandFields> playerHands,
	    Card dealerUncoveredCard, 
	    int dealerStartHandScore,
	    PerfectPairs playerPerfPairLevel,
	    int numPlayerHands,
	    HandFields dealerHand
) {}
