package it.uniurb.blackjack.view;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

// class that implements the main frame of the Blackjack application
public class BlackjackFrameImpl extends JFrame implements BlackjackFrame {
	// declaration of class' fields
	private final CardLayout cardLayout;     // layout of cards
    private final JPanel     panelContainer; // panel container

    private static final String INIT_SCREEN = "init";
    private static final String BET_SCREEN = "bet";
    private static final String TABLE_SCREEN = "table";
    
    private BlackjackSwingInitViewImpl initScreen; // initialization menu
    private BlackjackSwingBetViewImpl betScreen;   // bet menu
    private BlackjackSwingTableViewImpl tableView; // table view
    
    // class' constructor
    public BlackjackFrameImpl() {
        
    	super("Blackjack");
        // frame's settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        this.cardLayout = new CardLayout();
        this.panelContainer = new JPanel(cardLayout);

        this.initScreen = new BlackjackSwingInitViewImpl();
        this.betScreen = new BlackjackSwingBetViewImpl();
        this.tableView = new BlackjackSwingTableViewImpl();
        
        this.panelContainer.add(this.initScreen, INIT_SCREEN);
        this.panelContainer.add(this.betScreen, BET_SCREEN);
        this.panelContainer.add(this.tableView, TABLE_SCREEN);
        
        add(this.panelContainer);
        cardLayout.show(this.panelContainer, INIT_SCREEN);
    }

    public void showInitScreen() {
    	cardLayout.show(this.panelContainer, INIT_SCREEN);
    }
    
    public void showBetScreen() {
        cardLayout.show(this.panelContainer, BET_SCREEN);
    }
    
    public void showTableScreen() {
        cardLayout.show(this.panelContainer, TABLE_SCREEN);
    }
    
    public BlackjackSwingInitView getInitScreen() {
        return(this.initScreen);
    }

    public BlackjackSwingBetView getBetScreen() {
        return(this.betScreen);
    }
    
    public BlackjackSwingTableView getTableScreen() {
    	return(this.tableView);
    }
}
