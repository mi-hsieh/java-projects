/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package space.shooter;

/**
 *
 * @author Michael
 */
public class PowerUp3 extends PowerUp
{
    String powerup = "powerup_3.png";
    
    public PowerUp3(int x, int y)
    {
        super(x, y);
        setResource(powerup);
    }
}
