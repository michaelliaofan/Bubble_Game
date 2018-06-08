import java.awt.*;

public class Ball {
    public static final int SIZE = 50;

    private Point.Double center;
    private Color color;
    private Color shadow;


    private double dx, dy;

    public Ball(int r, int c) {
        this.center = new Point.Double(c*Ball.SIZE + Ball.SIZE/2, r*Ball.SIZE + Ball.SIZE/2);

        this.dx = 0;
        this.dy = 0;

        this.color = new Color(0, 0, 0);
        System.out.println("hi");
    }

    public Ball(Point center, Color color, Color shadow, double dx, double dy) {
        this.center = new Point.Double(center.x, center.y);

        this.dx = dx;
        this.dy = dy;

        this.color = color;
        this.shadow = shadow;
    }

    public Ball(Point center, double dx, double dy) {
        this.center = new Point.Double(center.x, center.y);

        this.dx = dx;
        this.dy = dy;

        randomizeColor();
    }

    public void draw(Graphics2D g2) {
        g2.setColor(shadow);
        g2.fillOval((int)(center.getX() - SIZE/2), (int)(center.getY() - SIZE/2), SIZE, SIZE);
        g2.setColor(color);
        g2.fillOval((int)(center.getX() - SIZE/2) + 2, (int)(center.getY() - SIZE/2) + 2, SIZE-12, SIZE-12);
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
        int num = (int)(Math.random() * 5);
        if(num == 0) {
            Color red = new Color (255, 119, 144);
            this.color = red;
            Color darkred = new Color (225, 117, 117);
            this.shadow = darkred;
        } else if(num == 1) {
            Color green = new Color(128, 245, 166);
            this.color = green;
            Color darkgreen = new Color(108, 215, 137);
            this.shadow = darkgreen;
        } else if(num == 2) {
            Color blue = new Color (147, 248, 255);
            this.color = blue;
            Color darkblue = new Color (122, 223, 230);
            this.shadow = darkblue;
        } else if(num == 3) {
            Color purple = new Color(251, 180, 255);
            this.color = purple;
            Color darkpurple = new Color(226, 158, 230);
            this.shadow = darkpurple;
        } else if(num == 4){
            Color yellow = new Color(255, 248, 0);
            this.color = yellow;
            Color darkyellow = new Color(245, 238, 0);
            this.shadow = darkyellow;
        }
    }

    //Getters/setters - no need to change
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }

    public Color getShadow() {
        return shadow;
    }
    public void setShadow(Color shadow) {
        this.shadow = shadow;
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
