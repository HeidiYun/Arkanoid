package Models;

import Utils.Vector2;
import processing.core.PApplet;

public abstract class View {
    private Vector2 pos;
    private Vector2 direction;
    private Vector2 velocity;
    private float speedX;
    private float speedY;

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

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
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
}
