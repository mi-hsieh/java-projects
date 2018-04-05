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
public class PowerUp {
    
    private String powerup = "powerup_1.png";
    
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean visible;
    private Image image;
    
    public PowerUp(int x, int y)
    {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(powerup));
        image = ii.getImage();
        width = image.getWidth(null);
        height = image.getHeight(null);
        visible = true;
        this.x = x;
        this.y = y;
    }
    
    // aliens return to the screen after thay have disappeared on the left
    public void move()
    {
        /*if (x < 0)
        {
            x = 400;
        }*/
        x -= 1;
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
    
    public Image getImage()
    {
        return image;
    }
    
    public Rectangle getBounds()
    {
        return new Rectangle(x, y, width, height);
    }
    
    public void setResource(String powerup)
    {
        this.powerup = powerup;
        ImageIcon ii = new ImageIcon(this.getClass().getResource(powerup));
        image = ii.getImage();
    }


}
