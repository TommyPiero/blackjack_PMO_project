package it.uniurb.blackjack.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.Dialog;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import it.uniurb.blackjack.model.cards.Card;
import it.uniurb.blackjack.model.cards.CardColor;
import it.uniurb.blackjack.model.cards.PerfectPairs;
import it.uniurb.blackjack.model.cards.Suit;
import it.uniurb.blackjack.model.game.HandFields;
import it.uniurb.blackjack.model.game.HandOutcome;
import it.uniurb.blackjack.model.game.OutcomeType;

import java.awt.BorderLayout;

public class BlackjackSwingTableViewImpl extends JPanel implements BlackjackSwingTableView{
	// declaration of color constants
	private final Color CASINO_GREEN = new Color(7, 94, 46);
    private final Color DARK_GOLD    = new Color(212, 175, 55);
    private final Color LIGHT_TEXT   = Color.WHITE;

    // declaration of local fields
    private JPanel dealerCardSpace;
    private JLabel dealerScore;
    private JPanel playerHandsSpace;
    private JLabel balanceLabel;
    private JLabel betLabel;
    private JLabel settingsLabel;
    
    private JButton hitButton;
    private JButton standButton;
    private JButton doubleButton;
    private JButton splitButton;
    
    private Timer   countdownTimer;
    private JLabel  timerLabel;       
	private int     secondsRemaining;
	
	private String currentSetType;
	
    // class' constructor
    public BlackjackSwingTableViewImpl() {

    	this.currentSetType = "PixelCards";
    	
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
    
    // method that creates a zone for the player
 	private JPanel createPlayerZone() {
 		// declaration and initialization of local variables
 		JPanel zone = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // zone for the player
 		
         zone.setBackground(CASINO_GREEN);
         
         zone.setPreferredSize(new Dimension(0, 220));
         
         // setting a border for the zone
         TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(DARK_GOLD, 2, true), " YOUR HAND ");
         border.setTitleColor(DARK_GOLD);
         zone.setBorder(BorderFactory.createCompoundBorder(border, new EmptyBorder(15, 15, 15, 15))); 
         
         this.playerHandsSpace = zone;
         
 		return(zone);
 	}
    
 	// method that creates a zone for the dealer
 	private JPanel createDealerZone() {
 		// declaration and initialization of local variables
 		JPanel zone = new JPanel(); // new zone for the dealer to create
         zone.setLayout(new BoxLayout(zone, BoxLayout.Y_AXIS));
         zone.setBackground(CASINO_GREEN);
         
         // setting the label for game settings
         this.settingsLabel = new JLabel("Decks: - | Dealer: -");
         this.settingsLabel.setForeground(DARK_GOLD);
         this.settingsLabel.setFont(new Font("Arial", Font.PLAIN, 11));
         this.settingsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
         
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
         
         // setting a label for the score
         JLabel score = new JLabel("Score: -");
         this.dealerScore = score;
         score.setForeground(LIGHT_TEXT);
         score.setAlignmentX(Component.CENTER_ALIGNMENT);
         
         zone.add(title);
         zone.add(this.settingsLabel);
         zone.add(cardSpace);
         zone.add(score);
         
         
 		return(zone);
 	}
 	
 	
 	
    // method that creates a button zone
	private JPanel createButtonZone() {
		// declaration and initialization of local variables
		JPanel zone = new JPanel(); // zone for button
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
        
        // setting the timer label
        this.timerLabel = new JLabel("Remaining time: -");
        this.timerLabel.setFont(new Font("Arial", Font.BOLD, 12));
		this.timerLabel.setForeground(DARK_GOLD);
		this.timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
        zone.add(infoPanel);
        zone.add(this.timerLabel);
        zone.add(buttonsPanel);
        
        return(zone);
	}

	// method that creates a button
	private JButton createButton(final String text) {
		// declaration and initialization of local variables
		JButton button = new JButton(text); // new button to create
		
		button.setFont(new Font("Arial", Font.BOLD, 13));
		button.setBackground(DARK_GOLD);
		button.setForeground(new Color(30, 30, 30));
		button.setFocusPainted(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		return(button);
		
	}
	
	// method that creates the space for a hand
	private JPanel createHandPanel(final List<Card> cards, final int score, final int numHand, boolean isActive) {
		// declaration and initialization of local variables
		JPanel handPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5)); // panel for a hand

		handPanel.setBackground(CASINO_GREEN);

		// setting border color
		Color borderColor = isActive ? Color.WHITE : DARK_GOLD;
		
		// setting border
		TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(borderColor, isActive ? 4 : 2, true),
															   (numHand == 0) ? " HAND " + (numHand + 1) : " HAND " + (numHand + 1));

		border.setTitleColor(DARK_GOLD);

		handPanel.setBorder( BorderFactory.createCompoundBorder(border, new EmptyBorder(10, 15, 10, 15)));
		
		// adding a component for each card
		for (Card card : cards) {
		    handPanel.add(createCardComponent(card));
		}

		// creating and setting a score label
		JLabel scoreLabel = new JLabel("Score: " + score);

		scoreLabel.setForeground(LIGHT_TEXT);

		handPanel.add(scoreLabel);

		return(handPanel);
	}
	
	// method that creates a blank space for a future card
	private Component createBlankCardSpace() {
		// declaration and initialization of local variables		
		JPanel space = new JPanel(); // panel for blank card
        space.setPreferredSize(new Dimension(64, 92));
        space.setBackground(Color.WHITE);
        space.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        return(space);
	}
	
	// method that creates a card with value and suit
	private JPanel createCardComponent(final Card card) {
		// declaration and initialization of local variables
		String fileName = "card_" + card.getSuit().toString().toLowerCase() + "_" + card.getNominalValue() + ".png"; // name of card's image file
		
		ImageIcon icon = loadCardImage(fileName);
		
		JPanel cardPanel = new JPanel(new BorderLayout()); // panel for a card
	    cardPanel.setPreferredSize(new Dimension(64, 92));
	    cardPanel.setBackground(Color.WHITE);
	    cardPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	    
	    // creating card label
	    JLabel cardLabel = new JLabel(icon);

	    // adding card to the panel
	    cardPanel.add(cardLabel);
	    
	    return(cardPanel);
	}
	
	// method that create a face down card
	private JPanel createFaceDownCard() {
		// declaration and initialization of local variables
		ImageIcon icon = loadCardImage("card_back.png"); // name of file for face down card
	    JPanel cardPanel = new JPanel();            	 // panel for a card
	    
	    cardPanel.setPreferredSize(new Dimension(64, 92));
	    cardPanel.setBackground(new Color(30, 30, 30));
	    cardPanel.setBorder(BorderFactory.createLineBorder(DARK_GOLD));
	    
	    // creating card label
	    JLabel cardLabel = new JLabel(icon);
	    
	    cardPanel.add(cardLabel);
	    
	    return cardPanel;
	}
	
	// method that loads the image of cards
	private ImageIcon loadCardImage(final String fileName) {
		URL imageUrl = getClass().getResource("/Resources." + this.currentSetType + "/" + fileName);
		
		if (imageUrl == null) {
	        throw new IllegalStateException("Card image not found: " + fileName);
	    }
		
		ImageIcon original = new ImageIcon(imageUrl);
		
		int originalWidth = original.getIconWidth();
	    int originalHeight = original.getIconHeight();
	    
	    int targetWidth = 80;
	    int targetHeight = (int) (targetWidth * ((double) originalHeight / originalWidth));
	    
	    
		
	    Image scaled = original.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
	    return new ImageIcon(scaled);
	}
	
	public void setCardSetType(final String cardSetType) {
	    this.currentSetType = cardSetType;
	}
	
	public void updateSettings(final int numDecks, final boolean hitOnSoft) {
		String dealerType = hitOnSoft ? "Hits on soft 17" : "Stands on soft 17";
	    this.settingsLabel.setText("Decks: " + numDecks + " | Dealer: " + dealerType);
	}
	
	public void updateDealerCards(final Card uncoveredCard, final boolean showCoveredCard, final Card coveredCard, final List<Card> dealerCards) {
	    this.dealerCardSpace.removeAll();
	    
	    // if the player's round is finished shows the covered card
	    if (showCoveredCard) {
	    	for (Card card : dealerCards) {
		        this.dealerCardSpace.add(createCardComponent(card));
		    }
	    } else {
	    	this.dealerCardSpace.add(createFaceDownCard());
		    this.dealerCardSpace.add(createCardComponent(uncoveredCard));
	    }

	    this.dealerCardSpace.revalidate();
	    this.dealerCardSpace.repaint();
	}
	
	public void updateDealerScore(final int score) {
	    this.dealerScore.setText("Score: " + score);
	}
	
	public void updatePlayerHands(final List<HandFields> playerHands, final int activeHand) {
		this.playerHandsSpace.removeAll();
		
		// creating an hand panel for each hand of the player (after a split)
		for (int i = 0;
			 (i < playerHands.size());
			 i++) {

	        JPanel handPanel = createHandPanel(playerHands.get(i).cards(), playerHands.get(i).score(), i, playerHands.get(i).isInGame());

	        this.playerHandsSpace.add(handPanel);
	    }

	    this.playerHandsSpace.revalidate();
	    this.playerHandsSpace.repaint();
	}
	
	public void updateBalance(final double balance) {
        this.balanceLabel.setText(String.format("Balance: %.2f \u20ac", balance));
    }
	
	public void updateBet(final double bet, final double sideBet) {
        this.betLabel.setText(String.format("Bet: %.2f \u20ac | Side bet: %.2f \u20ac", bet, sideBet));
    }
	
	public void showSideBetOutcome(final double winMoney, final PerfectPairs sideBetLevel) {
		// declaration and initialization of local variables
		Window parent = SwingUtilities.getWindowAncestor(this); // window for showing side bet outcome

		// setting dialog
	    JDialog dialog = new JDialog(parent, "Side Bet", Dialog.ModalityType.APPLICATION_MODAL);
	    dialog.setModal(true);
	    dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    dialog.setSize(500, 280);
	    dialog.setResizable(false);

	    // setting panel
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
	    
	    // setting title for the pop up
	    JLabel title = new JLabel("SIDE BET");
	    title.setFont(new Font("Arial", Font.BOLD, 30));
	    title.setForeground(DARK_GOLD);
	    title.setAlignmentX(Component.CENTER_ALIGNMENT);

	    // showing results based on the outcome
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

	    // setting the continue button that close the window
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
	
	// method that starts the timer for making a move
	public void startMoveTimer(final int seconds, final Runnable onTimeout) {
		// initialization of the seconds remaining
		this.secondsRemaining = seconds;
		
		// setting the timer
		this.countdownTimer = new Timer(1000, e -> {
	        this.secondsRemaining--;
	        timerLabel.setText("Remaining time: " + this.secondsRemaining + "s");
	        
	        // if time finish, stop the timer
	        if (this.secondsRemaining <= 0) {
	        	this.countdownTimer.stop();
	        	// forcing the stand move
	            onTimeout.run();
	        }
		});
		
		this.countdownTimer.start();
	}
	
	// method that stops the timer for making a move, used by the controller when the player press a button in time
	public void stopMoveTimer() {
		if (this.countdownTimer != null 
			&& this.countdownTimer.isRunning()) {
	        this.countdownTimer.stop();
		}
	}
	
	public void showMainBetOutcome(final List<HandOutcome> outcomes, final Runnable onContinue) {
		// declaration and initialization of local variables
		Window parent = SwingUtilities.getWindowAncestor(this); // window for showing main bet outcome

		// setting dialog
	    JDialog dialog = new JDialog(parent, "Outcome", Dialog.ModalityType.APPLICATION_MODAL);
	    dialog.setModal(true);
	    dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    dialog.setSize(500, 280);
	    dialog.setResizable(false);

	    // setting panel
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

	    // setting title
	    JLabel title = new JLabel("OUTCOME");
	    title.setFont(new Font("Arial", Font.BOLD, 30));
	    title.setForeground(DARK_GOLD);
	    title.setAlignmentX(Component.CENTER_ALIGNMENT);

	    panel.add(title);
	    panel.add(Box.createVerticalStrut(20));
	    
	    // showing the outcome for each hand
	    for (int i = 0;
	    	 (i < outcomes.size());
	    	 i++) {
	    	JLabel result;
	    
	    	switch (outcomes.get(i).outcome()) {
	    		case OutcomeType.PLAY_WIN:
	    			result = new JLabel("HAND " + (i + 1) + ": YOU WIN " + String.format("%.2f", outcomes.get(i).winMoney()) + " €!");
	    			break;
	    		case OutcomeType.PLAY_BJ:
	    			result = new JLabel("HAND " + (i + 1) + ": YOU WIN " + String.format("%.2f", outcomes.get(i).winMoney()) + " € WITH A BLACKJACK!");
	    			break;
	    		case OutcomeType.PUSH:
	    			result = new JLabel("HAND " + (i + 1) + ": IT'S A DRAW, YOU GET " + String.format("%.2f", outcomes.get(i).winMoney()) + " € BACK!");
	    			break;
	    		case OutcomeType.PLAY_LOSE:
	    			result = new JLabel("HAND " + (i + 1) + ": YOU LOST " + String.format("%.2f", outcomes.get(i).bet()) + " €!");
	    			break;
	    		default:
	    			result = new JLabel("MAIN BET RESULT");
	    			break;
	    	}
	    	
	    	result.setFont(new Font("Arial", Font.BOLD, 20));
	    	result.setForeground(LIGHT_TEXT);
	    	result.setAlignmentX(Component.CENTER_ALIGNMENT);
	    	
	    	panel.add(result);
		    panel.add(Box.createVerticalStrut(10));
	    }
	    
	    // setting the continue button that will show the pop up for playing again
	    JButton continueButton = createButton("CONTINUE");
	    continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    continueButton.addActionListener(
	            e -> {dialog.dispose();
	            onContinue.run();
	        });
	    
	    panel.add(continueButton);
	    
	    dialog.setContentPane(panel);

	    dialog.setVisible(true);
	}
	
	public void showNewRoundDialog(final Runnable onYes) {
		// declaration and initialization of local variables
		Window parent = SwingUtilities.getWindowAncestor(this); // window for asking for a new round

		// setting windows dialog
	    JDialog dialog = new JDialog(parent, "Blackjack", Dialog.ModalityType.APPLICATION_MODAL);
	    dialog.setModal(true);
	    dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    dialog.setSize(450, 220);
	    dialog.setResizable(false);
	    dialog.setLocationRelativeTo(parent);

	    // setting panel
	    JPanel panel = new JPanel();
	    panel.setLayout(
	        new BoxLayout(panel, BoxLayout.Y_AXIS)
	    );

	    panel.setBackground(CASINO_GREEN);

	    panel.setBorder(
	        BorderFactory.createCompoundBorder(
	            BorderFactory.createLineBorder(
	                DARK_GOLD, 4
	            ),
	            BorderFactory.createEmptyBorder(
	                25, 40, 25, 40
	            )
	        )
	    );

	    // setting windows title, it asks for a new round
	    JLabel title = new JLabel("PLAY AGAIN?");

	    title.setFont(
	        new Font("Arial", Font.BOLD, 28)
	    );

	    title.setForeground(DARK_GOLD);
	    title.setAlignmentX(Component.CENTER_ALIGNMENT);

	    // setting the buttons panel for yes or no
	    JPanel buttonsPanel = new JPanel(
	        new FlowLayout(
	            FlowLayout.CENTER,
	            20,
	            10
	        )
	    );

	    buttonsPanel.setBackground(CASINO_GREEN);

	    // setting the two buttons
	    JButton yesButton = createButton("YES");
	    JButton noButton = createButton("NO");

	    // on yes, the bets panel is opened again
	    yesButton.addActionListener(e -> {
	        dialog.dispose();
	        onYes.run();
	    });

	    // on no, the application gets closed
	    noButton.addActionListener(e -> {
	        dialog.dispose();
	        System.exit(0);
	    });

	    buttonsPanel.add(yesButton);
	    buttonsPanel.add(noButton);

	    panel.add(title);

	    panel.add(
	        Box.createVerticalStrut(35)
	    );

	    panel.add(buttonsPanel);

	    dialog.setContentPane(panel);
	    dialog.setVisible(true);
	}
	
	public void showInsuranceTimerDialog(final int seconds, final Runnable onYes) {
		// declaration and initialization of local variables
		Window parent = SwingUtilities.getWindowAncestor(this); // window for asking for the insurance
		
		// setting a label for the timer
		JLabel timerLabel = new JLabel("Remaining time: " + seconds + "s");
		timerLabel.setFont(new Font("Arial", Font.BOLD, 12));
		timerLabel.setForeground(DARK_GOLD);
		timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// setting windows dialog
		JDialog dialog = new JDialog(parent,"Blackjack",Dialog.ModalityType.APPLICATION_MODAL);
		dialog.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setSize(450, 220);
		dialog.setResizable(false);
		dialog.setLocationRelativeTo(parent);

		// setting panel
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		panel.setBackground(CASINO_GREEN);

		panel.setBorder(BorderFactory.createCompoundBorder(
			            BorderFactory.createLineBorder(DARK_GOLD, 4),
			            BorderFactory.createEmptyBorder(25, 40, 25, 40)));

		// setting windows title, it asks for insurance
		JLabel title = new JLabel("DO YOU WANT TO GET INSURANCE?");

		title.setFont(new Font("Arial", Font.BOLD, 20));

		title.setForeground(DARK_GOLD);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);

		// setting the buttons panel for yes or no
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

		buttonsPanel.setBackground(CASINO_GREEN);

		// setting the two buttons
		JButton yesButton = createButton("YES");
		JButton noButton = createButton("NO");
		// array for moving inside the lambda expression that contains remaining seconds
		int remaining[] = {seconds};
		
		// setting a timer for the insurance question
		Timer timer = new Timer(1000, null);
		timer.addActionListener(e -> {
	        remaining[0]--;
	        timerLabel.setText("Remaining time: " + remaining[0] + "s");
	        // if remaining time is zero, timer stops
	        if (remaining[0] <= 0) {
	            timer.stop();
	            dialog.dispose();
	        }
		});
		
		// on yes, the player insure
		yesButton.addActionListener(e -> {
			timer.stop();
			dialog.dispose();
			onYes.run();
		});
	    noButton.addActionListener(e -> {
	    	timer.stop();
	    	dialog.dispose();
	    });


		buttonsPanel.add(yesButton);
		buttonsPanel.add(noButton);

		panel.add(title);
		panel.add(Box.createVerticalStrut(35));
		panel.add(timerLabel);
		panel.add(Box.createVerticalStrut(15));
		panel.add(buttonsPanel);
		dialog.setContentPane(panel);
		
		timer.start();
		
		dialog.setVisible(true);
	}
	
	public void showInsuranceOutcome(final double winMoney, final Runnable onContinue) {
		// declaration and initialization of local variables
		Window parent = SwingUtilities.getWindowAncestor(this); // window for the insurance outcome

	    JDialog dialog = new JDialog(parent, "Outcome",  Dialog.ModalityType.APPLICATION_MODAL);
	    dialog.setModal(true);
	    dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    dialog.setSize(500, 280);
	    dialog.setResizable(false);
	    
	    // setting the panel
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
	    
	    // setting the title
	    JLabel title = new JLabel("INSURANCE OUTCOME");
	    title.setFont(new Font("Arial", Font.BOLD, 28));
	    title.setForeground(DARK_GOLD);
	    title.setAlignmentX(Component.CENTER_ALIGNMENT);
	    
	    // setting the result
	    JLabel result;
	    
	    if (winMoney == 0) {
	    	result = new JLabel("YOU LOST THE INSURANCE!");
	    } else {
	    	result = new JLabel("YOU WON " + winMoney + " €!");
	    }

	    result.setFont(new Font("Arial", Font.BOLD, 20));
	    result.setForeground(LIGHT_TEXT);
	    result.setAlignmentX(Component.CENTER_ALIGNMENT);

	    // setting the continue button, after pressed it will show the main outcome
	    JButton continueButton = createButton("CONTINUE");
	    continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    continueButton.addActionListener(
	            e -> {dialog.dispose();
	            onContinue.run();
	        });

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
