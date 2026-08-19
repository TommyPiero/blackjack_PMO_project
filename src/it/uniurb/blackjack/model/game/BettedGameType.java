package it.uniurb.blackjack.model.game;

// interface that models the concept of a game with only a main bet
public interface BettedGameType extends GameType {
	// declaration of methods

	// method that starts a round with only a main bet
	public void startRound(final double bet);
	
}
