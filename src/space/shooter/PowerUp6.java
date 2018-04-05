/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package space.shooter;

/**
 *
 * @author Michael
 */
public class PowerUp6 extends PowerUp {
    String powerup = "powerup_6.png";
    
    public PowerUp6(int x, int y)
    {
        super(x, y);
        setResource(powerup);
    }
}
