package it.uniurb.blackjack.model.game;

import it.uniurb.blackjack.model.cards.HandState;
import it.uniurb.blackjack.model.cards.Shoe;
import it.uniurb.blackjack.model.cards.ShoeImpl;
import it.uniurb.blackjack.model.cards.PerfectPairs;
import it.uniurb.blackjack.model.participants.Dealer;
import it.uniurb.blackjack.model.participants.Player;

// class that implements the logic of the game Blackjack
public class Blackjack implements GameType {
	// declaration of the fields of the class
	private Dealer         dealer;         // the dealer of the table
	private Player         player;         // the player of the game (single player)
	private Shoe           shoe;           // generic shoe used for cards
	private GameState      gameState;      // actual state of the game 
	private Configuration  configurations; // configurations of the game
	
	// constructor of the class
	public Blackjack(final int numDecks, final boolean hitOnSoft) {
		this.dealer = new Dealer(hitOnSoft);
		this.player = new Player();
		this.shoe = new ShoeImpl(numDecks);
		this.gameState = GameState.WAITING_BET;
	}

	public void startGame(final String playerName, final double balance) {
		// initializing the player
		this.player.initPlayer(playerName, balance);
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
		// throwing an exception if the state is not waiting_play
		if (!this.gameState.equals(GameState.WAITING_PLAY)) 
			throw new IllegalStateException("Player can't make a move now");
		
		// throwing an exception if the state of the hand is not active
		if (!this.player.getHand(numHand).getHandState().equals(HandState.ACTIVE))
			throw new IllegalStateException("The hand is not more active");
		
		switch (move) {
			case MoveType.HIT:
				this.player.hit(this.shoe.drawCard(), this.player.getHand(numHand));
				break;
			case MoveType.STAND:
				this.player.stand(this.player.getHand(numHand));
				// after a stand move the turn passes to the dealer
				this.gameState = GameState.DEALER_TURN;
				break;
			case MoveType.DOUBLE_DOWN:
				this.player.doubleDown(this.shoe.drawCard(), this.player.getHand(numHand));
				// after a double down move the turn passes to the dealer
				this.gameState = GameState.DEALER_TURN;
				break;
			case MoveType.SPLIT:
				// throwing an exception if the hand that require a split comes from a split
				if (this.player.getHand(numHand).isFromSplit())
					throw new IllegalStateException("A split can't be done on a hand that comes from a split");
				this.player.split(this.shoe.drawCard(), this.shoe.drawCard(), this.player.getHand(numHand));
				break;
		}
	}
	
	public void playDealerHand() {
		while (this.dealer.isInGame(configurations.isDealerHitSoft())) {
			this.dealer.hit(this.shoe.drawCard());
		}
	}
	
	// method that verifies the outcome of the side bet
	public void verifySideBet() {
		// if the hand is a perfect pair it pays 25:1 
		switch(this.player.getHand(0).perfectPairCalc()) {
		case PerfectPairs.PERF_PAIR: 
			this.player.winTheSideBet(25);
			break;
		case PerfectPairs.COLOU_PAIR: 
			this.player.winTheSideBet(12);
			break;
		case PerfectPairs.MIX_PAIR: 
			this.player.winTheSideBet(6);
			break;
		case PerfectPairs.NO_PAIR: 
			break;
		default:
			break;
		}
	}
	
	public OutcomeType verifyFinalOutcome(final int n) {
		// declaration of local variables
		OutcomeType outcome = null; 						 // output for the outcome of the round
		int playerScore = this.player.getHand(n).getScore(); // score of the player
		int dealerScore = this.dealer.getHand(0).getScore(); // score of the dealer
		
		// in this case the player lose the bet and he doesn't win money
		if (!((this.player.getHand(n).isBust()) ||
			((playerScore < dealerScore) &&
			 (this.player.getHand(n).isStand())))) {
			// in this case the player wins with a Blackjack and receives back the bet and a half
			if ((this.player.getHand(n).isBlackjack()) &&
				(this.dealer.getHand(0).isBlackjack())) {
				this.player.winTheBet(2.5, n);
				outcome = OutcomeType.PLAY_BJ;
			}
			// this is the push case, the player get back his bet
			else if (playerScore == dealerScore) {
				this.player.winTheBet(1, n);
				outcome = OutcomeType.PUSH;
			}
			// in this case the player wins normally and receives back double of the bet
			else if ((playerScore > dealerScore) &&
					 (this.player.getHand(n).isStand())) {
				this.player.winTheBet(2, n);
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
