package Models;

import Utils.Constants;
import Utils.Vector2;
import processing.core.PApplet;

public class Ball extends View implements Constants {


    public Ball(float x, float y) {
        setPos(new Vector2(x, y));
        setDirection(new Vector2(0, 0));
        setSpeedX(0);
        setSpeedY(0);
        setWidth(BALL_RADIUS * 2);
        setHeight(BALL_RADIUS * 2);
    }

    public void update() {
        checkWall();
        setVelocity(new Vector2(getSpeedX() * getDirection().getX(), getSpeedY() * getDirection().getY()));
        setPos(Vector2.add(getPos(), getVelocity()));
    }

    public void render(PApplet pApplet) {
        pApplet.fill(255);
        pApplet.ellipse(getPos().getX(), getPos().getY(), Constants.BALL_RADIUS * 2, Constants.BALL_RADIUS * 2);
    }

    public void checkWall() {
        if (getPos().getX() <= MARGIN_HORIZONTAL || getPos().getX() >= WINDOW_WIDTH - MARGIN_HORIZONTAL) {
            invertX();
        }
        if (getPos().getY() <= MARGIN_VERTICAL) {
            invertY();
        }

    }

}
