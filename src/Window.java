import Models.*;
import Utils.*;
import dwon.SpriteManager.SpriteManager;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class Window extends PApplet implements Constants {
    Block[][] blocks = new Block[100][13];
    private Ball ball;
    private boolean ballPressed = false;
    private List<Item> items = new ArrayList<>();
    private Bauss bauss;
    private boolean isPressedRight;
    private boolean isPressedLeft;
    private List<LaserBall> laserBalls = new ArrayList<>();
    private boolean start = false;
    private int life = 2;

    public void settings() {
        size(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public void setup() {
        setupMap();
        bauss = new Bauss();
        ball = new Ball(bauss.getPos().getX() + 5, bauss.getPos().getY() - Constants.BAUSS_HEIGHT / 2 - BALL_RADIUS);
        loadImage();
    }

    public void setupMap() {
        String[][] s = CsvFileManager.getCsvArray("./data/map.dat");

        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 13; j++) {
                blocks[i][j] = new Block(j, i, (Integer.parseInt(s[i][j]) / 100) * 10,
                        Integer.parseInt(s[i][j]) % 100 / 10,
                        Integer.parseInt(s[i][j]) % 10);
            }
        }
    }

    int tick;

    public void draw() {
        tick++;
        if (tick % 30 == 0)
            System.out.println(ball.getSpeedY());
        background(0);

        image(SpriteManager.getImage(BLOCK_WALL, 0), MARGIN_HORIZONTAL - 33, 0,
                WINDOW_WIDTH - MARGIN_HORIZONTAL, WINDOW_HEIGHT);
        drawBlocks();
        drawItems();
        drawLaserBalls();
        ballBaussCollision();
        drawBauss();
        drawBall();

        if (ball.getPos().getY() > WINDOW_HEIGHT) {
            life--;
            bauss.getPos().setX(WINDOW_WIDTH / 2);
            bauss.getPos().setY(WINDOW_HEIGHT - 100);
            ball.getPos().setX(bauss.getPos().getX() + 5);
            ball.getPos().setY(bauss.getPos().getY() - Constants.BAUSS_HEIGHT / 2 - BALL_RADIUS);
            ball.setSpeedX(BALL_SPEED / 2);
            ball.setSpeedX(BALL_SPEED);
            ball.setDirection(new Vector2(1, -1));
        }

        for (int i = 0; i < life; i++) {
            image(SpriteManager.getImage(BAUSS_NORMAL, 0), 55 * (i + 1), 780, BAUSS_WIDTH / 2, BAUSS_HEIGHT);
        }

    }

    public void ballBaussCollision() {
        if (CollisionChecker.rectCircleCollision(bauss.getPos(), ball.getPos(), BAUSS_WIDTH, BAUSS_HEIGHT, BALL_RADIUS) && start) {
            float diff = Util.difference(ball.getPos().getX(), bauss.getPos().getX());
            System.out.println(diff);
            if (diff > 0) {
                if (ball.getVelocity().getX() < 0) {
                    ball.invertX();
                }
//                ball.setSpeedY(ball.getSpeedY() - (ball.getSpeedX() - ball.getSpeedX() * diff/10));
                ball.setSpeedX(ball.getSpeedX() * diff / 10);
                ball.invertY();
            } else {
                if (ball.getVelocity().getX() > 0) {
                    ball.invertX();
                }
//                ball.setSpeedY(ball.getSpeedY() - (ball.getSpeedX() - ball.getSpeedX() * diff/10));
                ball.setSpeedX(ball.getSpeedX() * ((-1) * diff / 10));
                ball.invertY();
            }
        }
    }

    public void drawBlocks() {
        renderBlocks();
        ballBlockCollision();
    }

    public void drawLaserBalls() {
        for (LaserBall laserBall : laserBalls) {
            laserBall.render(this);
            laserBall.update();
        }
    }

    public void drawItems() {

        for (Item item : items) {
            if (CollisionChecker.rectCollision(bauss.getPos(), item.getPos(), BAUSS_WIDTH, BAUSS_HEIGHT, ITEM_WIDTH, ITEM_HEIGHT)) {
                item.setCollision();
                bauss.setItemState(item.getState());
            }
            item.update();
            item.render(this);
        }
    }

    public void drawBauss() {
        moveBauss();
        bauss.update();
        bauss.render(this);
    }

    public void drawBall() {
        initBall();

        ball.render(this);
        ball.update();
    }

    public void addItem(Block block) {
        if (block.getItem() != 0)
            items.add(new Item(block.getPos(), block.getItem()));
    }

    public void ballBlockCollision() {
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 13; j++) {
                if (CollisionChecker.rectCircleCollision(blocks[i][j].getPos(), ball.getPos(), BLOCK_WIDTH, BLOCK_HEIGHT, BALL_RADIUS)
                        && !blocks[i][j].isDestroyed()) {
//                    ball.getVelocity().setY(ball.getVelocity().getY() * -1);
                    blocks[i][j].minusLife();
                    if (blocks[i][j].getLife() < 1) {
                        blocks[i][j].destroy();
                        addItem(blocks[i][j]);
                    }

                    if (ball.getPos().getX() < blocks[i][j].getPos().getX() - BLOCK_WIDTH / 2 - BALL_RADIUS) {
                        ball.setPos(new Vector2(ball.getPos().getX() - (BALL_RADIUS - (blocks[i][j].getPos().getX() - ball.getPos().getX() - BLOCK_WIDTH / 2)),
                                ball.getPos().getY()));
                        ball.invertX();
                    } else if (ball.getPos().getX() > blocks[i][j].getPos().getX() + BLOCK_WIDTH / 2 + BALL_RADIUS) {
                        ball.setPos(new Vector2(ball.getPos().getX() + (BALL_RADIUS - (ball.getPos().getX() - blocks[i][j].getPos().getX() - BLOCK_WIDTH / 2)),
                                ball.getPos().getY()));
                        ball.invertX();
                    } else if (ball.getPos().getY() < blocks[i][j].getPos().getY() - BLOCK_HEIGHT / 2 - BALL_RADIUS) {
                        ball.setPos(new Vector2(ball.getPos().getX(),
                                ball.getPos().getY() - (BALL_RADIUS - (blocks[i][j].getPos().getY() - ball.getPos().getY() - BLOCK_HEIGHT / 2))));
                        ball.invertY();
                    } else {
                        ball.setPos(new Vector2(ball.getPos().getX(),
                                ball.getPos().getY() + (BALL_RADIUS - (ball.getPos().getY() - blocks[i][j].getPos().getY() - BLOCK_HEIGHT / 2))));
                        ball.invertY();
                    }
                }
            }
        }
    }

    public void initBall() {
        if (!start && millis() > 2000) {
            ball.setSpeedX(BALL_SPEED / 2);
            ball.setSpeedY(BALL_SPEED);
            ball.setDirection(new Vector2(1, -1));
            start = true;
        }
    }

    public void moveBauss() {
        if (isPressedRight) bauss.moveRight();
        if (isPressedLeft) bauss.moveLeft();
    }

    public void renderBlocks() {
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 13; j++) {
                blocks[i][j].render(this);
            }
        }
    }

    public void loadImage() {

        SpriteManager.loadSprites(this, ITEM_PLAYER, "./images/item_player.png", 0, 0, 80, 44, 8);
        SpriteManager.loadSprites(this, ITEM_LASER, "./images/item_laser.png", 0, 0, 80, 44, 8);
        SpriteManager.loadSprites(this, ITEM_EXTEND, "./images/item_extend.png", 0, 0, 80, 44, 8);
        SpriteManager.loadSprites(this, ITEM_CLASP, "./images/item_clasp.png", 0, 0, 80, 44, 8);
        SpriteManager.loadSprites(this, ITEM_SLOW, "./images/item_slow.png", 0, 0, 80, 44, 8);
        SpriteManager.loadSprites(this, ITEM_BONUS, "./images/item_bonus.png", 0, 0, 80, 44, 8);
        SpriteManager.loadSprites(this, ITEM_DOOM, "./images/item_doom.png", 0, 0, 80, 44, 8);
        SpriteManager.loadSprites(this, BAUSS_NORMAL, "./images/bauss.png", 0, 0, 152, 56, 6);
        SpriteManager.loadSprites(this, BAUSS_LASER, "./images/bauss_laser.png", 0, 0, 152, 56, 6);
        SpriteManager.loadSprites(this, BAUSS_EXTEND, "./images/bauss_extended.png", 0, 0, 228, 56, 6);

        SpriteManager.loadImage(this, BLOCK_BLUE, "./images/block_blue.png");
        SpriteManager.loadImage(this, BLOCK_WHITE, "./images/block_white.png");
        SpriteManager.loadImage(this, BLOCK_ORANGE, "./images/block_orange.png");
        SpriteManager.loadImage(this, BLOCK_SKYBLUE, "./images/block_skyblue.png");
        SpriteManager.loadImage(this, BLOCK_GREEN, "./images/block_green.png");
        SpriteManager.loadImage(this, BLOCK_RED, "./images/block_red.png");
        SpriteManager.loadImage(this, BLOCK_PINK, "./images/block_pink.png");
        SpriteManager.loadImage(this, BLOCK_YELLOW, "./images/block_yellow.png");
        SpriteManager.loadImage(this, BLOCK_HARD, "./images/block_hard.png");
        SpriteManager.loadImage(this, BLOCK_IMMORTAL, "./images/block_immortal.png");
        SpriteManager.loadImage(this, BLOCK_WALL, "./images/wall.png");
    }

    public void keyPressed() {
        switch (keyCode) {
            case LEFT:
                isPressedLeft = true;
                break;
            case RIGHT:
                isPressedRight = true;
                break;
            case 32:
                laserBalls.add(new LaserBall(bauss.getPos().getX() - BAUSS_WIDTH / 2, bauss.getPos().getY()));
                laserBalls.add(new LaserBall(bauss.getPos().getX() + BAUSS_WIDTH / 2, bauss.getPos().getY()));
                break;

        }
    }

    public void keyReleased() {
        switch (keyCode) {
            case LEFT:
                isPressedLeft = false;
                break;
            case RIGHT:
                isPressedRight = false;
                break;
        }

    }
}
