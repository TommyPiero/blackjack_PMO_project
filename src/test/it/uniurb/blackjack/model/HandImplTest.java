package test.it.uniurb.blackjack.model;


import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import it.uniurb.blackjack.model.cards.CardImpl;
import it.uniurb.blackjack.model.cards.Hand;
import it.uniurb.blackjack.model.cards.HandImpl;
import it.uniurb.blackjack.model.cards.HandState;
import it.uniurb.blackjack.model.cards.PerfectPairs;
import it.uniurb.blackjack.model.cards.Suit;

//implementation of a class for testing the correct behavior of HandImpl class 
public class HandImplTest {
	// 1. Testing the correct initialization of a normal hand with correct parameters
	@Test
	public void testInitHand() {
		// initializing two hands with different parameters 
		Hand hand1 = new HandImpl(50.0, true, 0.0);
		Hand hand2 = new HandImpl(20.50, false, 5.0);
		
		// checking the getter method for the bet
		assertEquals(50.0, hand1.getBet());
		assertEquals(20.50, hand2.getBet());
		
		// checking the getter method for the side bet
		assertEquals(0.0, hand1.getSideBet());
		assertEquals(5.0, hand2.getSideBet());
		
		// checking if a hand is from a split
		assertEquals(true, hand1.isFromSplit());
		assertEquals(false, hand2.isFromSplit());
		
		// checking if the state of the hands is correctly initialized to active
		assertEquals(HandState.ACTIVE, hand1.getHandState());
		assertEquals(HandState.ACTIVE, hand2.getHandState());
	}
	
	// 2. Testing the calculation of score in different possible hands
	@Test
	public void testCalcScore() {
		// initializing hands and giving them different cards
		Hand hand1 = new HandImpl(20.0, false, 0.0);
		Hand hand2 = new HandImpl(30.0, false, 5.0);
		Hand hand3 = new HandImpl(10.50, true, 0.0);
		Hand hand4 = new HandImpl(12.50, true, 0.0);
		
		// giving cards to the hands
		hand1.takeCard(new CardImpl(6, Suit.HEARTS));
		hand1.takeCard(new CardImpl(12, Suit.DIAMONDS));
		hand2.takeCard(new CardImpl(1, Suit.CLUBS));
		hand2.takeCard(new CardImpl(9, Suit.SPADES));
		hand3.takeCard(new CardImpl(1, Suit.CLUBS));
		hand3.takeCard(new CardImpl(1, Suit.SPADES));
		hand4.takeCard(new CardImpl(10, Suit.CLUBS));
		hand4.takeCard(new CardImpl(13, Suit.DIAMONDS));
		
		// checking the correct value of the score
		// expecting the first hand to have a score of 16
		assertEquals(16, hand1.getScore());
		// expecting the second hand to have a score of 20
		assertEquals(20, hand2.getScore());
		// expecting the third hand to have a score of 12
		assertEquals(12, hand3.getScore());
		// expecting the forth hand to have a score of 20
		assertEquals(20, hand4.getScore());
		
		// adding another card to the hands
		hand1.takeCard(new CardImpl(1, Suit.DIAMONDS));
		hand2.takeCard(new CardImpl(1, Suit.HEARTS));
		hand3.takeCard(new CardImpl(10, Suit.HEARTS));
		hand4.takeCard(new CardImpl(12, Suit.CLUBS));
		
		// checking the score again to see the correct behavior of soft ace
		// expecting the first hand to have a score of 16
		assertEquals(17, hand1.getScore());
		// expecting the second hand to have a score of 20
		assertEquals(21, hand2.getScore());
		// expecting the third hand to have a score of 12
		assertEquals(12, hand3.getScore());
		// expecting the fourth hand to have a score of 30
		assertEquals(30, hand4.getScore());
	}
	
	// 3. Testing the correct behavior of bust state
	@Test
	public void testBustState() {
		// initializing different hands
		Hand hand1 = new HandImpl(10.0, true, 0.0);
		Hand hand2 = new HandImpl(50.0, false, 3.0);
		Hand hand3 = new HandImpl(20.0, false, 0.0);
		
		// giving cards to the hands
		hand1.takeCard(new CardImpl(10, Suit.DIAMONDS));
		hand1.takeCard(new CardImpl(8, Suit.SPADES));
		hand1.takeCard(new CardImpl(4, Suit.HEARTS));
		hand2.takeCard(new CardImpl(3, Suit.DIAMONDS));
		hand2.takeCard(new CardImpl(6, Suit.CLUBS));
		hand2.takeCard(new CardImpl(1, Suit.HEARTS));
		hand3.takeCard(new CardImpl(12, Suit.SPADES));
		hand3.takeCard(new CardImpl(13, Suit.SPADES));
		hand3.takeCard(new CardImpl(5, Suit.DIAMONDS));
		
		// checking if a hand is bust or not
		// expecting the first hand to be bust (score: 22)
		assertEquals(true, hand1.isBust());
		// expecting the second hand not to be bust (score: 20)
		assertEquals(false, hand2.isBust());
		// expecting the third hand to be bust (score: 25)
		assertEquals(true, hand3.isBust());
	}
	
	// 4. Testing the correct behavior of blackjack state
	@Test
	public void testBlackjackState() {
		// initializing different hands
		Hand hand1 = new HandImpl(15.0, true, 0.0);
		Hand hand2 = new HandImpl(25.0, false, 3.0);
		Hand hand3 = new HandImpl(8.0, false, 0.0);
		Hand hand4 = new HandImpl(10.0, false, 2.0);
		Hand hand5 = new HandImpl(5.0, true, 0.0);
		
		// giving cards to the hands
		hand1.takeCard(new CardImpl(8, Suit.HEARTS));
		hand1.takeCard(new CardImpl(10, Suit.CLUBS));
		hand2.takeCard(new CardImpl(12, Suit.DIAMONDS));
		hand2.takeCard(new CardImpl(1, Suit.SPADES));
		hand3.takeCard(new CardImpl(13, Suit.HEARTS));
		hand3.takeCard(new CardImpl(10, Suit.DIAMONDS));
		hand4.takeCard(new CardImpl(11, Suit.CLUBS));
		hand4.takeCard(new CardImpl(1, Suit.SPADES));
		hand5.takeCard(new CardImpl(9, Suit.CLUBS));
		hand5.takeCard(new CardImpl(6, Suit.HEARTS));
		hand5.takeCard(new CardImpl(9, Suit.CLUBS));
		
		// checking if a hand is blackjack or not
		// expecting the first hand not to be BJ (score: 18)
		assertEquals(false, hand1.isBlackjack());
		// expecting the second hand to be BJ (score: 21)
		assertEquals(true, hand2.isBlackjack());
		// expecting the third hand not to be BJ (score: 20)
		assertEquals(false, hand3.isBlackjack());
		// expecting the fourth hand to be BJ (score: 21)
		assertEquals(true, hand4.isBlackjack());
		// expecting the fifth hand not to be BJ (score: 21 but the hand has three cards)
		assertEquals(false, hand5.isBlackjack());
	}
	
	// 5. Testing the correct behavior of stand state
	@Test
	public void testStandState() {
		// initializing different hands
		Hand hand1 = new HandImpl(10.0, true, 0.0);
		Hand hand2 = new HandImpl(50.0, false, 8.0);
		Hand hand3 = new HandImpl(3.0, false, 0.0);
		
		// giving cards to the hands
		hand1.takeCard(new CardImpl(7, Suit.CLUBS));
		hand1.takeCard(new CardImpl(6, Suit.HEARTS));
		hand1.takeCard(new CardImpl(8, Suit.CLUBS));
		hand2.takeCard(new CardImpl(10, Suit.SPADES));
		hand2.takeCard(new CardImpl(1, Suit.DIAMONDS));
		hand3.takeCard(new CardImpl(3, Suit.HEARTS));
		hand3.takeCard(new CardImpl(4, Suit.SPADES));
		
		// checking if a hand is stand or not
		// expecting the first hand to be stand (score: 21, three cards)
		assertEquals(true, hand1.isStand());
		// expecting the second hand not to be stand (score: 21, two cards, it's a BJ)
		assertEquals(false, hand2.isStand());
		// expecting the third hand not to be stand (score: 7)
		assertEquals(false, hand3.isStand());
	}
	
	// 6. Testing the calculation of perfect pairs
	@Test
	public void testPerfPairsCalc() {
		// initializing different hands
		Hand hand1 = new HandImpl(100.0, false, 0.0);
		Hand hand2 = new HandImpl(20.0, false, 8.0);
		Hand hand3 = new HandImpl(1.50, false, 0.0);
		Hand hand4 = new HandImpl(3.50, false, 0.0);
		
		// giving cards to the hands
		hand1.takeCard(new CardImpl(7, Suit.DIAMONDS));
		hand1.takeCard(new CardImpl(7, Suit.DIAMONDS));
		hand2.takeCard(new CardImpl(9, Suit.CLUBS));
		hand2.takeCard(new CardImpl(9, Suit.SPADES));
		hand3.takeCard(new CardImpl(1, Suit.HEARTS));
		hand3.takeCard(new CardImpl(1, Suit.CLUBS));
		hand4.takeCard(new CardImpl(12, Suit.HEARTS));
		hand4.takeCard(new CardImpl(13, Suit.HEARTS));
		
		// checking the types of perfect pairs
		// expecting a perfect pair in the first hand (same nominal value and same suit)
		assertEquals(PerfectPairs.PERF_PAIR, hand1.perfectPairCalc());
		// expecting a coloured pair in the second hand (same nominal value and same color, different suit)
		assertEquals(PerfectPairs.COLOU_PAIR, hand2.perfectPairCalc());
		// expecting a mixed pair in the third hand (same nominal value and different color)
		assertEquals(PerfectPairs.MIX_PAIR, hand3.perfectPairCalc());
		// not expecting a pair in the fourth hand (different nominal value)
		assertEquals(PerfectPairs.NO_PAIR, hand4.perfectPairCalc());
	}
	
	// 7. Testing the count of the soft aces
	@Test
	public void testCountSoftAces() {
		// initializing different hands
		Hand hand1 = new HandImpl(10.0, false, 0.0);
		Hand hand2 = new HandImpl(200.0, false, 8.0);
		Hand hand3 = new HandImpl(20.0, false, 4.0);
		
		// giving cards to the hands
		hand1.takeCard(new CardImpl(1, Suit.CLUBS));
		hand1.takeCard(new CardImpl(6, Suit.HEARTS));
		hand2.takeCard(new CardImpl(1, Suit.SPADES));
		hand2.takeCard(new CardImpl(9, Suit.DIAMONDS));
		hand2.takeCard(new CardImpl(6, Suit.DIAMONDS));
		hand3.takeCard(new CardImpl(1, Suit.HEARTS));
		hand3.takeCard(new CardImpl(1, Suit.SPADES));
		
		// calculating the score and the aces
		hand1.getScore();
		hand2.getScore();
		hand3.getScore();
		
		// checking if a hand has a soft hand or not
		// expecting a soft ace in the first hand
		assertEquals(true, hand1.hasSoftAce());
		// not expecting a soft ace in the second hand
		assertEquals(false, hand2.hasSoftAce());
		// expecting a soft ace in the third hand
		assertEquals(true, hand3.hasSoftAce());
	}
	
	// 8. Testing possible exceptions and edge cases in the constructor of the class
	@Test
	public void testEdgesInitHand() {
		// initializing a hand that comes from a split and has a parameter for the side bet higher than 0
		assertThrows(IllegalArgumentException.class, () -> {
			new HandImpl(10.50, true, 2.0);
		});
	}
	
	// 9. Testing possible exception in the calculation of the perfect pairs
	@Test
	public void testEdgesPerfPairs() {
		// in all the cases the constructor of the class has to throw an IllegalStateException
		// calling the calculation of perfect pairs on a hand with 3 cards
		assertThrows(IllegalStateException.class, () -> {
			Hand hand = new HandImpl(10.0, false, 5.0);
			hand.takeCard(new CardImpl(8, Suit.CLUBS));
			hand.takeCard(new CardImpl(2, Suit.HEARTS));
			hand.takeCard(new CardImpl(11, Suit.SPADES));
			hand.perfectPairCalc();
		});
		
		// calling the calculation of perfect pairs on a hand that comes from a split
		assertThrows(IllegalStateException.class, () -> {
			Hand hand = new HandImpl(10.0, true, 0.0);
			hand.takeCard(new CardImpl(9, Suit.DIAMONDS));
			hand.takeCard(new CardImpl(4, Suit.HEARTS));
			hand.perfectPairCalc();
		});
	}
}
