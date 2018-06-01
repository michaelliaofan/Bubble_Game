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
                Point initPos = new Point(w/2, h-24 - Ball.SIZE);

                int mouseX = e.getX();
                int mouseY = e.getY();

                int dx = mouseX - initPos.x;
                int dy = mouseY - initPos.y;

                int dxy = (int)Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

                dx *= 10;
                dy *= 10;

                dx /= dxy;
                dy /= dxy;

                nextBall = new Ball(initPos, dx, dy);
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

        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextBall.update(w, h);

                for (int i = 0; i < fixedBalls.length ; i++) {
                    for (int j = 0; j < fixedBalls[0].length; j++) {
                        if(fixedBalls[i][j]!=null){
                            if(nextBall.getBoundingRectangle().intersects(fixedBalls[i][j].getBoundingRectangle())) {
                                int r = (int)nextBall.getPosition().getY()/Ball.SIZE;
                                int c = (int)nextBall.getPosition().getX()/Ball.SIZE;
                                fixedBalls[r][c] = new Ball(new Point(c*Ball.SIZE + Ball.SIZE/2, r*Ball.SIZE + Ball.SIZE/2), nextBall.getColor(), 0,0);
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
    //public int ShiftDownCounter(){

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