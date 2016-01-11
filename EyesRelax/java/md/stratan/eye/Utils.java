package md.stratan.eye;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * @author Mihai Stratan
 * @created Feb 16, 2011
 */
public class Utils {

	public static Rectangle getMaxVisible() {
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		Rectangle maximumWindowBounds = graphicsEnvironment
				.getMaximumWindowBounds();

		return maximumWindowBounds;

	}

	public static synchronized void playSound() {
	    new Thread(new Runnable() {
	      public void run() {
	    	  try {
		          Clip clip = AudioSystem.getClip();
		          AudioInputStream inputStream = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream("/beep-2.wav"));
		          
		          clip.open(inputStream);
		          clip.start(); 
		        } catch (Exception e) {
		          e.printStackTrace();
		        }
	      }
	    }).start();
	  }
}
