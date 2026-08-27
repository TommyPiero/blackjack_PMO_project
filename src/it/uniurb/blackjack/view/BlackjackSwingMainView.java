package it.uniurb.blackjack.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import it.uniurb.blackjack.controller.TableState;
import it.uniurb.blackjack.model.game.OutcomeType;
import it.uniurb.blackjack.model.participants.Dealer;
import it.uniurb.blackjack.model.participants.Player;

public class BlackjackSwingMainView extends JPanel{
	
	// declaration of class' fields
	private JTextField   nameField;       // field for the player's name
    private JTextField   balanceField;    // field for the player's balance
    private JTextField   numDecksField;   // field for the number of decks
    private JCheckBox    typeDealerField; // field for the dealer type
	private JButton      confirmButton;   // button for starting the game
	private JRadioButton pixelRadio;
	private JRadioButton modernRadio;
	private JRadioButton classicRadio;
	private ButtonGroup  cardSetGroup;
	
	// definition of colors for the menu
	private final Color CASINO_GREEN = new Color(7, 94, 46); 
    private final Color DARK_GOLD    = new Color(212, 175, 55); 
    private final Color LIGHT_TEXT   = Color.WHITE;
	
    
    
    // class' constructor
    public BlackjackSwingMainView() {
    	this.setLayout(new BorderLayout(15, 15));
        this.setBackground(CASINO_GREEN);
        
        this.setBorder(new EmptyBorder(25, 25, 25, 25));
    	
        // title creation
        JLabel titleLabel = new JLabel("♣ ♦ BLACKJACK CASINÒ ♦ ♠", JLabel.CENTER);
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 26));
        titleLabel.setForeground(DARK_GOLD);
        titleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
        this.add(titleLabel, BorderLayout.NORTH);
        
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2, 12, 12)); 
        formPanel.setBackground(CASINO_GREEN);
        
        TitledBorder formBorder = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(DARK_GOLD, 2, true), " TABLE CONFIGURATION "
        );
        formBorder.setTitleColor(DARK_GOLD);
        formBorder.setTitleFont(new Font("Arial", Font.BOLD, 12));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            formBorder, 
            new EmptyBorder(20, 20, 20, 20) 
        ));
        
        this.nameField = createStyledTextField(15);
        this.balanceField = createStyledTextField(10);
        this.numDecksField = createStyledTextField(10);

        this.typeDealerField = new JCheckBox("Activate the Soft 17 rule (Hit on soft 17)");
        
        formPanel.add(createStyledLabel("Player name (max 30 characters):"));
        formPanel.add(this.nameField);
        
        formPanel.add(createStyledLabel("Starting balance (max 1000€):"));
        formPanel.add(this.balanceField);
        
        formPanel.add(createStyledLabel("Number of decks (2-8):"));
        formPanel.add(this.numDecksField);
        
        formPanel.add(createStyledLabel("Dealer Soft Hit (y/n):"));
        formPanel.add(this.typeDealerField);

        this.add(formPanel, BorderLayout.CENTER);

        JPanel centerWrapper = new JPanel();
        centerWrapper.setLayout(new BoxLayout(centerWrapper, BoxLayout.Y_AXIS));
        centerWrapper.setBackground(CASINO_GREEN);
        centerWrapper.add(formPanel);
        centerWrapper.add(Box.createVerticalStrut(20));
        centerWrapper.add(createCardSetSelection());
        
        this.add(centerWrapper, BorderLayout.CENTER);
        
        this.confirmButton = new JButton("ENTER GAME TABLE");
        this.confirmButton.setFont(new Font("Arial", Font.BOLD, 14));
        this.confirmButton.setBackground(DARK_GOLD);
        this.confirmButton.setForeground(new Color(30, 30, 30)); 
        this.confirmButton.setFocusPainted(false); 
        this.confirmButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.confirmButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 1),
            new EmptyBorder(12, 0, 12, 0) 
        ));
        
        JPanel buttonWrapper = new JPanel(new BorderLayout());
        buttonWrapper.setBackground(CASINO_GREEN);
        buttonWrapper.setBorder(new EmptyBorder(15, 50, 0, 50));
        buttonWrapper.add(this.confirmButton, BorderLayout.CENTER);
        
        this.add(buttonWrapper, BorderLayout.SOUTH);
    }
    
	private Component createStyledLabel(final String text) {
		JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        label.setForeground(LIGHT_TEXT);
        
        return(label);
	}

	private JTextField createStyledTextField(final int columns) {
		JTextField textField = new JTextField(columns);
        textField.setFont(new Font("Arial", Font.PLAIN, 13));
        textField.setBackground(new Color(245, 245, 245)); 
        textField.setForeground(Color.BLACK);

        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(DARK_GOLD, 1),
            new EmptyBorder(5, 5, 5, 5)
        ));
        
        return (textField);
	}
	
	private JPanel createCardSetSelection() {
		// creating the panel for the selection
		JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setBackground(CASINO_GREEN);
	    
	    // setting the title of the selection
	    JLabel title = new JLabel("Choose the set of cards:");
	    title.setForeground(LIGHT_TEXT);
	    title.setAlignmentX(Component.CENTER_ALIGNMENT);
	    
	    // setting the option row
	    JPanel optionsRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
	    optionsRow.setBackground(CASINO_GREEN);
	    
	    this.cardSetGroup = new ButtonGroup();
	    this.pixelRadio = createCardSetOption("PixelCards", optionsRow);
	    this.modernRadio = createCardSetOption("ModernCards", optionsRow);
	    this.classicRadio = createCardSetOption("ClassicCards", optionsRow);
	    
	    // standard choice
	    this.pixelRadio.setSelected(true);
	    
	    panel.add(title);
	    panel.add(optionsRow);
	    
	    return(panel);
	}
	
	// method that creates an option for the card set
	private JRadioButton createCardSetOption(final String setName, final JPanel choicePanel) {
		// creating and setting the panel for the choice
		JPanel optionPanel = new JPanel();
	    optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
	    optionPanel.setBackground(CASINO_GREEN);
	    
	    // setting the preview
	    JLabel preview = new JLabel(loadPreviewIcon(setName));
	    preview.setAlignmentX(Component.CENTER_ALIGNMENT);
	    
	    JRadioButton radio = new JRadioButton(setName);
	    radio.setForeground(LIGHT_TEXT);
	    radio.setBackground(CASINO_GREEN);
	    radio.setAlignmentX(Component.CENTER_ALIGNMENT);
	    
	    this.cardSetGroup.add(radio);
	    
	    optionPanel.add(preview);
	    optionPanel.add(radio);
	    choicePanel.add(optionPanel);
	    
	    return(radio);
	}
	
	// method that create an image for the preview (three aces)
	private ImageIcon loadPreviewIcon(final String setName) {
		URL url = getClass().getResource("/Resources." + setName + "/card_hearts_13.png");
	    if (url == null) {
	        throw new IllegalStateException("Card not found: " + setName);
	    }
	    ImageIcon original = new ImageIcon(url);
	    int targetWidth = 64;
	    int targetHeight = (int) (targetWidth * ((double) original.getIconHeight() / original.getIconWidth()));
	    Image scaled = original.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
	    
	    return(new ImageIcon(scaled));
	}
	
	// method that returns the set name selected
	public String askCardSetType() {
		// declaration of local variables
		String setName; // name of the choose set
		
	    if (this.pixelRadio.isSelected()) {
	    	setName = "PixelCards";
	    } else if (this.modernRadio.isSelected()) {
	    	setName = "ModernCards";
	    } else {
	    	setName = "ClassicCards";
	    }
	    
	    return(setName);
	}

	public String askName() {
		return(nameField.getText().trim());
	}

	public double askBalance() {
		return(Double.parseDouble(balanceField.getText().trim()));
	}

	public int askNumDecks() {
		return(Integer.parseInt(this.numDecksField.getText().trim()));
	}

	public boolean askDealerType() {
		// declaration and initialization of local variables
		boolean isSoftDealer = false; // flag that record if the dealer is a soft dealer or not
		
		if (this.typeDealerField.isSelected()) {
			isSoftDealer = true;
		}
		
		return(isSoftDealer);
	}

	public void showErrorMessage(final String string) {
		JOptionPane.showMessageDialog(this, string, "Errore", JOptionPane.ERROR_MESSAGE);
	}

	public void setConfirmButtonListener(final ActionListener listener) {
        this.confirmButton.addActionListener(listener);
    }

}
