/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package space.shooter;

import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author Michael
 */
// much of the methods in the code are copy-pasted from
// Alien2 so extending to Alien2 would make more sense
public class Boss extends Alien {
    
    private String name = "boss.gif";
    private int health;
    private ArrayList bossMissiles;
    private boolean hit;
    private Image[] hitAnim;
    private int hitFrameNum;
    private int pos;
    
    public Boss(int x, int y)
    {
        super(x, y);
        setResource(name);
        bossMissiles = new ArrayList();
        health = 1000;
        setVisible(false);
        hit = false;
        hitFrameNum = 0;
        hitAnim = new Image[2];
        hitAnim[0] = new ImageIcon(this.getClass().getResource("boss.gif")).getImage();
        hitAnim[1] = new ImageIcon(this.getClass().getResource("bosshit.gif")).getImage();
        pos = 1;
        
    }
    
    public void move()
    {
            if (getX() > 120)
            {
                setX(getX() - 1);
            }
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
    
    public ArrayList getAlienBossMissiles()
    {
        return bossMissiles;
    }
    
    public void fire()
    {
        
        pos = (int) (Math.random() * 2) + 1;
        if (pos == 1)
        {
        bossMissiles.add( new AlienMissile(getX() + getWidth()/6, getY() + getHeight()/5) );   //8
        bossMissiles.add( new AlienMissile(getX() + getWidth()/6, getY() + 195) );  //212
        }
        else
        {
            bossMissiles.add( new AlienMissile(getX() + getWidth()/6, getY() + getHeight()/12) );
            bossMissiles.add( new AlienMissile(getX() + getWidth()/6, getY() + 220) );
            
        }
        if (isVisible())
        Sound.BOSSLASER.play();
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
    
    public Ellipse2D getCircleBounds()
    {
        // boundary box options
        //return new Ellipse2D.Double(getX() + 40, getY(), getWidth(), getHeight());
        //return new Ellipse2D.Double(getX() + getWidth()/4 + 40, getY() + getHeight()/4, getWidth()/2, getHeight()/2);
        return new Ellipse2D.Double(getX() + getWidth()/3.5, getY() + getHeight()/8, getWidth()*0.75, getHeight()*0.75);
    }
    
    // technically this is used incorrectly b/c toString() is to provide a description of the
    // boss object itself, not circle bounds, but this was made for debug purposes only so whatever
    /*public String toString()
    {
        return "[x=" + getX() +", y=" + getY() + ", width=" + getWidth() + ", height=" + getHeight() + "]";
    }*/
    
}
