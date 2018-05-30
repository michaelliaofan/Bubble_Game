import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BubbleGame extends JPanel {
    private Ball[][] fixedBalls;
    private Ball nextBall;

    private Timer timer;

    public BubbleGame(int w, int h) {
        setSize(w, h);

        fixedBalls = new Ball[(h-24) / Ball.SIZE][w / Ball.SIZE];

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point initPos = new Point(w/2, h-24 - Ball.SIZE);

                int mouseX = e.getX();
                int mouseY = e.getY();

                int dx = mouseX - initPos.x;
                int dy = mouseY - initPos.y;

                int direction = (int)Math.toDegrees(Math.atan(dy/dx));

                nextBall = new Ball(initPos, direction);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                repaint();
            }
        });
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        //TODO: Write a for loop that loops through fixedBalls and draws all of them
        //TODO: Draw nextBall;
    }

    //Balls move one row down
    //Out of bounds = fail
    private void shiftBalls() {

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