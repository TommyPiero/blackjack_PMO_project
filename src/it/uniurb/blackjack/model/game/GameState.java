package it.uniurb.blackjack.model.game;

// enum for the state of the game
public enum GameState {
	PLAYER_TURN,       // turn of the player
	DEALER_TURN,	   // turn of the dealer
	WAITING_BET,       // state of waiting for bets
	WAITING_INSURANCE, // state of waiting for insurance
	WAITING_PLAY,      // state of waiting for player's play
	FINISHED		   // the round is finished
}
