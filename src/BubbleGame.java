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
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < fixedBalls[0].length; c++) {
                fixedBalls[r][c] = new Ball(new Point(c*Ball.SIZE + Ball.SIZE/2, r*Ball.SIZE + Ball.SIZE/2), 0, 0);
            }
        }

        nextBall = new Ball(new Point(300, 300), 2, 4);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Point initPos = new Point(w/2, h-24 - Ball.SIZE);

                double mouseX = e.getX();
                double mouseY = e.getY();

                double dx = mouseX - initPos.x;
                double dy = mouseY - initPos.y;

                double dxy = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

                dx *= 10;
                dy *= 10;

                dx /= dxy;
                dy /= dxy;

                nextBall = new Ball(initPos, (int)dx, (int)dy);
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nextBall != null) {
                    nextBall.update(w, h);
                }

                for(int r = 0; r < fixedBalls.length ; r++) {
                    for(int c = 0; c < fixedBalls[0].length; c++) {
                        if(fixedBalls[r][c] != null && nextBall != null) {
//                            if(nextBall.getBoundingRectangle().intersects(fixedBalls[r][c].getBoundingRectangle())) {
//
//                            }

                            if(nextBall.distanceTo(fixedBalls[r][c]) <= 50) {
                                int r1 = (int)nextBall.getPosition().getY()/Ball.SIZE;
                                int c1 = (int)nextBall.getPosition().getX()/Ball.SIZE;
                                fixedBalls[r1][c1] = new Ball(new Point(c1*Ball.SIZE + Ball.SIZE/2, r1*Ball.SIZE + Ball.SIZE/2), nextBall.getColor(), 0, 0);
                                nextBall = null;
                            }
                        }
                    }
                }

                repaint();
            }
        });

        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        for(int i = 0; i < fixedBalls.length; i++) {
            for(int j = 0; j < fixedBalls[0].length; j++) {
                if(fixedBalls[i][j] != null) {
                    fixedBalls[i][j].draw(g2);
                }
            }
        }

        if(nextBall != null) {
            nextBall.draw(g2);
        }
    }

    //Balls move one row down
    //Out of bounds = fail
    private void shiftBalls() {
        //if(ShiftDownCounter() == 0){

    }
    //public int ShiftCounter(){

   // }

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