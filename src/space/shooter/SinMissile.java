package space.shooter;

/**
 *
 * @author Michael
 */
public class SinMissile extends Missile
{
    private int x, y;
    
    public SinMissile(int x, int y)
    {
        super(x, y);
        setResource("sinmissile.gif");
        this.x = getX();
        this.y = getY();
    }
    
    public void move()
    {
        setX(x);
        setY(y);
        x += getMissileSpeed();
        y += 50 * Math.sin(x);
        if (x > getBoardWidth())
        {
            setVisible(false);
        }
    } 
}
