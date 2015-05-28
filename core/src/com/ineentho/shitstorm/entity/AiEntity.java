package com.ineentho.shitstorm.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.ineentho.shitstorm.util.Facing;

import java.util.Random;

public class AiEntity extends LivingEntity {
    Random r = new Random();

    public AiEntity(World world, Texture texture, Vector2 size) {
        super(world, texture, size);
    }

    @Override
    public void update(float delta) {
        if (hasTarget()) {
            moveToTarget(delta);
        } else {
            moveRandomly();
        }
        super.update(delta);
    }

    private void moveRandomly() {
        if (r.nextInt(200) == 0) {
            changeDirection();
        }
    }

    private void changeDirection() {
        int direction = r.nextInt(4);
        if (direction == 0) {
            setMovingDirection(Facing.RIGHT, true);
        } else if (direction == 1) {
            setMovingDirection(Facing.LEFT, true);
        } else {
            setMovingDirection(null, false);
        }
    }

    private void moveToTarget(float delta) {
    }
}
