/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// Note that as of this version, 5/14/15, Explosion is useless
package space.shooter;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Michael
 */
public class Explosion {
    private int x, y;
    private Image[] explosionFrames = new Image[17];
    /*= {new ImageIcon("boom00.png").getImage(),
        new ImageIcon("boom01.png").getImage(), };*/
    private boolean visible;
    private int width, height;
    private int frameNum;
    
    public Explosion(int x, int y)
    {
        for (int i = 0; i < 17; i++)
        {
            explosionFrames[i] = new ImageIcon(this.getClass().getResource("boom" + Integer.toString(i) + ".png")).getImage();
        }
        
        visible = true;
        width = explosionFrames[frameNum].getWidth(null);
        height = explosionFrames[frameNum].getHeight(null);
        this.x = x;
        this.y = y;
    }
    
    public Image getImage()
    {
        return explosionFrames[frameNum];
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public boolean isVisible()
    {
        return visible;
    }
    
    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }
    
    public int getFrameNum()
    {
        return frameNum;
    }
    
    public void setFrameNum(int num)
    {
        frameNum = num;
    }
    
}
