package it.uniurb.blackjack.model.game;

// enum for the four types of outcome of a round
public enum OutcomeType {
	PLAY_WIN, // player wins, dealer loses
	PUSH,     // there's a draw
	PLAY_BJ,  // player wins with a blackjack
	PLAY_LOSE // player lose, dealer wins
}
