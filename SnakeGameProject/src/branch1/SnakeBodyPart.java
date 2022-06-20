package branch1;

import java.awt.*;

public class SnakeBodyPart {


    private int unit = GamePanel.getUnitSize();
    private int x, y;

    public SnakeBodyPart(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g){
        g.setColor(Color.GREEN);
        g.fillRect(x*unit , y*unit , unit, unit);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
