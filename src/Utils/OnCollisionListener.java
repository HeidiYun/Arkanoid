package Utils;

import Models.View;

public interface OnCollisionListener {
    void onCollision(View target, View compareTarget, CollisionChecker.Direction direction);
}
