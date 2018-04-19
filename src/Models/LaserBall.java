package Models;

import Utils.Constants;
import Utils.Vector2;
import processing.core.PApplet;

public class LaserBall extends View implements Constants {
    private boolean overHeight;

    public LaserBall(float x, float y) {
        setPos(new Vector2(x, y));

    }

    public void update() {
        if (getPos().getY() < MARGIN_VERTICAL)
            overHeight = true;
        getPos().setY(getPos().getY() - LASERBALL_SPEED);
    }


    public void render(PApplet pApplet) {
        pApplet.fill(255, 0, 0);
        pApplet.ellipse(getPos().getX(), getPos().getY(), 5, 5);

    }

    public boolean getOverHeight() {
        return overHeight;
    }

}