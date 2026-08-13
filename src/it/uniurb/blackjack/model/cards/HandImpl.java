package it.uniurb.blackjack.model.cards;

import java.util.LinkedList;
import java.util.List;

public class HandImpl implements Hand {
	// declaration of the fields of the class
	private List<Card>   cards;         // cards of the hand
	private int 	     score;         // score of the hand (considering BJ values)
	private double       bet;           // bet linked to the hand
	private double       perfPairBet;   // bet linked to the perfect pair bet, it can be 0
	private HandState    handState;     // state of the hand (stand, blackjack, active, bust)
	private boolean      isFromSplit;   // bool value that record if a hand is from a split
	private PerfectPairs perfPairLevel; // bool value that record if a hand is a perfect pair
	
	// constructor of the class
	public HandImpl(final Shoe shoe, final double bet, final boolean isFromSplit, final double perfPairBet) {
		// initializing the list of cards and the score to 0
		this.cards = new LinkedList<Card>();
		
		// taking the first two cards and changing the score
		Card takenCard;
		
		for (int i = 0;
			 (i < 2);
			 i++) {
			takenCard = shoe.drawCard();
			this.cards.add(takenCard);
		}
		
		// calculating the score of the first two cards
		this.score = this.getScore();
		
		this.bet = bet;
		this.perfPairBet = perfPairBet;
		// setting the state of the hand to active if the score is not equal to 21
		// otherwise setting the state to blackjack (only a 21 with two cards is a BJ)
		if (this.score != 21)
			this.handState = HandState.ACTIVE;
		else
			this.handState = HandState.BLACKJACK;
		
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

		// if the cards has the same nominal value is a perfect pair
		if (this.cards.get(0).getNominalValue() == this.cards.get(0).getNominalValue()) {
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
		return(this.handState.equals(HandState.BUST));
	}

	public boolean isBlackjack() {
		return(this.handState.equals(HandState.BLACKJACK));
	}
	
	public void takeCard(final Shoe shoe) {
		// declaration and initialization of local variables
		Card cardToAdd = shoe.drawCard(); // card to add to the hand
		
		this.cards.add(cardToAdd);
		this.score += cardToAdd.getBlackjackValue();
		
		// changing the score of the hand
		this.score = this.getScore();
	}
}
