package it.uniurb.blackjack.launcher;

import javax.swing.SwingUtilities;

import it.uniurb.blackjack.controller.swingcontroller.BlackjackSwingController;
import it.uniurb.blackjack.model.game.Blackjack;
import it.uniurb.blackjack.view.swingview.BlackjackFrameImpl;

//Entry point of the Blackjack application in his GUI version.
//This class is responsible for assembling the Model, View, and Controller
//components and starting the game loop.
public class AppBlackjackGUI {

	public void start() {
		SwingUtilities.invokeLater(() -> {
			
	        Blackjack blackjack = new Blackjack();
	        
	        BlackjackFrameImpl frame = new BlackjackFrameImpl();
	        
	        // controller instance without assignment 
	        new BlackjackSwingController(blackjack, frame);
	        
	        frame.setVisible(true);
	    });
	}
	
	public static void main(String[] args) {
		new AppBlackjackGUI().start();
	}

}
