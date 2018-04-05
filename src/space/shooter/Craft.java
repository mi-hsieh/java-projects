package space.shooter;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Craft
{
    private String craft = "craft.gif";
    
    private int dx;
    private int dy;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean visible;
    private Image image;
    
    private ArrayList missiles;
    private ArrayList sinMissiles;
    private ArrayList cosMissiles;
    
    private boolean poweredUp1;
    private boolean animating;
    
    private boolean poweredUp2;
    private boolean poweredUp3;
    private boolean poweredUp4;
    private boolean poweredUp5;
    private boolean poweredUp6;
    
    public Craft()
    {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(craft));
        image = ii.getImage();
        width = image.getWidth(null);
        height = image.getHeight(null);
        // don't forget to initialize
        missiles = new ArrayList();
        sinMissiles = new ArrayList();
        cosMissiles = new ArrayList();
        visible = true;
        x = 40;
        y = 60;
    }
    
    public void move()
    {
        x += dx;
        y += dy;
        
        if (x < 1)
        {
            x = 1;
        }
        if ( y < 1)
        {
            y = 1;
        }
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public int getHeight()
    {
        return height;
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public Image getImage()
    {
        return image;
    }
    
    public ArrayList getMissiles()
    {
        return missiles;
    }
    
    public ArrayList getSinMissiles()
    {
        return sinMissiles;
    }
    
    public ArrayList getCosMissiles()
    {
        return cosMissiles;
    }
    
    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }
    
    public boolean isVisible()
    {
        return visible;
    }
    
    public boolean hasPowerUp1()
    {
        return poweredUp1;        
    }
    
    public void pUp1InEffect(boolean poweredUp1)
    {
        this.poweredUp1 = poweredUp1;
    }
    
    public boolean hasPowerUp2()
    {
        return poweredUp2;
    }
    
    public void pUp2InEffect(boolean poweredUp2)
    {
        this.poweredUp2 = poweredUp2;
    }
    
    public void pUp3InEffect(boolean poweredUp3)
    {
        this.poweredUp3 = poweredUp3;
    }
    
    public boolean hasPowerUp3()
    {
        return poweredUp3;
    }
    
    public void pUp4InEffect(boolean poweredUp4)
    {
        this.poweredUp4 = poweredUp4;
    }
    
    public boolean hasPowerUp4()
    {
        return poweredUp4;
    }
    
    public void pUp5InEffect(boolean poweredUp5)
    {
        this.poweredUp5 = poweredUp5;
    }
    
    public boolean hasPowerUp5()
    {
        return poweredUp5;
    }
    
    public void pUp6InEffect(boolean poweredUp6)
    {
        this.poweredUp6 = poweredUp6;
    }
    
    public boolean hasPowerUp6()
    {
        return poweredUp6;
    }
    
    public boolean isAnimating()
    {
        return animating;
    }
    
    public void setAnimating(boolean animating)
    {
        this.animating = animating;
    }
    
    // returns the bounds of the spacecraft image
    public Rectangle getBounds()
    {
        return new Rectangle(x, y, width, height);
    }
    
    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_SPACE)
        {
            fire();
            Sound.MISSILE.play();
            if (this.hasPowerUp1())
                Sound.SINMISSILE.play();
            if (hasPowerUp3())
                Sound.COSMISSILE.play();
            if (hasPowerUp4())
                Sound.MISSILE.play();
            //if (hasPowerUp5())
                //Sound.LASER.play();
        }
        
        if (key == KeyEvent.VK_LEFT)
        {
            dx = -1;
        }
        
        if (key == KeyEvent.VK_RIGHT)
        {
            dx = 1;
        }
        
        if (key == KeyEvent.VK_UP)
        {
            dy = -1;         
        }
        
        if (key == KeyEvent.VK_DOWN)
        {
            dy = 1;
        }
    
    }
    
    public void fire()
    {
        missiles.add(new Missile(x + width, y + height / 2));
        if (hasPowerUp1())
        {
            sinMissiles.add(new SinMissile(x + width, y + height / 2));
        }
        if (hasPowerUp3())
        {
            cosMissiles.add(new CosMissile(x + width, y + height / 2));
        }
        if (hasPowerUp4())
        {
            missiles.add(new Missile(x + width, y + height / 2 - 20));
        }
        
        if (hasPowerUp6())
        {
            ArrayList ms = getMissiles();
                    for (int i = 0; i < ms.size(); i++)
                    {
                        Missile m = (Missile) ms.get(i);
                        m.setMissileSpeed(m.getMissileSpeed() + 1);
                    }
                    
                    ArrayList sinms = getSinMissiles();
                    for (int i = 0; i < sinms.size(); i++)
                    {
                        SinMissile sinm = (SinMissile) sinms.get(i);
                        sinm.setMissileSpeed(sinm.getMissileSpeed() + 1);
                    }
        
                    ArrayList cosms = getCosMissiles();
                    for (int i = 0; i < cosms.size(); i++)
                    {
                        CosMissile cosm = (CosMissile) cosms.get(i);
                        cosm.setMissileSpeed(cosm.getMissileSpeed() + 1);
                    }
        }
    }
    
    public void keyReleased(KeyEvent e)
    {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_LEFT)
        {
            dx = 0;        
        }
        
        if (key == KeyEvent.VK_RIGHT)
        {
            dx = 0;
        }
        
        if (key == KeyEvent.VK_UP)
        {
            dy = 0;        
        }
        
        if (key == KeyEvent.VK_DOWN)
        {
            dy = 0;
        }
    }
        
}