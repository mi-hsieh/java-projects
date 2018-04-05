/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package space.shooter;

/**
 *
 * @author Michael
 */
public class CosMissile extends Missile {
    private int x, y;
    
    public CosMissile(int x, int y)
    {
        super(x, y);
        setResource("cosmissile.png");
        this.x = getX();
        this.y = getY();
    }
    
    public void move()
    {
        setX(x);
        setY(y);
        x += getMissileSpeed();
        y -= 50 * Math.cos(x);
        if (x > getBoardWidth())
        {
            setVisible(false);
        }
    } 
    
}
