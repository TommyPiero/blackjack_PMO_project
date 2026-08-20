package it.uniurb.blackjack.model.cards;

import java.util.LinkedList;
import java.util.List;

//implementation of the interface Hand
public class HandImpl implements Hand {
	
	// class' fields declaration
	private List<Card>   cards;         // cards of the hand
	private double       mainBet;       // main bet linked to the hand
	private double       sideBet;       // bet linked to the side bet, it can be 0
	private boolean      hasSoftAce;    // number of soft aces in the hand (aces that has value of 11)
	private HandState    handState;     // state of the hand (stand, blackjack, active, bust)
	private boolean      isFromSplit;   // bool value that record if a hand is from a split
	private PerfectPairs perfPairLevel; // bool value that record if a hand is a perfect pair
	
	// class' constructor
	public HandImpl(final double mainBet, final boolean isFromSplit, final double sideBet) {
		// checking possible errors in the initialization of the class		
		// throwing an exception if the hand is from a split and the sideBet is higher than 0
		if (isFromSplit &&
			sideBet > 0)
			throw new IllegalArgumentException("The hand from a split can't have side bets!");
		
		// initializing the list of cards, the bets and the states
		this.cards = new LinkedList<Card>();
		this.mainBet = mainBet;
		this.sideBet = sideBet;
		// there are no cards in the hand, so there cannot be a soft ace
		this.hasSoftAce = false;
		// setting the state of the hand to active 
		this.handState = HandState.ACTIVE;
		this.isFromSplit = isFromSplit;
		// perfect pair level initialized as no pair because in the beginning there are no cards in the hand
		this.perfPairLevel = PerfectPairs.NO_PAIR;
	}

	// method used for the calculation of the score and the management of hard and soft aces
	private int calcScore() {
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
			// a soft ace (11) is switched in a hard ace (1) if the score is more than 21
			actualScore -= 10;
			numAce--;
		}
		
		// setting the soft ace to true if there is at least one ace in this section
		if (numAce > 0)
			this.hasSoftAce = true;
		
		return(actualScore);
	}

	// method that updates the state of the hand
	private void updateState() {
		// declaration and initialization of local variables
		int handScore = getScore(); // actual hand score
		
		// if there are no more soft aces and the score is more than 21 the hand is considered bust
		if (handScore > 21)
			this.handState = HandState.BUST;
				
		// if the hand has only two cards and the score is equal to 21, the hand is a blackjack 
		if (handScore == 21) {
			if (this.cards.size() == 2) {
				this.handState = HandState.BLACKJACK;
			} else {
				this.handState = HandState.STAND;
			}
		}
	}
	
	public int getScore() {
		return(calcScore());
	}
	
	public double getBet() {
		return(this.mainBet);
	}
	
	public double getSideBet() {
		return(this.sideBet);
	}
	
	public List<Card> getCards() {
		return(this.cards);
	}
	
	public HandState getHandState() {
		return(this.handState);
	}
	
	public boolean hasSoftAce() {
		return(this.hasSoftAce);
	}
	
	public boolean isFromSplit() {
		return(this.isFromSplit);
	}
	
	public void stopCards() {
		this.handState = HandState.STAND;
	}

	public PerfectPairs perfectPairCalc() {

		// throwing an exception if the hand has more than two cards
		if (this.cards.size() > 2)
			throw new IllegalStateException("A perfect pair can't be calculated with more than two cards");
		
		// throwing an exception if the hand comes from a split
		if (this.isFromSplit)
			throw new IllegalStateException("A perfect pair can't be calculated on a hand that comes from a split");
		
		// if the cards has the same nominal value is a pair
		if (this.cards.get(0).getNominalValue() == this.cards.get(1).getNominalValue()) {
			// if the cards has also the same suit is a perfect pair (25:1)
			if (this.cards.get(0).getSuit() == this.cards.get(1).getSuit())
				this.perfPairLevel = PerfectPairs.PERF_PAIR;
			// if the cards has the same color is a colored pair (12:1)
			else if (this.cards.get(0).getColor().equals(this.cards.get(1).getColor()))
				this.perfPairLevel = PerfectPairs.COLOU_PAIR;
			// if the cards has only the same nominal value is a mixed pair (6:1)
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
		return(this.handState.equals(HandState.BUST));
	}

	public boolean isBlackjack() {
		return(this.handState.equals(HandState.BLACKJACK));
	}
	
	public boolean isStand() {
		return(this.handState.equals(HandState.STAND));
	}
	
	public boolean isInGame() {
		return(this.handState.equals(HandState.ACTIVE));
	}
	
	public void takeCard(final Card card) {
		// adding the card to the hand
		this.cards.add(card);
		// updating the state of the hand
		updateState();
	}

	public void doubleBet() {
		this.mainBet *= 2;
	}
}
