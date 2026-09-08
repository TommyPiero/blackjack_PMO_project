package it.uniurb.blackjack.launcher;

import javax.swing.SwingUtilities;

public class LauncherGeneralMain {
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ModeSelectionFrame frame = new ModeSelectionFrame(null);

            frame.setVisible(true);
        });
    }
}
