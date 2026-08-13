package it.uniurb.blackjack.model.cards;

// implementation of the interface Card
public class CardImpl implements Card {

	// declaration of class' fields
	private final int       nominalValue;   // nominal value of the card
	private final Suit      cardSuit;       // suit of the card (hearts, diamonds, clubs, spades)
	private final int       blackjackValue; // BJ value of the card, calculated following game rules
	private final CardColor cardColor;      // color of the card (red or black)        
	
	public CardImpl(final int nominalValue, final Suit cardSuit) {
		this.nominalValue = nominalValue;
		this.cardSuit = cardSuit;
		
		// management of the color of the card (hearts, diamonds -> red), (clubs, spades -> black)
		if (this.cardSuit == Suit.HEARTS ||
			this.cardSuit == Suit.DIAMONDS) 
			this.cardColor = CardColor.RED;
		else
			this.cardColor = CardColor.BLACK;
		
		// management of the value of the card following the blackjack rules
		// cards from 2 to 10 maintain their nominal value
		if (this.nominalValue >= 2 &&
			this.nominalValue <= 10)
			this.blackjackValue = this.nominalValue;
		// cards from 11 to 13 takes value 10
		else if (this.nominalValue >= 11 &&
				 this.nominalValue <= 13)
			this.blackjackValue = 10;
		// ace is initialized as eleven, the management of hard or soft ace will be carried out by the hand
		else if (this.nominalValue == 1)
			this.blackjackValue = 11;
		else
			throw new IllegalArgumentException("Not valid nominal value");
	}
	
	public int getNominalValue() {
		return(this.nominalValue);
	}
	
	public Suit getSuit() {
		return(this.cardSuit);
	}
	
	public CardColor getColor() {
		return(this.cardColor);
	}
	
	public int getBlackjackValue() {
		return(this.blackjackValue);
	}

	@Override
	public boolean isAnAce() {
		return(this.nominalValue == 1);
	}
}
