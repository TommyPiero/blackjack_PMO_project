package it.uniurb.blackjack.view.swingview;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.Dialog;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import it.uniurb.blackjack.model.cards.Card;
import it.uniurb.blackjack.model.cards.PerfectPairs;
import it.uniurb.blackjack.model.game.HandFields;
import it.uniurb.blackjack.model.game.HandOutcome;
import it.uniurb.blackjack.model.game.OutcomeType;

import java.awt.BorderLayout;

public class BlackjackSwingTableViewImpl extends JPanel implements BlackjackSwingTableView{
	// declaration of color constants
	private static final Color CASINO_GREEN = new Color(7, 94, 46);    // color for the menus background
    private static final Color DARK_GOLD    = new Color(212, 175, 55); // color for the borders
    private static final Color LIGHT_TEXT   = Color.WHITE;             // color for the labels
    
    // declaration of local fields
    private final JPanel      mainPanel;         // main panel of the table
    private final JPanel      dealerZone;        // zone for the dealer
    private final JPanel      playerZone;        // zone for the player
    private final JPanel      buttonsZone;       // zone for the buttons 
    private       JPanel      dealerCardSpace;   // panel for the dealer's cards space
    private       JLabel      dealerScore;       // label that will contain the dealer's score
    private       JPanel      playerHandsSpace;  // panel for the player's hands space
    private       JLabel      balanceLabel;      // label that will contain the player's balance
    private       JLabel      betLabel;		     // label that will contain the hand bets
    private       JPanel      infoPanel;         // panel for player info
    private       JLabel      settingsLabel;     // label that will contain informations about game settings
    private       JButton     hitButton;         // button for the hit move
    private       JButton     standButton;       // button for the stand move
    private       JButton     doubleButton;      // button for the double down move
    private       JButton     splitButton;       // button for the split move
    private       JPanel      buttonsPanel;      // panel for the move buttons
    private       Timer       countdownTimer;    // timer for the count down
    private       JLabel      timerLabel;        // label that will show the timer with the remaining time
	private       int         secondsRemaining;  // seconds of timer remaining
	private       String      currentSetType;    // current set type name
	private       Color       currentTableColor; // current table color
	private final CardManager cardManager;       // class manager for cards
	
    // class' constructor
    public BlackjackSwingTableViewImpl() {
    	// setting a default type for the set type and the table color
    	this.currentSetType = "PixelCards";
    	this.currentTableColor = new Color(7, 94, 46);
    	this.cardManager = new CardManager();
    	
    	// setting table layout
    	this.setLayout(new BorderLayout(15, 15));
        this.setBackground(this.currentTableColor);
        this.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // initializing the main panel with five spaces (NORTH, CENTER, SOUTH, WEST, EAST)
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        this.mainPanel = mainPanel;
        this.mainPanel.setBackground(this.currentTableColor);
        this.mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // initializing the zone for dealer's cards
        JPanel dealerZone = createDealerZone();
        this.dealerZone = dealerZone;
        mainPanel.add(this.dealerZone, BorderLayout.NORTH);
        
        // initializing the zone for player's cards
        JPanel playerZone = createPlayerZone();
        this.playerZone = playerZone;
        mainPanel.add(this.playerZone, BorderLayout.CENTER);
        
        // initializing the zone for buttons
        JPanel buttonsZone = createButtonZone();
        this.buttonsZone = buttonsZone;
        mainPanel.add(this.buttonsZone, BorderLayout.SOUTH);    
        
        // Add the table as bottom layer
        mainPanel.setAlignmentX(0.5f);
        mainPanel.setAlignmentY(0.5f);
        
        add(mainPanel);
    }
    
    // method that creates a zone for the player
 	private JPanel createPlayerZone() {
 		// declaration and initialization of local variables
 		JPanel zone = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // zone for the player
 		
        zone.setBackground(this.currentTableColor);
         
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
        zone.setBackground(this.currentTableColor);
         
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
        cardSpace.setBackground(this.currentTableColor);
         
        cardSpace.setPreferredSize(new Dimension(500, 140));
        cardSpace.setMinimumSize(new Dimension(500, 140));
        
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
        zone.setBackground(this.currentTableColor);
        
        // line with balance and bet
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 5));
        this.infoPanel = infoPanel;
        this.infoPanel.setBackground(this.currentTableColor);
        JLabel balance = new JLabel("Balance: -");
        this.balanceLabel = balance;
        balance.setForeground(LIGHT_TEXT);
        JLabel bet = new JLabel("Bet: -");
        this.betLabel = bet;
        bet.setForeground(LIGHT_TEXT);
        this.infoPanel.add(balance);
        this.infoPanel.add(bet);
        
        // line with button moves
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        this.buttonsPanel = buttonsPanel;
        this.buttonsPanel.setBackground(this.currentTableColor);
        
        this.hitButton = createButton("HIT");
        this.standButton = createButton("STAND");
        this.doubleButton = createButton("DOUBLE");
        this.splitButton = createButton("SPLIT");
        
        this.buttonsPanel.add(this.hitButton);
        this.buttonsPanel.add(this.standButton);
        this.buttonsPanel.add(this.doubleButton);
        this.buttonsPanel.add(this.splitButton);
        
        // setting the timer label
        this.timerLabel = new JLabel("Remaining time: -");
        this.timerLabel.setFont(new Font("Arial", Font.BOLD, 12));
		this.timerLabel.setForeground(DARK_GOLD);
		this.timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
        zone.add(this.infoPanel);
        zone.add(this.timerLabel);
        zone.add(this.buttonsPanel);
        
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

		handPanel.setBackground(this.currentTableColor);

		// setting border color
		Color borderColor = isActive ? Color.WHITE : DARK_GOLD;
		
		// setting border
		TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(borderColor, isActive ? 4 : 2, true),
															   (numHand == 0) ? " HAND " + (numHand + 1) : " HAND " + (numHand + 1));

		border.setTitleColor(DARK_GOLD);

		handPanel.setBorder( BorderFactory.createCompoundBorder(border, new EmptyBorder(10, 15, 10, 15)));
		
		// adding a component for each card
		for (Card card : cards) {
		    handPanel.add(this.cardManager.createCardComponent(card, this.currentTableColor, this.currentSetType));
		}

		// creating and setting a score label
		JLabel scoreLabel = new JLabel("Score: " + score);

		scoreLabel.setForeground(LIGHT_TEXT);

		handPanel.add(scoreLabel);

		return(handPanel);
	}
	
	// utility method used to set able or unable the game buttons
	private void setMoveButtonsEnabled(final boolean enabled) {
	    this.hitButton.setEnabled(enabled);
	    this.standButton.setEnabled(enabled);
	    this.doubleButton.setEnabled(enabled);
	    this.splitButton.setEnabled(enabled);
	}
	
	public void setCardSetType(final String cardSetType) {
	    this.currentSetType = cardSetType;
	}
	
	public void setTableColor(final Color tableColor) {
		this.currentTableColor = tableColor;
		
		// setting the color in all the panels
		this.setBackground(tableColor);
		this.mainPanel.setBackground(tableColor);
	    this.dealerZone.setBackground(tableColor);
	    this.playerZone.setBackground(tableColor);
	    this.buttonsZone.setBackground(tableColor);
	    this.dealerCardSpace.setBackground(tableColor);
	    this.playerHandsSpace.setBackground(tableColor);
	    this.infoPanel.setBackground(tableColor);
	    this.buttonsPanel.setBackground(tableColor);
	    this.revalidate();
	    this.repaint();
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
		        this.dealerCardSpace.add(this.cardManager.createCardComponent(card, this.currentTableColor, this.currentSetType));
		    }
	    } else {
	    	this.dealerCardSpace.add(this.cardManager.createFaceDownCard(this.currentTableColor, this.currentSetType));
		    this.dealerCardSpace.add(this.cardManager.createCardComponent(uncoveredCard, this.currentTableColor, this.currentSetType));
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
	
	public void revealCardsWithDelay(final JPanel cardPanel, final List<Card> cards, final int delayMillis, final Runnable onComplete) {
	    // declaration and initialization of local variables
		List<Card> cardsToShow = new ArrayList<>(cards); // list of cards to show
		
		// timer for revealing carsd
		Timer revealTimer = new Timer(delayMillis, null);
	    revealTimer.addActionListener(e -> {
	        if (!cardsToShow.isEmpty()) {
	            Card next = cardsToShow.remove(0);
	            cardPanel.add(this.cardManager.createCardComponent(next, this.currentTableColor, this.currentSetType));
	            cardPanel.revalidate();
	            cardPanel.repaint();
	            AudioManager.playSound("deal_card.wav");
	        }
	        
	        // if the cards to show are empty, stop
	        if (cardsToShow.isEmpty()) {
	            ((Timer) e.getSource()).stop();
	            if (onComplete != null) {
	                onComplete.run();
	            }
	        }
	    });
	    
	    revealTimer.start();
	}
	
	public void revealStartingCards(final List<Card> playerCards, final Card dealerUncovered, final Runnable onComplete) {
		// unable the buttons
		setMoveButtonsEnabled(false);
		
		// removing all cards from the previous round
		this.playerHandsSpace.removeAll();
	    this.dealerCardSpace.removeAll();
	    
	    this.playerHandsSpace.revalidate();
	    this.playerHandsSpace.repaint();
	    this.dealerCardSpace.revalidate();
	    this.dealerCardSpace.repaint();
	    
	    // revealing starting cards
	    revealCardsWithDelay(this.playerHandsSpace, playerCards, 1200, () -> {
	        revealCardsWithDelay(this.dealerCardSpace, List.of(dealerUncovered), 1200, () -> {
	        	int delay = 1200; 
	            Timer timerCoveredCard = new 
	            Timer(delay, e -> {
	            	this.dealerCardSpace.add(this.cardManager.createFaceDownCard(this.currentTableColor, this.currentSetType));            
	            	this.dealerCardSpace.revalidate();
	            	this.dealerCardSpace.repaint();
	            	AudioManager.playSound("deal_card.wav");
	            	// buttons can now be used again
	            	setMoveButtonsEnabled(true);
	            	
	            	if (onComplete != null) {
	            		onComplete.run();
	            	}
	            });
	            
	            timerCoveredCard.setRepeats(false);
	            timerCoveredCard.start();
	        });
	    });
	}
	
	public void revealSinglePlayerCard(final Card card, final Runnable onComplete) {
	    revealCardsWithDelay(this.playerHandsSpace, List.of(card), 400, onComplete);
	}
	
	public void revealSingleDealerCard(final Card card, final Runnable onComplete) {
	    revealCardsWithDelay(this.dealerCardSpace, List.of(card), 400, onComplete);
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
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

	    panel.setBackground(CASINO_GREEN);

	    panel.setBorder( BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(DARK_GOLD, 4),
	            											BorderFactory.createEmptyBorder(30, 40, 30, 40)));
	    
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
	    continueButton.addActionListener(e -> dialog.dispose());

	    panel.add(title);
	    panel.add(Box.createVerticalStrut(20));
	    panel.add(result);
	    panel.add(Box.createVerticalStrut(10));
	    panel.add(continueButton);
	    
	    dialog.setContentPane(panel);

	    dialog.setVisible(true);
	}
	
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
	
	public void stopMoveTimer() {
		if (this.countdownTimer != null &&
			this.countdownTimer.isRunning()) {
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

	    panel.setBackground(this.currentTableColor);

	    panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(DARK_GOLD, 4),
	            										   BorderFactory.createEmptyBorder(30, 40, 30, 40)));

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
	    continueButton.addActionListener(e -> {
	    	    dialog.dispose();
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

	    panel.setBackground(this.currentTableColor);

	    panel.setBorder(
	        BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(DARK_GOLD, 4), 
	        								   BorderFactory.createEmptyBorder(25, 40, 25, 40)));

	    // setting windows title, it asks for a new round
	    JLabel title = new JLabel("PLAY AGAIN?");

	    title.setFont(new Font("Arial", Font.BOLD, 28));

	    title.setForeground(DARK_GOLD);
	    title.setAlignmentX(Component.CENTER_ALIGNMENT);

	    // setting the buttons panel for yes or no
	    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

	    buttonsPanel.setBackground(this.currentTableColor);

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
	        stopMoveTimer();
	        setMoveButtonsEnabled(false);
	        this.timerLabel.setText("Ended game!");
	    });

	    buttonsPanel.add(yesButton);
	    buttonsPanel.add(noButton);

	    panel.add(title);

	    panel.add(Box.createVerticalStrut(35));

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

		panel.setBackground(this.currentTableColor);

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

		buttonsPanel.setBackground(this.currentTableColor);

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

	    panel.setBackground(this.currentTableColor);

	    panel.setBorder(
	        BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(DARK_GOLD, 4),
	        								   BorderFactory.createEmptyBorder(30, 40, 30, 40)));
	    
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
	    continueButton.addActionListener(e -> {
	    		dialog.dispose();
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

	public void showErrorMessage(final String string) {
		JOptionPane.showMessageDialog(this, string, "Errore", JOptionPane.ERROR_MESSAGE);
	}
}
