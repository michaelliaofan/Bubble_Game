import java.awt.*;

public class Ball {
    public static final int SIZE = 50;

    private Color color;
    private Point position;

    //Current angle of Ball's motion in degrees; EAST is 0 degrees; counter-clockwise rotation is positive rotation
    private int direction;

    //Speed of Ball
    private int speed;

    public Ball(Point position, Color color) {
        this.position = position;

        this.color = color;
    }

    //TODO: If a new Ball is created with no color parameter, make the Ball a random color (Red, Green, Blue)
    public Ball(Point position) {
        this.position = position;

        //Randomize this color!
        this.color = Color.BLACK;
    }

    //TODO: Draws a circle centered at the Ball's position, with the Ball's color. Radius = size/2
    public void draw(Graphics2D g2) {
        g2.drawOval(0, 0, 0, 0);
    }

    //TODO: Returns a square centered at the Ball's position, with side length = size
    public Rectangle getBoundingRectangle() {
        return new Rectangle();
    }

    //TODO: Update the Ball's position based on its speed and direction (need some trig...ANTHONY)
    public void updatePosition() {
        this.position = new Point();
    }

    //Getters - no need to change
    public Color getColor() {
        return color;
    }
    public Point getPosition() {
        return position;
    }
}
