/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mini.tennis;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/**
 *
 * @author Michael
 * Note: in the original example I found online this class was named Game, not
 * MiniTennis
 */
@SuppressWarnings("serial")
public class MiniTennis extends JPanel {    // IS-A JPanel
    
    public static final int WINDOW_WIDTH = 300;
    public static final int WINDOW_HEIGHT = 400;
    
    
    
    // isolate everything that has to do with the ball
    Ball ball = new Ball(this);
    // create another sprite type called racquet
    Racquet racquet = new Racquet(this);
    // the speed property
    int speed = 1;
    
    private int getScore()
    {
        return speed - 1;   // since speed starts at 1 and we want the score to start at 0
    }
    
    public MiniTennis()
    {
        /*----------------------------------------------
         KeyListener listener = new MyKeyListener();
         addKeyListener(listener);
         setFocusable(true);
         -----------------------------------------------*/
        
        // the following does the exact same thing as the commented key
        // listener code, consisting of constructor code and
        // separate class, inside /*------------*/
        addKeyListener( new KeyListener()   // anonymous class
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                
            }
            
            @Override
            public void keyReleased(KeyEvent e)
            {
                racquet.keyReleased(e);
            }
            
            @Override
            public void keyPressed(KeyEvent e)
            {
                racquet.keyPressed(e);
            }
        });
        setFocusable(true);
        Sound.BACK.loop();
    }
    
    /*------------------------------------------------------------------
    public class MyKeyListener implements KeyListener
    {
        @Override
            public void keyTyped(KeyEvent e)
            {
                
            }
            
            @Override
            public void keyReleased(KeyEvent e)
            {
                racquet.keyReleased(e);
            }
            
            @Override
            public void keyPressed(KeyEvent e)
            {
                racquet.keyPressed(e);
            }
    }
    ------------------------------------------------------------------*/
    
    private void move()
    {
        ball.move();
        racquet.move();
        
        
        
    }
        
    @Override   // the subclass is overriding the superclass's method
    public void paint(Graphics g)
    {
        super.paint(g); // cleans up the screen, prevents trails
        Graphics2D g2d = (Graphics2D) g;    // cast the Graphics object into a Graphics2D one
        // the purpose of applying antialias is to smooth the borders of figures, i.e. circle
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
        ball.paint(g2d);
        racquet.paint(g2d);
        
        g2d.setColor(Color.GRAY);
        g2d.setFont(new Font("Verdana", Font.BOLD, 30));
        g2d.drawString(String.valueOf(getScore()), 10, 30);
       
    }
    
    public void gameOver()
    {
        Sound.BACK.stop();
        Sound.GAMEOVER.play();
        JOptionPane.showMessageDialog(this, "Your score is " + getScore(),
                "Game Over", JOptionPane.YES_NO_OPTION);
        System.exit(ABORT); // makes the program finish
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Mini Tennis");
        MiniTennis game = new MiniTennis();
        frame.add(game);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        // setting the racquet position is optional and can be deleted
        // game.racquet.x = MiniTennis.WINDOW_WIDTH / 2 - 40;
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        while (true)
        {
            game.move();
            game.repaint();
            // tells processor the thread being run must sleep for 10 milliseconds
            Thread.sleep(10);
        }
    }
}
