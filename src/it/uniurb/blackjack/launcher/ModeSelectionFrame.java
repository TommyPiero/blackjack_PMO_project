package it.uniurb.blackjack.launcher;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

// class that creates a dialog for the first choice of the game mode
public class ModeSelectionFrame extends JDialog {

	private static final Color CASINO_GREEN = new Color(7, 94, 46);    // color for the background
    private static final Color LIGHT_TEXT   = Color.WHITE;             // color for the label
	
    public ModeSelectionFrame(JFrame parentFrame) {
        super(parentFrame, "Blackjack", true); 

        // creating and initializing the pop up
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(400, 250);
        setResizable(false);
        setLocationRelativeTo(parentFrame);

        JLabel title = new JLabel("Choose Game Mode", JLabel.CENTER);
        title.setForeground(LIGHT_TEXT);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        
        // creating buttons and buttons zone
        JButton graphicalButton = new JButton("Graphic Mode");
        graphicalButton.setBackground(Color.WHITE);
        graphicalButton.setForeground(Color.BLACK);
        graphicalButton.setOpaque(true);
        graphicalButton.setBorderPainted(true);
        graphicalButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        JButton commandLineButton = new JButton("Text Mode");
        commandLineButton.setBackground(Color.WHITE);
        commandLineButton.setForeground(Color.BLACK);
        commandLineButton.setOpaque(true);
        commandLineButton.setBorderPainted(true);
        commandLineButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        // starting a GUI game session if the correct button is pushed
        graphicalButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                new AppBlackjackGUI().start();
            });
        });

        // starting a CLI game session if the correct button is pushed
        commandLineButton.addActionListener(e -> {
            dispose();
            new AppBlackjackCLI().start();
        });

        JPanel buttonsPanel = new JPanel(new GridLayout(2, 1, 0, 15)); 
        buttonsPanel.setBackground(CASINO_GREEN);

        buttonsPanel.add(graphicalButton);
        buttonsPanel.add(commandLineButton);

        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(CASINO_GREEN);
        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);

        add(mainPanel);
    }
}
