package test.it.uniurb.blackjack.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import it.uniurb.blackjack.model.cards.Card;
import it.uniurb.blackjack.model.cards.CardColor;
import it.uniurb.blackjack.model.cards.CardImpl;
import it.uniurb.blackjack.model.cards.Suit;

// implementation of a class for testing the correct behavior of CardImpl class 
public class CardImplTest {

	// 1. Testing a normal value and suit for a card
	@Test
	public void testNominalValueAndSuit() {
		// creating a card with nominal value 3 and suit Clubs
		Card card = new CardImpl(3, Suit.CLUBS);
		
		assertEquals(3, card.getNominalValue());        // the getter method has to return the nominal value 3
		assertEquals(3, card.getBlackjackValue());      // the getter method has to return the blackjack value that is 3 for this card
		assertEquals(Suit.CLUBS, card.getSuit());       // the getter method for the suit has to return the suit of the card (clubs)
		assertEquals(CardColor.BLACK, card.getColor()); // the getter method for the color has to return the right color for clubs (black)
		assertEquals(false, card.isAnAce());			// this card is not an ace, so it returns false
	}
	
	// 2. Testing a figure (J, Q, K) to see if there is a difference between the nominal value and the blackjack value
	@Test
	public void testFigure() {
		// creating cards for all the three figures
		Card cardJ = new CardImpl(11, Suit.HEARTS);
		Card cardQ = new CardImpl(12, Suit.SPADES);
		Card cardK = new CardImpl(13, Suit.CLUBS);
		
		// testing nominal value
		assertEquals(11, cardJ.getNominalValue()); // the getter method has to return the nominal value 11 (J)
		assertEquals(12, cardQ.getNominalValue()); // the getter method has to return the nominal value 12 (Q)
		assertEquals(13, cardK.getNominalValue()); // the getter method has to return the nominal value 13 (K)		
		
		// testing blackjack value
		assertEquals(10, cardJ.getBlackjackValue()); // the getter method has to return the blackjack value that is 10 for this card
		assertEquals(10, cardQ.getBlackjackValue()); // the getter method has to return the blackjack value that is 10 for this card
		assertEquals(10, cardK.getBlackjackValue()); // the getter method has to return the blackjack value that is 10 for this card
		
		// testing the suit of the cards
		assertEquals(Suit.HEARTS, cardJ.getSuit()); // the getter method for the suit has to return the suit of the card (hearts)
		assertEquals(Suit.SPADES, cardQ.getSuit()); // the getter method for the suit has to return the suit of the card (spades)
		assertEquals(Suit.CLUBS, cardK.getSuit());  // the getter method for the suit has to return the suit of the card (hearts)

		// testing the color of the cards
		assertEquals(CardColor.RED, cardJ.getColor());   // the getter method for the color has to return the right color for hearts (red)
		assertEquals(CardColor.BLACK, cardQ.getColor()); // the getter method for the color has to return the right color for spades (black)
		assertEquals(CardColor.BLACK, cardK.getColor()); // the getter method for the color has to return the right color for hearts (red)
	}
	
	// 3. Testing an ace to see if there is a difference between the nominal value and the blackjack value
	@Test
	public void testAce() {
		// creating a card with nominal value 13(K) and suit Hearts
		Card card = new CardImpl(1, Suit.DIAMONDS);
			
		assertEquals(1, card.getNominalValue());      // the getter method has to return the nominal value 1
		assertEquals(11, card.getBlackjackValue());   // the getter method has to return the blackjack value that is 11 for this card
		assertEquals(Suit.DIAMONDS, card.getSuit());  // the getter method for the suit has to return the suit of the card (diamonds)
		assertEquals(CardColor.RED, card.getColor()); // the getter method for the color has to return the right color for diamonds (red)
		assertEquals(true, card.isAnAce());			  // this card is an ace, so it returns true
	}
	
	// 4. Testing possible errors and edge cases, like a wrong parameter for the nominal value of the card
	@Test
	public void testEdgeCases() {
		
		// in all the cases the class has to throw an IllegalArgumentException for an invalid input
		
		// case of an higher nominal value
		assertThrows(IllegalArgumentException.class, () -> {
			new CardImpl(16, Suit.DIAMONDS);
		});
		
		// case of a lower nominal value
		assertThrows(IllegalArgumentException.class, () -> {
			new CardImpl(-6, Suit.DIAMONDS);
		});
	}
	
}
