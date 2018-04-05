/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mini.tennis;
import java.awt.Graphics2D;
import java.awt.Rectangle;
/**
 *
 * @author Michael
 */
public class Ball {
    int x = 0;
    int y = 0;
    int xa = 1;     // represents speed, can also think as acceleration
    int ya = 1;
    
    // the fillOval() method fills an oval bounded by the specified rectangle with the current color.
    // in this case both the rectangle height and width are equal to the circle's diameter,
    //and could be replaced by one statement, private static final int DIAMETER = 30;
    private static final int BALL_WIDTH = 30;
    private static final int BALL_HEIGHT = 30;
    
    private MiniTennis game;
    
    public Ball(MiniTennis game)
    {
        this.game = game;
    }
    
    public void move()
    {
        boolean changeDirection = true;
        // each if statement verifies the ball doesn't go out of the borders of the canvas
        if (x + xa < 0)
        {
            xa = game.speed; // ball moves to the right, initialized to 1
        }
        
        else if (x + xa > game.getWidth() - BALL_WIDTH)   // remember to change 30 in accordance to ball size
        {
               xa = -game.speed; // ball moves to the left, originally xa = -1     
        
        } 
        
        else if (y + ya < 0)
        {
            ya = game.speed; // moves ball down (due to funky coordinate axis), original 1
        }
        
        else if (y + ya > game.getHeight() - BALL_HEIGHT)
        {
            game.gameOver();
               
        
        } 
        
        else if ( collision() )
        {
             ya = -game.speed; // moves ball up, original value 1
             y = game.racquet.getTopY() - BALL_HEIGHT;
             game.speed++;
        }
        
        else
        {
            changeDirection = false;
        }
        
        if (changeDirection)
        {
            Sound.BALL.play();
        }
        
        x = x + xa;  // alternatively you can use x += xa, but this is more legible
        y = y + ya;
    }
    
    private boolean collision()
    {
        return game.racquet.getBounds().intersects(getBounds());
    }
    
    public void paint(Graphics2D g)
    {
         g.fillOval(x, y, BALL_WIDTH, BALL_HEIGHT);
    }
    
    public Rectangle getBounds()
    {
        return new Rectangle(x, y, BALL_WIDTH, BALL_HEIGHT);
    }
}
