package it.uniurb.blackjack.view;

import java.util.Scanner;

import it.uniurb.blackjack.controller.TableState;
import it.uniurb.blackjack.model.cards.Card;
import it.uniurb.blackjack.model.cards.PerfectPairs;
import it.uniurb.blackjack.model.game.OutcomeType;
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
			System.out.println("\nInsert your user name(max 30 chars): ");
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
			System.out.println("\nInsert your balance(max 1000): ");
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

	public int askNumDecks() {
		// declaration of local variables
		String  input;             // starting input in a string format
		int     numDecks = 0;      // number of decks to use in the shoe
		boolean isCorrect = false; // boolean for a correct input from the user
		
		do {
			System.out.println("\nHow many decks you want to use (between 2 and 8)?");
			input = scanner.nextLine().trim();
			
			try {
				numDecks = Integer.parseInt(input);

				if (numDecks >= 2 &&
					numDecks <= 8)
					isCorrect = true;
				else
					System.out.println("The number of decks must be between 2 and 8!");
			} catch (NumberFormatException e) {
				System.out.println("Error: not valid number inserted");
			}
		} while (!isCorrect);
		
		return(numDecks);
	}
	
	public boolean askDealerType() {
		// declaration of local variables
		String  input;              // starting input in a string format
		boolean softDealer = false; // bool for the choice of the player about the dealer's type
		boolean isCorrect = false;  // boolean for a correct input from the user
		
		do {
			System.out.println("\nDo you want a soft hit dealer(y/n)? (He hits also with soft 17)");
			input = scanner.nextLine().trim();
			
			try {
				if (input.equals("y") ||
					input.equals("n"))
					isCorrect = true;
				else
					System.out.println("The answer is not valid retry");
			} catch (NumberFormatException e) {
				System.out.println("Error: not valid value inserted");
			}
		} while (!isCorrect);
		
		if (input.equals("y"))
			softDealer = true;
		
		return(softDealer);
	}
	
	public double askChips(final Player player) {
		// declaration of local variables
		String  input;             // starting input in a string format
		Double  chips = 0.0;       // number of chips bet
		boolean isCorrect = false; // boolean for a correct input from the user

		do {
			System.out.println("\nHow many chips you want to bet? The remaining balance is: " + player.getBalance());
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
		String input; // starting input from the user
		double chipsOnSideBet = -1.0; // number of chips on the side bet, it can be zero
		boolean isCorrect = false; // boolean control for the number of chips

		do {
			System.out.println("\nHow many chips you want to bet on the side bet(perfect pairs)?");
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
	
	public boolean askInsurance() {
		// declaration of local variables
		String  input;             // starting input from the user
		boolean isInsured = false; // boolean output that control if the player wants to insure
		boolean isCorrect = false; // boolean control for the number of chips
		
		do {
			System.out.println("\nDo you want to insure(y/n)? It will cost half of the bet");
			input = scanner.nextLine().trim();
			
			try {
				if (input.equals("y") ||
					input.equals("n"))
					isCorrect = true;
				else
					System.out.println("The answer is not valid retry");
			} catch (NumberFormatException e) {
				System.out.println("Error: not valid value inserted");
			}
		} while (!isCorrect);
		
		if (input.equals("y"))
			isInsured = true;
		
		return(isInsured);
	}
	
	public void showStartTable(final TableState startTableState) {
		System.out.println("\n==========================");
		System.out.println("         GAME TABLE        ");
		System.out.println("===========================");
		System.out.println("\nPlayer balance: " + startTableState.playerBalance() + "\n");
		System.out.println("\nRound bet: " + startTableState.playerHands().get(0).bet());
		System.out.println("\nRound side bet: " + startTableState.playerSideBet() + "\n");
		// printing dealer cards
		System.out.println("Dealer cards:");
		System.out.println("COVERED CARD");
		System.out.println(startTableState.dealerUncoveredCard().toString());
		
		// printing dealer score
		System.out.println("Dealer score: " + startTableState.dealerStartHandScore() + "\n");
		
		// printing player cards
		System.out.println("Player cards:");
		for (Card card: startTableState.playerHands().get(0).cards())
			System.out.println(card.toString());
				
		// printing player score
		System.out.println("Player score: " + startTableState.playerHands().get(0).score() + "\n");
	}

	public void showNextTable(final TableState tableState) {
		System.out.println("\n==========================");
		System.out.println("         GAME TABLE        ");
		System.out.println("===========================");
		
		// printing dealer cards
		System.out.println("Dealer cards:");
		System.out.println("Covered card");
		System.out.println(tableState.dealerUncoveredCard().toString());
		
		// printing dealer score
		System.out.println("Dealer score: " + (tableState.dealerStartHandScore()) + "\n");
		
		// printing player cards
		for (int i = 0;
			 (i < tableState.numPlayerHands());
			 i++) {
			System.out.println("Player cards of hand " + (i + 1) + ":");
			for (Card card: tableState.playerHands().get(i).cards())
				System.out.println(card.toString());
		}
				
		// printing player score
		for (int i = 0;
			 (i < tableState.numPlayerHands());
			 i++)
				System.out.println("Player score of hand " + (i + 1) + " is: " + tableState.playerHands().get(i).score());
	}

	public void showFinalTable(final TableState tableState) {
		System.out.println("\n==========================");
		System.out.println("         GAME TABLE        ");
		System.out.println("===========================");
		
		// printing dealer cards
		System.out.println("Dealer cards:");
		for (Card card : tableState.dealerHand().cards()) {
			System.out.println(card.toString());
		}
		
		// printing dealer score
		System.out.println("Dealer score: " + tableState.dealerHand().score() + "\n");
		
		// printing player cards
		for (int i = 0;
			 (i < tableState.numPlayerHands());
			 i++) {
			System.out.println("Player cards of hand " + i + ":");
			for (Card card: tableState.playerHands().get(i).cards())
				System.out.println(card.toString());
		}
				
		// printing player score
		for (int i = 0;
			 (i < tableState.numPlayerHands());
			 i++)
				System.out.println("Player score of hand " + i + " is: " + tableState.playerHands().get(i).score());

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

	public void showSideBet(final TableState tableState, final double wonMoney) {
		// printing the correct outcome of perfect pair
		switch (tableState.playerPerfPairLevel()) {
			case PerfectPairs.PERF_PAIR:
				System.out.println("It's a perfect pair!");
				System.out.println("You won " + wonMoney + " chips!");
				break;
			case PerfectPairs.COLOU_PAIR:
				System.out.println("It's a coloured pair!");
				System.out.println("You won " + wonMoney + " chips!");
				break;
			case PerfectPairs.MIX_PAIR:
				System.out.println("It's a mixed pair!");
				System.out.println("You won " + wonMoney + " chips!");
				break;
			case PerfectPairs.NO_PAIR:
				System.out.println("It's not a pair!");
				System.out.println("You lost " + tableState.playerSideBet() + " chips!");
				break;
		}
	}
	
	public void showOutcome(final TableState tableState, final double wonBet, final OutcomeType outcome) {
		System.out.println("\n==========================");
		System.out.println("          RESULTS          ");
		System.out.println("===========================");
		// printing the correct outcome
		switch (outcome) {
			case OutcomeType.PLAY_WIN:
				System.out.println("Player won " + wonBet + " chips!");
				break;
			case OutcomeType.PLAY_LOSE:
				System.out.println("Player lost " + tableState.playerHands().get(0).bet() + " chips!");
				break;
			case OutcomeType.PLAY_BJ:
				System.out.println("Player won with a Blackjack! He won " + wonBet + " chips!");
				break;
			case OutcomeType.PUSH:
				System.out.println("It's a draw! Player receives back " + wonBet + " chips!");
				break;
		}
	}

	public boolean askForNewRound(final Player player) {
		// declaration of local variables
		String  input;             // starting input in string format
		boolean isCorrect = false; // bool for the check of a correct value
		boolean wannaPlay = false; // flag for the player decision
		
		do {
			System.out.println("Do you want to play another round(y/n)? The remaininig balance is: " + player.getBalance());
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

	public void showErrorMessage(final String string) {
		System.out.println(string);
	}
}	