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
        int num = (int)(Math.random() * 3);
        if(num == 0){
            this.color = Color.RED;
        }
        if(num == 1){
            this.color = Color.GREEN;
        }
        if(num == 2){
            this.color = Color.BLUE;
        }

        this.speed = 10;
        this.direction = direction;
        //test
    }

    //TODO: Draws a circle centered at the Ball's position, with the Ball's color. diameter = size
    public void draw(Graphics2D g2) {
        g2.drawOval(0, 0, 0, 0);
    }

    public Rectangle getBoundingRectangle() {
         return new Rectangle((int)(position.getX() - SIZE/2), (int)(position.getY()-SIZE/2), SIZE, SIZE);
    }


    private void updatePosition() {
        //dx and dy are the changes in x and y
        //Math.cos() and Math.sin() are a thing -- THEY TAKE RADIANS!!!
        //YOU NEED TO USE Math.toRadians() to turn a degree measurement into radians
        int dx = (int)(Math.cos(Math.toRadians(direction)) * speed);
        int dy = (int)(Math.sin(Math.toRadians(direction))* speed);

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

    //Getters/setters - no need to change
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public Point getPosition() {
        return position;
    }
    public void setPosition(Point position) {
        this.position = position;
    }
}
