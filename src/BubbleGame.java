import javax.swing.*;
import java.awt.*;

public class BubbleGame extends JPanel {
    private Ball[][] fixedBalls;

    public BubbleGame(int w, int h) {
        setSize(w, h);

        fixedBalls = new Ball[h/Ball.SIZE][w/Ball.SIZE];
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        //TODO: Write a for loop that loops through fixedBalls and draws all of them
    }

    //Main - no need to change
    public static void main(String[] args) {
        JFrame frame = new JFrame("Bubble Game!");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        int width = 800;
        int height = 800;
        frame.setPreferredSize(new Dimension(width, height+24));

        JPanel panel = new BubbleGame(width, height);
        panel.setFocusable(true);
        panel.grabFocus();

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}