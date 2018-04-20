package Utils;

public interface Constants {
    int NONE_ITEM = 0;
    int ITEM_PLAYER = 1;
    int ITEM_LASER = 2;
    int ITEM_EXTEND = 3;
    int ITEM_CLASP = 4;
    int ITEM_SLOW = 5;
    int ITEM_BONUS = 6;
    int ITEM_DOOM = 7;

    int BLOCK_WHITE = 50;
    int BLOCK_ORANGE = 60;
    int BLOCK_SKYBLUE = 70;
    int BLOCK_GREEN = 80;
    int BLOCK_RED = 90;
    int BLOCK_BLUE = 100;
    int BLOCK_PINK = 110;
    int BLOCK_YELLOW = 120;

    int BLOCK_HARD = 200;
    int BLOCK_IMMORTAL = 300;

    int BLOCK_WALL = 400;

    int BLOCK_WIDTH = 30;
    int BLOCK_HEIGHT = 15;

    int BALL_RADIUS = 5;

    int BLOCK_ROW = 25;
    int BLOCK_COLUMN = 13;

    int WINDOW_WIDTH = 480;
    int WINDOW_HEIGHT = 800;

    int ITEM_WIDTH = 26;
    int ITEM_HEIGHT = 12;

    int ITEM_SPEED = 3;
    int BALL_SPEED = 6;

    int IMAGE_MERGE = 5;

    int LASER_SPEED = 6;
    int LASER_RADIUS = 3;
    int LASER_LIMIT = 6;

    int VAUS_WIDTH = 60;
    int VAUS_HEIGHT = 15;
    int VAUS_SPEED = 10;

    int VAUS_NORMAL = 21;
    int VAUS_LASER = 22;
    int VAUS_EXTEND = 23;

    int MARGIN_HORIZONTAL = (WINDOW_WIDTH - BLOCK_WIDTH * BLOCK_COLUMN) / 2 + (BLOCK_WIDTH / 2);
    int MARGIN_VERTICAL = BLOCK_HEIGHT / 2;
}
