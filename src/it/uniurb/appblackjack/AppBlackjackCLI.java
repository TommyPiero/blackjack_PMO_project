package it.uniurb.appblackjack;

import it.uniurb.blackjack.controller.BlackjackTextController;
import it.uniurb.blackjack.controller.BlackjackTextControllerImpl;
import it.uniurb.blackjack.model.game.Blackjack;
import it.uniurb.blackjack.view.BlackjackTextViewImpl;
import it.uniurb.blackjack.view.BlackjackTextView;

// Entry point of the Blackjack application in his CLI version.
// This class is responsible for assembling the Model, View, and Controller
// components and starting the game loop.
public class AppBlackjackCLI {

	public void start() {
		// allocating the model
		Blackjack model = new Blackjack();
				
		// allocating the view
		BlackjackTextView view = new BlackjackTextViewImpl();
				
		// creating the controller
		BlackjackTextController controller = new BlackjackTextControllerImpl(model, view);
				
		System.out.println("STARTING A NEW GAME LOOP");
				
		// starting the game
		controller.playGame();
	}
	
	public static void main(String[] args) {
		new AppBlackjackCLI().start();
	}
}
