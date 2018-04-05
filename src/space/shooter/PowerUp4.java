/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package space.shooter;

/**
 *
 * @author Michael
 */
public class PowerUp4 extends PowerUp
{
    String powerup = "powerup_4.png";
    
    public PowerUp4(int x, int y)
    {
        super(x, y);
        setResource(powerup);
    }
}
