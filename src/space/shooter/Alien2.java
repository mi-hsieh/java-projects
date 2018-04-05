/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package space.shooter;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
//import javax.swing.Timer;

/**
 *
 * @author Michael
 */
public class Alien2 extends Alien
{
    private String name = "MS_UFO.png";
    private int health;
    private ArrayList alienMissiles;
    private boolean hit;
    private Image[] hitAnim;
    private int hitFrameNum;
    private int randomY;
    private final int BOARD_HEIGHT = 250;
    
    public Alien2(int x, int y)
    {
        super(x, y);
        setResource(name);
        alienMissiles = new ArrayList();
        health = 5;
        hit = false;
        hitFrameNum = 0;
        hitAnim = new Image[2];
        hitAnim[0] = new ImageIcon(this.getClass().getResource("MS_UFO.png")).getImage();
        hitAnim[1] = new ImageIcon(this.getClass().getResource("alien2hit.png")).getImage();
        randomY = 0;
    }
    
    // alien2 returns on a random y coordinate after disappearing on the left
    public void move()
    {
        if (getX() < 0)
        {
             setX(400);
             randomY = (int) (Math.random() * BOARD_HEIGHT) + 1;
             setY(randomY);
        }
        setX(getX() - 1);
    }
    
    public int getHitFrameNum()
    {
        return hitFrameNum;
    }
    
    public void setHitFrameNum(int hitFrameNum)
    {
        this.hitFrameNum = hitFrameNum;
    }
    
    public Image getHitAnim()
    {
        return hitAnim[hitFrameNum];
    }
    
    public ArrayList getAlienMissiles()
    {
        return alienMissiles;
    }
    
    public void fire()
    {
        alienMissiles.add( new AlienMissile(getX() - getWidth() - this.getWidth() + 15, getY() - getHeight() / 2 + 15) );
        Sound.ENEMYFIRE.play();
    }
    
    public int getHealth()
    {
        return health;
    }
    
    public void setHealth(int health)
    {
        this.health = health;
    }
    
    public boolean isHit()
    {
        return hit;
    }
    
    public void setHit(boolean hit)
    {
        this.hit = hit;
    }
    
}
