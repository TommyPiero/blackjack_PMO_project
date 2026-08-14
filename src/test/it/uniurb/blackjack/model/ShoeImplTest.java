package test.it.uniurb.blackjack.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import it.uniurb.blackjack.model.cards.Card;
import it.uniurb.blackjack.model.cards.Shoe;
import it.uniurb.blackjack.model.cards.ShoeImpl;

//implementation of a class for testing the correct behavior of ShoeImpl class 
public class ShoeImplTest {
	
	// 1. Testing the correct behavior of the method drawCard initializing a shoe
	@Test
	public void testMethodDrawCard() {
		// initializing a new shoe with a correct value of numDecks
		Shoe shoe = new ShoeImpl(4);
		int cardsRemaining = shoe.getRemainCards(); // initial number of remaining cards
		// drawing a card from the shoe and saving it
		Card drawnCard = shoe.drawCard();
		
		// the number of remaining cards after a draw has to be decreased by one
		assertEquals(cardsRemaining - 1, shoe.getRemainCards());
		// the card drawn has not to be null
		assertNotNull(drawnCard);
	}
	
	// 2. Testing limit cases and error in the initialization of the shoe
	@Test
	public void testEdgesInitShoe() {
		
		// in both cases the constructor should throw an IllegalArgumentException
		assertThrows(IllegalArgumentException.class, () -> {
			new ShoeImpl(12);
		});
		
		assertThrows(IllegalArgumentException.class, () -> {
			new ShoeImpl(0);
		});
	}
	
	// 3. Testing the limit case for cards, method drawCard must throw an exception when the Shoe is empty
	@Test
	public void testEmptyShoe() {
		// simulating to draw out all the cards from a shoe
		Shoe shoe = new ShoeImpl(2);
		int initialCards = shoe.getRemainCards(); // starting number of cards
		
		for (int i = 0;
			 (i < initialCards);
			 i++)
			shoe.drawCard();
		
		// drawing a card with an empty shoe must return an exception
		assertThrows(IllegalStateException.class, () -> {
			shoe.drawCard();
		});
	}
}
