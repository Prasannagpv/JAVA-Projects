import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
//import java.util.Timer;


public class GamePanel extends JPanel implements ActionListener{
    
     static final int screen_witdh  = 1300;//size of the panel
     static final int screen_height = 750;//size of the panel
     static final int unit_size = 25; // size of the objects
     static final int game_units = (screen_witdh*screen_witdh)/unit_size;
     static final int delay = 75;
     final int x[] = new int[game_units];
     final int y[] = new int[game_units];
     int bodyParts = 5;// initial size of the snake
     int appleEaten;// starting score =0;
     int appleX;
     int appleY;
     char direction ='R'; // staring direction of the snake
     boolean running = false;
     Timer timer;
     Random random;



    GamePanel()
    {
        random = new Random();
        this.setPreferredSize(new Dimension(screen_witdh,screen_height));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame()
    {
        newApple();
        running= true;
        timer = new Timer(delay,this);
        timer.start();

    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g)
    {   
        
        if(running)
        {   /* 
            for(int i = 0 ;i<screen_height/unit_size;i++)
            {
                g.drawLine(i*unit_size, 0, i*unit_size, screen_height); // to create the grid
                g.drawLine(0, i*unit_size, screen_witdh, i*unit_size);  // to create the grid
            }
            */

            //to draw the apple

            //g.setColor(Color.orange);
            //g.drawOval(appleX, appleY, unit_size,  unit_size);
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, unit_size,  unit_size);


            //BODY OF THE SNAKE
            for(int i=0; i<bodyParts; i++)
            {
                if(i==0)// HEAD OF THE SNAKE
                {
                    g.setColor(Color.green);
                    g.fillRect(x[i],y[i],unit_size,unit_size);
                }
                else//BODY OF THE SNAKE
                {
                    g.setColor(new Color(random.nextInt(225),random.nextInt(225),random.nextInt(225)));
                    //g.setColor(Color.pink);
                    g.fillRect(x[i],y[i],unit_size,unit_size);
                }
            }

            g.setColor(Color.orange);
            g.setFont(new Font("INK Free",Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+appleEaten, (screen_witdh - metrics.stringWidth("Score: "+appleEaten))/2, g.getFont().getSize());
        }
        else
        {
            gameOver(g);
        }
    }

    public void newApple()
    {
        appleX = random.nextInt((int)(screen_witdh/unit_size)) *unit_size;
        appleY = random.nextInt((int)(screen_height/unit_size))*unit_size;
    }


    public void move()
    {
        for(int i = bodyParts;i>0;i--)
        {
            x[i] = x[i-1];
            y[i] = y[i-1];

        }

        switch (direction) {
            case 'U':
            y[0] = y[0] - unit_size;
            break;
            case 'D':
            y[0] = y[0] + unit_size;
            break;
            case 'L':
            x[0] = x[0] - unit_size;
            break;
            case 'R':
            x[0] = x[0] + unit_size;
            break;

            default:
            break;
        }
    }

    public void checkApple()
    {
        if((x[0]== appleX) && (y[0]== appleY))
        {
            bodyParts++;
            appleEaten++;
            newApple();
        }
    }


    public void checkCollisions()
    {   
        //head colides with the body
        for(int i = bodyParts; i>0; i--)
        {
            if((x[0] == x[i]) && y[0] == y[i])
            {
                running = false;
            }
        }
        //checks if head touches borders
        if(x[0] <0)
        {
            running = false;
        }
        if(x[0] >screen_witdh)
        {
            running = false;
        }
        if(y[0] <0)
        {
            running = false;
        }
        if(y[0] >screen_height)
        {
            running = false;
        }

        if(!running)
        {
            timer.stop();
        }
    
    }

    public void gameOver(Graphics g)
    {
        //score
        g.setColor(Color.orange);
        g.setFont(new Font("INK Free",Font.BOLD,40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+appleEaten, (screen_witdh - metrics1.stringWidth("Score: "+appleEaten))/2, g.getFont().getSize());

        // game over text
        g.setColor(Color.red);
        g.setFont(new Font("INK Free",Font.BOLD,80));
        FontMetrics metrics2= getFontMetrics(g.getFont());
        g.drawString("Game Over", (screen_witdh - metrics2.stringWidth("Game Over"))/2, screen_height/2);
        
        
    
    
    }


    

    @Override
    public void actionPerformed(ActionEvent e) {


        if(running)
        {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }




    public class MyKeyAdapter extends KeyAdapter {
    
        public void keyPressed(KeyEvent e)
        {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R')
                    {
                        direction = 'L';
                    }
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L')
                    {
                        direction = 'R';
                    }
                    
                break;

                case KeyEvent.VK_UP:
                    if(direction != 'D')
                    {
                        direction = 'U';
                    }
                    
                break;

                case KeyEvent.VK_DOWN:
                    if(direction != 'U')
                    {
                        direction = 'D';
                    }
                    
                break;
            
                default:
                    break;
            }
        }
        
    }
}
