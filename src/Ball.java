import java.awt.*;

public class Ball {
    public static final int SIZE = 50;

    private Point.Double center;
    private Color color;

    private double dx, dy;

    public Ball(Point center, Color color, double dx, double dy) {
        this.center = new Point.Double(center.x, center.y);

        this.dx = dx;
        this.dy = dy;

        this.color = color;
    }

    public Ball(Point center, double dx, double dy) {
        this.center = new Point.Double(center.x, center.y);

        this.dx = dx;
        this.dy = dy;

        int num = (int)(Math.random() * 5);
        if(num == 0) {
            Color red = new Color (255, 119, 144);
            this.color = red;
        } else if(num == 1) {
            Color green = new Color(128, 245, 166);
            this.color = green;
        } else if(num == 2) {
            Color blue = new Color (181, 250, 255);
            this.color = blue;
        } else if(num == 3) {
            Color purple = new Color(251, 180, 255);
            this.color = purple;
        } else if(num == 4){
            Color yellow = new Color(255, 248, 0);
            this.color = yellow;
        }

    }

    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fillOval((int)(center.getX() - SIZE/2), (int)(center.getY() - SIZE/2), SIZE, SIZE);
    }

    //Calculates the distance between this Ball's center and another Ball's center
    public double distanceTo(Ball other) {
        double dx = (Math.abs(this.getCenter().getX() - other.getCenter().getX()));
        double dy = (Math.abs(this.getCenter().getY() - other.getCenter().getY()));
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

    //Updates the Ball's center per frame
    private void updatePosition() {
        this.center.setLocation(this.center.getX()+dx, this.center.getY()+dy);
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

    public void randomizeColor() {
        int num = (int)(Math.random() * 3);
        if(num == 0) {
            this.color = Color.RED;
        } else if(num == 1) {
            this.color = Color.GREEN;
        } else if(num == 2) {
            this.color = Color.BLUE;
        }
    }

    //Getters/setters - no need to change
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public Point.Double getCenter() {
        return center;
    }
    public void setCenter(Point.Double center) {
        this.center = center;
    }
    public void setVelocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }
    public double getVelocity() {
        return Math.sqrt(Math.pow(this.dx, 2) + Math.pow(this.dy, 2));
    }
}
