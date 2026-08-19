package it.uniurb.appblackjack;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import it.uniurb.blackjack.controller.BlackjackController;
import it.uniurb.blackjack.controller.BlackjackSwingController;
import it.uniurb.blackjack.model.game.Blackjack;
import it.uniurb.blackjack.view.BlackjackSwingMainView;
import it.uniurb.blackjack.view.BlackjackView;

//Entry point of the Blackjack application in his GUI version.
//This class is responsible for assembling the Model, View, and Controller
//components and starting the game loop.
public class AppBlackjackGUI {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(() -> {
			// allocating the model
			Blackjack model = new Blackjack();
		
			// allocating the view
			BlackjackSwingMainView view = new BlackjackSwingMainView();
		
			// creating the controller
			BlackjackController controller = new BlackjackSwingController(model, view);
			
			// creating the frame
			JFrame frame = new JFrame("Blackjack - Configurazione Iniziale");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
            frame.add(view); 
            
            frame.pack();
            frame.setLocationRelativeTo(null); 
            
            frame.setVisible(true);
			
		});
	}

}
