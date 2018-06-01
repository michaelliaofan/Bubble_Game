import java.awt.*;

public class Ball {
    public static final int SIZE = 50;

    private Point position;
    private Color color;

    private int dx, dy;

    public Ball(Point position, Color color, int dx, int dy) {
        this.position = position;
        this.color = color;

        this.dx = dx;
        this.dy = dy;
    }

    public Ball(Point position, int dx, int dy) {
        this.position = position;

        //Randomize this color! (only red, green, or blue for now)
        //this.color = Color.RED;
        int num = (int)(Math.random() * 3);
        if(num == 0) {
            this.color = Color.RED;
        } else if(num == 1) {
            this.color = Color.GREEN;
        } else if(num == 2) {
            this.color = Color.BLUE;
        }

        this.dx = dx;
        this.dy = dy;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fillOval((int)(position.getX() - SIZE/2), (int)(position.getY() - SIZE/2), SIZE, SIZE);
    }

    public Rectangle getBoundingRectangle() {
         return new Rectangle((int)(position.getX() - SIZE/2), (int)(position.getY()-SIZE/2), SIZE, SIZE);
    }

    private void updatePosition() {
        this.position.translate(dx, dy);
    }

    //Updates the Ball's direction if the Ball moves off screen
    private void updateDirection(int width, int height) {
        if(position.x <= 0  || position.x + SIZE >= width) {
            dx = - dx;
        } else if(position.y + SIZE >= height || position.y <= 0) {
            dy = - dy;
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
