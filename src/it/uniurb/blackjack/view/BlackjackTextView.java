package it.uniurb.blackjack.view;

import java.util.Scanner;

import it.uniurb.blackjack.model.game.Blackjack;
import it.uniurb.blackjack.model.game.OutcomeType;
import it.uniurb.blackjack.model.participants.Participant;
import it.uniurb.blackjack.model.participants.Player;

// class that implements the view of the model in a command line version
public class BlackjackTextView implements BlackjackView{
	// declaration of the fields of the class
	private Scanner scanner; // scanner for the inputs from the player
	
	public BlackjackTextView() {
		this.scanner = new Scanner(System.in);
	}

	public String askName() {
		// declaration of local variables
		String name; // name of the player 
		
		// asking for the name, names with more than 30 chars are not accepted
		do {
			System.out.println("Insert your user name(max 30 chars): ");
			name = scanner.nextLine().trim();
		} while (name.length() > 30);
		
		return(name);
	}

	public double askBalance() {
		// declaration of local variables
		String  input;             // starting input in string format
		double  balance;           // starting balance of the player
		boolean isCorrect = false; // bool for the check of a correct balance value
 		
		do {
			System.out.println("Insert your balance(max 1000): ");
			input = scanner.nextLine().trim();
			
			// changing the , with . for avoiding bugs
			input = input.replace(',', '.');
			
			try {
				balance = Double.parseDouble(input);
				
				if (balance <= 1000)
					isCorrect = true;
				else
					System.out.println("The number of chips must be smaller than 1000");
			} catch (NumberFormatException e) {
				System.out.println("Error: not valid number inserted");
			}
			
		} while (!isCorrect);
		
		return 0;
	}
	
	public double askChips() {
		// declaration of local variables
		String input;              // starting input in a string format
		Double chips = 0.0;			   // number of chips bet
		boolean isCorrect = false; // boolean for a correct input from the user
		
		do {
			System.out.println("How many chips you want to bet?");
			input = scanner.nextLine().trim();
			
			// changing the , with . for avoiding bugs
			input = input.replace(',', '.');
			
			try {
				chips = Double.parseDouble(input);
				
				if (chips > 0)
					isCorrect = true;
				else
					System.out.println("The number of chips must be bigger than 0");
			} catch (NumberFormatException e) {
				System.out.println("Error: not valid number inserted");
			}
			
		} while (!isCorrect);
		
		return(chips);
	}

	public double askSideBet() {
		// declaration of local variables
		String input;                  // starting input from the user
		double chipsOnSideBet = -1.0;  // number of chips on the side bet, it can be zero
		boolean isCorrect = false;     // boolean control for the number of chips
		
		do {
			System.out.println("How many chips you want to bet on the side bet(perfect pairs)?");
			input = scanner.nextLine().trim();
			
			// changing the , with . for avoiding bugs
			input = input.replace(',', '.');
			
			try {
				chipsOnSideBet = Double.parseDouble(input);
				
				if (chipsOnSideBet >= 0)
					isCorrect = true;
				else
					System.out.println("The number of chips must be at least 0");
			} catch (NumberFormatException e) {
				System.out.println("Error: not valid number inserted");
			}
		} while (!isCorrect);
		
		return(chipsOnSideBet);
	}
	
	public void showStartTable(final Player player, final Participant dealer) {
		System.out.println("----------TABLE----------");
		System.out.println("username: " + player.getName() + ", balance: " + player.getBalance());
		System.out.println("The main bet is: " + player.getHand().getBet() + ", the side bet is: " + player.getHand().getSideBet());
		// showing dealer starting card and covering one
		System.out.println(dealer.getName() + ": " + dealer.getHand().getCards().get(0) + " + covered -> " + dealer.getHand().getCards().get(0));
		// showing player starting cards
		System.out.println(player.getName() + ": " + player.getHand().getCards().get(0) + " + " + player.getHand().getCards().get(1) + " -> " + player.getHand().getScore());
	}

	public void showSplit(Blackjack blackjack) {
		System.out.println("----------TABLE----------");
		System.out.println("username: " + blackjack.getPlayer().getName() + ", balance: " + blackjack.getPlayer().getBalance());
		System.out.println("The main bet is: " + blackjack.getPlayer().getHand().getBet() + ", the side bet is: " + blackjack.getPlayer().getHand().getSideBet());
		// showing dealer starting card and covering one
		System.out.println(blackjack.getDealer().getName() + ": " + blackjack.getDealer().getHand().getCards().get(0) + " + covered -> " + blackjack.getDealer().getHand().getCards().get(0));
		
		System.out.println("First hand: " + blackjack.getPlayer().getHand().getCards().get(0));
		System.out.println("Second hand: " + blackjack.getPlayer().getHand().getCards().get(1));
	}
	
	// method that incrementally shows the new table with updates of player cards
	public void showNextPlayCard(final Player player, final Participant dealer) {
		this.showStartTable(player, dealer);
		System.out.print(" + " + player.getHand().getCards().getLast() + " -> " + player.getHand().getScore());
	}
	
	// method that incrementally shows the new table with updates of dealer cards
	public void showNextDealCard(final Player player, final Participant dealer) {
		this.showStartTable(player, dealer);
		System.out.print(" + " + dealer.getHand().getCards().getLast() + " -> " + dealer.getHand().getScore());
	}
	
	public String askMoves() {
		// declaration of local variables
		String move;               // move chose from the player
		boolean isCorrect = false; // bool for the correct outcome of the input
		
		do {
			System.out.println("Choose a move: hit(+), stand(-), double down(x), split(/)");
			move = scanner.nextLine().trim();
			
			if (move.equals("+") ||
				move.equals("-") ||
				move.equals("x") ||
				move.equals("/"))
				isCorrect = true;
			else
				System.out.println("The move is not valid");
		} while (!isCorrect);
		
		return(move);
	}

	public void showOutcome(final Blackjack blackjack) {
		System.out.println("Outcome:");
		System.out.println("Dealer points -> " + blackjack.getDealer().getHand().getScore());
		System.out.println("Player points -> " + blackjack.getPlayer().getHand().getScore());
		
		// switch cases for the four types of outcome of a round
		switch (blackjack.verifyOutcome()) {
			case OutcomeType.PLAY_LOSE:
				System.out.println("Dealer wins, player lost " + blackjack.getPlayer().getHand().getBet() + " chips");
				break;
			case OutcomeType.PLAY_WIN:
				System.out.println("Dealer losy, player won " + (2 * blackjack.getPlayer().getHand().getBet()) + " chips");
				break;
			case OutcomeType.PUSH:
				System.out.println("There is a draw, player receives back " + blackjack.getPlayer().getHand().getBet() + " chips");
				break;
			case OutcomeType.PLAY_BJ:
				System.out.println("Dealer lost, player won with a Blackjack and received " + ((2 * blackjack.getPlayer().getHand().getBet()) + (blackjack.getPlayer().getHand().getBet() / 2)) + " chips");
				break;
		}
	}
}
