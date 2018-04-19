package Models;

import Utils.Constants;
import Utils.Vector2;
import dwon.SpriteManager.SpriteManager;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class Bauss implements Constants {
    private Vector2 pos;
    private int tick;
    private int itemState;
    private List<Integer> itemTimes = new ArrayList<>();

    public Bauss() {
        pos = new Vector2(WINDOW_WIDTH / 2, WINDOW_HEIGHT - 100);
    }

    public void update() {
        for (int i = 0; i < itemTimes.size(); i++) {
            if (itemTimes.get(i) == 30 * 30) {
                itemState = 0;
                itemTimes.remove(i);
            } else {
                int time = itemTimes.get(i) + 1;
                itemTimes.set(i, time);
            }
        }
    }

    public void render(PApplet pApplet) {
        tick++;

        if (itemState == ITEM_LASER) {
            pApplet.image(SpriteManager.getImage(BAUSS_LASER, tick / 10 % 6), pos.getX() - BAUSS_WIDTH / 2, pos.getY() - BAUSS_HEIGHT / 2, BAUSS_WIDTH, BAUSS_HEIGHT);
        } else if (itemState == ITEM_EXTEND) {
            pApplet.image(SpriteManager.getImage(BAUSS_EXTEND, tick / 10 % 6), pos.getX() - BAUSS_WIDTH / 2, pos.getY() - BAUSS_HEIGHT / 2, BAUSS_WIDTH * 2, BAUSS_HEIGHT);
        } else
            pApplet.image(SpriteManager.getImage(BAUSS_NORMAL, tick / 10 % 6), pos.getX() - BAUSS_WIDTH / 2, pos.getY() - BAUSS_HEIGHT / 2, BAUSS_WIDTH, BAUSS_HEIGHT);
    }


    public void setItemState(int itemState) {
        this.itemState = itemState;
        itemTimes.add(0);
    }

    public void moveLeft() {
        if (MARGIN_HORIZONTAL <= pos.getX() - BAUSS_WIDTH / 2)
            pos.setX(pos.getX() - BAUSS_SPEED);
    }

    public void moveRight() {
        if (WINDOW_WIDTH - MARGIN_HORIZONTAL >= pos.getX() + BAUSS_WIDTH / 2)
            pos.setX(pos.getX() + BAUSS_SPEED);
    }

    public Vector2 getPos() {
        return pos;
    }

}