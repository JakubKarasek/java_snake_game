 package branch1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

 public
     class GamePanel
     extends JPanel
     implements Runnable {

     private static final int WIDTH = 600, HEIGHT = 600;
     private static final int UNIT_SIZE = 25;

     private Thread thread;
     private boolean running = false;

     private SnakeBodyPart bodyPart;
     private List<SnakeBodyPart> snake;

     private Apple apple;
     private List<Apple> apples;

     private Obstacle obstacle;

     private int x = 1, y = 1;
     private int snakeSizeCheck = 1;

     private boolean right = false, left = false, up = false, down = true;

     private int delay;

     private ScorePanel scorePanel = MyFrame.getScorePanel();
     private int score;

     public GamePanel(){
         initSnake();
         initApple();
         initObstacle();
         delay = 200;
         score = 0;

         startGame();

         this.setFocusable(true);
         this.addKeyListener(new Keyboard());

         this.setBackground(Color.BLACK);
         this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
     }

     private void initSnake(){
         snake = new ArrayList<>();
         bodyPart = new SnakeBodyPart(x, y);
         snake.add(bodyPart);
     }

     private void initApple(){
         apples = new ArrayList<>();
     }

     private void initObstacle(){
         obstacle = new Obstacle((int)(Math.random()*24),(int)(Math.random()*24));
         checkObstacleRespawn();
     }

     private void checkAppleRespawn(){
         for(int i = 0; i< snake.size() ; i++){
             if(apple.getX() == snake.get(i).getX() && apple.getY() == snake.get(i).getY())
                 newAppleRespawn();
         }
     }

     private void newAppleRespawn(){
         apple = new Apple((int)(Math.random()*24),(int)(Math.random()*24));
     }

     private void checkObstacleRespawn(){
         for(int i = 0; i< snake.size() ; i++){
             if(obstacle.getX() == snake.get(i).getX() && obstacle.getY() == snake.get(i).getY())
                 newObstacleRespawn();
         }
     }

     private void newObstacleRespawn(){
         apple = new Apple((int)(Math.random()*24),(int)(Math.random()*24));
     }

     public static int getUnitSize() {
         return UNIT_SIZE;
     }

     public void tick() {

         if(apples.size() == 0){
             apple = new Apple((int)(Math.random()*24),(int)(Math.random()*24));
             checkAppleRespawn();
             apples.add(apple);
         }

         // check apple with snake's head
         for(int i = 0; i < apples.size(); i++){
             if(x == apples.get(i).getX() && y == apples.get(i).getY()) {
                 snakeSizeCheck++;
                 apples.remove(i);
                 initObstacle();
                 delay -= 5;
                 score += 10;
                 scorePanel.setScoreTextLabel2(score);
 //                System.out.println("Apple eaten!!!!!");
             }
         }

         // check collision with obstacle
         if(x == obstacle.getX() && y == obstacle.getY())
            stopGame();

         // check snake's body collision (incl. last part)
         for(int i=0; i < snake.size(); i++){
             if(x == snake.get(i).getX() && y == snake.get(i).getY())
                 if(i != snake.size() - 1)
                     stopGame();
         }

         // check collision with board
         if(x < 0 || x >= 24 || y < 0 || y >= 24)
             stopGame();


         if(right) x++;
         if(left) x--;
         if(up) y--;
         if(down) y++;

         // move mechanics - front
         bodyPart = new SnakeBodyPart(x, y);
 //        System.out.println("before; snake.size() " + snake.size());
 //        System.out.println("size check " + snakeSizeCheck);
         snake.add(bodyPart);

         // move mechanics - back
         if(snake.size() > snakeSizeCheck) {
 //            System.out.println("after; snake.size() " +snake.size());
             snake.remove(0);
         }

         try {
             thread.sleep(delay);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     }

     public void paint(Graphics g){

         for(int i = 0; i < snake.size(); i++){
             snake.get(i).draw(g);
         }

         for(int i = 0; i < apples.size(); i++){
             apples.get(i).draw(g);
         }

         obstacle.draw(g);

         g.setColor(new Color(216, 216, 216));
         for (int i = 0; i <= HEIGHT / UNIT_SIZE; i++) {
             g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, HEIGHT);
             g.drawLine(0, i * UNIT_SIZE, WIDTH, i * UNIT_SIZE);
         }
     }

     public void startGame(){
         try {
             BufferedReader reader = new BufferedReader(new FileReader("src/score/score.txt"));
             int highestScoreInt = 0;
             String line = reader.readLine();

             while (line != null) {
                 int tmp = Integer.parseInt(line);
                 if (tmp > highestScoreInt)
                     highestScoreInt = tmp;
                 line = reader.readLine();
             }
             reader.close();

             String highestScore = Integer.toString(highestScoreInt);


             JOptionPane.showMessageDialog(this, "Hi! You're about to play the Snake Game." +
                     "\nUse the arrow keys to navigate upon the game board and try to eat as much Apples as you can." +
                     "\nEach Apple is worth 10 points. Beware! There will be obstacles!" +
                     "\n\nThe highest score so far is " + highestScore + "."+
                     "\n\nClick OK to start the game.");

         } catch(NumberFormatException e){
             e.printStackTrace();
         } catch (IOException e){
             e.printStackTrace();
         }

         running = true;
         thread = new Thread(this);
         thread.start();
     }

     public void stopGame() {
         running = false;

         //IO

         try{
             PrintWriter writer;
             String scoreText = Integer.toString(score);
             writer = new PrintWriter(new FileWriter("src/score/score.txt", true));
             writer.write(scoreText);
             writer.write("\n");
             writer.close();

         } catch (IOException e){
             e.printStackTrace();
         }

         JOptionPane.showMessageDialog(this,"GAME OVER!\n" +
         "Your score is " + score);

         System.exit(0);

     }

     @Override
     public void run() {
         while (running) {
                 tick();
                 repaint();
             }
     }


     private class Keyboard implements KeyListener{

         @Override
         public void keyPressed(KeyEvent e) {
             int keyCode = e.getKeyCode();

             if(keyCode == KeyEvent.VK_RIGHT && !left) {
                 up = false;
                 down = false;
                 right = true;
             }

             if(keyCode == KeyEvent.VK_LEFT && !right) {
                 up = false;
                 down = false;
                 left = true;
             }

             if(keyCode == KeyEvent.VK_UP && !down) {
                 left = false;
                 right = false;
                 up = true;
             }

             if(keyCode == KeyEvent.VK_DOWN && !up) {
                 left = false;
                 right = false;
                 down = true;
             }

         }
         @Override
         public void keyTyped(KeyEvent e) {}

         @Override
         public void keyReleased(KeyEvent e) {}
     }

 }


