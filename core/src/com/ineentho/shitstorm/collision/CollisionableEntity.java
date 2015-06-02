package com.ineentho.shitstorm.collision;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * Created by hkhen on 2015-05-29.
 */
public interface CollisionableEntity {
    public void onCollision(Fixture fixture, Body other);
}
