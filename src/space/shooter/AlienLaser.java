/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package space.shooter;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 *
 * @author Michael
 */
public class AlienLaser extends Laser {
    
    private Image[] cannonAnim;
    private int cannonFrameNum;
    
    public AlienLaser(int x, int y)
    {
        super(x, y);
        setResource("lasercannon0.gif");
        cannonAnim = new Image[12];
        cannonFrameNum = 0;
        for (int i = 0; i < 12; i++)
        {
            cannonAnim[i] = new ImageIcon(this.getClass().getResource("lasercannon" + Integer.toString(i) + ".gif")).getImage();
        }
    }
    
    public int getCannonFrameNum()
    {
        return cannonFrameNum;
    }
    
    public void setCannonFrameNum(int cannonFrameNum)
    {
        this.cannonFrameNum = cannonFrameNum;
    }
    
    public Image getCannonAnim()
    {
        return cannonAnim[cannonFrameNum];
    }
    
    @Override
    public Rectangle getBounds()
    {
        return new Rectangle(getX(), getY() + getHeight()/4, getWidth(), getHeight()/2);
    }
}
