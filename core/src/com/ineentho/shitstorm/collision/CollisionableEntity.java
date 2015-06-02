package com.ineentho.shitstorm.collision;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

public interface CollisionableEntity {
    void onCollision(Fixture fixture, Body other);
}
