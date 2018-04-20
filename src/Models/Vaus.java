package Models;

import Utils.Constants;
import Utils.Vector2;
import dwon.SpriteManager.SpriteManager;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class Vaus extends View implements Constants {
    private int tick;
    private int itemState;
    private int wideWidth;
    private int playerLife = 2;
    private List<Integer> itemTimes = new ArrayList<>();


    public Vaus() {
        setPos(new Vector2(WINDOW_WIDTH / 2, WINDOW_HEIGHT - 100));
        setWidth(VAUS_WIDTH);
        setHeight(VAUS_HEIGHT);
    }

    public void update() {
        for (int i = 0; i < itemTimes.size(); i++) {
            if (itemTimes.get(i) == 30 * 30) {
                itemState = 0;
                wideWidth = 0;
                getPos().setX(getPos().getX() - VAUS_WIDTH / 2);
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
            pApplet.image(SpriteManager.getImage(VAUS_LASER, tick / 10 % 6), getPos().getX() - VAUS_WIDTH / 2, getPos().getY() - VAUS_HEIGHT / 2, VAUS_WIDTH + IMAGE_MERGE, VAUS_HEIGHT);
        } else if (itemState == ITEM_EXTEND) {
            pApplet.image(SpriteManager.getImage(VAUS_EXTEND, tick / 10 % 6), getPos().getX() - VAUS_WIDTH * 2 / 2, getPos().getY() - VAUS_HEIGHT / 2, VAUS_WIDTH * 2 + IMAGE_MERGE, VAUS_HEIGHT);
        } else
            pApplet.image(SpriteManager.getImage(VAUS_NORMAL, tick / 10 % 6), getPos().getX() - VAUS_WIDTH / 2, getPos().getY() - VAUS_HEIGHT / 2, VAUS_WIDTH + IMAGE_MERGE, VAUS_HEIGHT);
    }


    private void applyState() {
        if (itemState == ITEM_PLAYER) {
            plusPlayerLife();
        }
        if (itemState == ITEM_EXTEND) {
            System.out.println("item Extend");
            wideWidth = VAUS_WIDTH;
            getPos().setX(getPos().getX() + VAUS_WIDTH / 2);
            itemTimes.add(0);
        } else {
            itemTimes.add(0);
        }
    }


    public void setItemState(int itemState) {
        this.itemState = itemState;
        applyState();
    }

    public int getItemState() {
        return itemState;
    }

    public void moveLeft() {
        if (MARGIN_HORIZONTAL <= getPos().getX() - VAUS_WIDTH / 2)
            getPos().setX(getPos().getX() - VAUS_SPEED);
    }

    public void moveRight() {
        if (WINDOW_WIDTH - MARGIN_HORIZONTAL >= getPos().getX() + VAUS_WIDTH / 2)
            getPos().setX(getPos().getX() + VAUS_SPEED);
    }

    public int getPlayerLife() {
        return playerLife;
    }

    public void minusPlayerLife() {
        playerLife--;
    }

    public void plusPlayerLife() {
        playerLife++;
    }

    public int getWideWidth() {
        return wideWidth;
    }
}