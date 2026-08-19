package it.uniurb.blackjack.model.game;

// interface for a generic game in casino
public interface GameType {
	// declaration of methods
	
	// method that starts all the game
	public void startGame(final String playerName, final double balance);
	
	// method that starts a round
	public void startRound(final double bet, final double sideBet);
	
	// method that verifies the outcome of a play
	public double verifyFinalOutcome(final int n);
}
