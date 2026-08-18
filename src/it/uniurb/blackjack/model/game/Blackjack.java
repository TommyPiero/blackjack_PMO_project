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
	private OutcomeType    roundOutcome;   // outcome of a round
	
	// constructor of the class
	public Blackjack() {
		this.gameState = GameState.WAITING_BET;
		this.roundOutcome = OutcomeType.PLAY_LOSE;
	}

	// setter method that configure the game
	public void configureGame(final int numDecks, final boolean hitOnSoft) {
		this.configurations = new ConfigurationImpl(numDecks, hitOnSoft);
		this.dealer = new Dealer(hitOnSoft);
		this.shoe = new ShoeImpl(numDecks);
		this.player = new Player();
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
		// giving standard cards to player and dealer
		this.player.hit(this.shoe.drawCard(), this.player.getHand(0));
		this.dealer.hit(this.shoe.drawCard());
		this.player.hit(this.shoe.drawCard(), this.player.getHand(0));
		this.dealer.hit(this.shoe.drawCard());
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
				break;
			case MoveType.DOUBLE_DOWN:
				this.player.doubleDown(this.shoe.drawCard(), this.player.getHand(numHand));
				break;
			case MoveType.SPLIT:
				// throwing an exception if the hand that require a split comes from a split
				if (this.player.getHand(numHand).isFromSplit())
					throw new IllegalStateException("A split can't be done on a hand that comes from a split");
				this.player.split(this.shoe.drawCard(), this.shoe.drawCard(), this.player.getHand(numHand));
				break;
		}
	}
	
	// method that change the turn from player to dealer
	public void changeDealerTurn() {
		this.gameState = GameState.DEALER_TURN;
	}
	
	public void playDealerHand() {
		while (this.dealer.isInGame(configurations.isDealerHitSoft())) {
			this.dealer.hit(this.shoe.drawCard());
		}
		
		this.gameState = GameState.FINISHED;
	}
	
	// method that verifies the outcome of the side bet
	public double verifySideBet() {
		// declaration of local variables
		double moneySideBet = 0; // money won from the side bet
		
		// if the hand is a perfect pair it pays 25:1 
		switch(this.player.getHand(0).perfectPairCalc()) {
		case PerfectPairs.PERF_PAIR: 
			moneySideBet = this.player.winTheSideBet(25);
			break;
		case PerfectPairs.COLOU_PAIR: 
			moneySideBet = this.player.winTheSideBet(12);
			break;
		case PerfectPairs.MIX_PAIR: 
			moneySideBet = this.player.winTheSideBet(6);
			break;
		case PerfectPairs.NO_PAIR: 
			break;
		default:
			break;
		}
		
		return(moneySideBet);
	}
	
	public double verifyFinalOutcome(final int n) {
		// declaration of local variables
		double wonMoney = 0.0;							     // money won from a round
		int playerScore = this.player.getHand(n).getScore(); // score of the player
		int dealerScore = this.dealer.getHand(0).getScore(); // score of the dealer
		
		// in this case the player lose the bet and he doesn't win money
		if (this.player.getHand(n).isBust()) 
			this.roundOutcome = OutcomeType.PLAY_LOSE;
		// in this case the player wins with a Blackjack and receives back the bet and a half
		else if ((this.player.getHand(n).isBlackjack()) &&
				 !(this.dealer.getHand(0).isBlackjack())) {
			wonMoney = this.player.winTheBet(2.5, n);
			this.roundOutcome = OutcomeType.PLAY_BJ;
		}
		// this is the push case, the player get back his bet
		else if (playerScore == dealerScore) {
			wonMoney = this.player.winTheBet(1, n);
			this.roundOutcome = OutcomeType.PUSH;
		}
		// in this case the player wins normally and receives back double of the bet
		else if ((playerScore > dealerScore) ||
				 (this.dealer.getHand(0).isBust())) {
			wonMoney = this.player.winTheBet(2, n);
			this.roundOutcome = OutcomeType.PLAY_WIN;
		}
		else {
			this.roundOutcome = OutcomeType.PLAY_LOSE;
		}
		
		return(wonMoney);
	}
	
	// method that verifies the insurance of the player
	public double verifyInsurance() {
		// declaration of local variables
		double winInsurance = 0; // money won from the insurance
		
		if (this.player.isInsured()) {
			if (this.dealer.getHand(0).isBlackjack()) {
				winInsurance = this.player.winInsurance();
			}
		}
		
		return(winInsurance);
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
	
	// getter method that return if a round is finished or not
	public boolean isFinished() {
		return(this.gameState.equals(GameState.FINISHED));
	}
	
	// getter method that return the outcome of the round
	public OutcomeType getOutcome() {
		return(this.roundOutcome);
	}
}
