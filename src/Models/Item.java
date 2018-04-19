package Models;

import Utils.Constants;
import Utils.Vector2;
import dwon.SpriteManager.SpriteManager;
import processing.core.PApplet;

public class Item implements Constants {
    private Vector2 pos;
    private int state;
    private int tick;
    private boolean isCollision;

    public Item(Vector2 vector2, int state) {
        this.pos = new Vector2(vector2.getX(), vector2.getY());
        this.state = state;
    }

    public void update() {
        pos.setY((pos.getY() + ITEM_SPEED));
    }

    public void render(PApplet pApplet) {
        tick++;
        if (!isCollision)
            pApplet.image(SpriteManager.getImage(state, tick / 10 % 8),
                    pos.getX() - ITEM_WIDTH / 2,
                    pos.getY() - ITEM_HEIGHT / 2,
                    ITEM_WIDTH, ITEM_HEIGHT);
    }

    public Vector2 getPos() {
        return pos;
    }

    public int getState() {
        return state;
    }

    public void setCollision() {
        this.isCollision = true;
    }
}


