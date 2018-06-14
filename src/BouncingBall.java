import java.awt.*;

public class BouncingBall extends Ball {
    private int numBouncesRemaining;

    public BouncingBall(int r, int c) {
        super(r, c);

        numBouncesRemaining = 3;
    }

    public BouncingBall(Point center, Color color, Color shadow, double dx, double dy) {
        super(center, color, shadow, dx, dy);

        numBouncesRemaining = 3;
    }

    public BouncingBall(Point center, double dx, double dy) {
        super(center, dx, dy);

        numBouncesRemaining = 3;
    }

    public void bounce(Point.Double otherCent) {
        double dx = otherCent.getX() - getCenter().getX();
        double dy = otherCent.getY() - getCenter().getY();

        if(Math.abs(dx - dy) / Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)) <= 0.1) {
            this.dx = -this.dx;
            this.dy = -this.dy;
        } else if(dx > dy) {
            this.dy = -this.dy;
        } else {
            this.dx = -this.dx;
        }

        this.update();
        this.update();

        numBouncesRemaining--;
    }

    public int getNumBouncesRemaining() {
        return numBouncesRemaining;
    }
    public void setNumBouncesRemaining(int numBouncesRemaining) {
        this.numBouncesRemaining = numBouncesRemaining;
    }
}
