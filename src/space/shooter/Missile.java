package space.shooter;

import java.awt.Image;

import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 *
 * @author Michael
 */
public class Missile {
    
    private int x, y;
    private Image image;
    private boolean visible;
    private int width, height;
    
    private final int BOARD_WIDTH = 390;
    private int MISSILE_SPEED = 2;
    
    private String missile;
    
    public Missile(int x, int y)
    {
        missile = "missile.png";
        ImageIcon ii = new ImageIcon(this.getClass().getResource("missile.png"));
        image = ii.getImage();
        visible = true;
        width = image.getWidth(null);
        height = image.getHeight(null);
        this.x = x;
        this.y = y;
    }
    
    /*********Used by subclasses************************************************/
    public void setResource(String missile)
    {
        this.missile = missile;
        ImageIcon ii = new ImageIcon(this.getClass().getResource(missile));
        image = ii.getImage();
        width = image.getWidth(null);
        height = image.getHeight(null);
    }
    
    public int getBoardWidth()
    {
        return BOARD_WIDTH;
    }
    
    public int getMissileSpeed()
    {
        return MISSILE_SPEED;
    }
    
    public void setMissileSpeed(int speed)
    {
        MISSILE_SPEED = speed;
    }
    
    public void setX(int x)
    {
        this.x = x;
    }
    
    public void setY(int y)
    {
        this.y = y;
    }
    /***************************************************************************/
    
    public Image getImage()
    {
        return image;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
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
    
    public Rectangle getBounds()
    {
        return new Rectangle(x, y, width, height);
    }
    
    public void move()
    {
        x += MISSILE_SPEED;
        if (x > BOARD_WIDTH)
        {
            visible = false;
        }
    }
}
