package it.uniurb.blackjack.model.game;

import it.uniurb.blackjack.model.cards.HandState;
import it.uniurb.blackjack.model.cards.Shoe;
import it.uniurb.blackjack.model.cards.ShoeImpl;
import it.uniurb.blackjack.model.participants.Dealer;
import it.uniurb.blackjack.model.participants.Player;

// class that implements the logic of the game Blackjack
public class Blackjack implements GameType {
	// declaration of the fields of the class
	private Dealer    dealer;    // the dealer of the table
	private Player    player;    // the player of the game (single player)
	private Shoe      shoe;      // generic shoe used for cards
	private GameState gameState; // actual state of the game 
	
	// constructor of the class
	public Blackjack(final int numDecks) {
		this.dealer = new Dealer();
		this.player = new Player();
		this.shoe = new ShoeImpl(numDecks);
		this.gameState = GameState.WAITING_BET;
	}

	public void startRound(final double bet, final double perfPairBet) {
		// player gets a hand, isFromSplit set to false because is a new round
		this.player.newRound(bet, perfPairBet);
		// dealer gets a hand
		this.dealer.newRound();
		// setting the state getting the turn to the player
		this.gameState = GameState.WAITING_PLAY;
	}

	public void makeMove(final MoveType move, final int numHand) {
		if (this.gameState == GameState.WAITING_PLAY) {
			switch (move) {
				case MoveType.HIT:
					this.player.hit(this.shoe.drawCard(), this.player.getHand(numHand));
					break;
				case MoveType.STAND:
					this.player.stand(this.player.getHand(numHand));
					this.gameState = GameState.DEALER_TURN;
					break;
				case MoveType.DOUBLE_DOWN:
					this.player.doubleDown(this.shoe.drawCard(), this.player.getHand(numHand));
					this.gameState = GameState.DEALER_TURN;
					break;
				case MoveType.SPLIT:
					this.player.split(this.shoe.drawCard(), this.shoe.drawCard(), this.player.getHand(numHand));
					break;
			}
		}
		
		if (this.gameState == GameState.DEALER_TURN) {
			this.playDealerHand();
		}
	}
	
	private void playDealerHand() {
		while (this.dealer.isInGame()) {
			this.dealer.hit(this.shoe.drawCard(), this.dealer.getHand(0));
		}
	}
	
	public OutcomeType verifyOutcome(final int n) {
		// declaration of local variables
		OutcomeType outcome = null; 						 // output for the outcome of the round
		int playerScore = this.player.getHand(n).getScore(); // score of the player
		int dealerScore = this.dealer.getHand(0).getScore(); // score of the dealer
		
		// in this case the player lose the bet and he doesn't win money
		if (!((this.player.getHand(n).isBust()) ||
			((playerScore < dealerScore) &&
			 (this.player.getHand(n).getHandState().equals(HandState.STAND))))) {
			// in this case the player wins with a Blackjack and receives back the bet and a half
			if ((this.player.getHand(n).isBlackjack()) &&
				(this.dealer.getHand(0).getHandState() != HandState.BLACKJACK)) {
				this.player.winTheBet((2 * this.player.getHand(n).getBet()) + (this.player.getHand(n).getBet() / 2));
				outcome = OutcomeType.PLAY_BJ;
			}
			// this is the push case, the player get back his bet
			else if (playerScore == dealerScore) {
				this.player.winTheBet(this.player.getHand(n).getBet());
				outcome = OutcomeType.PUSH;
			}
			// in this case the player wins normally and receives back double of the bet
			else if ((playerScore > dealerScore) &&
					 (this.player.getHand(n).getHandState().equals(HandState.STAND))) {
				this.player.winTheBet(2 * this.player.getHand(n).getBet());
				outcome = OutcomeType.PLAY_WIN;
			}
		} else {
			outcome = OutcomeType.PLAY_LOSE;
		}
		
		return(outcome);
	}
	
	// getter method for the player
	public Player getPlayer() {
		return(this.player);
	}
	
	// getter method for the dealer
	public Dealer getDealer() {
		return(this.dealer);
	}
	
	// getter method for the shoe
	public Shoe getShoe() {
		return(this.shoe);
	}
}
