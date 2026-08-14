package test.it.uniurb.blackjack.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import it.uniurb.blackjack.model.cards.CardImpl;
import it.uniurb.blackjack.model.cards.Suit;
import it.uniurb.blackjack.model.participants.Dealer;

//implementation of a class for testing the correct behavior of Dealer class 
public class DealerTest {
	
	// 1. Testing if a dealer is still in game or not
	@Test
	public void testIsDealerInGame() {
		// initializing dealers with different hands
		Dealer dealer1 = new Dealer(false);
		Dealer dealer2 = new Dealer(false);
		Dealer dealer3 = new Dealer(true);
		Dealer dealer4 = new Dealer(true);
		
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
		assertEquals(false, dealer1.isInGame(dealer1.hitOnSoft()));
		// expecting the second dealer to be not more in game (score: 17, soft ace, but false configuration)
		assertEquals(false, dealer2.isInGame(dealer2.hitOnSoft()));
		// expecting the third dealer to be in game (score: 17, soft ace, true configuration)
		assertEquals(true, dealer3.isInGame(dealer3.hitOnSoft()));
		// expecting the fourth dealer to be not more in game (score: 17, true configuration, doesn't have a soft ace)
		assertEquals(false, dealer4.isInGame(dealer4.hitOnSoft()));
	}
}
