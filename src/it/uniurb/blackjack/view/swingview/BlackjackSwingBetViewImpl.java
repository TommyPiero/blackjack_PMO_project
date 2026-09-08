package it.uniurb.blackjack.view.swingview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

// class that extends JPanel and that will manage the panel for asking bet and side bet
public class BlackjackSwingBetViewImpl extends JPanel implements BlackjackSwingBetView{
	private static final long serialVersionUID = 1L;
	// declaration of class' fields
	private final JLabel     walletLabel;      // label that shows the current balance in real time
    private final JTextField mainBetField;     // field for the main bet
    private final JTextField sideBetField;     // field for the side bet
    private final JButton    placeBetsButton;  // button to confirm and place the bets
    private final JLabel     titleLabel;       // label for the panel's title
    private final JPanel     betsPanel;        // panel for inserting bets
    private final JButton    backButton;       // back button for the bet screen
    
    // initialization of constants for panel's colors
    private final Color CASINO_GREEN = new Color(7, 94, 46);
    private final Color DARK_GOLD    = new Color(212, 175, 55);
    private final Color LIGHT_TEXT   = Color.WHITE;
    
    // class' constructor
    public BlackjackSwingBetViewImpl() {
    	// setting the main panel
    	this.setLayout(new BorderLayout(140, 140));
    	this.setBackground(CASINO_GREEN);
    	this.setBorder(new EmptyBorder(25, 25, 25, 25));
    	
    	// setting panel's title
    	this.titleLabel = new JLabel("PLACE YOUR BETS");
    	
    	titleLabel.setFont(new Font("Georgia", Font.BOLD, 22));
        titleLabel.setForeground(DARK_GOLD);
        this.add(titleLabel, BorderLayout.NORTH);
        
        // setting central panel for the bets
        this.betsPanel = new JPanel(new GridLayout(3, 2, 12, 12));
        betsPanel.setBackground(CASINO_GREEN);
        
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(DARK_GOLD, 2, true), " GAME TABLE "
        );
        border.setTitleColor(DARK_GOLD);
        betsPanel.setBorder(BorderFactory.createCompoundBorder(border, new EmptyBorder(20, 20, 20, 20)));
    
        // initializing fields
        this.walletLabel = new JLabel("Loading balance...", JLabel.LEFT);
        this.walletLabel.setFont(new Font("Arial", Font.BOLD, 14));
        this.walletLabel.setForeground(DARK_GOLD);

        this.mainBetField = createStyledTextField(10);
        this.sideBetField = createStyledTextField(10);
        this.sideBetField.setText("0");
        
        // adding to layout
        betsPanel.add(createStyledLabel("Your actual balance:"));
        betsPanel.add(this.walletLabel);
        
        betsPanel.add(createStyledLabel("Main Bet:"));
        betsPanel.add(this.mainBetField);
        
        betsPanel.add(createStyledLabel("Side Bet:"));
        betsPanel.add(this.sideBetField);
        
        this.add(betsPanel, BorderLayout.CENTER);
        
        // setting the confirm button
        this.placeBetsButton = new JButton("CONFIRM BETS AND DISTRIBUTE CARDS");
        this.placeBetsButton.setFont(new Font("Arial", Font.BOLD, 14));
        this.placeBetsButton.setBackground(DARK_GOLD);
        this.placeBetsButton.setForeground(new Color(30, 30, 30));
        this.placeBetsButton.setFocusPainted(false);
        this.placeBetsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.placeBetsButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.WHITE, 1),
        																  new EmptyBorder(12, 0, 12, 0)));
        
        JPanel buttonWrapper = new JPanel(new BorderLayout());
        buttonWrapper.setBackground(CASINO_GREEN);
        buttonWrapper.setBorder(new EmptyBorder(15, 30, 0, 30));
        buttonWrapper.add(this.placeBetsButton, BorderLayout.CENTER);
        this.add(buttonWrapper, BorderLayout.SOUTH);
        
        // adding a back button. It permits to return to the previous screen

        this.backButton = new JButton("← BACK");
        this.backButton.setFont(new Font("Arial", Font.BOLD, 13));
        this.backButton.setBackground(DARK_GOLD);
        this.backButton.setForeground(new Color(30, 30, 30));
        this.backButton.setFocusPainted(false);
        this.backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(CASINO_GREEN);

        topPanel.add(this.backButton);

        add(topPanel, BorderLayout.NORTH);
    }
    
    // method that creates a styled label with a text passed as a parameter
	private Component createStyledLabel(final String text) {
		// declaration and initialization of local variables
		JLabel label = new JLabel(text); // label to create
		label.setFont(new Font("Arial", Font.BOLD, 13));
		label.setForeground(LIGHT_TEXT);
		return(label);
	}

	// method that creates a styled text field
	private JTextField createStyledTextField(final int columns) {
		// declaration of local variables
		JTextField textField = new JTextField(columns); // text field to create
		textField.setFont(new Font("Arial", Font.PLAIN, 13));
		textField.setBackground(new Color(245, 245, 245));
		textField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(DARK_GOLD, 1), new EmptyBorder(5, 5, 5, 5)));
		return(textField);
	}    
    
    public void updateBalanceDisplay(final double currentBalance) {
    	this.walletLabel.setText(String.format("%.2f €", currentBalance));
    }
    
    public String getMainBetText() {
    	return(this.mainBetField.getText().trim());
    }
    
    public String getSideBetText() {
    	return(this.sideBetField.getText().trim());
    }
    
    public void setPlaceBetsListener(final ActionListener listener) {
    	this.placeBetsButton.addActionListener(listener);
    }
    
    public void setBackButtonListener(final ActionListener listener) {
        this.backButton.addActionListener(listener);
    }
    
    public void showErrorMessage(final String string) {
		JOptionPane.showMessageDialog(this, string, "Errore", JOptionPane.ERROR_MESSAGE);
    }
}
