package it.uniurb.blackjack.model.game;

// class that implements the interface Configuration
public class ConfigurationImpl implements Configuration {
	
	// declaration of class' fields
	private final int     numDecks;      // number of decks in the shoe
	private final boolean dealerHitSoft; // boolean for the dealer, true: hit on a soft 17, false: stand on a soft 17
	
	// class' constructor
	public ConfigurationImpl(final int numDecks, final boolean dealerHitSoft) {
		this.numDecks = numDecks;
		this.dealerHitSoft = dealerHitSoft;
	}

	public int getNumDecks() {
		return(this.numDecks);
	}

	public boolean isDealerHitSoft() {
		return(this.dealerHitSoft);
	}
	
	
}
