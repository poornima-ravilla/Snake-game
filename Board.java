package snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  

public class Board extends JPanel implements ActionListener {
    
    private int dots;
    private Image food; 
    private Image snake;
    
    private final int allDots = 1600;
    private final int dotSize = 10;
    private final int randomPosition = 30;
    
    private int food_x;
    private int food_y;
    
    private final int x[] = new int[allDots];
    private final int y[] = new int[allDots];
    
    private Timer timer;
    
    private boolean leftdir = false;
    private boolean rightdir = true;
    private boolean updir = false;
    private boolean downdir = false;
    
    private boolean endGame = false;
    
    Board(){
        
        addKeyListener(new TAdapter());
        setBackground(new Color(0,0,0));
        setPreferredSize(new Dimension(500,500));
        setFocusable(true);
        
        loadImages();
        startGame();
    }
    
    public void loadImages(){
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("snakegame/images/food.png"));
        food = i1.getImage();
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("snakegame/images/snake.png"));
        snake = i2.getImage();    
    }
    
    public void startGame(){
        dots = 3;
        
        for(int i=0; i<dots; i++){
            y[i] = 50;
            x[i] = 50 - i * dotSize;
        }
        
        locateBall();
        
        timer = new Timer(140,this);
        timer.start();
    }
    
    public void locateBall(){
        int r = (int)(Math.random() * randomPosition);
        food_x = r * dotSize;
        r = (int)(Math.random() * randomPosition);
        food_y = r * dotSize;
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        draw(g);
    }
    
    public void draw(Graphics g){
        if(!endGame){
            g.drawImage(food,food_x,food_y,this);
            
            for(int i=0; i<dots; i++){
                g.drawImage(snake, x[i], y[i], this);
            }
            Toolkit.getDefaultToolkit().sync(); 
        }
        else{
            gameOver(g);
        }
           
    }
    
    public void gameOver(Graphics g){
        String msg ="Game Over!!";
        Font font = new Font("SAN-SERIF",Font.BOLD,30);
        FontMetrics metrics = getFontMetrics(font);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg, ((500 - metrics.stringWidth(msg))/2), 500/2);
    }
    
    public void actionPerformed(ActionEvent ae){
        if(!endGame){
            checkBall();
            checkCollision();
            move();
        }
        
        repaint(); 
    }
    
    public void checkBall(){
        if(x[0] == food_x && y[0] == food_y){
            dots++;
            locateBall();
        }
        
    }
    public void move(){
        for(int i=dots; i>0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }   
        if(leftdir){
            x[0] -= dotSize;
        }
        if(rightdir){
            x[0] += dotSize;
        }
        if(updir){
            y[0] -= dotSize;
        }
        if(downdir){
            y[0] += dotSize;
        }
    }
    public void checkCollision(){
        for(int i=dots; i>0; i--){
            if((i>4) && (x[0] == x[i]) && (y[0] == y[i])){
                endGame = true;
            }
            if(x[0] < 0){
                endGame = true;
            }
            if(y[0] < 0){
                endGame = true;
            }
            if(x[0] >= 300){
                endGame = true;
            }
            if(y[0] >= 300){
                endGame = true;
            }
            if(endGame){
                timer.stop();
            }
        }
    }
    
    
    public class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            
            if(key == KeyEvent.VK_LEFT && (!rightdir)){
                leftdir = true;
                updir = false;
                downdir = false;   
            }
            if(key == KeyEvent.VK_RIGHT && (!leftdir)){
                rightdir = true;
                updir = false;
                downdir = false;   
            }
            if(key == KeyEvent.VK_UP&& (!downdir)){
                updir = true;
                rightdir = false;
                leftdir = false;   
            }
            if(key == KeyEvent.VK_DOWN && (!updir)){
                downdir = true;
                rightdir = false;
                leftdir = false;   
            }
        }
    }
    
}