package Models;

import Utils.Constants;
import Utils.Vector2;
import processing.core.PApplet;

public class Laser extends View implements Constants {
    private boolean overHeight;

    public Laser(float x, float y) {
        setPos(new Vector2(x, y));
        setWidth(LASER_RADIUS * 2);
        setHeight(LASER_RADIUS * 2);
    }

    public void update() {
        if (getPos().getY() < MARGIN_VERTICAL)
            overHeight = true;
        getPos().setY(getPos().getY() - LASER_SPEED);
    }


    public void render(PApplet pApplet) {
        pApplet.fill(255, 0, 0);
        pApplet.ellipse(getPos().getX(), getPos().getY(), getWidth(), getHeight());

    }

    public boolean getOverHeight() {
        return overHeight;
    }

}