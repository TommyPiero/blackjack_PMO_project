package it.uniurb.blackjack.model.game;

// interface that models the concept of a game with a main bet and a side bet(BJ is an example)
public interface SideBettedGameType extends GameType {
	// declaration of methods
	
	// method that starts a round with a main bet and a side bet
	public void startRound(final double bet, final double sideBet);
	
}
