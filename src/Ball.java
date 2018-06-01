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
        } else if(num == 1){
            this.color = Color.GREEN;
        } else if(num == 2){
            this.color = Color.BLUE;
        }

        this.speed = 10;
        this.direction = direction;
        //test
    }

    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fillOval((int)(position.getX() - SIZE/2), (int)(position.getY() - SIZE/2), SIZE, SIZE);
    }

    public Rectangle getBoundingRectangle() {
         return new Rectangle((int)(position.getX() - SIZE/2), (int)(position.getY()-SIZE/2), SIZE, SIZE);
    }

    private void updatePosition() {
        int dx = (int)(Math.cos(Math.toRadians(direction)) * speed);
        int dy = (int)(Math.sin(Math.toRadians(direction)) * speed);

        this.position.translate(dx, dy);
    }

    //Updates the Ball's direction if the Ball moves off screen
    private void updateDirection(int width, int height) {
        if(position.x <= 0 || position.y <= 0 || position.x + SIZE >= width || position.y + SIZE >= height) {

        }
    }

    public void update(int width, int height) {
        updatePosition();
        updateDirection(width, height);
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
