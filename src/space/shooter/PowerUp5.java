/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package space.shooter;

/**
 *
 * @author Michael
 */
public class PowerUp5 extends PowerUp
{
    String powerup = "powerup_5.png";
    
    public PowerUp5(int x, int y)
    {
        super(x, y);
        setResource(powerup);
    }
}
