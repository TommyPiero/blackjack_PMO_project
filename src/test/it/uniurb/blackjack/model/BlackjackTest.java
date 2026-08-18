package test.it.uniurb.blackjack.model;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import it.uniurb.blackjack.model.cards.CardImpl;
import it.uniurb.blackjack.model.cards.HandState;
import it.uniurb.blackjack.model.cards.Suit;
import it.uniurb.blackjack.model.game.Blackjack;
import it.uniurb.blackjack.model.game.MoveType;
import it.uniurb.blackjack.model.game.OutcomeType;

//implementation of a class for testing the correct behavior of Blackjack class 
public class BlackjackTest {
	
	// 1. Test of the four possible moves for the player
	@Test
	public void testFourMoves() {
		// initialization of games
		Blackjack bj1 = new Blackjack();
		Blackjack bj2 = new Blackjack();
		Blackjack bj3 = new Blackjack();
		Blackjack bj4 = new Blackjack();
		
		bj1.configureGame(4, true);
		bj2.configureGame(5, true);
		bj3.configureGame(6, false);
		bj4.configureGame(3, false);
		
		// initialization of players
		bj1.getPlayer().initPlayer("first", 100.0);
		bj2.getPlayer().initPlayer("second", 200.0);
		bj3.getPlayer().initPlayer("third", 300.0);
		bj4.getPlayer().initPlayer("fourth", 400.0);
		
		// all of four players take two cards (using hit directly by players, this permit to choose specific cards, only for testing)
		bj1.getPlayer().newRound(20.0, 2.0);
		bj1.getPlayer().hit(new CardImpl(8, Suit.CLUBS), bj1.getPlayer().getHand(0));
		bj1.getPlayer().hit(new CardImpl(4, Suit.DIAMONDS), bj1.getPlayer().getHand(0));
		
		bj2.getPlayer().newRound(10.0, 0.0);
		bj2.getPlayer().hit(new CardImpl(1, Suit.HEARTS), bj2.getPlayer().getHand(0));
		bj2.getPlayer().hit(new CardImpl(1, Suit.SPADES), bj2.getPlayer().getHand(0));
		
		bj3.getPlayer().newRound(15.0, 4.0);
		bj3.getPlayer().hit(new CardImpl(5, Suit.HEARTS), bj3.getPlayer().getHand(0));
		bj3.getPlayer().hit(new CardImpl(5, Suit.HEARTS), bj3.getPlayer().getHand(0));
		
		bj4.getPlayer().newRound(50.0, 0.0);
		bj4.getPlayer().hit(new CardImpl(1, Suit.HEARTS), bj4.getPlayer().getHand(0));
		bj4.getPlayer().hit(new CardImpl(13, Suit.DIAMONDS), bj4.getPlayer().getHand(0));
		
		// first player hits a card and it bust
		bj1.getPlayer().hit(new CardImpl(10, Suit.DIAMONDS), bj1.getPlayer().getHand(0));
		
		// second player splits his cards and hits on both of them
		bj2.getPlayer().split(new CardImpl(4, Suit.DIAMONDS), new CardImpl(5, Suit.SPADES), bj2.getPlayer().getHand(0));
		bj2.getPlayer().hit(new CardImpl(4, Suit.CLUBS), bj2.getPlayer().getHand(0));
		bj2.getPlayer().hit(new CardImpl(12, Suit.HEARTS), bj2.getPlayer().getHand(1));
		bj2.getPlayer().stand(bj2.getPlayer().getHand(0));
		
		// third player hits a double down
		bj3.getPlayer().doubleDown(new CardImpl(2, Suit.SPADES), bj3.getPlayer().getHand(0));
		
		// checking if the score matches the cards draw
		assertEquals(22, bj1.getPlayer().getHand(0).getScore());
		
		assertEquals(19, bj2.getPlayer().getHand(0).getScore());
		assertEquals(16, bj2.getPlayer().getHand(1).getScore());
		
		assertEquals(12, bj3.getPlayer().getHand(0).getScore());
		
		assertEquals(21, bj4.getPlayer().getHand(0).getScore());
		
		// checking if the hand state matches the moves of the player
		assertEquals(HandState.BUST, bj1.getPlayer().getHand(0).getHandState());
		
		assertEquals(HandState.STAND, bj2.getPlayer().getHand(0).getHandState());
		assertEquals(HandState.ACTIVE, bj2.getPlayer().getHand(1).getHandState());
		
		assertEquals(HandState.STAND, bj3.getPlayer().getHand(0).getHandState());
		
		assertEquals(HandState.BLACKJACK, bj4.getPlayer().getHand(0).getHandState());
	}
	
	// 2. Test that verifies the correct pay out of the rounds (bets and side bets)
	@Test
	public void testPayment() {
		Blackjack bj1 = new Blackjack();
		Blackjack bj2 = new Blackjack();
		Blackjack bj3 = new Blackjack();
		Blackjack bj4 = new Blackjack();
		
		bj1.configureGame(5, false);
		bj2.configureGame(4, true);
		bj3.configureGame(6, false);
		bj4.configureGame(2, false);
		
		// initializing name and balance of the player
		bj1.getPlayer().initPlayer("first", 100.0);
		bj2.getPlayer().initPlayer("second", 200.0);
		bj3.getPlayer().initPlayer("third", 300.0);
		bj4.getPlayer().initPlayer("fourth", 400.0);
		
		// playing the first game
		bj1.getPlayer().newRound(20.0, 2.0);
		bj1.getDealer().newRound();
		bj1.getPlayer().hit(new CardImpl(8, Suit.CLUBS), bj1.getPlayer().getHand(0));
		bj1.getDealer().hit(new CardImpl(4, Suit.DIAMONDS));
		bj1.getPlayer().hit(new CardImpl(10, Suit.DIAMONDS), bj1.getPlayer().getHand(0));
		bj1.getDealer().hit(new CardImpl(3, Suit.SPADES));
		bj1.verifySideBet();
		
		bj1.getPlayer().stand(bj1.getPlayer().getHand(0));
		bj1.getDealer().hit(new CardImpl(13, Suit.HEARTS));
		
		// playing the second game
		bj2.getPlayer().newRound(50.0, 3.0);
		bj2.getDealer().newRound();
		bj2.getPlayer().hit(new CardImpl(8, Suit.HEARTS), bj2.getPlayer().getHand(0));
		bj2.getDealer().hit(new CardImpl(10, Suit.DIAMONDS));
		bj2.getPlayer().hit(new CardImpl(8, Suit.HEARTS), bj2.getPlayer().getHand(0));
		bj2.getDealer().hit(new CardImpl(10, Suit.SPADES));
		bj2.verifySideBet();
		
		bj2.getPlayer().stand(bj2.getPlayer().getHand(0));
		bj2.playDealerHand();
		
		// playing the third game
		bj3.getPlayer().newRound(30.0, 0.0);
		bj3.getDealer().newRound();
		bj3.getPlayer().hit(new CardImpl(1, Suit.CLUBS), bj3.getPlayer().getHand(0));
		bj3.getDealer().hit(new CardImpl(7, Suit.DIAMONDS));
		bj3.getPlayer().hit(new CardImpl(10, Suit.DIAMONDS), bj3.getPlayer().getHand(0));
		bj3.getDealer().hit(new CardImpl(10, Suit.SPADES));
		bj3.verifySideBet();
		
		bj3.playDealerHand();
		
		// playing the fourth game
		bj4.getPlayer().newRound(10.0, 2.0);
		bj4.getDealer().newRound();
		bj4.getPlayer().hit(new CardImpl(7, Suit.CLUBS), bj4.getPlayer().getHand(0));
		bj4.getDealer().hit(new CardImpl(7, Suit.DIAMONDS));
		bj4.getPlayer().hit(new CardImpl(7, Suit.SPADES), bj4.getPlayer().getHand(0));
		bj4.getDealer().hit(new CardImpl(5, Suit.HEARTS));
		bj4.verifySideBet();
		
		bj4.getPlayer().hit(new CardImpl(5, Suit.DIAMONDS), bj4.getPlayer().getHand(0));
		bj4.getPlayer().stand(bj4.getPlayer().getHand(0));
		bj4.getDealer().hit(new CardImpl(7, Suit.SPADES));
		
		// verifying outcomes
		bj1.verifyFinalOutcome(0);
		bj2.verifyFinalOutcome(0);
		bj3.verifyFinalOutcome(0);
		bj4.verifyFinalOutcome(0);
		
		// checking the right outcome of the rounds
		assertEquals(OutcomeType.PLAY_WIN, bj1.getOutcome());
		assertEquals(118.0, bj1.getPlayer().getBalance());
		
		assertEquals(OutcomeType.PLAY_LOSE, bj2.getOutcome());
		assertEquals(222.0, bj2.getPlayer().getBalance());
		
		assertEquals(OutcomeType.PLAY_BJ, bj3.getOutcome());
		assertEquals(345.0, bj3.getPlayer().getBalance());
		
		assertEquals(OutcomeType.PUSH, bj4.getOutcome());
		assertEquals(422.0, bj4.getPlayer().getBalance());
	}
	
	// 3. Test for edge cases and exceptions
	@Test
	public void testMovesExceptions() {
		// initializing new bj objects
		Blackjack bj1 = new Blackjack();
		Blackjack bj2 = new Blackjack();
		
		bj1.configureGame(4, true);
		bj2.configureGame(6, false);
		
		// initializing players
		bj1.getPlayer().initPlayer("primo", 100.0);
		bj2.getPlayer().initPlayer("secondo", 200.0);
		
		// trying to make a move before the bet has set
		assertThrows(IllegalStateException.class, () -> {
			bj1.makeMove(MoveType.HIT, 0);
		});
		
		// starting a new round
		bj1.startRound(10.0, 0.0);
		bj1.makeMove(MoveType.STAND, 0);
		// standing the hand and verify that any other move is not possible
		assertThrows(IllegalStateException.class, () -> {
			bj1.makeMove(MoveType.HIT, 0);
		});
		
		// starting a new round
		bj2.startRound(20.0, 0.0);
		// trying to split twice a hand
		bj2.makeMove(MoveType.SPLIT, 0);
		assertThrows(IllegalStateException.class, () -> {
			bj2.makeMove(MoveType.SPLIT, 0);
		});
	}
}
