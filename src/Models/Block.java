package Models;

import Utils.Constants;
import Utils.Util;
import Utils.Vector2;
import dwon.SpriteManager.SpriteManager;
import processing.core.PApplet;

public class Block extends View implements Constants {
    private int item;
    private int color;
    private int life;
    private boolean isDestroyed;

    public Block(int x, int y, int color, int item, int life) {
//        this.pos = new Utils.pos2(x, y);
        setPos(Util.indexToCoord(x, y));
        this.color = color;
        this.item = item;
        this.life = life;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(PApplet pApplet) {
        if (!isDestroyed && color != 0)
            pApplet.image(SpriteManager.getImage(color, 0),
                    getPos().getX() - Constants.BLOCK_WIDTH / 2,
                    getPos().getY() - Constants.BLOCK_HEIGHT / 2,
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
    
    public int getItem() {
        return item;
    }
}
