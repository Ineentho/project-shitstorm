package com.ineentho.shitstorm.collision;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by hkhen on 2015-05-29.
 */
public interface CollisionableEntity {
    public void onCollision(Body other);
}
