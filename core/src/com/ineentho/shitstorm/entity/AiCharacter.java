package com.ineentho.shitstorm.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

public class AiCharacter extends LivingEntity {
	Random r = new Random();

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
	}

	private void moveToTarget(float delta) {
	}

	public AiCharacter(World world, Texture texture, Vector2 size) {
		super(world, texture, size);
	}
}
