package Models;

import Utils.Constants;
import Utils.Vector2;
import dwon.SpriteManager.SpriteManager;
import processing.core.PApplet;

public class Item extends View implements Constants {
    private int state;
    private int tick;
    private boolean isCollision;

    public Item(Vector2 vector2, int state) {
        setPos(new Vector2(vector2.getX(), vector2.getY()));
        this.state = state;
        setWidth(ITEM_WIDTH);
        setHeight(ITEM_HEIGHT);
    }

    @Override
    public void update() {
        setPos(new Vector2(getPos().getX(), getPos().getY() + ITEM_SPEED));
    }

    @Override
    public void render(PApplet pApplet) {
        tick++;
        if (!isCollision)
            pApplet.image(SpriteManager.getImage(state, tick / 10 % 8),
                    getPos().getX() - ITEM_WIDTH / 2,
                    getPos().getY() - ITEM_HEIGHT / 2,
                    ITEM_WIDTH, ITEM_HEIGHT);
    }

    public int getState() {
        return state;
    }

    public void setCollision() {
        this.isCollision = true;
    }
}


