package it.uniurb.appblackjack;

import javax.swing.SwingUtilities;

import it.uniurb.blackjack.controller.BlackjackSwingController;
import it.uniurb.blackjack.model.game.Blackjack;
import it.uniurb.blackjack.view.BlackjackFrame;

//Entry point of the Blackjack application in his GUI version.
//This class is responsible for assembling the Model, View, and Controller
//components and starting the game loop.
public class AppBlackjackGUI {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			
	        Blackjack blackjack = new Blackjack();
	        
	        BlackjackFrame frame = new BlackjackFrame();
	        
	        BlackjackSwingController controller = new BlackjackSwingController(blackjack, frame);
	        
	        frame.setVisible(true);
	    });
	}

}
