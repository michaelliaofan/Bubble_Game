import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class BubbleGame extends JPanel {
    private Ball[][] fixedBalls;
    private Ball nextBall;
    private int score;

    private boolean wasCounted[][];
    private boolean wasFlagged[][];

    private Timer timer;

    private boolean didLose;
    private int ballshiftcount;

    public BubbleGame(int w, int h) {
        setSize(w, h);

        fixedBalls = new Ball[(h-24) / Ball.SIZE][w / Ball.SIZE];
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < fixedBalls[0].length; c++) {
                fixedBalls[r][c] = new Ball(new Point(c*Ball.SIZE + Ball.SIZE/2, r*Ball.SIZE + Ball.SIZE/2), 0, 0);
            }
        }

        nextBall = new Ball(new Point(w/2, h-24 - Ball.SIZE), 0, 0);

        wasCounted = new boolean[fixedBalls.length][fixedBalls[0].length];
        wasFlagged = new boolean[fixedBalls.length][fixedBalls[0].length];

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

                                fixedBalls[r1][c1] = new Ball(new Point(c1*Ball.SIZE + Ball.SIZE/2, r1*Ball.SIZE + Ball.SIZE/2), nextBall.getColor(), 0, 0);

                                nextBall.setCenter(new Point.Double(w/2, h-24 - Ball.SIZE));
                                nextBall.setVelocity(0, 0);
                                nextBall.randomizeColor();

                                System.out.println("Count: " + countBalls(fixedBalls[r1][c1].getColor(), r1, c1));
//                                if(countBalls(fixedBalls[r1][c1].getColor(), r1, c1) >= 3) {
//                                    score += removeBalls(fixedBalls[r1][c1].getColor(), r1, c1);
//                                }
//                                //STOP
//                                shiftBalls();
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

    private int countBalls(Color color, int r, int c) {
        int total = 1;

        total = countBalls(color, r-1, c-1, total);
        total = countBalls(color, r, c-1, total);
        total = countBalls(color, r+1, c-1, total);

        total = countBalls(color, r-1, c, total);
        total = countBalls(color, r+1, c, total);

        total = countBalls(color, r-1, c+1, total);
        total = countBalls(color, r, c+1, total);
        total = countBalls(color, r+1, c+1, total);

        return total;
    }

    private int countBalls(Color color, int r, int c, int total) {
        if(0 <= r && r < fixedBalls.length && 0 <= c && c < fixedBalls[0].length) {
            if(fixedBalls[r][c] != null) {
                if(fixedBalls[r][c].getColor() == color) {
                    if(!wasCounted[r][c]) {
                        total++;

                        wasCounted[r][c] = true;
                    }
                }
            }
        }
        return total;
    }

//    private int countBalls(Color color, int r, int c) {
//        int count = 0;
//
//        if(fixedBalls[r][c] != null) {
//            System.out.println("NOT NULL");
//
//            if(fixedBalls[r][c].getColor() == color) {
//                System.out.println("RIGHT COLOR");
//
//                if(!wasCounted[r][c]) {
//                    System.out.println("COUNT++");
//
//                    count++;
//
//                    wasCounted[r][c] = true;
//
//                    count += countBalls(color, r-1, c-1);
//                    count += countBalls(color, r, c-1);
//                    count += countBalls(color, r+1, c-1);
//
//                    count += countBalls(color, r-1, c);
//                    count += countBalls(color, r+1, c);
//
//                    count += countBalls(color, r-1, c+1);
//                    count += countBalls(color, r, c+1);
//                    count += countBalls(color, r+1, c+1);
//                }
//            }
//        }
//
////        for(int row = r - 1; row <= r + 1; row++) {
////            for(int col = c - 1; col <= c + 1; col++) {
////                if(0 <= row && row < fixedBalls.length && 0 <= col && col < fixedBalls[0].length) {
////
////                }
////            }
////        }
//
//        System.out.println("RETURN");
//        return count;
//    }

    //Checks the balls immediately above and to the sides of the current ball. If any of them are the same color, remove them and call this method on them.
    private int removeBalls(Color color, int r, int c) {
        for(int row = r - 1; row <= r + 1; row++) {
            for(int col = c - 1; col <= c + 1; col++) {
                if(0 <= row && row < fixedBalls.length && 0 <= col && col < fixedBalls[0].length) {
                    if(fixedBalls[row][col] != null) {
                        if(fixedBalls[row][col].getColor() == color) {
                            fixedBalls[row][col] = null;

                            removeBalls(color, row, col);
                        }
                    }
                }
            }
        }

        return 1;
    }

    //TODO: Move all Balls in fixedBalls down one row. If a Ball is moved out of bounds, make didLose = true and stop the method
    private void shiftBalls() {

        for(int r = fixedBalls.length - 1; r >= 0 ; r--) {
            for(int c = fixedBalls[0].length - 1; c >= 0; c--) {
                if(fixedBalls[fixedBalls.length - 1][c] != null) {
                    didLose = true;
                    return;
                }

                if(r > 0) {
                    fixedBalls[r][c] = fixedBalls[r - 1][c];
                } else {
                    fixedBalls[r][c] = null;
                }
            }
        }

        addRow();
    }

    //Makes a random set of Balls in row 0 of fixedBalls
    private void addRow() {
        for(int c = 0; c < fixedBalls[0].length; c++) {
            fixedBalls[0][c] = new Ball(new Point(c*Ball.SIZE + Ball.SIZE/2, Ball.SIZE/2), 0, 0);
        }
    }

    public boolean win(){ //edit this is the win condition
        return false;
    }

    public boolean lose(){ //edit this is the lose condition
        return false;
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

        if(win() == true){
            Font myFont = new Font("Serif", Font.ITALIC | Font.BOLD, 30);
            g2.setFont(myFont);
            g2.setColor(Color.black);
            g2.drawString("YOU WIN", 400, 25);
        }
        if(lose() == true){
            Font myFont = new Font("Serif", Font.ITALIC | Font.BOLD, 30);
            g2.setFont(myFont);
            g2.setColor(Color.black);
            g2.drawString("YOU LOSE", 400, 25);
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