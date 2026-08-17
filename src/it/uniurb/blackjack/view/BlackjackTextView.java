package it.uniurb.blackjack.view;

import java.util.Scanner;

import it.uniurb.blackjack.model.cards.Card;
import it.uniurb.blackjack.model.cards.PerfectPairs;
import it.uniurb.blackjack.model.game.OutcomeType;
import it.uniurb.blackjack.model.participants.Participant;
import it.uniurb.blackjack.model.participants.Player;

// class that implements the view of the model in a command line version
public class BlackjackTextView implements BlackjackView {
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

		return (name);
	}

	public double askBalance() {
		// declaration of local variables
		String  input;             // starting input in string format
		double  balance = -1.0;           // starting balance of the player
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

		return(balance);
	}

	public double askChips() {
		// declaration of local variables
		String input; // starting input in a string format
		Double chips = 0.0; // number of chips bet
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

		return (chips);
	}

	public double askSideBet() {
		// declaration of local variables
		String input; // starting input from the user
		double chipsOnSideBet = -1.0; // number of chips on the side bet, it can be zero
		boolean isCorrect = false; // boolean control for the number of chips

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

		return (chipsOnSideBet);
	}

	public void showStartTable(final Participant player, final Participant dealer) {
		System.out.println("\n==========================");
		System.out.println("         GAME TABLE        ");
		System.out.println("===========================");
		
		// printing dealer cards
		System.out.println("Dealer cards:");
		for (Card card: dealer.getHand(0).getCards())
			System.out.println(card.toString());
		
		// printing dealer score
		System.out.println("Dealer score: " + dealer.getHand(0).getScore());
		
		// printing player cards
		System.out.println("Player cards:");
		for (Card card: player.getHand(0).getCards())
			System.out.println(card.toString());
				
		// printing player score
		System.out.println("Player score: " + player.getHand(0).getScore());
	}

	public void showNextTable(final Participant player, final Participant dealer) {
		System.out.println("\n==========================");
		System.out.println("         GAME TABLE        ");
		System.out.println("===========================");
		
		// printing dealer cards
		System.out.println("Dealer cards:");
		for (Card card: dealer.getHand(0).getCards())
			System.out.println(card.toString());
		
		// printing dealer score
		System.out.println("Dealer score: " + dealer.getHand(0).getScore());
		
		// printing player cards
		for (int i = 0;
			 (i < player.getNumHands());
			 i++) {
			System.out.println("Player cards of hand " + i + ":");
			for (Card card: player.getHand(i).getCards())
				System.out.println(card.toString());
		}
				
		// printing player score
		for (int i = 0;
			 (i < player.getNumHands());
			 i++)
				System.out.println("Player score of hand " + i + " is:" + player.getHand(i).getScore());
	}

	public String askMoves() {
		// declaration of local variables
		String move; // move chose from the player
		boolean isCorrect = false; // bool for the correct outcome of the input

		do {
			System.out.println("Choose a move: hit(+), stand(-), double down(x), split(/)");
			move = scanner.nextLine().trim();

			if (move.equals("+") || move.equals("-") || move.equals("x") || move.equals("/"))
				isCorrect = true;
			else
				System.out.println("The move is not valid");
		} while (!isCorrect);

		return (move);
	}

	public void showSideBet(final Player player) {
		// printing the correct outcome of perfect pair
		switch (player.getHand(0).perfectPairCalc()) {
			case PerfectPairs.PERF_PAIR:
				System.out.println("It's a perfect pair!");
				break;
			case PerfectPairs.COLOU_PAIR:
				System.out.println("It's a coloured pair!");
				break;
			case PerfectPairs.MIX_PAIR:
				System.out.println("It's a mixed pair!");
				break;
			case PerfectPairs.NO_PAIR:
				System.out.println("It's not a pair!");
				break;
		}
	}
	
	public void showOutcome(final OutcomeType outcome) {
		// printing the correct outcome
		switch (outcome) {
			case OutcomeType.PLAY_WIN:
				System.out.println("Player won!");
				break;
			case OutcomeType.PLAY_LOSE:
				System.out.println("Player lost!");
				break;
			case OutcomeType.PLAY_BJ:
				System.out.println("Player won with a Blackjack!");
				break;
			case OutcomeType.PUSH:
				System.out.println("It's a draw!");
				break;
		}
	}

	public boolean askForNewRound() {
		
		// declaration of local variables
		String  input;             // starting input in string format
		boolean isCorrect = false; // bool for the check of a correct value
		boolean wannaPlay = false; // flag for the player decision
		
		do {
			System.out.println("Do you want to play another round(y/n)?");
			input = scanner.nextLine().trim();
			
			// checking if it's a correct input
			if (input.equals("y") ||
				input.equals("n"))
				isCorrect = true;
			else
				System.out.println("Not valid answer, retry!");
		} while (!isCorrect);
		
		// setting the variable to the player's decision
		if (input.equals("y"))
			wannaPlay = true;
		
		return(wannaPlay);
	}
}	