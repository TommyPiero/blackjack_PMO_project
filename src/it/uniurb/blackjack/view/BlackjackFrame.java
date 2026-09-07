package it.uniurb.blackjack.view;

// interface that declares method for the main frame of the Blackjack application
public interface BlackjackFrame {
	// declaration of methods
	
	// method that shows the initialization screen
	public void showInitScreen();
    
	// method that shows the bets screen
    public void showBetScreen();
    
    // method that shows the table screen
    public void showTableScreen();
    
    // getter method for the initialization screen
    public BlackjackSwingInitView getInitScreen();

    // getter method for the bets screen
    public BlackjackSwingBetView getBetScreen();
    
    // getter method for the table screen
    public BlackjackSwingTableView getTableScreen();
}
