import java.awt.*;

public class Ball {
    //Red, green, or blue
    private Color color;

    private Point position;

    private int size;

    public Ball(Point position, Color color) {
        this.position = position;

        this.color = color;

        this.size = 50;
    }

    //TODO: If a new Ball is created with no color parameter, make the ball a random color (Red, Green, Blue)
    public Ball(Point position) {
        this.position = position;

        //Randomize this color!
        this.color = Color.BLACK;

        this.size = 50;
    }

    //TODO: Returns a square centered at the Ball's position, with side length = size
    public Rectangle getBoundingRectangle() {
        return new Rectangle();
    }

    //Getters - no need to change
    public Color getColor() {
        return color;
    }
    public Point getPosition() {
        return position;
    }
}
