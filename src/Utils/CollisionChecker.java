package Utils;

import Models.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CollisionChecker {

    private static HashMap<Class<? extends View>, List<View>> library = new HashMap<>();
    private static HashMap<Class<? extends View>, String> shapes = new HashMap<>();
    private static HashMap<View, OnCollisionListener> listeners = new HashMap<>();
//    private static List<OnCollisionListener> listenerList = new ArrayList<>();

    public CollisionChecker() { }

    public void addOnCollisionListener(View view, OnCollisionListener listener) {
        listeners.put(view, listener);
    }

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

    public static void allocate(View view, String shape) {
        List<View> list;

        if (!library.containsKey(view.getClass())) {
            list = new ArrayList<>();
            list.add(view);
        } else {
            list = library.get(view.getClass());
            list.add(view);
        }
        library.put(view.getClass(), list);
        shapes.put(view.getClass(), shape);
    }

    public static HashMap<Class<? extends View>, List<View>> getLibrary() {
        return library;
    }

    private static void alertListener(View target, View comparisonTarget) {
        if (listeners.get(target) != null)
            listeners.get(target).onCollision(target);
        if (listeners.get(comparisonTarget) != null)
            listeners.get(comparisonTarget).onCollision(comparisonTarget);
    }

    public static void checkCollisions() {
        ArrayList<Class<? extends View>> keySet = new ArrayList<>(library.keySet());
        for (int i = 0; i < keySet.size(); i++) {
            Class target = keySet.get(i);
            ArrayList<Class<? extends View>> compare = new ArrayList<>(keySet);
            compare.remove(keySet.get(i));
            for (Class c : compare) {
                if (shapes.get(target).equals("rect") && shapes.get(c).equals("circle")) {
                    List<View> targetList = library.get(target);
                    List<View> compareTargetList = library.get(c);

                    for (View t : targetList) {
                        for (View ct : compareTargetList) {
                            if (rectCircleCollision(t.getPos(), ct.getPos(), t.getWidth(), t.getHeight(), ct.getHeight() / 2)) {
                                alertListener(t, ct);
                                System.out.println("rectCircleCollision");
                            }
                        }
                    }
                } else if (shapes.get(target).equals("rect") && shapes.get(c).equals("rect")) {
                    List<View> targetList = library.get(target);
                    List<View> compareTargetList = library.get(c);

                    for (View t : targetList) {
                        for (View ct : compareTargetList) {
                            if (rectCollision(t.getPos(), ct.getPos(), t.getWidth(), t.getHeight(), ct.getWidth(), ct.getHeight())) {
                                alertListener(t, ct);
                                System.out.println("rectCollision");
                            }
                        }
                    }
                } else if (shapes.get(target).equals("rect") && shapes.get(c).equals("point")) {
                    List<View> targetList = library.get(target);
                    List<View> compareTargetList = library.get(c);

                    for (View t : targetList) {
                        for (View ct : compareTargetList) {
                            if (rectPointCollision(t.getPos(), ct.getPos(), t.getWidth(), t.getHeight())) {
                                alertListener(t, ct);
                                System.out.println("rectPointCollision");
                            }


                        }
                    }
                } else if (shapes.get(target).equals("circle") && shapes.get(c).equals("circle")) {
                    List<View> targetList = library.get(target);
                    List<View> compareTargetList = library.get(c);

                    for (View t : targetList) {
                        for (View ct : compareTargetList) {
                            if (circleCollision(t.getPos(), ct.getPos(), t.getWidth() / 2, t.getHeight() / 2)) {
                                alertListener(t, ct);
                                System.out.println("CircleCollision");
                            }
                        }
                    }
                } else if (shapes.get(target).equals("circle") && shapes.get(c).equals("point")) {
                    List<View> targetList = library.get(target);
                    List<View> compareTargetList = library.get(c);

                    for (View t : targetList) {
                        for (View ct : compareTargetList) {
                            if (circlePointCollision(t.getPos(), ct.getPos(), t.getWidth() / 2)) {
                                alertListener(t, ct);
                                System.out.println("CirclePointCollision");
                            }
                        }
                    }
                }
            }
        }
    }
}
