import java.awt.*;

public class Ball {
    public static final int SIZE = 50;

    private Point center;
    private Color color;

    private int dx, dy;

    public Ball(Point center, Color color, int dx, int dy) {
        this.center = center;

        this.dx = dx;
        this.dy = dy;

        this.color = color;
    }

    public Ball(Point center, int dx, int dy) {
        this.center = center;

        this.dx = dx;
        this.dy = dy;

        int num = (int)(Math.random() * 3);
        if(num == 0) {
            this.color = Color.RED;
        } else if(num == 1) {
            this.color = Color.GREEN;
        } else if(num == 2) {
            this.color = Color.BLUE;
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fillOval((int)(center.getX() - SIZE/2), (int)(center.getY() - SIZE/2), SIZE, SIZE);
    }

//    public Rectangle getBoundingRectangle() {
//         return new Rectangle((int)(center.getX() - SIZE/2), (int)(center.getY()-SIZE/2), SIZE, SIZE);
//    }

    //TODO: Calculate the distance between this Ball's center and another Ball's center
    public double distanceTo(Ball other) {
        //This ball's center is given by this.center
        //Ball other's center is given by other.getCenter()

        return 0;
    }

    //Updates the Ball's center per frame
    private void updatePosition() {
        this.center.translate(dx, dy);
    }

    //Updates the Ball's direction if the Ball moves off screen
    private void updateDirection(int width, int height) {
        if(center.x<= 0 || center.x + SIZE >= width) {
            dx = - dx;
        } else if(center.y + SIZE >= height || center.y <= 0) {
            dy = - dy;
        }
    }

    //Updates the Ball's direction and center
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
    public Point getCenter() {
        return center;
    }
    public void setCenter(Point center) {
        this.center = center;
    }
    public void setVelocity(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }
}
