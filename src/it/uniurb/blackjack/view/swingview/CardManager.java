package it.uniurb.blackjack.view.swingview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.uniurb.blackjack.model.cards.Card;

// class that contains methods that manage the creation of cards
public class CardManager {
	
	// method that creates a card with value and suit
	public JPanel createCardComponent(final Card card, final Color tableColor, final String setType) {
		// declaration and initialization of local variables
		String fileName = "card_" + card.getSuit().toString().toLowerCase() + "_" + card.getNominalValue() + ".png"; // name of card's image file
		
		ImageIcon icon = loadCardImage(fileName, setType);
		
		JPanel cardPanel = new JPanel(new BorderLayout()); // panel for a card
	    cardPanel.setPreferredSize(new Dimension(64, 92));
	    cardPanel.setBackground(tableColor);
	    cardPanel.setBorder(BorderFactory.createLineBorder(tableColor));
	    
	    // creating card label with proportional dimensions
	    JLabel cardLabel = new JLabel(icon);
	    cardLabel.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
	    
	    // adding card to the panel
	    cardPanel.add(cardLabel);
	    
	    return(cardPanel);
	}
	
	// method that create a face down card
	public JPanel createFaceDownCard(final Color tableColor, final String setType) {
		// declaration and initialization of local variables
		ImageIcon icon = loadCardImage("card_back.png", setType); // name of file for face down card
	    JPanel cardPanel = new JPanel();            	          // panel for a card
	    
	    cardPanel.setPreferredSize(new Dimension(66, 94));
	    cardPanel.setBackground(tableColor);
	    cardPanel.setBorder(BorderFactory.createLineBorder(tableColor));
	    
	    // creating card label with proportional dimensions
	    JLabel cardLabel = new JLabel(icon);
	    cardLabel.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));

	    cardPanel.add(cardLabel);
	    
	    return cardPanel;
	}
	
	// method that loads the image of cards
	private ImageIcon loadCardImage(final String fileName, final String setType) {
		// initializing the image URL
		URL imageUrl = getClass().getResource("/Resources." + setType + "/" + fileName);
		
		// throwing an exception if the URL is null
		if (imageUrl == null) {
	        throw new IllegalStateException("Card image not found: " + fileName);
	    }
		
		ImageIcon original = new ImageIcon(imageUrl);
		
		int originalWidth = original.getIconWidth();
	    int originalHeight = original.getIconHeight();
	    
	    // resizing proportionally the images, if pixel cards change the width value
	    int targetWidth = setType.equals("PixelCards") ? 90 : 64;;
	    int targetHeight = (int) (targetWidth * ((double) originalHeight / originalWidth));
	   
	    Image scaled;
	    if (setType.equals("PixelCards")) {
	    	BufferedImage buffered = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
	        Graphics2D g = buffered.createGraphics();
	        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
	        g.drawImage(original.getImage(), 0, 0, targetWidth, targetHeight, null);
	        g.dispose();
	        scaled = buffered;
	    } else {
	    	scaled = original.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
	    }
	    
	    return new ImageIcon(scaled);
	}
}
