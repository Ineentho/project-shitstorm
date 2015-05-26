package com.ineentho.shitstorm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class PlayerCharacter extends LivingEntity {

	public PlayerCharacter(World world, Texture texture, Vector2 size) {
		super(world, texture, size);
	}

	@Override
	public void update(float delta) {
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			jump();
		}

		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			movingRight = true;
		} else {
			movingRight = false;
			justStoppedMovingLeft = true;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			movingLeft = true;
		} else {
			movingLeft = false;
			justStoppedMovingRight = true;
		}

		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			attack();
		}

		super.update(delta);
	}

}
