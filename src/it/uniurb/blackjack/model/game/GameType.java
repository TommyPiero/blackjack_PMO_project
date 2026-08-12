package it.uniurb.blackjack.model.game;

// interface for a generic game in casino
public interface GameType {
	// declaration of methods
	
	// method that starts a round
	public void startRound(final double bet, final double perfPairBet);
	
	// method that verifies the outcome of a play
	public OutcomeType verifyOutcome();
}
