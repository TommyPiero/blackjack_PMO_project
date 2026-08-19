package it.uniurb.blackjack.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import it.uniurb.blackjack.model.game.OutcomeType;
import it.uniurb.blackjack.model.participants.Dealer;
import it.uniurb.blackjack.model.participants.Player;

public class BlackjackSwingMainView extends JPanel implements BlackjackView {
	
	// declaration of class' fields
	private JTextField nameField;       // field for the player's name
    private JTextField balanceField;    // field for the player's balance
    private JTextField numDecksField;   // field for the number of decks
    private JCheckBox  typeDealerField; // field for the dealer type
	private JButton    confirmButton;   // button for starting the game
    
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
        
        // Popoliamo il pannello centrale con Label e TextField
        formPanel.add(createStyledLabel("Nome Giocatore:"));
        formPanel.add(this.nameField);
        
        formPanel.add(createStyledLabel("Saldo Iniziale (€):"));
        formPanel.add(this.balanceField);
        
        formPanel.add(createStyledLabel("Numero Mazzi (2-8):"));
        formPanel.add(this.numDecksField);
        
        formPanel.add(createStyledLabel("Dealer Soft Hit (y/n):"));
        formPanel.add(this.typeDealerField);

        this.add(formPanel, BorderLayout.CENTER);

        // 5. CREAZIONE DEL PULSANTE DI CONFERMA (In basso - SOUTH)
        this.confirmButton = new JButton("ENTRA NEL TAVOLO DI GIOCO");
        this.confirmButton.setFont(new Font("Arial", Font.BOLD, 14));
        this.confirmButton.setBackground(DARK_GOLD);
        this.confirmButton.setForeground(new Color(30, 30, 30)); // Testo scuro sul bottone oro
        this.confirmButton.setFocusPainted(false); // Rimuove il fastidioso rettangolino attorno al testo al click
        this.confirmButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Manina al passaggio del mouse
        this.confirmButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 1),
            new EmptyBorder(12, 0, 12, 0) // Rende il bottone più alto e cliccabile
        ));
        
        // Pannello di posizionamento per non far allungare troppo il bottone a tutto schermo
        JPanel buttonWrapper = new JPanel(new BorderLayout());
        buttonWrapper.setBackground(CASINO_GREEN);
        buttonWrapper.setBorder(new EmptyBorder(15, 50, 0, 50)); // Stringe il bottone ai lati
        buttonWrapper.add(this.confirmButton, BorderLayout.CENTER);
        
        this.add(buttonWrapper, BorderLayout.SOUTH);
    }
    
	private Component createStyledLabel(String text) {
		JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        label.setForeground(LIGHT_TEXT);
        return label;
	}

	private JTextField createStyledTextField(int columns) {
		JTextField textField = new JTextField(columns);
        textField.setFont(new Font("Arial", Font.PLAIN, 13));
        textField.setBackground(new Color(245, 245, 245)); // Grigio chiarissimo quasi bianco
        textField.setForeground(Color.BLACK);
        // Aggiunge un po' di margine interno al testo per non farlo stare appiccicato ai bordi del box
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(DARK_GOLD, 1),
            new EmptyBorder(5, 5, 5, 5)
        ));
        return textField;
	}

	public String askName() {
		return(nameField.getText().trim());
	}

	public double askBalance() {
		return(Double.parseDouble(balanceField.getText().trim()));
	}

	@Override
	public double askChips(Player player) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double askSideBet() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int askNumDecks() {
		return(Integer.parseInt(this.numDecksField.getText().trim()));
	}

	public boolean askDealerType() {
		// declaration and initialization of local variables
		boolean isSoftDealer = false; // flag that record if the dealer is a soft dealer or not
		
		if ((this.typeDealerField.getText().trim()).equals("y"))
			isSoftDealer = true;
		
		return(isSoftDealer);
	}

	@Override
	public boolean askInsurance() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void showStartTable(Player player, Dealer dealer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showNextTable(Player player, Dealer dealer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showFinalTable(Player player, Dealer dealer) {
		// TODO Auto-generated method stub

	}

	@Override
	public String askMoves() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void showSideBet(Player player, double wonMoney) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showOutcome(double wonBet, OutcomeType outcome, Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean askForNewRound(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	public void showErrorMessage(String string) {
		JOptionPane.showMessageDialog(this, string, "Errore", JOptionPane.ERROR_MESSAGE);
	}

	public void setConfirmButtonListener(ActionListener listener) {
        this.confirmButton.addActionListener(listener);
    }
}
