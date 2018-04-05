package space.shooter;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Image;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.geom.Ellipse2D;

import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;   // note this is the Swing timer, not the other one

/**
 * This project was adapted from: http://zetcode.com/tutorials/javagamestutorial/
 * 
 * Yes, I know many of the coordinates are arbitrary and should use percentages to scale
 * proportionally, and much of the code is repeated so it would be more sensible to use
 * private methods, but give me a break - it's my first game.
 * 
 * @author Michael
 */
public class Board extends JPanel implements ActionListener {
    
    private Timer timer;
    private Timer secondsAnim;  // ship hit animation
    private Timer powerUpTimer; // power-up animation
    private Timer powerUpDuration;  // how long power-up lasts
    private Timer alienHitTimer;
    private int alienID;
    private final int RANDOMNESS_FACTOR;
    private Craft craft;
    private ArrayList aliens;
    // Note: not using ArrayList identifiers results in "uses unchecked or unsafe operations.
    // Recompile with -Xlint:unchecked for details." error
    // can use @SuppressWarnings("unchecked"); above the method definition.
    private ArrayList aliens2;
    private Boss boss;
    private boolean ingame;
    private int B_WIDTH;
    private int B_HEIGHT;
    private Image[] explosions = new Image[17];
    private Image[] bigExplosion = new Image[15];
    private boolean explosionVisible = false;
    private int frameNum = 0;
    private int bigEFrameNum = 0;
    private boolean bigEVisible, bigEStarted, bigEEnded = false;
    private int killSpotX, killSpotY;
    private boolean victory;
    private int score = 0;
    private int lives;
    private Image livesImage;
    private Image[] playerHitAnim = new Image[2];
    private Image[] poweredUpAnim1 = new Image[2];
    private int damageFrameNum;
    private boolean playerHit;
    private int milliSecondsPassed;
    private int alienMilliSeconds; // used as counter when aliens are hit
    private ArrayList powerups;
    private ArrayList powerups2;
    private ArrayList powerups3;
    private ArrayList powerups4;
    private ArrayList powerups5;
    private ArrayList powerups6;
    private Laser laser;
    private AlienLaser cannon;
    private int laserDistX, laserDistY;
    private int cannonDistX, cannonDistY;
    private boolean animateCannon;
    private int powerUpFrameNum;
    private String powerUpType;
    private int level;
    
    private Timer laserDelay;
    private Timer cannonDelay;
    private Timer chargeUp; // to give the player a warning before the laser fires
    private int counter;    // used to count the duration of the boss laser cannon animation
    private int randomSec;
    private int randomMilliSec;
    
    // initial positions of alien ships
    private int[][] pos = { {2380, 29}, {2500, 59}, {1380, 89},
                            {780, 109}, {580, 139}, {680, 239}, 
                            /*{790, 259},*/ {760, 50}, {790, 150},
                            {980, 209}, {560, 45}, {510, 70},
                            {930, 159}, {590, 80}, {530, 60},
                            {940, 59}, {990, 30}, {920, 200},
                            /*{900, 259},*/ {660, 50}, {540, 90},
                            {810, 220}, {860, 20}, {740, 180},
                            {820, 128}, {490, 170}, {700, 30}
                         };
    
    private int[][] pos2 = { {560, 45}, {510, 70}, {760, 50}, {790, 150}, {680, 239}
                            
                           };
    
    public Board()
    {
        addKeyListener( new TAdapter() );
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        
        ingame = true;
        
        setSize(400, 300);
        
        craft = new Craft();
        
        laserDistX = 45;
        laserDistY = 3;
        laser = new Laser(craft.getX(), craft.getY());
        laser.setVisible(false);
        
        
        initAliens();
        initAliens2();
        boss = new Boss(400, 5);
        randomMilliSec = (int) (Math.random() * 3) + 1;
        
        //randomSec = (int) (Math.random() * 3) + 1;
        counter = 0;
        // also tried new Timer( ( (int) (Math.random() * 3) + 1 ) * 5000, this ) and new Timer( ( (int) (Math.random() * 3) + 1 ) * 3000, this )
        cannonDelay = new Timer( ( (int) (Math.random() * 2) + 1 ) * 5000, this );
        chargeUp = new Timer(3000, this);
        cannonDistX = 25;
        cannonDistY = 10;
        animateCannon = false;
        cannon = new AlienLaser(boss.getX(), boss.getY());
        cannon.setVisible(false);
        
        RANDOMNESS_FACTOR = 20;
        
        for (int i = 0; i < 17; i++)
        {
            explosions[i] = new ImageIcon(this.getClass().getResource("boom" + Integer.toString(i) + ".png")).getImage();
        }
        
        for (int i = 0; i < 15; i++)
        {
            bigExplosion[i] = new ImageIcon(this.getClass().getResource("bigboom" + Integer.toString(i) + ".gif")).getImage();
        }
        
        ImageIcon livesii = new ImageIcon(this.getClass().getResource("smallcraft.gif"));
        livesImage = livesii.getImage();
        
        playerHitAnim[0] = new ImageIcon(this.getClass().getResource("craft.gif")).getImage();
        playerHitAnim[1] = new ImageIcon(this.getClass().getResource("crafthit.gif")).getImage();
        
        poweredUpAnim1[0] = new ImageIcon(this.getClass().getResource("craft.gif")).getImage();
        poweredUpAnim1[1] = new ImageIcon(this.getClass().getResource("craftUp1.gif")).getImage();
        
        timer = new Timer(5, this);
        secondsAnim = new Timer(50, this);
        powerUpTimer = new Timer(50, this);
        
        alienHitTimer = new Timer(50, this);
        alienID = -1;
        
        int second = 1000;
        powerUpDuration = new Timer(10 * second, this);
        timer.start();
        lives = 3;
        
        powerups = new ArrayList();
        powerups2 = new ArrayList();
        powerups3 = new ArrayList();
        powerups4 = new ArrayList();
        powerups5 = new ArrayList();
        powerups6 = new ArrayList();
        
        powerUpType = "";
        
        level = 1;
    }
    
    public void addNotify()
    {
        super.addNotify();
        B_WIDTH = getWidth();
        B_HEIGHT = getHeight();
    }
    
    public void initAliens()
    {
        aliens = new ArrayList();
        
        for (int i = 0; i < pos.length; i++)
        {
            aliens.add( new Alien(pos[i][0], pos[i][1]) );
        }
    }
    
    public void initAliens2()
    {
        aliens2 = new ArrayList();
        
        for (int i = 0; i < pos2.length; i++)
        {
            aliens2.add( new Alien2(pos2[i][0], pos2[i][1]) );
            randomSec = (int) (Math.random() * 3) + 1;
            laserDelay = new Timer(randomSec * 2000, this); //technically milliseconds, 2 seconds
        }
        
    }
    
    public void paint(Graphics g)
    {
      super.paint(g);
        
      if (ingame)
      {
        Graphics2D g2d = (Graphics2D)g;
        
          int currentLifeX = B_WIDTH - 72;
          if (lives <= 3)
          {
            for (int i = 0; i < lives; i++)
            {
                g2d.drawImage(livesImage, currentLifeX, 8, this);
                currentLifeX += livesImage.getWidth(null);
                
            }
          }
          else
          {
              for (int i = 0; i < 3; i++)
              {
                g2d.drawImage(livesImage, currentLifeX, 8, this);
                currentLifeX += livesImage.getWidth(null);
                
              }
          }
        
        if (craft.isVisible())
        {
            g2d.drawImage(craft.getImage(), craft.getX(), craft.getY(), this);
        }
        
        if (playerHit)
        {
            g2d.drawImage(playerHitAnim[damageFrameNum], craft.getX(), craft.getY(), this);
        }
        
        if (craft.isAnimating())
        {
            g2d.drawImage(poweredUpAnim1[powerUpFrameNum], craft.getX(), craft.getY(), this);
        }
        
        ArrayList ms = craft.getMissiles();
        
        // draw all missiles from the array list
        for (int i = 0; i < ms.size(); i++)
        {
            Missile m = (Missile) ms.get(i);
            g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
        }
        
        ArrayList sinms = craft.getSinMissiles();
        if (craft.hasPowerUp1())
        {
             for (int i = 0; i < sinms.size(); i++)
             {
                SinMissile sinm = (SinMissile) sinms.get(i);
                g2d.drawImage(sinm.getImage(), sinm.getX(), sinm.getY(), this);
             }
        }
        
        ArrayList cosms = craft.getCosMissiles();
        if (craft.hasPowerUp3())
        {
            for (int i = 0; i < cosms.size(); i++)
            {
                CosMissile cosm = (CosMissile) cosms.get(i);
                g2d.drawImage(cosm.getImage(), cosm.getX(), cosm.getY(), this);
            }
        }
        
        if (craft.hasPowerUp5() && laser.isVisible())
        {
            g2d.drawImage(laser.getImage(), craft.getX() + laserDistX, craft.getY() + laserDistY, this);
        }
        
        if (cannon.isVisible())
        {
            g2d.drawImage(cannon.getCannonAnim(), boss.getX() - boss.getWidth() + cannonDistX, boss.getY() + cannon.getHeight()/2 - cannonDistY, this);
        }
        
        for (int i = 0; i < aliens.size(); i++)
        {
            Alien a = (Alien) aliens.get(i);
            
            // draw aliens only if they haven't been previously destroyed
            if (a.isVisible())
            {
                g2d.drawImage(a.getImage(), a.getX(), a.getY(), this);
            }
               
            
        }
        
        for (int i = 0; i < aliens2.size(); i++)
        {
            Alien2 a2 = (Alien2) aliens2.get(i);
            if (a2.isVisible())
            {
                g2d.drawImage(a2.getImage(), a2.getX(), a2.getY(), this);
            }
            if (a2.isHit() && alienID == i)
            {
                g2d.drawImage(a2.getHitAnim(), a2.getX(), a2.getY(), this);
            }
            
            
            ArrayList alienms = a2.getAlienMissiles();
            
            for (int j = 0; j < alienms.size(); j++)
            {
                AlienMissile alienm = (AlienMissile) alienms.get(j);
                g2d.drawImage(alienm.getImage(), alienm.getX(), alienm.getY(), this);
            }
            
        }
        
        if (boss.isVisible())
        {
            g2d.drawImage(boss.getImage(), boss.getX(), boss.getY(), this);
            //test code
            /*g2d.draw(boss.getBounds());
            g2d.draw(boss.getCircleBounds());*/
        }
        
        if (boss.isHit() && boss.isVisible())
        {
            g2d.drawImage(boss.getHitAnim(), boss.getX(), boss.getY(), this);
        }
        
        ArrayList bossms = boss.getAlienBossMissiles();
            
        for (int j = 0; j < bossms.size(); j++)
        {
            AlienMissile bossm = (AlienMissile) bossms.get(j);
            bossm.setResource("red_laser.png");
            g2d.drawImage(bossm.getImage(), bossm.getX(), bossm.getY(), this);
        }
        
        if (powerups != null)
        {
            for (int i = 0; i < powerups.size(); i++)
            {
                PowerUp pUp = (PowerUp) powerups.get(i);
                if (pUp.isVisible())
                {
                    g2d.drawImage(pUp.getImage(), pUp.getX(), pUp.getY(), this);
                    //pUp.move();
                }
                /*if (pUp.getX() < 0)
                {
                    pUp.setVisible(false);
                    powerups.remove(i);
                }*/
                
            }
        }
        
        if (powerups2 != null)
        {
            for (int i = 0; i < powerups2.size(); i++)
            {
                PowerUp2 pUp2 = (PowerUp2) powerups2.get(i);
                if (pUp2.isVisible())
                {
                    g2d.drawImage(pUp2.getImage(), pUp2.getX(), pUp2.getY(), this);
                }
                
            }
        }
        
        if (powerups3 != null)
        {
            for (int i = 0; i < powerups3.size(); i++)
            {
                PowerUp3 pUp3 = (PowerUp3) powerups3.get(i);
                if (pUp3.isVisible())
                {
                    g2d.drawImage(pUp3.getImage(), pUp3.getX(), pUp3.getY(), this);
                }
                
            }
        }
        
        if (powerups4 != null)
        {
            for (int i = 0; i < powerups4.size(); i++)
            {
                PowerUp4 pUp4 = (PowerUp4) powerups4.get(i);
                if (pUp4.isVisible())
                {
                    g2d.drawImage(pUp4.getImage(), pUp4.getX(), pUp4.getY(), this);
                }
                
            }
        }
        
         if (powerups5 != null)
        {
            for (int i = 0; i < powerups5.size(); i++)
            {
                PowerUp5 pUp5 = (PowerUp5) powerups5.get(i);
                if (pUp5.isVisible())
                {
                    g2d.drawImage(pUp5.getImage(), pUp5.getX(), pUp5.getY(), this);
                }
                
            }
        }
         
        if (powerups6 != null)
        {
            for (int i = 0; i < powerups6.size(); i++)
            {
                PowerUp6 pUp6 = (PowerUp6) powerups6.get(i);
                if (pUp6.isVisible())
                {
                    g2d.drawImage(pUp6.getImage(), pUp6.getX(), pUp6.getY(), this);
                }
                
            }
        }
        
        if (explosionVisible)
        {
            g2d.drawImage(explosions[frameNum], killSpotX - explosions[frameNum].getWidth(null) / 2,
                 killSpotY - explosions[frameNum].getHeight(null) / 2, this);
        }
        
        if (bigEVisible)
        {
            g2d.drawImage(bigExplosion[bigEFrameNum], boss.getX() - /*bigExplosion[bigEFrameNum].getWidth(null) -*/ boss.getWidth()/4 +10,
                 boss.getY() - boss.getHeight()/4 + 27/*- bigExplosion[bigEFrameNum].getHeight(null) / 4*/, this);
            /*System.out.println("boss's x: " + boss.getX() + ", boss's y: " + boss.getY());
            System.out.println("b's width: " + boss.getWidth() + ", b's height: " + boss.getHeight());
            System.out.println("e's width: " + bigExplosion[bigEFrameNum].getWidth(null) +", e's height: " + bigExplosion[bigEFrameNum].getHeight(null));*/
            try {
                  Thread.sleep(200);
            } catch (InterruptedException ex) {
                        Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        g2d.setColor(Color.WHITE);
        if (level == 1)
        {
        g2d.drawString("aliens left: " + aliens.size(), 5, 15);
        }
        else if (level == 2)
        {
            g2d.drawString("aliens left: " + aliens2.size(), 5, 15);
        }
        else if (level == 3)
        {
            g2d.drawString("boss health: " + boss.getHealth(), 5, 15);
            //g2d.draw(cannon.getBounds());
            if (boss.getHealth() > 0)
            {
                boss.setVisible(true);
            }
        }
        g2d.drawString("score: " + score, 5, 30);
        g2d.drawString("lives: ", B_WIDTH - 100, 15);
        if (lives > 3)
        {
            g2d.drawString("+ " + (lives - 3), B_WIDTH - 100, 25);
        }
        
        // expected 3
        g2d.drawString("level: " + level + " / 3", (B_WIDTH / 2) - 50, 15); // planned 5
        
        
        if (craft.hasPowerUp1() || craft.hasPowerUp2() || craft.hasPowerUp3() || craft.hasPowerUp4()
                || craft.hasPowerUp5() || craft.hasPowerUp6())
        {
            g2d.drawString("Power-up: " + powerUpType, (B_WIDTH / 2 - 75), 30);
        }
        
      }
      else
      {
          String msg = "Game Over";
          String scoreDisp = "Score: " + score;
          if (victory)
              msg = "You Win";
          Font small = new Font("Helvetica", Font.BOLD, 14);
          FontMetrics metr = this.getFontMetrics(small);
          
          g.setColor(Color.white);
          g.setFont(small);
          g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
          g.drawString(scoreDisp, (B_WIDTH - metr.stringWidth(scoreDisp)) / 2, B_HEIGHT / 2 + 50);
      }
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
        if (!ingame)
        {
           Sound.LOSE.stop();
           Sound.BOOM.stop();
           Sound.CANNON.stop();
        
        }
        
    }
    
    // called every 5 milliseconds
    public void actionPerformed(ActionEvent e)
    {
        // if we destroy all alien ships, the game is finished
        if (aliens.isEmpty() && ingame)
        {
            level = 2;
            laserDelay.start();
            //ingame = false;
            //victory = true;
        }
        
        if (aliens2.isEmpty() && ingame)
        {
            level = 3;
            //laserDelay.stop();
            laserDelay.setDelay(randomMilliSec*1000);
            if (boss.isVisible() && boss.getX() <= 120)
            cannonDelay.start();
            //ingame = false;
            //victory = true;
        }
        
        if (boss.getHealth() <= 0)
        {
            laserDelay.stop();
            cannonDelay.stop();
            chargeUp.stop();
            Sound.CANNON.stop();
            bigEStarted = true;
            bigEVisible = true;
            boss.setVisible(false);
            if (bigEStarted && bigEEnded)
            {
                ingame = false;
                victory = true;
            }
        }
        
        
        // parse all missiles from the ArrayList; move the missile or
        // remove it from the container depending on whether it's visible
        ArrayList ms = craft.getMissiles();
        
        for (int i = 0; i < ms.size(); i++)
        {
            Missile m = (Missile) ms.get(i);
            if (m.isVisible())
            {
                m.move();
            }
            else
            {
                ms.remove(i);
            }
        }
        
        ArrayList sinms = craft.getSinMissiles();
        
        for (int i = 0; i < sinms.size(); i++)
        {
            SinMissile sinm = (SinMissile) sinms.get(i);
            if (sinm.isVisible())
            {
                sinm.move();
            }
            else
            {
                sinms.remove(i);
            }
        }
        
        ArrayList cosms = craft.getCosMissiles();
        
        for (int i = 0; i < cosms.size(); i++)
        {
            CosMissile cosm = (CosMissile) cosms.get(i);
            if (cosm.isVisible())
            {
                cosm.move();
            }
            else
            {
                cosms.remove(i);
            }
        }
        
        for (int i = 0; i < aliens.size(); i++)
        {
            Alien a = (Alien) aliens.get(i);
            if (a.isVisible())
            {
                a.move();
            }
            else
            {
                aliens.remove(i);
                if (ingame && !playerHit)
                {
                    score += 5;
                }
            }
        }
        
        for (int i = 0; i < aliens2.size(); i++)
        {
            Alien2 a2 = (Alien2) aliens2.get(i);
            if (level == 2 && ingame)
            {
              if (a2.isVisible())
              {
                a2.move();
                if (e.getSource() == laserDelay)
                {
                    a2.fire();
                
                }
              }
              else
              {
                   aliens2.remove(i);
                   if (ingame && !playerHit)
                   {
                       score += 10;
                   }
              }
                ArrayList alienms = a2.getAlienMissiles();
                for (int j = 0; j < alienms.size(); j++)
                {
                    AlienMissile alienm = (AlienMissile) alienms.get(j);
                    if (alienm.isVisible())
                    {
                        alienm.move();
                    }
                    else
                    {
                        alienms.remove(j);
                    }
                }
            }
        }
        
        if (level == 3 && ingame)
        {
            if (boss.isVisible())
            {
                boss.move();
                if (e.getSource() == laserDelay && boss.getX() <= 120)
                {
                    boss.fire();
                }
            }
            else
            {
                /*if (ingame && !playerHit)
                {
                    score += 100;
                }*/
            }
            
                ArrayList bossms = boss.getAlienBossMissiles();
                for (int j = 0; j < bossms.size(); j++)
                {
                    AlienMissile bossm = (AlienMissile) bossms.get(j);
                    if (bossm.isVisible())
                    {
                        bossm.move();
                        //System.out.println(bossm.getBounds());
                    }
                    else
                    {
                        bossms.remove(j);
                    }
                }
        }
        
        if (powerups != null && ingame)
        {
            for (int i = 0; i < powerups.size(); i++)
            {
                PowerUp pUp = (PowerUp) powerups.get(i);
                if (pUp.isVisible() && pUp.getX() >= 0)
                {
                    pUp.move();
                }
                else
                {
                    powerups.remove(i);
                }
            }
        }
        
        if (powerups2 != null && ingame)
        {
            for (int i = 0; i < powerups2.size(); i++)
            {
                PowerUp2 pUp2 = (PowerUp2) powerups2.get(i);
                if (pUp2.isVisible() && pUp2.getX() >= 0)
                {
                    pUp2.move();
                }
                else
                {
                    powerups2.remove(i);
                }
            }
        }
        
         if (powerups3 != null && ingame)
        {
            for (int i = 0; i < powerups3.size(); i++)
            {
                PowerUp3 pUp3 = (PowerUp3) powerups3.get(i);
                if (pUp3.isVisible() && pUp3.getX() >= 0)
                {
                    pUp3.move();
                }
                else
                {
                    powerups3.remove(i);
                }
            }
        }
         
        if (powerups4 != null && ingame)
        {
            for (int i = 0; i < powerups4.size(); i++)
            {
                PowerUp4 pUp4 = (PowerUp4) powerups4.get(i);
                if (pUp4.isVisible() && pUp4.getX() >= 0)
                {
                    pUp4.move();
                }
                else
                {
                    powerups4.remove(i);
                }
            }
        }
        
        if (powerups5 != null && ingame)
        {
            for (int i = 0; i < powerups5.size(); i++)
            {
                PowerUp5 pUp5 = (PowerUp5) powerups5.get(i);
                if (pUp5.isVisible() && pUp5.getX() >= 0)
                {
                    pUp5.move();
                }
                else
                {
                    powerups5.remove(i);
                }
            }
        }
        
        if (powerups6 != null && ingame)
        {
            for (int i = 0; i < powerups6.size(); i++)
            {
                PowerUp6 pUp6 = (PowerUp6) powerups6.get(i);
                if (pUp6.isVisible() && pUp6.getX() >= 0)
                {
                    pUp6.move();
                }
                else
                {
                    powerups6.remove(i);
                }
            }
        }
        
        craft.move();
        if (explosionVisible)
        {
            frameNum++;
            if (frameNum > 16)
            {
                frameNum = 0;
                explosionVisible = false;
            }
        }
        
        if (bigEVisible)
        {
            if (bigEFrameNum == 0 && ingame)
            {
                Sound.BOOM.play();
                score += 100;
                
            }
            bigEFrameNum++;
            if (bigEFrameNum > 14)
            {
                bigEFrameNum = 0;
                bigEVisible = false;
                bigEEnded = true;
            }
        }
        checkCollisions();
        
        
        if (e.getSource() == secondsAnim)
        {
            if (milliSecondsPassed == 0)
            {
                lives--;
                Sound.LOSE.play();
            }
            // increase hit animation frame
            damageFrameNum++;
            if (damageFrameNum > 1)
            {
                damageFrameNum = 0;
            }
            milliSecondsPassed += 50;
            if (milliSecondsPassed >= 500)
            {
                playerHit = false;
                secondsAnim.stop();
                milliSecondsPassed = 0;
            }
           
            if (lives == 0)
            {
                ingame = false;
                Sound.LOSE.play();
            }
          
        }
       
        if (e.getSource() == alienHitTimer)
        {
          for (int i = 0; i < aliens2.size(); i++)
          {
            Alien2 a2 = (Alien2) aliens2.get(i);
        
           if (i == alienID)
           {
            if ( alienMilliSeconds == 0 && a2.isHit())
            {
                a2.setHealth(a2.getHealth()-1);
            }
            // increase hit animation frame
            a2.setHitFrameNum(a2.getHitFrameNum() + 1);
            //System.out.println("Alien ID: " + alienID);
            //System.out.println("frame num: " + a2.getHitFrameNum());
            if (a2.getHitFrameNum() > 1)
            {
                a2.setHitFrameNum(0);
            }
            alienMilliSeconds += 50;
            if (alienMilliSeconds >= 500)
            {
                a2.setHit(false);
                alienHitTimer.stop();
                alienMilliSeconds = 0;
                
            }
            
           
           }
          }
          
          if (level == 3)
          {
              
            if (alienMilliSeconds == 0 && boss.isHit() && boss.isVisible())
            {
              boss.setHealth(boss.getHealth()-1);
            }
          
            boss.setHitFrameNum(boss.getHitFrameNum()+1);
            if (boss.getHitFrameNum() > 1)
            {
              boss.setHitFrameNum(0);
            }
            alienMilliSeconds += 50;
            if (alienMilliSeconds >= 100)
            {
              boss.setHit(false);
              alienHitTimer.stop();
              alienMilliSeconds = 0;
            }
          }
        }
        
        /*if (boss.isHit())
        {
            boss.setHealth(boss.getHealth()-1);
        }
        
        boss.setHitFrameNum(boss.getHitFrameNum() + 1);
        if (boss.getHitFrameNum() > 1)
        {
            boss.setHitFrameNum(0);
            boss.setHit(false);
        }*/
        
        if (level == 3 && e.getSource() == cannonDelay && ingame)
        {
            chargeUp.start();
            Sound.CANNON.play();
            //animateCannon = true;
        }
        
        if (e.getSource() == chargeUp)
        {
            animateCannon = true;
            chargeUp.stop();
        }
        
        if (animateCannon)
        {
            if (ingame && boss.isVisible() && boss.getX() <= 120)
            {
                cannon.setVisible(true);
                cannon.setCannonFrameNum(cannon.getCannonFrameNum()+1);
                if(cannon.getCannonFrameNum() > 11)
                {
                    cannon.setCannonFrameNum(0);
                    counter++;
                    if (counter == 15)
                    {
                        cannon.setVisible(false);
                        animateCannon = false;
                        counter = 0;
                    }
                }
            }
        }
        
        if (e.getSource() == powerUpTimer)
        {
          if (!playerHit)
          {
            powerUpFrameNum++;
            if (powerUpFrameNum > 1)
            {
                powerUpFrameNum = 0;
            }
            milliSecondsPassed += 50;
            if (milliSecondsPassed >= 600)
            {
                powerUpTimer.stop();
                craft.setAnimating(false);
                milliSecondsPassed = 0;
            }
          }
          else
          {
              powerUpTimer.stop();
              craft.setAnimating(false);
              milliSecondsPassed = 0;
          }
            
        }
        
        if (e.getSource() == powerUpDuration)
        {
            craft.pUp1InEffect(false);
            craft.pUp2InEffect(false);
            craft.pUp3InEffect(false);
            craft.pUp4InEffect(false);
            craft.pUp5InEffect(false);
            laser.setVisible(false);
            craft.pUp6InEffect(false);
            
            for (int i = 0; i < ms.size(); i++)
            {
                Missile m = (Missile) ms.get(i);
                m.setMissileSpeed(2);
            }
        
             for (int i = 0; i < sinms.size(); i++)
             {
                SinMissile sinm = (SinMissile) sinms.get(i);
                sinm.setMissileSpeed(2);
             }
        
        
            for (int i = 0; i < cosms.size(); i++)
            {
                CosMissile cosm = (CosMissile) cosms.get(i);
                cosm.setMissileSpeed(2);
            }
        
            
        }
        
        repaint();
    }
    
    public void checkCollisions()
    {
        Rectangle r3 = craft.getBounds();
        // the purpose of the hitbox is to set the reasonable bounds within which the ship can be hit
        Rectangle hitbox = new Rectangle(craft.getX(), craft.getY() + 10, craft.getWidth() - 20, craft.getHeight() / 2);
        // orig hitbox width set to craft.getwidth() / 2, also tried craft.getwidth() - 10
        for (int j = 0; j < aliens.size(); j++)
        {
            Alien a = (Alien) aliens.get(j);
            Rectangle r2 = a.getBounds();
            
            if (r3.intersects(r2))
            {
               if (lives > 0)
               {
                   secondsAnim.start();
                   playerHit = true;
                   a.setVisible(false);
                   killSpotX = a.getX();
                   killSpotY = a.getY();
                   explosionVisible = true;
               }
               else
               {
                craft.setVisible(false);
                a.setVisible(false);
                ingame = false;
                Sound.LOSE.play();
                
               }
                
                
            }
        }
        
        // if alien laser collides, craft loses a life or is destroyed
        for (int i = 0; i < aliens2.size(); i++)
        {
            Alien2 a2 = (Alien2) aliens2.get(i);
            
            ArrayList alienms = a2.getAlienMissiles();
            
            for (int j = 0; j < alienms.size(); j++)
            {
                AlienMissile alienm = (AlienMissile) alienms.get(j);
                Rectangle r6 = alienm.getBounds();
                if (hitbox.intersects(r6))
                {
                    /*System.out.println("alien laser x: " + alienm.getX() + ", alien laser y: " + alienm.getY());
                    System.out.println("rectangle: " + r6);*/
                    
                    if (lives > 0)
                    {
                        secondsAnim.start();
                        playerHit = true;
                        alienm.setVisible(false);
                   
                    }
                    else
                    {
                        craft.setVisible(false);
                        alienm.setVisible(false);
                        ingame = false;
                        Sound.LOSE.play();
                    }
                }
            
            }
        }
        
        ArrayList bossms = boss.getAlienBossMissiles();
            
            for (int j = 0; j < bossms.size(); j++)
            {
                AlienMissile bossm = (AlienMissile) bossms.get(j);
                Rectangle bl = bossm.getBounds();
                if (hitbox.intersects(bl))
                {
                    /*System.out.println("alien laser x: " + alienm.getX() + ", alien laser y: " + alienm.getY());
                    System.out.println("rectangle: " + r6);*/
                    
                    if (lives > 0)
                    {
                        secondsAnim.start();
                        playerHit = true;
                        bossm.setVisible(false);
                   
                    }
                    else
                    {
                        craft.setVisible(false);
                        bossm.setVisible(false);
                        ingame = false;
                        Sound.LOSE.play();
                    }
                }
            
            }
        
        cannon.setX(boss.getX() - boss.getWidth() + cannonDistX);
        cannon.setY(boss.getY() + cannon.getHeight()/2 - cannonDistY);
        Rectangle ac = cannon.getBounds();
        if (r3.intersects(ac) && cannon.isVisible())
        {
            if (lives > 0)
            {
               secondsAnim.start();
               playerHit = true;
            }
            else
            {
               craft.setVisible(false);
               ingame = false;
               if (ingame)
               {
                Sound.LOSE.play();
               }
            }
        }
        
        // check if power-ups collide with spaceship
        if (powerups != null && ingame)
        {
            for (int k = 0; k < powerups.size(); k++)
            {
                PowerUp pUp = (PowerUp) powerups.get(k);
                Rectangle r4 = pUp.getBounds();
                if (r3.intersects(r4))
                {
                    Sound.POWERUP.play();
                    pUp.setVisible(false);
                    powerUpType = "Wave Missile";
                    craft.pUp1InEffect(true);
                    poweredUpAnim1[1] = new ImageIcon(this.getClass().getResource("craftUp1.gif")).getImage();
                    craft.setAnimating(true);
                    //powerUpTimer.start();
                    powerUpTimer.restart();
                    //powerUpDuration.start();
                    powerUpDuration.restart();
                    
                }
            }
        }
        
        if (powerups2 != null && ingame)
        {
            for (int k = 0; k < powerups2.size(); k++)
            {
                PowerUp2 pUp2 = (PowerUp2) powerups2.get(k);
                Rectangle r4 = pUp2.getBounds();
                if (r3.intersects(r4))
                {
                    Sound.POWERUP.play();
                    pUp2.setVisible(false);
                    lives++;
                    powerUpType = "Extra Life";
                    craft.pUp2InEffect(true);
                    poweredUpAnim1[1] = new ImageIcon(this.getClass().getResource("craftUp2.gif")).getImage();
                    craft.setAnimating(true);
                    //powerUpTimer.start();
                    powerUpTimer.restart();
                    //powerUpDuration.start();
                    powerUpDuration.restart();
                    
                }
            }
        }
        
        if (powerups3 != null && ingame)
        {
            for (int k = 0; k < powerups3.size(); k++)
            {
                PowerUp3 pUp3 = (PowerUp3) powerups3.get(k);
                Rectangle r4 = pUp3.getBounds();
                if (r3.intersects(r4))
                {
                    Sound.POWERUP.play();
                    pUp3.setVisible(false);
                    powerUpType = "Wave Missile 2";
                    craft.pUp3InEffect(true);
                    poweredUpAnim1[1] = new ImageIcon(this.getClass().getResource("craftUp3.gif")).getImage();
                    craft.setAnimating(true);
                    //powerUpTimer.start();
                    powerUpTimer.restart();
                    //powerUpDuration.start();
                    powerUpDuration.restart();
                    
                }
            }
        }
        
        if (powerups4 != null && ingame)
        {
            for (int k = 0; k < powerups4.size(); k++)
            {
                PowerUp4 pUp4 = (PowerUp4) powerups4.get(k);
                Rectangle r4 = pUp4.getBounds();
                if (r3.intersects(r4))
                {
                    Sound.POWERUP.play();
                    pUp4.setVisible(false);
                    powerUpType = "Double Shot";
                    craft.pUp4InEffect(true);
                    poweredUpAnim1[1] = new ImageIcon(this.getClass().getResource("craftUp4.gif")).getImage();
                    craft.setAnimating(true);
                    //powerUpTimer.start();
                    powerUpTimer.restart();
                    //powerUpDuration.start();
                    powerUpDuration.restart();
                    
                }
            }
        }
        
        if (powerups5 != null && ingame)
        {
            for (int k = 0; k < powerups5.size(); k++)
            {
                PowerUp5 pUp5 = (PowerUp5) powerups5.get(k);
                Rectangle r4 = pUp5.getBounds();
                if (r3.intersects(r4))
                {
                    Sound.POWERUP.play();
                    pUp5.setVisible(false);
                    powerUpType = "Laser";
                    craft.pUp5InEffect(true);
                    poweredUpAnim1[1] = new ImageIcon(this.getClass().getResource("craftUp5.gif")).getImage();
                    craft.setAnimating(true);
                    //powerUpTimer.start();
                    powerUpTimer.restart();
                    //powerUpDuration.start();
                    laser.setVisible(true);
                    Sound.LASER.play();
                    powerUpDuration.restart();
                    
                }
            }
        }
        
        if (powerups6 != null && ingame)
        {
            for (int k = 0; k < powerups6.size(); k++)
            {
                PowerUp6 pUp6 = (PowerUp6) powerups6.get(k);
                Rectangle r4 = pUp6.getBounds();
                if (r3.intersects(r4))
                {
                    Sound.POWERUP.play();
                    pUp6.setVisible(false);
                    powerUpType = "Increased Fire Rate";
                    craft.pUp6InEffect(true);
                    poweredUpAnim1[1] = new ImageIcon(this.getClass().getResource("craftUp6.gif")).getImage();
                    craft.setAnimating(true);
                    //powerUpTimer.start();
                    powerUpTimer.restart();
                    //powerUpDuration.start();
                    powerUpDuration.restart();
                    
                }
            }
        }
        
        // check if any missiles intersect with any aliens. If they do, both
        // missile and alien are destroyed
        ArrayList ms = craft.getMissiles();
        
        for (int i = 0; i < ms.size(); i++)
        {
            Missile m = (Missile) ms.get(i);
            
            Rectangle r1 = m.getBounds();
            
            for (int j = 0; j < aliens.size(); j++)
            {
                Alien a = (Alien) aliens.get(j);
                Rectangle r2 = a.getBounds();
                
                if (r1.intersects(r2) && ingame)
                {
                    //explosionVisible = true;
                    m.setVisible(false);
                    a.setVisible(false);
                    killSpotX = a.getX();
                    killSpotY = a.getY();
                    explosionVisible = true;
                    Sound.HIT.play();
                    //explosionVisible = true;
                    int randomPUpGenerator = (int) (Math.random() * RANDOMNESS_FACTOR) + 1;
                    //System.out.println("random integer generator: " + randomPUpGenerator);
                    if (randomPUpGenerator == 1)
                    {
                        powerups.add(new PowerUp(killSpotX, killSpotY));
                        PowerUp pUp = (PowerUp) powerups.get(0);
                        pUp.setVisible(true);
                    }
                    
                    else if (randomPUpGenerator == 2)
                    {
                        powerups2.add(new PowerUp2(killSpotX, killSpotY));
                        PowerUp2 pUp2 = (PowerUp2) powerups2.get(0);
                        pUp2.setVisible(true);
                    }
                    
                    else if (randomPUpGenerator == 3)
                    {
                        powerups3.add(new PowerUp3(killSpotX, killSpotY));
                        PowerUp3 pUp3 = (PowerUp3) powerups3.get(0);
                        pUp3.setVisible(true);
                    }
                    
                    else if (randomPUpGenerator == 4)
                    {
                        powerups4.add(new PowerUp4(killSpotX, killSpotY));
                        PowerUp4 pUp4 = (PowerUp4) powerups4.get(0);
                        pUp4.setVisible(true);
                    }
                    
                    else if (randomPUpGenerator == 5)
                    {
                        powerups5.add(new PowerUp5(killSpotX, killSpotY));
                        PowerUp5 pUp5 = (PowerUp5) powerups5.get(0);
                        pUp5.setVisible(true);
                    }
                    
                    else if (randomPUpGenerator == 6)
                    {
                        powerups6.add(new PowerUp6(killSpotX, killSpotY));
                        PowerUp6 pUp6 = (PowerUp6) powerups6.get(0);
                        pUp6.setVisible(true);
                    }
                }
            }
            
            
            for (int j = 0; j < aliens2.size(); j++)
            {
                Alien2 a2 = (Alien2) aliens2.get(j);
                Rectangle r2a = a2.getBounds();
                
                if (r1.intersects(r2a) && ingame)
                {
                    m.setVisible(false);
                    if (a2.getHealth() > 1)
                    {
                      alienID = j;
                      alienHitTimer.start();
                      a2.setHit(true);
                    }
                  else
                  {
                    a2.setVisible(false);
                    killSpotX = a2.getX();
                    killSpotY = a2.getY();
                    explosionVisible = true;
                    Sound.HIT.play();
                    int randomPUpGenerator = (int) (Math.random() * RANDOMNESS_FACTOR) + 1;
                    //System.out.println("random integer generator: " + randomPUpGenerator);
                    if (randomPUpGenerator == 1)
                    {
                        powerups.add(new PowerUp(killSpotX, killSpotY));
                        PowerUp pUp = (PowerUp) powerups.get(0);
                        pUp.setVisible(true);
                    }
                    
                    else if (randomPUpGenerator == 2)
                    {
                        powerups2.add(new PowerUp2(killSpotX, killSpotY));
                        PowerUp2 pUp2 = (PowerUp2) powerups2.get(0);
                        pUp2.setVisible(true);
                    }
                    
                    else if (randomPUpGenerator == 3)
                    {
                        powerups3.add(new PowerUp3(killSpotX, killSpotY));
                        PowerUp3 pUp3 = (PowerUp3) powerups3.get(0);
                        pUp3.setVisible(true);
                    }
                    
                    else if (randomPUpGenerator == 4)
                    {
                        powerups4.add(new PowerUp4(killSpotX, killSpotY));
                        PowerUp4 pUp4 = (PowerUp4) powerups4.get(0);
                        pUp4.setVisible(true);
                    }
                                      
  
                    else if (randomPUpGenerator == 5)
                    {
                        powerups5.add(new PowerUp5(killSpotX, killSpotY));
                        PowerUp5 pUp5 = (PowerUp5) powerups5.get(0);
                        pUp5.setVisible(true);
                    }
                    
                    else if (randomPUpGenerator == 6)
                    {
                        powerups6.add(new PowerUp6(killSpotX, killSpotY));
                        PowerUp6 pUp6 = (PowerUp6) powerups6.get(0);
                        pUp6.setVisible(true);
                    }
                  }
                  
                }
            }
            
            //Rectangle rb = boss.getBounds();
            //if (r1.intersects(rb) && ingame && boss.getX() <= 120)
            Ellipse2D cb = boss.getCircleBounds();
            if (cb.intersects(m.getX(), m.getY(), m.getWidth(), m.getHeight()) && ingame && boss.getX() <= 120)
            {
                
                //if (boss.getHealth() > 0)
                //{
                m.setVisible(false);
                alienMilliSeconds = 0;
                alienHitTimer.start();
                boss.setHit(true);
                /*System.out.println(boss);
                System.out.println("missile's bounds: " + m.getBounds());
                System.out.println("missile x: " + m.getX() + ", missile y: " + m.getY()
                        + ", missile width: " + m.getWidth() + ", missile height: " + m.getHeight());*/
                //System.out.println(boss.getBounds());
                //}
                /*else
                {
                    boss.setVisible(false);
                }*/
                
                
            }
            
        }
        
        ArrayList sinms = craft.getSinMissiles();
        
        for (int i = 0; i < sinms.size(); i++)
        {
            SinMissile sinm = (SinMissile) sinms.get(i);
            
            Rectangle r1 = sinm.getBounds();
            
            for (int j = 0; j < aliens.size(); j++)
            {
                Alien a = (Alien) aliens.get(j);
                Rectangle r2 = a.getBounds();
                
                if (r1.intersects(r2) && ingame)
                {
                    sinm.setVisible(false);
                    a.setVisible(false);
                    killSpotX = a.getX();
                    killSpotY = a.getY();
                    explosionVisible = true;
                    Sound.HIT.play();
                    int randomPUpGenerator = (int) (Math.random() * RANDOMNESS_FACTOR) + 1;
                    if (randomPUpGenerator == 1)
                    {
                        powerups.add(new PowerUp(killSpotX, killSpotY));
                        PowerUp pUp = (PowerUp) powerups.get(0);
                        pUp.setVisible(true);
                    }
                    else if (randomPUpGenerator == 2)
                    {
                        powerups2.add(new PowerUp2(killSpotX, killSpotY));
                        PowerUp2 pUp2 = (PowerUp2) powerups2.get(0);
                        pUp2.setVisible(true);
                    }
                    else if (randomPUpGenerator == 3)
                    {
                        powerups3.add(new PowerUp3(killSpotX, killSpotY));
                        PowerUp3 pUp3 = (PowerUp3) powerups3.get(0);
                        pUp3.setVisible(true);
                    }
                    
                    else if (randomPUpGenerator == 4)
                    {
                        powerups4.add(new PowerUp4(killSpotX, killSpotY));
                        PowerUp4 pUp4 = (PowerUp4) powerups4.get(0);
                        pUp4.setVisible(true);
                    }
                    
                    else if (randomPUpGenerator == 5)
                    {
                        powerups5.add(new PowerUp5(killSpotX, killSpotY));
                        PowerUp5 pUp5 = (PowerUp5) powerups5.get(0);
                        pUp5.setVisible(true);
                    }
                    
                    else if (randomPUpGenerator == 6)
                    {
                        powerups6.add(new PowerUp6(killSpotX, killSpotY));
                        PowerUp6 pUp6 = (PowerUp6) powerups6.get(0);
                        pUp6.setVisible(true);
                    }
                }
            }
            
            for (int j = 0; j < aliens2.size(); j++)
            {
                Alien2 a2 = (Alien2) aliens2.get(j);
                Rectangle r2a = a2.getBounds();
                
                if (r1.intersects(r2a) && ingame)
                {
                    sinm.setVisible(false);
                    if (a2.getHealth() > 1)
                    {
                      alienID = j;
                      alienHitTimer.start();
                      a2.setHit(true);
                    }
                  else
                  {
                    a2.setVisible(false);
                    killSpotX = a2.getX();
                    killSpotY = a2.getY();
                    explosionVisible = true;
                    Sound.HIT.play();
                    int randomPUpGenerator = (int) (Math.random() * RANDOMNESS_FACTOR) + 1;
                    //System.out.println("random integer generator: " + randomPUpGenerator);
                    if (randomPUpGenerator == 1)
                    {
                        powerups.add(new PowerUp(killSpotX, killSpotY));
                        PowerUp pUp = (PowerUp) powerups.get(0);
                        pUp.setVisible(true);
                    }
                    
                    else if (randomPUpGenerator == 2)
                    {
                        powerups2.add(new PowerUp2(killSpotX, killSpotY));
                        PowerUp2 pUp2 = (PowerUp2) powerups2.get(0);
                        pUp2.setVisible(true);
                    }
                    
                    else if (randomPUpGenerator == 3)
                    {
                        powerups3.add(new PowerUp3(killSpotX, killSpotY));
                        PowerUp3 pUp3 = (PowerUp3) powerups3.get(0);
                        pUp3.setVisible(true);
                    }
                    
                    else if (randomPUpGenerator == 4)
                    {
                        powerups4.add(new PowerUp4(killSpotX, killSpotY));
                        PowerUp4 pUp4 = (PowerUp4) powerups4.get(0);
                        pUp4.setVisible(true);
                    }
                                      
  
                    else if (randomPUpGenerator == 5)
                    {
                        powerups5.add(new PowerUp5(killSpotX, killSpotY));
                        PowerUp5 pUp5 = (PowerUp5) powerups5.get(0);
                        pUp5.setVisible(true);
                    }
                    
                    else if (randomPUpGenerator == 6)
                    {
                        powerups6.add(new PowerUp6(killSpotX, killSpotY));
                        PowerUp6 pUp6 = (PowerUp6) powerups6.get(0);
                        pUp6.setVisible(true);
                    }
                  }
                  
                }
            }
            
            Ellipse2D cb = boss.getCircleBounds();
            if (cb.intersects(sinm.getX(), sinm.getY(), sinm.getWidth(), sinm.getHeight()) && ingame && boss.getX() <= 120)
            {
                sinm.setVisible(false);
                alienMilliSeconds = 0;
                alienHitTimer.start();
                boss.setHit(true);
            }
        }
        
        ArrayList cosms = craft.getCosMissiles();
        
        for (int i = 0; i < cosms.size(); i++)
        {
            CosMissile cosm = (CosMissile) cosms.get(i);
            
            Rectangle r1 = cosm.getBounds();
            
            for (int j = 0; j < aliens.size(); j++)
            {
                Alien a = (Alien) aliens.get(j);
                Rectangle r2 = a.getBounds();
                
                if (r1.intersects(r2) && ingame)
                {
                    cosm.setVisible(false);
                    a.setVisible(false);
                    killSpotX = a.getX();
                    killSpotY = a.getY();
                    explosionVisible = true;
                    Sound.HIT.play();
                    int randomPUpGenerator = (int) (Math.random() * RANDOMNESS_FACTOR) + 1;
                    if (randomPUpGenerator == 1)
                    {
                        powerups.add(new PowerUp(killSpotX, killSpotY));
                        PowerUp pUp = (PowerUp) powerups.get(0);
                        pUp.setVisible(true);
                    }
                    else if (randomPUpGenerator == 2)
                    {
                        powerups2.add(new PowerUp2(killSpotX, killSpotY));
                        PowerUp2 pUp2 = (PowerUp2) powerups2.get(0);
                        pUp2.setVisible(true);
                    }
                    else if (randomPUpGenerator == 3)
                    {
                        powerups3.add(new PowerUp3(killSpotX, killSpotY));
                        PowerUp3 pUp3 = (PowerUp3) powerups3.get(0);
                        pUp3.setVisible(true);
                    }
                    
                    else if (randomPUpGenerator == 4)
                    {
                        powerups4.add(new PowerUp4(killSpotX, killSpotY));
                        PowerUp4 pUp4 = (PowerUp4) powerups4.get(0);
                        pUp4.setVisible(true);
                    }
                    
                    else if (randomPUpGenerator == 5)
                    {
                        powerups5.add(new PowerUp5(killSpotX, killSpotY));
                        PowerUp5 pUp5 = (PowerUp5) powerups5.get(0);
                        pUp5.setVisible(true);
                    }
                    
                    else if (randomPUpGenerator == 6)
                    {
                        powerups6.add(new PowerUp6(killSpotX, killSpotY));
                        PowerUp6 pUp6 = (PowerUp6) powerups6.get(0);
                        pUp6.setVisible(true);
                    }
                }
            }
            
            for (int j = 0; j < aliens2.size(); j++)
            {
                Alien2 a2 = (Alien2) aliens2.get(j);
                Rectangle r2a = a2.getBounds();
                
                if (r1.intersects(r2a) && ingame)
                {
                    cosm.setVisible(false);
                    if (a2.getHealth() > 1)
                    {
                      alienID = j;
                      alienHitTimer.start();
                      a2.setHit(true);
                    }
                  else
                  {
                    a2.setVisible(false);
                    killSpotX = a2.getX();
                    killSpotY = a2.getY();
                    explosionVisible = true;
                    Sound.HIT.play();
                    int randomPUpGenerator = (int) (Math.random() * RANDOMNESS_FACTOR) + 1;
                    //System.out.println("random integer generator: " + randomPUpGenerator);
                    if (randomPUpGenerator == 1)
                    {
                        powerups.add(new PowerUp(killSpotX, killSpotY));
                        PowerUp pUp = (PowerUp) powerups.get(0);
                        pUp.setVisible(true);
                    }
                    
                    else if (randomPUpGenerator == 2)
                    {
                        powerups2.add(new PowerUp2(killSpotX, killSpotY));
                        PowerUp2 pUp2 = (PowerUp2) powerups2.get(0);
                        pUp2.setVisible(true);
                    }
                    
                    else if (randomPUpGenerator == 3)
                    {
                        powerups3.add(new PowerUp3(killSpotX, killSpotY));
                        PowerUp3 pUp3 = (PowerUp3) powerups3.get(0);
                        pUp3.setVisible(true);
                    }
                    
                    else if (randomPUpGenerator == 4)
                    {
                        powerups4.add(new PowerUp4(killSpotX, killSpotY));
                        PowerUp4 pUp4 = (PowerUp4) powerups4.get(0);
                        pUp4.setVisible(true);
                    }
                                      
  
                    else if (randomPUpGenerator == 5)
                    {
                        powerups5.add(new PowerUp5(killSpotX, killSpotY));
                        PowerUp5 pUp5 = (PowerUp5) powerups5.get(0);
                        pUp5.setVisible(true);
                    }
                    
                    else if (randomPUpGenerator == 6)
                    {
                        powerups6.add(new PowerUp6(killSpotX, killSpotY));
                        PowerUp6 pUp6 = (PowerUp6) powerups6.get(0);
                        pUp6.setVisible(true);
                    }
                  }
                  
                }
            }
            
            Ellipse2D cb = boss.getCircleBounds();
            if (cb.intersects(cosm.getX(), cosm.getY(), cosm.getWidth(), cosm.getHeight()) && ingame && boss.getX() <= 120)
            {
                cosm.setVisible(false);
                alienMilliSeconds = 0;
                alienHitTimer.start();
                boss.setHit(true);
            }
        }
        
        laser.setX(craft.getX() + laserDistX);
        laser.setY(craft.getY() + laserDistY);
        Rectangle r5 = laser.getBounds();
         for (int j = 0; j < aliens.size(); j++)
            {
                Alien a = (Alien) aliens.get(j);
                Rectangle r2 = a.getBounds();
                if (laser.isVisible() && r5.intersects(r2) && ingame && a.getX() < B_WIDTH - 50)
                {
                    a.setVisible(false);
                    killSpotX = a.getX();
                    killSpotY = a.getY();
                    explosionVisible = true;
                    Sound.HIT.play();
                    int randomPUpGenerator = (int) (Math.random() * RANDOMNESS_FACTOR) + 1;
                    if (randomPUpGenerator == 1)
                    {
                        powerups.add(new PowerUp(killSpotX, killSpotY));
                        PowerUp pUp = (PowerUp) powerups.get(0);
                        pUp.setVisible(true);
                    }
                    else if (randomPUpGenerator == 2)
                    {
                        powerups2.add(new PowerUp2(killSpotX, killSpotY));
                        PowerUp2 pUp2 = (PowerUp2) powerups2.get(0);
                        pUp2.setVisible(true);
                    }
                    else if (randomPUpGenerator == 3)
                    {
                        powerups3.add(new PowerUp3(killSpotX, killSpotY));
                        PowerUp3 pUp3 = (PowerUp3) powerups3.get(0);
                        pUp3.setVisible(true);
                    }
                    
                    else if (randomPUpGenerator == 4)
                    {
                        powerups4.add(new PowerUp4(killSpotX, killSpotY));
                        PowerUp4 pUp4 = (PowerUp4) powerups4.get(0);
                        pUp4.setVisible(true);
                    }
                    
                    else if (randomPUpGenerator == 5)
                    {
                        powerups5.add(new PowerUp5(killSpotX, killSpotY));
                        PowerUp5 pUp5 = (PowerUp5) powerups5.get(0);
                        pUp5.setVisible(true);
                    }
                    
                    else if (randomPUpGenerator == 6)
                    {
                        powerups6.add(new PowerUp6(killSpotX, killSpotY));
                        PowerUp6 pUp6 = (PowerUp6) powerups6.get(0);
                        pUp6.setVisible(true);
                    }
                }
            }
         
            for (int j = 0; j < aliens2.size(); j++)
            {
                Alien2 a2 = (Alien2) aliens2.get(j);
                Rectangle r2a = a2.getBounds();
                
                if ( laser.isVisible() && r5.intersects(r2a) && ingame && a2.getX() < B_WIDTH - 50  )
                {
                    if (a2.getHealth() > 1)
                    {
                      alienID = j;
                      alienHitTimer.start();
                      a2.setHit(true);
                    }
                  else
                  {
                    a2.setVisible(false);
                    killSpotX = a2.getX();
                    killSpotY = a2.getY();
                    explosionVisible = true;
                    Sound.HIT.play();
                    int randomPUpGenerator = (int) (Math.random() * RANDOMNESS_FACTOR) + 1;
                    //System.out.println("random integer generator: " + randomPUpGenerator);
                    if (randomPUpGenerator == 1)
                    {
                        powerups.add(new PowerUp(killSpotX, killSpotY));
                        PowerUp pUp = (PowerUp) powerups.get(0);
                        pUp.setVisible(true);
                    }
                    
                    else if (randomPUpGenerator == 2)
                    {
                        powerups2.add(new PowerUp2(killSpotX, killSpotY));
                        PowerUp2 pUp2 = (PowerUp2) powerups2.get(0);
                        pUp2.setVisible(true);
                    }
                    
                    else if (randomPUpGenerator == 3)
                    {
                        powerups3.add(new PowerUp3(killSpotX, killSpotY));
                        PowerUp3 pUp3 = (PowerUp3) powerups3.get(0);
                        pUp3.setVisible(true);
                    }
                    
                    else if (randomPUpGenerator == 4)
                    {
                        powerups4.add(new PowerUp4(killSpotX, killSpotY));
                        PowerUp4 pUp4 = (PowerUp4) powerups4.get(0);
                        pUp4.setVisible(true);
                    }
                                      
  
                    else if (randomPUpGenerator == 5)
                    {
                        powerups5.add(new PowerUp5(killSpotX, killSpotY));
                        PowerUp5 pUp5 = (PowerUp5) powerups5.get(0);
                        pUp5.setVisible(true);
                    }
                    
                    else if (randomPUpGenerator == 6)
                    {
                        powerups6.add(new PowerUp6(killSpotX, killSpotY));
                        PowerUp6 pUp6 = (PowerUp6) powerups6.get(0);
                        pUp6.setVisible(true);
                    }
                  }
                  
                }
            }
            
            Ellipse2D cb = boss.getCircleBounds();
            // laser.isVisible() && r5.intersects(r2) && ingame && a.getX() < B_WIDTH - 50
            if (laser.isVisible() && cb.intersects(laser.getX(), laser.getY(), laser.getWidth(), laser.getHeight()) && ingame && boss.getX() <= 120)
            {
                alienMilliSeconds = 0;
                alienHitTimer.start();
                boss.setHit(true);
            }
        
        
    }
    
    private class TAdapter extends KeyAdapter
    {
        public void keyReleased(KeyEvent e)
        {
            craft.keyReleased(e);
        }
        
        public void keyPressed(KeyEvent e)
        {
            craft.keyPressed(e);
        }
    }
}
