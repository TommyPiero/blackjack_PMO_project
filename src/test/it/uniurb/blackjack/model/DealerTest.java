package test.it.uniurb.blackjack.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.uniurb.blackjack.model.cards.CardImpl;
import it.uniurb.blackjack.model.cards.HandState;
import it.uniurb.blackjack.model.cards.Suit;
import it.uniurb.blackjack.model.participants.DealerImpl;
import it.uniurb.blackjack.model.participants.Dealer;

//implementation of a class for testing the correct behavior of Dealer class 
public class DealerTest {
	
	// 1. Testing if a dealer is still in game or not
	@Test
	public void testIsDealerInGame() {
		// initializing dealers with different hands
		Dealer dealer1 = new DealerImpl(false);
		Dealer dealer2 = new DealerImpl(false);
		Dealer dealer3 = new DealerImpl(true);
		Dealer dealer4 = new DealerImpl(true);
		
		// initializing hands for the dealers
		dealer1.newRound();
		dealer2.newRound();
		dealer3.newRound();
		dealer4.newRound();
		
		// giving cards to dealers
		dealer1.hit(new CardImpl(7, Suit.CLUBS));
		dealer1.hit(new CardImpl(8, Suit.HEARTS));
		dealer1.hit(new CardImpl(4, Suit.CLUBS));
		
		dealer2.hit(new CardImpl(1, Suit.CLUBS));
		dealer2.hit(new CardImpl(6, Suit.SPADES));
		
		dealer3.hit(new CardImpl(1, Suit.CLUBS));
		dealer3.hit(new CardImpl(6, Suit.SPADES));
		
		dealer4.hit(new CardImpl(1, Suit.DIAMONDS));
		dealer4.hit(new CardImpl(6, Suit.HEARTS));
		dealer4.hit(new CardImpl(10, Suit.DIAMONDS));
		
		// checking the correct state of the dealer (in game or not)
		// expecting the first dealer to be not more in game (score: 19)
		assertFalse(dealer1.isInGame(dealer1.hitOnSoft()));
		// expecting the second dealer to be not more in game (score: 17, soft ace, but false configuration)
		assertFalse(dealer2.isInGame(dealer2.hitOnSoft()));
		// expecting the third dealer to be in game (score: 17, soft ace, true configuration)
		assertTrue(dealer3.isInGame(dealer3.hitOnSoft()));
		// expecting the fourth dealer to be not more in game (score: 17, true configuration, doesn't have a soft ace)
		assertFalse(dealer4.isInGame(dealer4.hitOnSoft()));
	}
	
	@Test
	public void testDealerHandState() {
		// initializing dealers with different hands
		Dealer dealer1 = new DealerImpl(false);
		Dealer dealer2 = new DealerImpl(false);
		Dealer dealer3 = new DealerImpl(true);
		Dealer dealer4 = new DealerImpl(true);
		Dealer dealer5 = new DealerImpl(true);
		Dealer dealer6 = new DealerImpl(false);
		
		// initializing hands for the dealers
		dealer1.newRound();
		dealer2.newRound();
		dealer3.newRound();
		dealer4.newRound();
		dealer5.newRound();
		dealer6.newRound();
		
		// giving cards to dealers
		dealer1.hit(new CardImpl(7, Suit.DIAMONDS));
		dealer1.hit(new CardImpl(8, Suit.HEARTS));
		dealer1.hit(new CardImpl(4, Suit.SPADES));
				
		dealer2.hit(new CardImpl(1, Suit.HEARTS));
		dealer2.hit(new CardImpl(6, Suit.HEARTS));
				
		dealer3.hit(new CardImpl(1, Suit.CLUBS));
		dealer3.hit(new CardImpl(6, Suit.DIAMONDS));
				
		dealer4.hit(new CardImpl(1, Suit.SPADES));
		dealer4.hit(new CardImpl(6, Suit.DIAMONDS));
		dealer4.hit(new CardImpl(10, Suit.DIAMONDS));
		
		dealer5.hit(new CardImpl(10, Suit.SPADES));
		dealer5.hit(new CardImpl(6, Suit.CLUBS));
		dealer5.hit(new CardImpl(13, Suit.HEARTS));
		
		dealer6.hit(new CardImpl(10, Suit.DIAMONDS));
		dealer6.hit(new CardImpl(1, Suit.CLUBS));
		
		// calculate scores and soft aces
		dealer1.isInGame(false);
		dealer2.isInGame(false);
		dealer3.isInGame(true);
		dealer4.isInGame(true);
		dealer5.isInGame(true);
		dealer6.isInGame(false);
		
		// expecting a stand for the first dealer (score: 19)
		assertEquals(HandState.STAND, dealer1.getHand().getHandState());
		// expecting a stand for the second dealer (score: 17, dealer doesn't hit on soft 17)
		assertEquals(HandState.STAND, dealer2.getHand().getHandState());
		// expecting an active state for the third dealer (score: 17, dealer hits on soft 17)
		assertEquals(HandState.ACTIVE, dealer3.getHand().getHandState());
		// expecting a stand for the fourth dealer (score: 17, dealer hits on soft 17, doesn't have a soft ace)
		assertEquals(HandState.STAND, dealer4.getHand().getHandState());
		// expecting a bust for the fifth dealer (score: 26)
		assertEquals(HandState.BUST, dealer5.getHand().getHandState());
		// expecting a Blackjack for the sixth dealer (score: 21, two cards in the hand)
		assertEquals(HandState.BLACKJACK, dealer6.getHand().getHandState());
	}
}
