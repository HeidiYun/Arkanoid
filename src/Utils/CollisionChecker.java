package Utils;

public class CollisionChecker {
    public static boolean rectCollision(Vector2 rect1, Vector2 rect2, int rect1Width, int rect1Height, int rect2Width, int rect2Height) {
        return (rect1.getX() - rect1Width / 2 <= rect2.getX() + rect2Width / 2
                && rect1.getX() + rect1Width / 2 >= rect2.getX() - rect2Width / 2
                && rect1.getY() - rect1Height / 2 <= rect2.getY() + rect2Height / 2
                && rect1.getY() + rect1Height / 2 >= rect2.getY() - rect2Height / 2);

    }

    public static boolean rectCircleCollision(Vector2 rect, Vector2 circle, int rectWidth, int rectHeight, int circleRadius) {
        if (circle.getX() > (rect.getX() - (rectWidth / 2) - circleRadius)
                && circle.getX() < (rect.getX() + (rectWidth / 2) + circleRadius)
                && circle.getY() > (rect.getY() - (rectHeight / 2) - circleRadius)
                && circle.getY() < (rect.getY() + (rectHeight / 2) + circleRadius)) {
            if (rect.distance(rect.getX(), rect.getY()) > rectWidth / 2
                    && rect.distance(rect.getX(), rect.getY()) > rectHeight / 2) {
                if (circle.distance(rect.getX() - rectWidth / 2, rect.getY() - rectHeight / 2) < circleRadius
                        || circle.distance(rect.getX() + rectWidth / 2, rect.getY() - rectHeight / 2) < circleRadius
                        || circle.distance(rect.getX() - rectWidth / 2, rect.getY() + rectHeight / 2) < circleRadius
                        || circle.distance(rect.getX() + rectWidth / 2, rect.getY() + rectHeight / 2) < circleRadius)
                    return true;
                else return false;
            } else return true;
        } else return false;
    }

    public static boolean circleCollision(Vector2 circle1, Vector2 circle2, int circle1Radius, int circle2Radius) {
        return circle1.distance(circle2.getX(), circle2.getY()) <= circle1Radius + circle2Radius;
    }

    public static boolean rectPointCollision(Vector2 rect, Vector2 point, int rectWidth, int rectHeight) {
        return rect.getX() - rectWidth / 2 <= point.getX()
                && rect.getX() + rectWidth / 2 >= point.getX()
                && rect.getY() - rectHeight / 2 >= point.getY()
                && rect.getY() + rectHeight / 2 <= point.getY();
    }

    public static boolean circlePointCollision(Vector2 circle, Vector2 point, int circleRadius) {
        return circle.distance(point.getX(), point.getY()) <= circleRadius;
    }
}

