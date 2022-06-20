package branch1;

import javax.swing.*;
import java.awt.*;

public
    class MyFrame
    extends JFrame {

    private static ScorePanel scorePanel;

    public MyFrame() {
        this.initComponents();
        this.initFrame();
    }

    public void initComponents(){
        this.setLayout(new BorderLayout());

        scorePanel = new ScorePanel();
        this.add(scorePanel, BorderLayout.PAGE_START);

        GamePanel gamePanel = new GamePanel();
        this.add(gamePanel, BorderLayout.CENTER);
    }

    public void initFrame(){
        this.pack();

        this.setTitle("Snake Game");
        this.setResizable(false);
        this.setLocationRelativeTo(null);


        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static ScorePanel getScorePanel() {
        return scorePanel;
    }
}
