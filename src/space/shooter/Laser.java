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
public class Laser {
    
    private int x, y;
    private Image image;
    private boolean visible;
    private int width, height;
    
    private final int BOARD_WIDTH = 390;
    
    private String laser;
    
    public Laser(int x, int y)
    {
        laser = "laser2v2.jpg";
        ImageIcon ii = new ImageIcon(this.getClass().getResource(laser));
        image = ii.getImage();
        visible = true;
        width = image.getWidth(null);
        height = image.getHeight(null);
        this.x = x;
        this.y = y;
    }
    
    public void setResource(String laser)
    {
        this.laser = laser;
        ImageIcon ii = new ImageIcon(this.getClass().getResource(laser));
        image = ii.getImage();
        width = image.getWidth(null);
        height = image.getHeight(null);
    }
    
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
    
    public Rectangle getBounds()
    {
        return new Rectangle(x, y, width, height);
    }
}
