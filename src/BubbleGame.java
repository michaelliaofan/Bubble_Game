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

    private boolean didLose;

    public BubbleGame(int w, int h) {
        setSize(w, h);

        fixedBalls = new Ball[(h-24) / Ball.SIZE][w / Ball.SIZE];
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < fixedBalls[0].length; c++) {
                fixedBalls[r][c] = new Ball(new Point(c*Ball.SIZE + Ball.SIZE/2, r*Ball.SIZE + Ball.SIZE/2), 0, 0);
            }
        }

        nextBall = new Ball(new Point(w/2, h-24 - Ball.SIZE), 0, 0);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(-0.1 <= nextBall.getVelocity() && nextBall.getVelocity() <= 0.1) {
                    Point initCenter = new Point(w/2, h-24 - Ball.SIZE);

                    double mouseX = e.getX();
                    double mouseY = e.getY();

                    double dx = mouseX - initCenter.x;
                    double dy = mouseY - initCenter.y;

                    double dxy = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

                    dx *= 10;
                    dy *= 10;

                    dx /= dxy;
                    dy /= dxy;

                    nextBall.setVelocity(dx, dy);


                }
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

                //Checks for collisions
                for(int r = 0; r < fixedBalls.length ; r++) {
                    for(int c = 0; c < fixedBalls[0].length; c++) {
                        if(fixedBalls[r][c] != null && nextBall != null) {
                            if(nextBall.distanceTo(fixedBalls[r][c]) <= Ball.SIZE) {
                                int r1 = (int)nextBall.getCenter().getY()/Ball.SIZE;
                                int c1 = (int)nextBall.getCenter().getX()/Ball.SIZE;

                                if(removeBalls(nextBall.getColor(), r1, c1) == 0) {
                                    fixedBalls[r1][c1] = new Ball(new Point(c1*Ball.SIZE + Ball.SIZE/2, r1*Ball.SIZE + Ball.SIZE/2), nextBall.getColor(), 0, 0);
                                }

                                shiftBalls();

                                nextBall.setCenter(new Point.Double(w/2, h-24 - Ball.SIZE));
                                nextBall.setVelocity(0, 0);
                                nextBall.randomizeColor();
                            }
                        }
                    }
                }

                repaint();
            }
        });

        timer.start();

        didLose = false;
    }

    //TODO: Check the balls immediately above and to the sides of the current ball. If any of them are the same color, remove them and call this method on them.
    private int removeBalls(Color color, int r, int c) {
        int count = 0;

        for(int row = r - 1; row <= r + 1; row++) {
            for(int col = c - 1; col <= c + 1; col++) {
                if(0 <= row && row < fixedBalls.length && 0 <= col && col < fixedBalls[0].length) {
                    if(fixedBalls[row][col] != null) {
                        if(fixedBalls[row][col].getColor() == color) {
                            fixedBalls[row][col] = null;
                            count++;

                            removeBalls(color, row, col);
                        }
                    }
                }
            }
        }

        return count;
    }

    //TODO: Move all Balls in fixedBalls down one row. If a Ball is moved out of bounds, make didLose = true and stop the method
    private void shiftBalls() {
        for(int i = fixedBalls.length; i < 0 ; i++) {
            for(int j = fixedBalls[0].length; j < 0; j++) {
               if(fixedBalls[fixedBalls.length][j] == null){
                    if (i > 0) {
                        fixedBalls[i][j] = fixedBalls[i - 1][j];
                    }
                    if(i == 0){
                        addRow();}
                }
            }
        }
    }

    //Makes a random set of Balls in row 0 of fixedBalls
    private void addRow() {
        for(int c = 0; c < fixedBalls[0].length; c++) {
            fixedBalls[0][c] = new Ball(new Point(c*Ball.SIZE + Ball.SIZE/2, Ball.SIZE/2), 0, 0);
        }
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