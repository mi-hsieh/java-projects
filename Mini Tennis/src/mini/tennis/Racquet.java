/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mini.tennis;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
/**
 *
 * @author Michael
 */
public class Racquet {
    private static final int Y = 330;
    private static final int WIDTH = 60;
    private static final int HEIGHT = 10;
    
    
    // no y values because the racquet doesn't change vertical position
    int x = 0;  // indicates racquet will be in the left border of the canvas
    int xa = 0;
    private MiniTennis game;
    
    public Racquet(MiniTennis game)
    {
        this.game = game;
    }
    
    public void move()  // verifies the sprite doesn't go out of borders
    {
        if (x + xa > 0 && x + xa < game.getWidth() - WIDTH)
        {
            x = x + xa;
        }
    }
    
    public void paint(Graphics2D g)
    {
         g.fillRect(x, Y, WIDTH, HEIGHT);    // defines rectangle of 60 w x 10 h in position (x, 330)
    }
    
    public void keyReleased(KeyEvent e)
    {
        xa = 0;
    }
    
    
    // USER INPUT!
    // ARROW KEYS!
    // like the Ball class, game.speed replaces xa or xy which were set to -1 or 1
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            xa = -game.speed;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            xa = game.speed;
        }
    }
    
    public Rectangle getBounds()
    {
        return new Rectangle(x, Y, WIDTH, HEIGHT);
    }
    
    public int getTopY()
    {
        return Y;
    }
}
