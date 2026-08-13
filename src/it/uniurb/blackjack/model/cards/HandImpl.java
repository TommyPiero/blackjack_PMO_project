package it.uniurb.blackjack.model.cards;

import java.util.LinkedList;
import java.util.List;

public class HandImpl implements Hand {
	// declaration of the fields of the class
	private List<Card>   cards;         // cards of the hand
	private double       bet;           // bet linked to the hand
	private double       perfPairBet;   // bet linked to the perfect pair bet, it can be 0
	private HandState    handState;     // state of the hand (stand, blackjack, active, bust)
	private boolean      isFromSplit;   // bool value that record if a hand is from a split
	private PerfectPairs perfPairLevel; // bool value that record if a hand is a perfect pair
	
	// constructor of the class
	public HandImpl(final double bet, final boolean isFromSplit, final double perfPairBet) {
		// initializing the list of cards and the score to 0
		this.cards = new LinkedList<Card>();
		this.bet = bet;
		this.perfPairBet = perfPairBet;
		// setting the state of the hand to active 
		this.handState = HandState.ACTIVE;
		this.isFromSplit = isFromSplit;
		this.perfPairLevel = this.perfectPairCalc();
	}

	// method used for the calculation of the score and the management of hard and soft aces
	public int getScore() {
		// declaration and initialization of local variables
		int actualScore = 0; // actual score of the hand
		int numAce = 0;      // number of aces in the hand
		
		for (Card card : cards) {
			// adding the BJ value to the actual score
			actualScore += card.getBlackjackValue();
			
			// increasing the number of aces if the card is an ace
			if (card.isAnAce())
				numAce++;
		}
		
		// changing the value of aces while the score is more than 21 and there is at least one ace 
		while (actualScore > 21 &&
			   numAce > 0) {
			// an hard ace (11) is switched in a soft ace (1) if the score is more than 21
			actualScore -= 10;
			numAce--;
		}
		
		// if there are no more hard aces and the score is more than 21 the hand is considered bust
		if (actualScore > 21)
			this.handState = HandState.BUST;
		
		// if the hand has only two cards and the score is equal to 21, the hand is a blackjack
		if (actualScore == 21 &&
			this.cards.size() == 2)
			this.handState = HandState.BLACKJACK;
		
		return(actualScore);
	}
	
	public double getBet() {
		return(this.bet);
	}
	
	public double getSideBet() {
		return(this.perfPairBet);
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

	// method that calculates and returns the type of the perfect pair (perfect, coloured, mixed, no perfect pair)
	private PerfectPairs perfectPairCalc() {

		// if the cards has the same nominal value and it doesn't come from a split is a perfect pair
		if (this.cards.get(0).getNominalValue() == this.cards.get(0).getNominalValue() &&
			!this.isFromSplit) {
			// if the cards has the same suit is a perfect pair (25:1)
			if (this.cards.get(0).getSuit() == this.cards.get(1).getSuit())
				this.perfPairLevel = PerfectPairs.PERF_PAIR;
			// if the cards has the same suit is a coloured pair (12:1)
			else if (this.cards.get(0).getColor().equals(this.cards.get(0).getColor()))
				this.perfPairLevel = PerfectPairs.COLOU_PAIR;
			// if the cards has only the same nominal value is a mixed perfect pair (6:1)
			else
				this.perfPairLevel = PerfectPairs.MIX_PAIR;
		} 
		// if the cards has different nominal values is not a perfect pair
		else {
			this.perfPairLevel = PerfectPairs.NO_PAIR;
		}
		
		return(this.perfPairLevel);
	}
	
	public boolean isBust() {
		// if the score of the hand is higher than 21 the hand is considered bust
		return(this.getScore() > 21);
	}

	public boolean isBlackjack() {
		return(this.getScore() == 21 &&
			   this.cards.size() == 2);
	}
	
	public void takeCard(final Card card) {
		// adding the card to the hand
		this.cards.add(card);
	}
}
