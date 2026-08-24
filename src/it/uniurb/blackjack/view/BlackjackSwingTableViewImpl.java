package it.uniurb.blackjack.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.Dialog;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import it.uniurb.blackjack.model.cards.Card;
import it.uniurb.blackjack.model.cards.CardColor;
import it.uniurb.blackjack.model.cards.PerfectPairs;
import it.uniurb.blackjack.model.cards.Suit;

import java.awt.BorderLayout;

public class BlackjackSwingTableViewImpl extends JPanel implements BlackjackSwingTableView{
	// declaration of color constants
	private final Color CASINO_GREEN = new Color(7, 94, 46);
    private final Color DARK_GOLD    = new Color(212, 175, 55);
    private final Color LIGHT_TEXT   = Color.WHITE;

    // declaration of local fields
    private JPanel dealerCardSpace;
    private JLabel dealerScore;
    private JPanel playerCardSpace;
    private JLabel balanceLabel;
    private JLabel betLabel;
    private JPanel sideBetOverlay;

    private JButton hitButton;
    private JButton standButton;
    private JButton doubleButton;
    private JButton splitButton;
    
    // class' constructor
    public BlackjackSwingTableViewImpl() {

        this.setLayout(new OverlayLayout(this));

        this.setBackground(CASINO_GREEN);

        this.setBorder(new EmptyBorder(20, 20, 20, 20));
    	
    	this.setLayout(new BorderLayout(15, 15));
        this.setBackground(CASINO_GREEN);
        this.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // initializing the main panel with five spaces (NORTH, CENTER, SOUTH, WEST, EAST)
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(CASINO_GREEN);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // initializing the zone for dealer's cards
        JPanel dealerZone = createDealerZone();
        mainPanel.add(dealerZone, BorderLayout.NORTH);
        
        // initializing the zone for player's cards
        JPanel playerZone = createPlayerZone();
        mainPanel.add(playerZone, BorderLayout.CENTER);
        
        // initializing the zone for buttons
        JPanel buttonsZone = createButtonZone();
        mainPanel.add(buttonsZone, BorderLayout.SOUTH);    
        
        // Add the table as bottom layer
        mainPanel.setAlignmentX(0.5f);
        mainPanel.setAlignmentY(0.5f);
        
        add(mainPanel);
    }

    // method that creates a button zone
	private JPanel createButtonZone() {
		JPanel zone = new JPanel();
        zone.setLayout(new BoxLayout(zone, BoxLayout.Y_AXIS));
        zone.setBackground(CASINO_GREEN);
        
        // line with balance and bet
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 5));
        infoPanel.setBackground(CASINO_GREEN);
        JLabel balance = new JLabel("Balance: -");
        this.balanceLabel = balance;
        balance.setForeground(LIGHT_TEXT);
        JLabel bet = new JLabel("Bet: -");
        this.betLabel = bet;
        bet.setForeground(LIGHT_TEXT);
        infoPanel.add(balance);
        infoPanel.add(bet);
        
        // line with button moves
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonsPanel.setBackground(CASINO_GREEN);
        
        this.hitButton = createButton("HIT");
        this.standButton = createButton("STAND");
        this.doubleButton = createButton("DOUBLE");
        this.splitButton = createButton("SPLIT");
        
        buttonsPanel.add(this.hitButton);
        buttonsPanel.add(this.standButton);
        buttonsPanel.add(this.doubleButton);
        buttonsPanel.add(this.splitButton);
        
        zone.add(infoPanel);
        zone.add(buttonsPanel);
        
        return(zone);
	}

	// method that creates a button
	private JButton createButton(final String text) {
		JButton button = new JButton(text);
		button.setFont(new Font("Arial", Font.BOLD, 13));
		button.setBackground(DARK_GOLD);
		button.setForeground(new Color(30, 30, 30));
		button.setFocusPainted(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		return(button);
		
	}
	
	// method that creates a zone for the player
	private JPanel createPlayerZone() {
		JPanel zone = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        zone.setBackground(CASINO_GREEN);
        
        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(DARK_GOLD, 2, true), " YOUR HAND ");
        border.setTitleColor(DARK_GOLD);
        zone.setBorder(BorderFactory.createCompoundBorder(border, new EmptyBorder(15, 15, 15, 15)));
		
        zone.add(createBlankCardSpace());
        zone.add(createBlankCardSpace());
        
        this.playerCardSpace = zone;
        
		return(zone);
	}

	// method that creates a blank space for a future card
	private Component createBlankCardSpace() {
		JPanel space = new JPanel();
        space.setPreferredSize(new Dimension(64, 92));
        space.setBackground(Color.WHITE);
        space.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        return(space);
	}

	// method that creates a zone for the dealer
	private JPanel createDealerZone() {
		// creating a new zone for the dealer
		JPanel zone = new JPanel();
        zone.setLayout(new BoxLayout(zone, BoxLayout.Y_AXIS));
        zone.setBackground(CASINO_GREEN);
        
        // creating a title for the zone
        JLabel title = new JLabel("DEALER");
        title.setFont(new Font("Arial", Font.BOLD, 14));
        title.setForeground(DARK_GOLD);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // blank zone that will contain dealer's cards
        JPanel cardSpace = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        this.dealerCardSpace = cardSpace;
        cardSpace.setBackground(CASINO_GREEN);
        cardSpace.add(createBlankCardSpace());
        cardSpace.add(createBlankCardSpace());
        
        JLabel score = new JLabel("Score: -");
        this.dealerScore = score;
        score.setForeground(LIGHT_TEXT);
        score.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        zone.add(title);
        zone.add(cardSpace);
        zone.add(score);
        
        
		return(zone);
	}
	
	// method that creates a card with value and suit
	private JPanel createCardComponent(final Card card) {
		// creating the panel for a card
		JPanel cardPanel = new JPanel(new BorderLayout());
	    cardPanel.setPreferredSize(new Dimension(64, 92));
	    cardPanel.setBackground(Color.WHITE);
	    cardPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	    
	    // initializing the color of the card
	    boolean isRed = (card.getColor() == CardColor.RED || card.getColor() == CardColor.BLACK);
	    Color suitColor = isRed ? new Color(200, 30, 30) : Color.BLACK;

	    // initializing the value of the card
	    JLabel valueLabel = new JLabel(cardValueText(card));
	    valueLabel.setForeground(suitColor);
	    valueLabel.setBorder(new EmptyBorder(4, 6, 0, 0));
	    
	    // initializing the suit of the card
	    JLabel suitLabel = new JLabel(suitSymbol(card.getSuit()), SwingConstants.RIGHT);
	    suitLabel.setForeground(suitColor);
	    suitLabel.setBorder(new EmptyBorder(0, 0, 4, 6));

	    // adding card to the panel
	    cardPanel.add(valueLabel, BorderLayout.NORTH);
	    cardPanel.add(suitLabel, BorderLayout.SOUTH);
	    
	    return(cardPanel);
	}
	
	// method that create a face down card
	private JPanel createFaceDownCard() {
		// declaration and initialization of local variables
	    JPanel cardPanel = new JPanel(); // panel for a card
	    
	    cardPanel.setPreferredSize(new Dimension(64, 92));
	    cardPanel.setBackground(new Color(30, 30, 30));
	    cardPanel.setBorder(BorderFactory.createLineBorder(DARK_GOLD));
	    return cardPanel;
	}
	
	// method that return a text value for each BJ value of cards
	private String cardValueText(final Card card) {
	    if (card.isAnAce()) return "A";
	    int value = card.getNominalValue();
	    if (value == 11) return "J";
	    if (value == 12) return "Q";
	    if (value == 13) return "K";
	    return String.valueOf(value);
	}
	
	// method that return a suit symbol
	private String suitSymbol(final Suit suit) {
	    switch (suit) {
	        case Suit.HEARTS:   return "\u2665";
	        case Suit.DIAMONDS: return "\u2666";
	        case Suit.CLUBS:    return "\u2663";
	        case Suit.SPADES:   return "\u2660";
	        default:       return "?";
	    }
	}
	
	public void updateDealerCards(final Card uncoveredCard, final boolean showCoveredCard, final Card coveredCard, final List<Card> dealerCards) {
	    this.dealerCardSpace.removeAll();

	    if (showCoveredCard) {
	    	for (Card card : dealerCards) {
		        this.dealerCardSpace.add(createCardComponent(card));
		    }
	    } else {
		    this.dealerCardSpace.add(createCardComponent(uncoveredCard));
	        this.dealerCardSpace.add(createFaceDownCard());
	    }

	    this.dealerCardSpace.revalidate();
	    this.dealerCardSpace.repaint();
	}
	
	public void updateDealerScore(final int score) {
	    this.dealerScore.setText("Score: " + score);
	}
	
	public void updatePlayerCards(final List<Card> cards, final int score) {
	    this.playerCardSpace.removeAll();

	    for (Card card : cards) {
	        this.playerCardSpace.add(createCardComponent(card));
	    }
	    // adding player's score in the same panel
	    this.playerCardSpace.add(new JLabel("  Score: " + score));

	    this.playerCardSpace.revalidate();
	    this.playerCardSpace.repaint();
	}
	
	public void updateBalance(final double balance) {
        this.balanceLabel.setText(String.format("Balance: %.2f \u20ac", balance));
    }
	
	public void updateBet(final double bet, final double sideBet) {
        this.betLabel.setText(String.format("Bet: %.2f \u20ac | Side bet: %.2f \u20ac", bet, sideBet));
    }
	
	public void showSideBetOutcome(final double winMoney, final PerfectPairs sideBetLevel) {
		
		Window parent = SwingUtilities.getWindowAncestor(this);

	    JDialog dialog = new JDialog(
	        parent,
	        "Side Bet",
	        Dialog.ModalityType.MODELESS
	    );

	    dialog.setSize(500, 280);
	    dialog.setResizable(false);

	    Point parentLocation = parent.getLocationOnScreen();

	    int x = parentLocation.x
	            + (parent.getWidth() - dialog.getWidth()) / 2;

	    int y = parentLocation.y
	            + 30;

	    dialog.setLocation(x, y);
		
	    JPanel panel = new JPanel();
	    panel.setLayout(
	        new BoxLayout(panel, BoxLayout.Y_AXIS)
	    );

	    panel.setBackground(CASINO_GREEN);

	    panel.setBorder(
	        BorderFactory.createCompoundBorder(
	            BorderFactory.createLineBorder(
	                DARK_GOLD,
	                4
	            ),
	            BorderFactory.createEmptyBorder(
	                30, 40, 30, 40
	            )
	        )
	    );

	    JLabel title = new JLabel("SIDE BET");
	    title.setFont(new Font("Arial", Font.BOLD, 30));
	    title.setForeground(DARK_GOLD);
	    title.setAlignmentX(Component.CENTER_ALIGNMENT);

	    JLabel result;
	    
	    switch (sideBetLevel) {
	    	case PerfectPairs.PERF_PAIR:
	    		result = new JLabel("PERFECT PAIR - YOU WIN " + String.format("%.2f", winMoney) + " €!");
	    		break;
	    	case PerfectPairs.COLOU_PAIR:
	    		result = new JLabel("COLORED PAIR, YOU WIN " + String.format("%.2f", winMoney) + " €!");
	    		break;
	    	case PerfectPairs.MIX_PAIR:
	    		result = new JLabel("MIXED PAIR, YOU WIN " + String.format("%.2f", winMoney) + " €!");
	    		break;
	    	case PerfectPairs.NO_PAIR:
	    		result = new JLabel("NO PAIR, YOU LOST!");
	    		break;
	    	default:
	    		result = new JLabel("SIDE BET RESULT");
	    		break;
	    }

	    result.setFont(new Font("Arial", Font.BOLD, 20));
	    result.setForeground(LIGHT_TEXT);
	    result.setAlignmentX(Component.CENTER_ALIGNMENT);

	    JButton continueButton = createButton("CONTINUE");
	    continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    continueButton.addActionListener(
	            e -> dialog.dispose()
	        );

	    panel.add(title);
	    panel.add(Box.createVerticalStrut(20));
	    panel.add(result);
	    panel.add(Box.createVerticalStrut(10));
	    panel.add(continueButton);
	    
	    dialog.setContentPane(panel);

	    dialog.setVisible(true);
	}

	public void setHitListener(final ActionListener listener) {
        this.hitButton.addActionListener(listener);
    }
	
	public void setStandListener(final ActionListener listener) {
        this.standButton.addActionListener(listener);
    }
	
	public void setDoubleListener(final ActionListener listener) {
        this.doubleButton.addActionListener(listener);
    }
	
	public void setSplitListener(final ActionListener listener) {
        this.splitButton.addActionListener(listener);
    }

	public void showErrorMessage(String string) {
		JOptionPane.showMessageDialog(this, string, "Errore", JOptionPane.ERROR_MESSAGE);
	}
}
