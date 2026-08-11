package it.uniurb.blackjack.model.game;

import it.uniurb.blackjack.model.cards.HandState;
import it.uniurb.blackjack.model.cards.Shoe;
import it.uniurb.blackjack.model.cards.ShoeImpl;
import it.uniurb.blackjack.model.participants.Dealer;
import it.uniurb.blackjack.model.participants.Participant;
import it.uniurb.blackjack.model.participants.Player;

// class that implements the logic of the game Blackjack
public class Blackjack implements GameType {
	// declaration of the fields of the class
	private Dealer dealer; // the dealer of the table
	private Player player; // the player of the game (single player)
	private Shoe   shoe;   // generic shoe used for cards
	
	// constructor of the class
	public Blackjack(final String playerName, final int playerBalance, final int numDecks) {
		this.dealer = new Dealer();
		this.player = new Player(playerName, playerBalance);
		this.shoe = new ShoeImpl(numDecks);
	}

	public void startRound(final int bet) {
		// player gets a hand, isFromSplit set to false because is a new round
		this.player.newHand(this.shoe, bet, false);
		// dealer gets a hand
		this.dealer.newHand(shoe);
	}

	public void verifyOutcome() {
		// in this case the player lose the bet and he doesn't win money
		if (!((this.player.getHand().getHandState() == HandState.BUST) ||
			((this.player.getHand().getScore() < this.dealer.getHand().getScore()) &&
			 (this.player.getHand().getHandState() == HandState.STAND)))) {
			// in this case the player wins with a Blackjack and receives back the bet and a half
			if ((this.player.getHand().getHandState() == HandState.BLACKJACK) &&
				(this.dealer.getHand().getHandState() != HandState.BLACKJACK))
				this.player.winTheBet((2 * this.player.getHand().getBet()) + (this.player.getHand().getBet() / 2));
			// this is the push case, the player get back his bet
			else if (this.player.getHand().getScore() == this.dealer.getHand().getScore())
				this.player.winTheBet(this.player.getHand().getBet());
			// in this case the player wins normally and receives back double of the bet
			else if ((this.player.getHand().getScore() > this.dealer.getHand().getScore()) &&
					 (this.player.getHand().getHandState() == HandState.STAND))
				this.player.winTheBet(2 * this.player.getHand().getBet());
		}
	}
	
	// getter method for the player
	public Participant getPlayer() {
		return(this.player);
	}
	
	// getter method for the dealer
	public Participant getDealer() {
		return(this.dealer);
	}
	
	// getter method for the shoe
	public Shoe getShoe() {
		return(this.shoe);
	}
}
