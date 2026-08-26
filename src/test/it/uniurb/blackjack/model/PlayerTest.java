package test.it.uniurb.blackjack.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import it.uniurb.blackjack.model.cards.CardImpl;
import it.uniurb.blackjack.model.cards.Suit;
import it.uniurb.blackjack.model.participants.PlayerImpl;

//implementation of a class for testing the correct behavior of Player class 
public class PlayerTest {
	// 1. Testing the edge cases and exception starting a new round for the player
	@Test
	public void testEdgeNewPlayRound() {
		// initializing different players
		PlayerImpl player1 = new PlayerImpl();
		PlayerImpl player2 = new PlayerImpl();
		PlayerImpl player3 = new PlayerImpl();
		PlayerImpl player4 = new PlayerImpl();
		PlayerImpl player5 = new PlayerImpl();
		PlayerImpl player6 = new PlayerImpl();
		
		// initializing names and balance of players
		player1.initPlayer("OK", 500.0);
		player2.initPlayer("Not_valid_bet", 700.0);
		player3.initPlayer("Not_valid_side_bet", 10.0);
		player4.initPlayer("Not_valid_bets", 100.0);
		player5.initPlayer("not_enough_money", 5.0);
		player6.initPlayer("not_enough_money_for_insurance", 5.0);
		
		// starting new rounds for the players
		// expecting the first player to not throw an exception
		player1.newRound(10.0, 2.0);
		// expecting the second player to throw an exception (not valid bet value)
		assertThrows(IllegalArgumentException.class, () -> {
			player2.newRound(-2.0, 4.0);
		});
		// expecting the third player to throw an exception (not valid side bet value)
		assertThrows(IllegalArgumentException.class, () -> {
			player3.newRound(7.0, -3.0);
		});
		// expecting the fourth player to throw an exception (not valid bet and side bet value)
		assertThrows(IllegalArgumentException.class, () -> {
			player4.newRound(0.0, -8.0);
		});
		// expecting the fifth player to throw an exception (not enough balance)
		assertThrows(IllegalArgumentException.class, () -> {
			player5.newRound(15.0, 0.0);
		});
		
		player6.newRound(5.0, 0.0);
		// expecting the sixth player to throw an exception (not enough money for the insurance)
		assertThrows(IllegalStateException.class, () -> {
			player6.insure();
		});
	}
	
	// 2. Testing split and double down exceptions
	@Test
	public void testSplitDoubleDownExceptions() {
		// initializing different players
		PlayerImpl player1 = new PlayerImpl();
		PlayerImpl player2 = new PlayerImpl();
		PlayerImpl player3 = new PlayerImpl();
		
		// initializing names and balance of players
		player1.initPlayer("two_splits", 500.0);
		player2.initPlayer("split_diff_cards", 700.0);
		player3.initPlayer("illegal_doub_down", 300.0);
		
		// starting a new round for player one
		player1.newRound(10.0, 0.0);
		player1.hit(new CardImpl(6, Suit.CLUBS), player1.getHand(0));
		player1.hit(new CardImpl(6, Suit.HEARTS), player1.getHand(0));
		player1.split(new CardImpl(8, Suit.DIAMONDS), new CardImpl(10, Suit.SPADES), player1.getHand(0));
		// trying to split a hand twice
		assertThrows(IllegalStateException.class, () -> {
			player1.split(new CardImpl(12, Suit.DIAMONDS), new CardImpl(4, Suit.HEARTS), player1.getHand(0));
		});
		
		// starting a new round for player two
		player2.newRound(20.0, 0.0);
		player2.hit(new CardImpl(12, Suit.SPADES), player2.getHand(0));
		player2.hit(new CardImpl(13, Suit.DIAMONDS), player2.getHand(0));
		// trying to split a hand with cards with different nominal value
		assertThrows(IllegalStateException.class, () -> {
			player2.split(new CardImpl(11, Suit.HEARTS), new CardImpl(2, Suit.CLUBS), player2.getHand(0));
		});
		
		// starting a new round for player three
		player3.newRound(30.0, 0.0);
		player3.hit(new CardImpl(5, Suit.DIAMONDS), player3.getHand(0));
		player3.hit(new CardImpl(6, Suit.DIAMONDS), player3.getHand(0));
		player3.hit(new CardImpl(2, Suit.SPADES), player3.getHand(0));
		// trying to double down with more than two cards
		assertThrows(IllegalStateException.class, () -> {
			player3.doubleDown(new CardImpl(5, Suit.SPADES), player3.getHand(0));
		});
	}
}
