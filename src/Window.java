import Models.*;
import Utils.*;
import dwon.SpriteManager.SpriteManager;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Window extends PApplet implements Constants {
    private Block[][] blocks = new Block[100][13];
    private Ball ball;
    private boolean ballPressed = false;
    private List<Item> items = new ArrayList<>();
    private Vaus vaus;
    private boolean isPressedRight;
    private boolean isPressedLeft;
    private boolean start = false;
    private boolean isBallVausCollision;
    private List<Laser> lasers = new LinkedList<>();
    private CollisionChecker collisionChecker = new CollisionChecker();


    public void settings() {
        size(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public void setup() {
        setupMap();
        vaus = new Vaus();
        ball = new Ball(vaus.getPos().getX() + 5, vaus.getPos().getY() - (VAUS_HEIGHT / 2) - BALL_RADIUS);

        loadImage();
        allocateCollision();
        addListeners();
    }

    private void allocateCollision() {
        CollisionChecker.allocate(vaus, "rect");
        CollisionChecker.allocate(ball, "circle");

        for (int i = 0; i < BLOCK_ROW; i++) {
            for (int j = 0; j < BLOCK_COLUMN; j++) {
                CollisionChecker.allocate(blocks[i][j], "rect");
            }
        }
    }

    private void addListeners() {
        collisionChecker.addOnCollisionListener(vaus, (view) -> {
            System.out.println(view.toString() + " vaus col!");
            ballVausCollision();
        });
        collisionChecker.addOnCollisionListener(ball, view -> System.out.println(view.toString() + " ball col!"));

        for (int i = 0; i < BLOCK_ROW; i++) {
            for (int j = 0; j < BLOCK_COLUMN; j++) {
                collisionChecker.addOnCollisionListener(blocks[i][j], view -> {
                    System.out.println(view.toString() + " blocks[i][j] col!");
                    ballBlockCollision();
                });
            }
        }
    }

    private void setupMap() {
        String[][] s = CsvFileManager.getCsvArray("./data/map.dat");

        for (int i = 0; i < BLOCK_ROW; i++) {
            for (int j = 0; j < BLOCK_COLUMN; j++) {
                blocks[i][j] = new Block(j, i, (Integer.parseInt(s[i][j]) / 100) * 10,
                        Integer.parseInt(s[i][j]) % 100 / 10,
                        Integer.parseInt(s[i][j]) % 10);
                if (blocks[i][j].getLife() < 1)
                    blocks[i][j].destroy();
            }
        }
    }

    int tick;

    public void draw() {
        CollisionChecker.checkCollisions();
        tick++;

        if (tick % 30 == 0)
            System.out.println(ball.getSpeedY());

        background(0);

        image(SpriteManager.getImage(BLOCK_WALL, 0), MARGIN_HORIZONTAL - 33, 0,
                WINDOW_WIDTH - MARGIN_HORIZONTAL, WINDOW_HEIGHT);
        drawBlocks();
        drawItems();
        drawLaserBalls();
        laserBallBlockCollision();
//        ballVausCollision();
        drawVaus();
        drawBall();

        if (ball.getPos().getY() > WINDOW_HEIGHT) {
            vaus.minusPlayerLife();
            vaus.getPos().setX(WINDOW_WIDTH / 2);
            vaus.getPos().setY(WINDOW_HEIGHT - 100);
            ball.getPos().setX(vaus.getPos().getX() + 5);
            ball.getPos().setY(vaus.getPos().getY() - VAUS_HEIGHT / 2 - BALL_RADIUS);
            ball.setSpeedX(0);
            ball.setSpeedX(0);
            ball.setDirection(new Vector2(0, 0));
            vaus.setItemState(NONE_ITEM);
            start = false;
            tick = 0;
        }

        for (int i = 0; i < vaus.getPlayerLife(); i++) {
            image(SpriteManager.getImage(VAUS_NORMAL, 0), 55 * (i + 1), 780, VAUS_WIDTH / 2, VAUS_HEIGHT);
        }


    }



    public void ballVausCollision() {
        if (CollisionChecker.rectCircleCollision(vaus.getPos(), ball.getPos(), VAUS_WIDTH + vaus.getWideWidth(),
                VAUS_HEIGHT, BALL_RADIUS) && start) {
            float diff = Util.difference(ball.getPos().getX(), vaus.getPos().getX());
            if (vaus.getItemState() != ITEM_CLASP) {
//                System.out.println(diff);
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
    }

    public void drawBlocks() {
        renderBlocks();
//        ballBlockCollision();
    }

    public void drawLaserBalls() {
        for (int i = 0; i < lasers.size(); i++) {
            lasers.get(i).render(this);
            lasers.get(i).update();
            if (lasers.get(i).getOverHeight())
                lasers.remove(lasers.get(i));
        }
    }

    public void drawItems() {

        for (int i = 0; i < items.size(); i++) {
            items.get(i).update();
            items.get(i).render(this);

            if (CollisionChecker.rectCollision(vaus.getPos(), items.get(i).getPos(),
                    VAUS_WIDTH + vaus.getWideWidth(), VAUS_HEIGHT, ITEM_WIDTH, ITEM_HEIGHT)) {
                items.get(i).setCollision();
                vaus.setItemState(items.get(i).getState());
                items.remove(i);
            }

        }
    }

    public void itemCollision() {}

    public void drawVaus() {
        moveVaus();
        vaus.update();
        vaus.render(this);
    }

    public void drawBall() {
        initBall();

        if (vaus.getItemState() == ITEM_SLOW) {
            ball.setSpeedX((float) (ball.getSpeedX() * 0.1));
            ball.setSpeedX((float) (ball.getSpeedY() * 0.1));
        }
        ball.render(this);
        ball.update();
    }

    public void addItem(Block block) {
        if (block.getItem() != 0)
            items.add(new Item(block.getPos(), block.getItem()));
    }

    public void laserBallBlockCollision() {
        for (int i = 0; i < BLOCK_ROW; i++) {
            for (int j = 0; j < BLOCK_COLUMN; j++) {
                for (int k = 0; k < lasers.size(); k++) {
                    if (CollisionChecker.rectCircleCollision(blocks[i][j].getPos(), lasers.get(k).getPos(), BLOCK_WIDTH, BLOCK_HEIGHT, BALL_RADIUS)
                            && !blocks[i][j].isDestroyed()) {
//                    ball.getVelocity().setY(ball.getVelocity().getY() * -1);
                        blocks[i][j].minusLife();
                        if (blocks[i][j].getLife() < 1) {
                            blocks[i][j].destroy();
                            lasers.remove(lasers.get(k));
                            addItem(blocks[i][j]);
                        }
                    }
                }

            }
        }
    }

    public void ballBlockCollision() {
        for (int i = 0; i < BLOCK_ROW; i++) {
            for (int j = 0; j < BLOCK_COLUMN; j++) {
                if (CollisionChecker.rectCircleCollision(blocks[i][j].getPos(), ball.getPos(), BLOCK_WIDTH, BLOCK_HEIGHT, BALL_RADIUS)
                        && !blocks[i][j].isDestroyed()) {
//                    ball.getVelocity().setY(ball.getVelocity().getY() * -1);
                    blocks[i][j].minusLife();
                    if (blocks[i][j].getLife() < 1) {
                        blocks[i][j].destroy();
                        addItem(blocks[i][j]);
                    }

                    if (ball.getPos().getX() < blocks[i][j].getPos().getX() - BLOCK_WIDTH / 2 - BALL_RADIUS) {
                        ball.setPos(new Vector2(ball.getPos().getX()
                                - (BALL_RADIUS - (blocks[i][j].getPos().getX() - ball.getPos().getX() - BLOCK_WIDTH / 2)),
                                ball.getPos().getY()));
                        ball.invertX();
                    } else if (ball.getPos().getX() > blocks[i][j].getPos().getX() + BLOCK_WIDTH / 2 + BALL_RADIUS) {
                        ball.setPos(new Vector2(ball.getPos().getX()
                                + (BALL_RADIUS - (ball.getPos().getX() - blocks[i][j].getPos().getX() - BLOCK_WIDTH / 2)),
                                ball.getPos().getY()));
                        ball.invertX();
                    } else if (ball.getPos().getY() < blocks[i][j].getPos().getY() - BLOCK_HEIGHT / 2 - BALL_RADIUS) {
                        ball.setPos(new Vector2(ball.getPos().getX(),
                                ball.getPos().getY()
                                        - (BALL_RADIUS
                                        - (blocks[i][j].getPos().getY() - ball.getPos().getY() - BLOCK_HEIGHT / 2))));
                        ball.invertY();
                    } else {
                        ball.setPos(new Vector2(ball.getPos().getX(),
                                ball.getPos().getY()
                                        + (BALL_RADIUS
                                        - (ball.getPos().getY() - blocks[i][j].getPos().getY() - BLOCK_HEIGHT / 2))));
                        ball.invertY();
                    }
                }
            }
        }
    }



    public void initBall() {
        if (!start && tick > 30 * 2) {
            System.out.println("init");
            ball.setSpeedX(BALL_SPEED / 2);
            ball.setSpeedY(BALL_SPEED);
            ball.setDirection(new Vector2(1, -1));
            start = true;
            isBallVausCollision = false;
        } else {
            if (!start)
                isBallVausCollision = true;
            else isBallVausCollision = false;
        }
    }

    public void moveVaus() {
        if (isPressedRight) {
            vaus.moveRight();
            if (isBallVausCollision) {
                ball.getPos().setX(ball.getPos().getX() + VAUS_SPEED);
            }
        }

        if (isPressedLeft) {
            vaus.moveLeft();
            if (isBallVausCollision) {
                ball.getPos().setX(ball.getPos().getX() - VAUS_SPEED);
            }
        }
    }

    public void renderBlocks() {
        for (int i = 0; i < BLOCK_ROW; i++) {
            for (int j = 0; j < BLOCK_COLUMN; j++) {
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
        SpriteManager.loadSprites(this, VAUS_NORMAL, "./images/vaus.png", 0, 0, 152, 56, 6);
        SpriteManager.loadSprites(this, VAUS_LASER, "./images/vaus_laser.png", 0, 0, 152, 56, 6);
        SpriteManager.loadSprites(this, VAUS_EXTEND, "./images/vaus_extended.png", 0, 0, 228, 56, 6);

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
                if (vaus.getItemState() == ITEM_LASER) {
                    if (lasers.size() < LASER_LIMIT) {
                        lasers.add(new Laser(vaus.getPos().getX() + 10, vaus.getPos().getY()));
                        lasers.add(new Laser(vaus.getPos().getX() - 10, vaus.getPos().getY()));
                    }
                } else if (vaus.getItemState() == ITEM_CLASP) {
                    ball.setPos(new Vector2(ball.getPos().getX(),
                            ball.getPos().getY() - (BALL_RADIUS - (vaus.getPos().getY() - ball.getPos().getY() - VAUS_HEIGHT / 2))));
                    ball.setDirection(new Vector2(1, -1));
                    isBallVausCollision = false;
                }
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
