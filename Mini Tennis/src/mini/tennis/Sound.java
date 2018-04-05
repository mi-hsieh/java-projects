/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mini.tennis;

import java.applet.Applet;
import java.applet.AudioClip;
/**
 *
 * @author Michael
 */
public class Sound {
    public static final AudioClip BALL = Applet.newAudioClip(Sound.class.getResource("bounce.wav"));
    public static final AudioClip GAMEOVER = Applet.newAudioClip(Sound.class.getResource("gameover.wav"));
    public static final AudioClip BACK = Applet.newAudioClip(Sound.class.getResource("background.wav"));
}
