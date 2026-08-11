package it.uniurb.blackjack.model.cards;

// implementation of the interface Card
public class CardImpl implements Card {

	// declaration of class' fields
	private final int    nominalValue;   // nominal value of the card
	private final Suit   cardSuit;       // suit of the card (hearts, diamonds, clubs, spades)
	private int          blackjackValue; // BJ value of the card, calculated following game rules
	private String cardColor;      // color of the card (black or white)        
	
	public CardImpl(final int nominalValue, final Suit cardSuit) {
		this.nominalValue = nominalValue;
		this.cardSuit = cardSuit;
		this.cardColor = getColor(); 
	}
	
	public int getNominalValue() {
		return(this.nominalValue);
	}
	
	public Suit getSuit() {
		return(this.cardSuit);
	}
	
	public String getColor() {
		
		if (this.cardSuit == Suit.HEARTS ||
			this.cardSuit == Suit.DIAMONDS) 
			this.cardColor = "red";
		else
			this.cardColor = "black";
		
		return(this.cardColor);
	}
	
	public int getBlackjackValue(final int handScore) {
	
		if (this.nominalValue >= 2 &&
			this.nominalValue <= 9)
			this.blackjackValue = this.nominalValue;
		else if (this.nominalValue >= 10 &&
				 this.nominalValue <= 13)
			this.blackjackValue = 10;
		else if (this.nominalValue == 1)
			this.blackjackValue = calcAce(handScore);
		else
			throw new IllegalArgumentException("Not valid nominal value");
		
		return(this.blackjackValue);
	}
	
	// private method that calculates the ace value (soft or hard)
	// it takes the handScore as a parameter
	private static int calcAce(final int handScore) {
		// declaration of local variables
		int aceValue;
		
		// if the hand score is less or equal to 21 the ace takes BJ value 11
		// if the hand score is more than 21 the ace takes BJ value 1
		if (handScore <= 21)
			aceValue = 11;
		else
			aceValue = 1;
		
		return aceValue;
	}

}
