package it.uniurb.blackjack.model;

import java.util.LinkedList;
import java.util.List;

public class HandImpl implements Hand {
	// declaration of the fields of the class
	private List<Card>   cards;         // cards of the hand
	private int 	     score;         // score of the hand (considering BJ values)
	private double       bet;           // bet linked to the hand
	private HandState    handState;     // state of the hand (stand, blackjack, active, bust)
	private boolean      isFromSplit;   // bool value that record if a hand is from a split
	private PerfectPairs perfPairLevel; // bool value that record if a hand is a perfect pair
	
	// constructor of the class
	public HandImpl(final Shoe shoe, final double bet, final boolean isFromSplit) {
		// initializing the list of cards and the score to 0
		this.cards = new LinkedList<Card>();
		this.score = 0;
		
		// taking the first two cards and changing the score
		Card takenCard;
		
		for (int i = 0;
			 (i < 2);
			 i++) {
			takenCard = shoe.giveCard();
			this.cards.add(takenCard);
			this.score += takenCard.getBlackjackValue(this.score);
		}

		this.bet = bet;
		// setting the state of the hand to active if the score is not equal to 21
		// otherwise setting the state to blackjack (only a 21 with two cards is a BJ)
		if (this.score != 21)
			this.handState = HandState.ACTIVE;
		else
			this.handState = HandState.BLACKJACK;
		
		this.isFromSplit = isFromSplit;
		this.perfPairLevel = perfectPairCalc();
	}

	public int getScore() {
		return(this.score);
	}

	public double getBet() {
		return(this.bet);
	}
	
	public List<Card> getCards() {
		return(this.cards);
	}
	
	public HandState getHandState() {
		return(this.handState);
	}
	
	public boolean isFromSplit() {
		return(this.isFromSplit);
	}

	public void stopCards() {
		this.handState = HandState.STAND;
	}

	// method that calculates and returns the level of the perfect pair (high, mid, low, no perfect pair)
	private PerfectPairs perfectPairCalc() {

		// if the cards has the same nominal value is a perfect pair
		if (this.cards.get(0).getNominalValue() == this.cards.get(0).getNominalValue()) {
			// if the cards has the same suit is an high level perfect pair (25:1)
			if (this.cards.get(0).getSuit() == this.cards.get(1).getSuit())
				this.perfPairLevel = PerfectPairs.HIGH_PERF_PAIR;
			// if the cards has the same suit is a mid level perfect pair (12:1)
			else if (this.cards.get(0).getColor().equals(this.cards.get(0).getColor()))
				this.perfPairLevel = PerfectPairs.MID_PERF_PAIR;
			// if the cards has the same suit is a Low level perfect pair (6:1)
			else
				this.perfPairLevel = PerfectPairs.LOW_PERF_PAIR;
		} 
		// if the cards has different nominal values is not a perfect pair
		else {
			this.perfPairLevel = PerfectPairs.NO_PERF_PAIR;
		}
		
		return(this.perfPairLevel);
	}
	
	public void isBust() {
		// if the score of the hand is higher than 21 the hand is considered bust
		if (this.score > 21)
			this.handState = HandState.BUST;
	}

	public void takeCard(final Shoe shoe) {
		// declaration and initialization of local variables
		Card cardToAdd = shoe.giveCard(); // card to add to the hand
		
		this.cards.add(cardToAdd);
		this.score += cardToAdd.getBlackjackValue(this.score);
		
		// check if the hand is bust or not
		this.isBust();
	}
}
