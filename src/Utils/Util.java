package Utils;

public class Util implements Constants {
    public static Vector2 indexToCoord(int x, int y) {
        Vector2 vector2 = new Vector2(x * BLOCK_WIDTH + MARGIN_HORIZONTAL, y * BLOCK_HEIGHT + MARGIN_VERTICAL);
        return vector2;
    }

    public static float difference(float a, float b) {
        return a - b;
    }
}
