package test.it.uniurb.blackjack.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

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
}
