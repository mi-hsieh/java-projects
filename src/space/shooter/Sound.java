package space.shooter;

import java.applet.Applet;
import java.applet.AudioClip;

/**
 *
 * @author Michael
 */
public class Sound {
    public static final AudioClip MISSILE = Applet.newAudioClip(Sound.class.getResource("Space Gun 06.wav"));
    public static final AudioClip HIT = Applet.newAudioClip(Sound.class.getResource("Explosion 01.wav"));
    public static final AudioClip LOSE = Applet.newAudioClip(Sound.class.getResource("Arcade Action 04.wav"));
    public static final AudioClip POWERUP = Applet.newAudioClip(Sound.class.getResource("Arcade Power Up 01.wav"));
    public static final AudioClip SINMISSILE = Applet.newAudioClip(Sound.class.getResource("Space Gun 03.wav"));
    public static final AudioClip COSMISSILE = Applet.newAudioClip(Sound.class.getResource("Space Gun 12.wav"));
    public static final AudioClip LASER = Applet.newAudioClip(Sound.class.getResource("UFO Sweep 04.wav"));
    public static final AudioClip ENEMYFIRE = Applet.newAudioClip(Sound.class.getResource("Laser Gun.wav"));
    public static final AudioClip BOOM = Applet.newAudioClip(Sound.class.getResource("Explosion 04.wav"));
    public static final AudioClip CANNON = Applet.newAudioClip(Sound.class.getResource("Alien Woosh 01.wav"));
    public static final AudioClip BOSSLASER = Applet.newAudioClip(Sound.class.getResource("Space Gun 10.wav"));
    
}
