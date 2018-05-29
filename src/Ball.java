import java.awt.*;

public class Ball {
    public static final int SIZE = 50;

    private Point position;
    private Color color;

    //Current angle of Ball's motion in degrees; EAST is 0 degrees; counter-clockwise rotation is positive rotation
    private int direction;

    //Speed of Ball
    private int speed;

    public Ball(Point position, Color color, int direction) {
        this.position = position;
        this.color = color;

        this.speed = 10;
        this.direction = direction;
    }

    //TODO: If a new Ball is created with no color parameter, make the Ball a random color (Red, Green, Blue)
    public Ball(Point position, int direction) {
        this.position = position;

        //Randomize this color! (only red, green, or blue for now)
        this.color = Color.RED;

        this.speed = 10;
        this.direction = direction;
    }

    //TODO: Draws a circle centered at the Ball's position, with the Ball's color. diameter = size
    public void draw(Graphics2D g2) {
        g2.drawOval(0, 0, 0, 0);
    }

    //TODO: Returns a square centered at the Ball's position, with side length = size
    public Rectangle getBoundingRectangle() {
        return new Rectangle();
    }

    //TODO: Update the Ball's position based on its speed and direction (need some trig...ANTHONY)
    private void updatePosition() {
        //dx and dy are the changes in x and y
        //Math.cos() and Math.sin() are a thing -- THEY TAKE RADIANS!!!
        //YOU NEED TO USE Math.toRadians() to turn a degree measurement into radians
        int dx = 0;
        int dy = 0;

        //Moves the current position by the calculated changes, dx and dy
        this.position.translate(dx, dy);
    }

    //Updates the Ball's direction if the Ball moves off screen
    private void updateDirection() {
    }

    public void update() {
        updatePosition();
        updateDirection();
    }

    //Getters - no need to change
    public Color getColor() {
        return color;
    }
    public Point getPosition() {
        return position;
    }
}
