package it.uniurb.appblackjack;

import it.uniurb.blackjack.controller.BlackjackTextController;
import it.uniurb.blackjack.model.game.Blackjack;
import it.uniurb.blackjack.view.BlackjackTextView;
import it.uniurb.blackjack.view.BlackjackView;

// Entry point of the Blackjack application in his CLI version.
// This class is responsible for assembling the Model, View, and Controller
// components and starting the game loop.
public class AppBlackjackCLI {

	public static void main(String[] args) {
		// allocating the model
		Blackjack model = new Blackjack();
		
		// allocating the view
		BlackjackView view = new BlackjackTextView();
		
		// creating the controller
		BlackjackTextController controller = new BlackjackTextController(model, view);
		
		System.out.println("STARTING A NEW GAME LOOP");
		
		// starting the game
		controller.startGame();
	}

}
