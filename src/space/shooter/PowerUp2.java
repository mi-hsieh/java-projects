/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package space.shooter;

/**
 *
 * @author Michael
 */
public class PowerUp2 extends PowerUp
{
    String powerup = "powerup_2.png";
    
    public PowerUp2(int x, int y)
    {
        super(x, y);
        setResource(powerup);
    }
}
