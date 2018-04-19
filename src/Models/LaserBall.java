package Models;

import Utils.Vector2;
import processing.core.PApplet;

public class LaserBall extends View {

    public LaserBall(float x, float y) {
        setPos(new Vector2(x, y));
    }

    public void update() {
        getPos().setY(getPos().getY() - 2);
    }

    public void render(PApplet pApplet) {
        pApplet.fill(255, 0, 0);
        pApplet.ellipse(getPos().getX(), getPos().getY(), 5, 5);
    }

}