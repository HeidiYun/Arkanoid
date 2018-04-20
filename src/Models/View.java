package Models;

import Utils.Constants;
import Utils.Vector2;
import processing.core.PApplet;

public abstract class View implements Constants {
    private Vector2 pos;
    private Vector2 direction;
    private Vector2 velocity;
    private float speed;
    private int width;
    private int height;

    public View() {
    }

    public abstract void update();
    public abstract void render(PApplet pApplet);

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public void invertX() {
        setDirection(new Vector2(getDirection().getX() * -1, getDirection().getY()));
    }

    public void invertY() {
        setDirection(new Vector2(getDirection().getX(),getDirection().getY() * -1));
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
