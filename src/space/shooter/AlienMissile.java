/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package space.shooter;


/**
 *
 * @author Michael
 */
public class AlienMissile extends Missile
{
    private int x, y;
    public AlienMissile(int x, int y)
    {
        super(x, y);
        setResource("green_laser.png");
        this.x = getX();
        this.y = getY();
    }
    
    public void move()
    {
        setX(x);
        setY(y);
        x -= getMissileSpeed();
        if (x < 0)
        {
            setVisible(false);
        }
    }
    
}
