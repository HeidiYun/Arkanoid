package Models;

import Utils.Constants;
import Utils.Util;
import Utils.Vector2;
import dwon.SpriteManager.SpriteManager;
import processing.core.PApplet;

public class Block implements Constants {
    private Vector2 pos;
    private int item;
    private int color;
    private int life;
    private boolean isDestroyed;

    public Block(int x, int y, int color, int item, int life) {
//        this.pos = new Utils.pos2(x, y);
        this.pos = Util.indexToCoord(x, y);
        this.color = color;
        this.item = item;
        this.life = life;
    }

    public void render(PApplet pApplet) {
        if (!isDestroyed && color != 0)
            pApplet.image(SpriteManager.getImage(color, 0),
                    pos.getX() - Constants.BLOCK_WIDTH / 2,
                    pos.getY() - Constants.BLOCK_HEIGHT / 2,
                    Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT);
    }

    public int getLife() {
        return life;
    }

    public void minusLife() {
        life -= 1;
    }

    public void destroy() {
        this.isDestroyed = true;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public Vector2 getPos() {
        return pos;
    }

    public int getItem() {
        return item;
    }
}
