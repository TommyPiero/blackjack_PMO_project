package it.uniurb.blackjack.view;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

// class that contains the audio management methods
public class AudioManager {
	// declaration of class' fields
	private static Clip menuMusicClip; // music clip for the menu
	
	// method that starts the single reproduction of a sound
	public static void playSound(final String fileName) {
        try {
            URL soundUrl = AudioManager.class.getResource("/Resources.Sounds/" + fileName);
            if (soundUrl == null) {
                System.err.println("Sound not found: " + fileName);
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundUrl);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            System.err.println("Error on file reproduction: " + e.getMessage());
        }
    }
	
	// method that plays in loop the menu music
	public static void startMenuMusic(final String fileName) {
        try {
            stopMenuMusic();

            URL soundUrl = AudioManager.class.getResource("/Resources.Sounds/" + fileName);
            if (soundUrl == null) {
                System.err.println("Music not found: " + fileName);
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundUrl);
            menuMusicClip = AudioSystem.getClip();
            menuMusicClip.open(audioStream);
            menuMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            System.err.println("Error on music reproduction: " + e.getMessage());
        }
    }

	// method that stops the menu music
	public static void stopMenuMusic() {
		if (menuMusicClip != null && menuMusicClip.isRunning()) {
            menuMusicClip.stop();
            menuMusicClip.close();
        }
	}
}
