package Models;

import Utils.Vector2;
import processing.core.PApplet;

public class LaserBall  {
    private Vector2 pos;

    public LaserBall(float x, float y) {
        pos = new Vector2(x, y);
    }

    public void update() {
        pos.setY(pos.getY() - 2);
    }

    public void render(PApplet pApplet) {
        pApplet.fill(255, 0, 0);
        pApplet.ellipse(pos.getX(), pos.getY(), 5, 5);
    }

    public Vector2 getPos() {
        return pos;
    }
}
