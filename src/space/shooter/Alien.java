package space.shooter;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 *
 * @author Michael
 */
public class Alien {
    
    private String craft = "alien.png";
    
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean visible;
    private Image image;
    
    public Alien(int x, int y)
    {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(craft));
        image = ii.getImage();
        width = image.getWidth(null);
        height = image.getHeight(null);
        visible = true;
        this.x = x;
        this.y = y;
    }
    
    public void setResource(String craft)
    {
        this.craft = craft;
        ImageIcon ii = new ImageIcon(this.getClass().getResource(craft));
        image = ii.getImage();
        width = image.getWidth(null);
        height = image.getHeight(null);
    }
    
    // aliens return to the screen after thay have disappeared on the left
    public void move()
    {
        if (x < 0)
        {
            x = 400;
        }
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
    
    public void setX(int x)
    {
        this.x = x;
    }
    
    public void setY(int y)
    {
        this.y = y;
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public int getHeight()
    {
        return height;
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
    
    
}
