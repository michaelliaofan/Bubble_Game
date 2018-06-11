import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.InputStream;

public class BubbleGame extends JPanel {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;

    private Ball[][] fixedBalls;
    private Ball nextBall;

    private boolean wasCounted[][];

    private Timer timer;

    private boolean didLose;

    private int shotsUntilShift;

    public BubbleGame() {
        setSize(WIDTH, HEIGHT);

        fixedBalls = new Ball[(HEIGHT-24) / Ball.SIZE][WIDTH / Ball.SIZE];

        for(int r = 1; r < 4; r++) {
            for(int c = 1; c < fixedBalls[0].length - 1; c++) {
                fixedBalls[r][c] = new Ball(new Point(c*Ball.SIZE + Ball.SIZE/2, r*Ball.SIZE + Ball.SIZE/2), 0, 0);
            }
        }

        nextBall = new BouncingBall(new Point(WIDTH/2, HEIGHT - 24 - Ball.SIZE), 0, 0);

        wasCounted = new boolean[fixedBalls.length][fixedBalls[0].length];

        shotsUntilShift = 5;

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
                    Point initCenter = new Point(WIDTH/2, HEIGHT - 24 - Ball.SIZE);

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
                    nextBall.update();
                }

                //Checks for collisions
                outer:
                for(int r = 0; r < fixedBalls.length ; r++) {
                    for(int c = 0; c < fixedBalls[0].length; c++) {
                        if(fixedBalls[r][c] != null && nextBall != null) {
                            if(nextBall.distanceTo(fixedBalls[r][c]) <= Ball.SIZE) {
                                //Bouncing ball
                                if(nextBall instanceof BouncingBall) {
                                    fixedBalls[r][c] = null;

                                    ((BouncingBall) nextBall).bounce();

                                    if(((BouncingBall) nextBall).getNumBouncesRemaining() <= 0) {
                                        ((BouncingBall) nextBall).setNumBouncesRemaining(3);
                                        resetNextBall();
                                    }
                                }

                                //Normal ball
                                else {
                                    int r1 = (int)(nextBall.getCenter().getY()/Ball.SIZE);
                                    int c1 = (int)(nextBall.getCenter().getX()/Ball.SIZE);

                                    fixedBalls[r1][c1] = new Ball(new Point(c1*Ball.SIZE + Ball.SIZE/2, r1*Ball.SIZE + Ball.SIZE/2), nextBall.getColor(), nextBall.getShadow(), 0, 0);

                                    resetNextBall();

                                    if(countBalls(fixedBalls[r1][c1].getColor(), r1, c1) > 2) {
                                        removeBalls();
                                    }

                                    clearWasCounted();

                                    shotsUntilShift--;

                                    if(shotsUntilShift == 0) {
                                        shotsUntilShift = 5;
                                        shiftBalls();
                                    }

                                    break outer;
                                }
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

        wasCounted[r][c] = true;

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
                if(fixedBalls[r][c].getColor().equals(color)) {
                    if(!wasCounted[r][c]) {
                        total++;

                        wasCounted[r][c] = true;

                        total = countBalls(color, r-1, c-1, total);
                        total = countBalls(color, r, c-1, total);
                        total = countBalls(color, r+1, c-1, total);

                        total = countBalls(color, r-1, c, total);
                        total = countBalls(color, r+1, c, total);

                        total = countBalls(color, r-1, c+1, total);
                        total = countBalls(color, r, c+1, total);
                        total = countBalls(color, r+1, c+1, total);                    }
                }
            }
        }

        return total;
    }

    private int removeBalls() {
        int total = 0;

        for(int r = 0; r < wasCounted.length ; r++) {
            for(int c = 0; c < wasCounted[0].length; c++) {
                if(wasCounted[r][c]) {
                    fixedBalls[r][c] = null;
                    try {
                        String hop = "Sounds/cork_pop_x.wav";
                        InputStream in = new FileInputStream(hop);
                        AudioStream audioStream = new AudioStream(in);

                        AudioPlayer.player.start(audioStream);
                    }catch(Exception e){
                        e.printStackTrace();
                        System.out.println("Error loading sound file.");
                    }

                    total++;
                }
            }
        }

        return total;
    }

    private void clearWasCounted() {
        for(int r = 0; r < wasCounted.length ; r++) {
            for(int c = 0; c < wasCounted[0].length; c++) {
                wasCounted[r][c] = false;
            }
        }
    }

    private void shiftBalls() {
        for(int c = 0; c < fixedBalls[0].length; c++) {
            if(fixedBalls[fixedBalls.length - 1][c] != null) {
                didLose = true;
                return;
            }
        }

        for(int r = fixedBalls.length - 1; r > 0 ; r--) {
            for(int c = fixedBalls[0].length - 1; c >= 0; c--) {
                if(r > 1) {
                    if(fixedBalls[r][c] == null) {
                        fixedBalls[r][c] = new Ball(r, c);
                    }

                    if(fixedBalls[r - 1][c] == null) {
                        fixedBalls[r][c] = null;
                    } else {
                        moveBall(fixedBalls[r - 1][c], fixedBalls[r][c]);
                    }
                }

                if(r == 1) {
                    fixedBalls[r][c] = null;
                }
            }
        }

        addRow();
    }

    //Makes a random set of Balls in row 1 of fixedBalls
    private void addRow() {
        for(int c = 1; c < fixedBalls[0].length - 1; c++) {
            fixedBalls[1][c] = new Ball(new Point(c*Ball.SIZE + Ball.SIZE/2, Ball.SIZE * 3/2), 0, 0);
        }
    }

    private void moveBall(Ball from, Ball to) {
        to.setColor(from.getColor());
        to.setShadow(from.getShadow());
        to.setCenter(new Point.Double(from.getCenter().getX(), from.getCenter().getY() + Ball.SIZE));

        from = null;
    }

    private void resetNextBall() {
        nextBall.setCenter(new Point.Double(WIDTH/2, HEIGHT-24 - Ball.SIZE));
        nextBall.setVelocity(0, 0);
        nextBall.randomizeColor();
    }

    private boolean win() { //edit this is the win condition
        for(int r = 0; r<fixedBalls.length; r++){
            for(int c = 0; c<fixedBalls[0].length; c++){
                if(fixedBalls[r][c] != null){
                    return false;
                }
            }
        }

        return true;
    }

    private boolean lose() { //edit this is the lose condition
        return false;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(new Color(34, 35, 32));
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.setColor(new Color (255, 176, 221));
        g2.fillRect(800, 0, 200, 800);

        //g2.

        for (int i = 0; i < fixedBalls.length; i++) {
            for (int j = 0; j < fixedBalls[0].length; j++) {
                if (fixedBalls[i][j] != null) {
                    fixedBalls[i][j].draw(g2);
                }
            }
        }

        if (nextBall != null) {
            nextBall.draw(g2);
        }

        if (win() == true) {
            Font myFont = new Font("Impact", Font.ITALIC | Font.BOLD, 100);
            g2.setFont(myFont);
            g2.setColor(Color.BLACK);
            g2.drawString("YOU WON!", 200, 400);
            Font myFont2 = new Font("Impact", Font.ITALIC | Font.BOLD, 90);
            g2.setFont(myFont2);
            g2.setColor(Color.WHITE);
            g2.drawString("YOU  WON !", 200, 388);
//            try {
//                String hop = "Sounds/cheer_long.wav";
//                InputStream in = new FileInputStream(hop);
//                AudioStream audioStream = new AudioStream(in);
//
//                AudioPlayer.player.start(audioStream);
//            }catch(Exception e){
//                e.printStackTrace();
//                System.out.println("Error loading sound file.");
//            }
        }

        if (didLose) {
            Font myFont = new Font("Impact", Font.ITALIC | Font.BOLD, 100);
            g2.setFont(myFont);
            g2.setColor(Color.BLACK);
            g2.drawString("YOU LOSE!", 200, 400);
            Font myFont2 = new Font("Impact", Font.ITALIC | Font.BOLD, 90);
            g2.setFont(myFont2);
            g2.setColor(Color.WHITE);
            g2.drawString("YOU  LOSE !", 200, 388);
//            try {
//                String hop = "Sounds/Sad_Trombone-Joe_Lamb-665429450.wav";
//                InputStream in = new FileInputStream(hop);
//                AudioStream audioStream = new AudioStream(in);
//
//                AudioPlayer.player.start(audioStream);
//            } catch(Exception e){
//                e.printStackTrace();
//                System.out.println("Error loading sound file.");
//            }
//        }
        }
    }
    //Main - no need to change
    public static void main(String[] args) {
        JFrame frame = new JFrame("Bubble Game!");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT+24));

        JPanel panel = new BubbleGame();
        panel.setFocusable(true);
        panel.grabFocus();

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
