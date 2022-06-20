package branch1;

import javax.swing.*;
import java.awt.*;

public
    class ScorePanel
    extends JPanel {

    private final int WIDTH = 30, HEIGHT = 30;

    JLabel scoreTextLabel1;
    JLabel scoreTextLabel2;

    public ScorePanel(){

        this.setLayout(new FlowLayout());

        scoreTextLabel1 = new JLabel("Score:\t\t\t\t");
        scoreTextLabel1.setForeground(Color.GREEN);
        scoreTextLabel1.setFont(new Font(Font.SANS_SERIF ,Font.BOLD ,15));

        scoreTextLabel2 = new JLabel("0");
        scoreTextLabel2.setForeground(Color.GREEN);
        scoreTextLabel2.setFont(new Font(Font.SANS_SERIF ,Font.BOLD ,15));

        JPanel scoreTextLocation1 = new JPanel();
        scoreTextLocation1.setBackground(Color.BLACK);
        scoreTextLocation1.add(scoreTextLabel1);

        JPanel scoreTextLocation2 = new JPanel();
        scoreTextLocation2.setBackground(Color.BLACK);
        scoreTextLocation2.add(scoreTextLabel2);


        this.add(scoreTextLocation1);
        this.add(scoreTextLocation2);

        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public void setScoreTextLabel2(int score){
        String scoreText = Integer.toString(score);
        this.scoreTextLabel2.setText(scoreText);
    }

}
