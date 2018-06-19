import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import javax.swing.JOptionPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

public class BubbleGame extends JPanel {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;

    private Ball[][] fixedBalls;
    private Ball nextBall;
    private int points;

    private boolean wasCounted[][];

    private Timer timer;

    private boolean didLose;
    private boolean didWin;

    private int timeUntilShift;

    private boolean isIndented;

    public BubbleGame() {
        setSize(WIDTH, HEIGHT);

        fixedBalls = new Ball[HEIGHT / Ball.SIZE][HEIGHT / Ball.SIZE];

        isIndented = false;

        for(int r = 1; r < 4; r++) {
            for(int c = 1; c < fixedBalls[0].length - 1; c++) {
                if(isIndented) {
                    fixedBalls[r][c] = new Ball(new Point(c*Ball.SIZE + Ball.SIZE, r*Ball.SIZE + Ball.SIZE/2), 0, 0, isIndented);
                } else {
                    fixedBalls[r][c] = new Ball(new Point(c*Ball.SIZE + Ball.SIZE/2, r*Ball.SIZE + Ball.SIZE/2), 0, 0, isIndented);
                }
            }

            isIndented = !isIndented;
        }

        nextBall = new Ball(new Point(WIDTH/2, HEIGHT - 24 - Ball.SIZE), 0, 0, false);

        wasCounted = new boolean[fixedBalls.length][fixedBalls[0].length];

        didLose = false;
        didWin = false;

        timeUntilShift = 3000;

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(timer.isRunning()) {
                    if (-0.1 <= nextBall.getVelocity() && nextBall.getVelocity() <= 0.1) {
                        Point initCenter = new Point(WIDTH / 2, HEIGHT - 24 - Ball.SIZE);

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
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                    pause();
                } else if(e.getKeyCode() == KeyEvent.VK_1) {
                    nextBall = new Ball(0, 0);
                    resetNextBall();
                } else if(e.getKeyCode() == KeyEvent.VK_2) {
                    nextBall = new BouncingBall(0, 0);
                    resetNextBall();
                }
                else if(e.getKeyCode() == KeyEvent.VK_I){
                    pause();
                    JOptionPane.showMessageDialog(null, "press space to pause & press 1 for regular ball & press 2 for bouncing ball & press r to reset", "Controls", JOptionPane.INFORMATION_MESSAGE);
                    run();
                }

                else if(e.getKeyCode() == KeyEvent.VK_R){
                    fixedBalls = new Ball[HEIGHT / Ball.SIZE][HEIGHT / Ball.SIZE];

                    isIndented = false;

                    for(int r = 1; r < 4; r++) {
                        for(int c = 1; c < fixedBalls[0].length - 1; c++) {
                            if(isIndented) {
                                fixedBalls[r][c] = new Ball(new Point(c*Ball.SIZE + Ball.SIZE/2, r*Ball.SIZE + Ball.SIZE/2), 0, 0, isIndented);
                            } else {
                                fixedBalls[r][c] = new Ball(new Point(c*Ball.SIZE + Ball.SIZE, r*Ball.SIZE + Ball.SIZE/2), 0, 0, isIndented);
                            }
                        }

                        isIndented = !isIndented;
                    }

                    nextBall = new Ball(new Point(WIDTH/2, HEIGHT - 24 - Ball.SIZE), 0, 0, false);

                    wasCounted = new boolean[fixedBalls.length][fixedBalls[0].length];

                    didLose = false;
                    didWin = false;

                    timeUntilShift = 3000;
                } else if(e.getKeyCode() == KeyEvent.VK_D) {
                    pause();

                    System.out.println();

                    for(Ball[] row: fixedBalls) {
                        System.out.println(Arrays.toString(row));
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nextBall != null) {
                    nextBall.update();
                }

                handleCollisions();

                timeUntilShift -= 10;

                if(timeUntilShift <= 0) {
                    timeUntilShift = 3000;
                    shiftBalls();
                }

                repaint();
            }
        });

        timer.start();

        didLose = false;
        didWin = false;
    }

    private int countBalls(Color color, int r, int c) {
        int total = 1;

        wasCounted[r][c] = true;

        total = countBalls(color, r, c-1, total);
        total = countBalls(color, r, c+1, total);

        if(fixedBalls[r][c].isIndented()) {
            total = countBalls(color, r+1, c, total);
            total = countBalls(color, r+1, c+1, total);

            total = countBalls(color, r-1, c, total);
            total = countBalls(color, r-1, c+1, total);
        } else {
            total = countBalls(color, r+1, c-1, total);
            total = countBalls(color, r+1, c, total);

            total = countBalls(color, r-1, c-1, total);
            total = countBalls(color, r-1, c, total);
        }

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

                    } catch(Exception e){
                        e.printStackTrace();
                        System.out.println("Error loading sound file.");
                    }
                    wincheck();

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

    private void addRow() {
        for(int c = 1; c < fixedBalls[0].length - 1; c++) {
            if(isIndented) {
                fixedBalls[1][c] = new Ball(new Point(c*Ball.SIZE + Ball.SIZE, Ball.SIZE * 3/2), 0, 0, isIndented);
            } else {
                fixedBalls[1][c] = new Ball(new Point(c*Ball.SIZE + Ball.SIZE/2, Ball.SIZE * 3/2), 0, 0, isIndented);
            }
        }

        isIndented = !isIndented;
    }

    private void moveBall(Ball from, Ball to) {
        to.setColor(from.getColor());
        to.setShadow(from.getShadow());
        to.setIndented(from.isIndented());
        to.setCenter(new Point.Double(from.getCenter().getX(), from.getCenter().getY() + Ball.SIZE));

        from = null;
    }

    private void resetNextBall() {
        nextBall.setCenter(new Point.Double(WIDTH/2, HEIGHT-24 - Ball.SIZE));
        nextBall.setVelocity(0, 0);
        nextBall.randomizeColor();
    }

    private void wincheck() { //edit this is the win condition
        didWin = true;

        for(int r = 0; r<fixedBalls.length; r++){
            for(int c = 0; c<fixedBalls[0].length; c++){
                if(fixedBalls[r][c] != null){
                    didWin = false;
                }
            }
        }

    }

    private void handleCollisions() {
        outer:
        for(int r = 0; r < fixedBalls.length ; r++) {
            for(int c = 0; c < fixedBalls[0].length; c++) {
                if(fixedBalls[r][c] != null && nextBall != null) {
                    if(nextBall.distanceTo(fixedBalls[r][c]) <= Ball.SIZE) {
                        //Bouncing ball
                        if(nextBall instanceof BouncingBall) {
                            ((BouncingBall) nextBall).bounce(fixedBalls[r][c].getCenter());

                            fixedBalls[r][c] = null;

                            if(((BouncingBall) nextBall).getNumBouncesRemaining() <= 0) {
                                ((BouncingBall) nextBall).setNumBouncesRemaining(3);
                                resetNextBall();
                            }
                        }

                        //Normal ball
                        else {
                            int r1 = (int)(nextBall.getCenter().getY()/Ball.SIZE);
                            int c1 = (int)(nextBall.getCenter().getX()/Ball.SIZE);

                            if(fixedBalls[r][c].isIndented()) {
                                if(r1 == r) {
                                    fixedBalls[r1][c1] = new Ball(new Point(c1*Ball.SIZE + Ball.SIZE, r1*Ball.SIZE + Ball.SIZE/2), nextBall.getColor(), nextBall.getShadow(), 0, 0, true);
                                } else {
                                    fixedBalls[r1][c1] = new Ball(new Point(c1*Ball.SIZE + Ball.SIZE/2, r1*Ball.SIZE + Ball.SIZE/2), nextBall.getColor(), nextBall.getShadow(), 0, 0, false);
                                }
                            } else {
                                if(r1 == r) {
                                    fixedBalls[r1][c1] = new Ball(new Point(c1*Ball.SIZE + Ball.SIZE/2, r1*Ball.SIZE + Ball.SIZE/2), nextBall.getColor(), nextBall.getShadow(), 0, 0, false);
                                } else {
                                    fixedBalls[r1][c1] = new Ball(new Point(c1*Ball.SIZE + Ball.SIZE, r1*Ball.SIZE + Ball.SIZE/2), nextBall.getColor(), nextBall.getShadow(), 0, 0, true);
                                }
                            }

                            resetNextBall();

                            if(countBalls(fixedBalls[r1][c1].getColor(), r1, c1) > 2) {
                                removeBalls();
                                points++;
                            }

                            clearWasCounted();
                        }

                        break outer;
                    }
                }
            }
        }
    }

    private void pause() {
        if(timer.isRunning())
            timer.stop();
        else
            timer.start();
    }

    private void run(){
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(new Color(34, 35, 32));
        g2.fillRect(0, 0, getWidth() +25, getHeight()); //big black square

        //g2.setColor(new Color (255, 176, 221));
        g2.fillRect(825, 0, 200, 800); //small black square


        g2.setColor(new Color(108, 204, 211));
        g2.fillRect(0, 0, 1025, 50);
        g2.fillRect(0, 0, 50, 800);
        g2.fillRect(0, 750, 1025, 50);
        g2.fillRect(775, 0, 50, 800); //
        g2.fillRect(1000, 0, 50, 800);

        g2.setColor(new Color(250, 251, 255));
        g2.setStroke(new BasicStroke(10));
        g2.drawRect(45, 45, 735, 710);
        g2.drawRect(825, 45, 175, 710); //

        Font font = new Font("Impact", Font.BOLD, 40);
        g2.setFont(font);
        g2.setColor(Color.WHITE);
        g2.drawString("THE", 883, 100); //+25
        g2.drawString("BUBBLE", 847, 150); //+50
        g2.drawString("GAME", 865, 200);

        Font thefont = new Font("Impact", Font.BOLD, 25);
        g2.setFont(thefont);
        g2.drawString("Points : " + points, 840, 260);
        g2.drawString("Press I", 840, 320);

        g2.setStroke(new BasicStroke(8));
        g2.drawRect(825, 217, 168, 1);



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

        if (didWin == true) {
            Font myFont = new Font("Impact", Font.ITALIC | Font.BOLD, 100);
            g2.setFont(myFont);
            g2.setColor(Color.BLACK);
            g2.drawString("YOU WON!", 200, 400);
            Font myFont2 = new Font("Impact", Font.ITALIC | Font.BOLD, 90);
            g2.setFont(myFont2);
            g2.setColor(Color.WHITE);
            g2.drawString("YOU  WON !", 200, 388);
            try {
                String hop = "Sounds/cheer_long.wav";
                InputStream in = new FileInputStream(hop);
                AudioStream audioStream = new AudioStream(in);

                AudioPlayer.player.start(audioStream);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error loading sound file.");
            }
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

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bubble Game!");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(WIDTH + 250, HEIGHT+24));

        JPanel panel = new BubbleGame();
        panel.setFocusable(true);
        panel.grabFocus();

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}