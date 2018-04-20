package Utils;

public class Vector2 {
    private float x;
    private float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2 add(Vector2 a, Vector2 b) {
        return new Vector2(a.x + b.x, a.y + b.y);
    }

    public static float distance(Vector2 a, Vector2 b) {
        return (float) Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
    }


    public float distance(float x, float y) {
        return (float) Math.sqrt((this.x - x) * (this.x - x) + (this.y - y) * (this.y - y));
    }

    public Vector2 add(float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector2 add(Vector2 vector2) {
        this.x += vector2.x;
        this.y += vector2.y;
        return this;
    }

    public Vector2 mul(float d) {
        this.x *= d;
        this.y *= d;
        return this;
    }



    public float magnitude() {
        return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public static float magnitude(Vector2 vector2) {
        return (float) Math.sqrt(Math.pow(vector2.getX(), 2) + Math.pow(vector2.getY(), 2));
    }

    public static float magnitude(float x, float y) {
        return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public static float normalizeX(float x, float y) {
        return x /= magnitude(x, y);
    }

    public static float normalizeY(float x, float y) {
        return y /= magnitude(x, y);
    }

    public Vector2 normalize() {
        this.x /= this.magnitude();
        this.y /= this.magnitude();
        return this;
    }

    public static Vector2 normalize(Vector2 vector2) {
        vector2.setX(vector2.getX() / magnitude(vector2));
        vector2.setY(vector2.getY() / magnitude(vector2));
        return vector2;
    }

    public float normalizeX() {
        return this.x /= this.magnitude();
    }

    public float normalizeY() {
        return this.y /= this.magnitude();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setXY(float x, float y) {
        this.x = x;
        this.y =y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
