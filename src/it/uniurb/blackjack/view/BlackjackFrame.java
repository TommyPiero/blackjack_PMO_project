package it.uniurb.blackjack.view;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BlackjackFrame extends JFrame {
	// declaration of class' fields
	private CardLayout cardLayout;
    private JPanel panelContainer;

    private static final String INIT_SCREEN = "init";
    private static final String BET_SCREEN = "bet";
    private static final String TABLE_SCREEN = "table";
    
    private BlackjackSwingMainView initScreen;
    private BlackjackSwingBetViewImpl betScreen;
    private BlackjackSwingTableViewImpl tableView;
    
    // class' constructor
    public BlackjackFrame() {
        
    	super("Blackjack");
        // frame's settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);

        this.cardLayout = new CardLayout();
        this.panelContainer = new JPanel(cardLayout);

        this.initScreen = new BlackjackSwingMainView();
        this.betScreen = new BlackjackSwingBetViewImpl();
        this.tableView = new BlackjackSwingTableViewImpl();
        
        this.panelContainer.add(this.initScreen, INIT_SCREEN);
        this.panelContainer.add(this.betScreen, BET_SCREEN);
        this.panelContainer.add(this.tableView, TABLE_SCREEN);
        
        add(this.panelContainer);
        cardLayout.show(this.panelContainer, INIT_SCREEN);
    }

    public void showBetScreen() {
        cardLayout.show(this.panelContainer, BET_SCREEN);
    }
    
    public void showTableScreen() {
        cardLayout.show(this.panelContainer, TABLE_SCREEN);
    }
    
    public BlackjackSwingMainView getInitScreen() {
        return(this.initScreen);
    }

    public BlackjackSwingBetViewImpl getBetScreen() {
        return(this.betScreen);
    }
    
    public BlackjackSwingTableViewImpl getTableScreen() {
    	return(this.tableView);
    }
}
