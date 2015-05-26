package com.ineentho.shitstorm.weapon;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ineentho.shitstorm.Facing;
import com.ineentho.shitstorm.LivingEntity;
import com.ineentho.shitstorm.ProjectShitstorm;

public class Axe extends Weapon {
	private final Sprite weaponSprite;
	private float rot;
	private int animStage;
	private LivingEntity character;

	public Axe(LivingEntity character) {
		this.character = character;
		weaponSprite = new Sprite(ProjectShitstorm.getInstance().getTexture("yxa"));
		weaponSprite.setSize(14 / 14f, 28 / 14f);
		weaponSprite.setOrigin(.6f, .2f);
	}

	@Override
	public void changedDirection() {
		rot = 0;
		animStage = 0;

		weaponSprite.flip(true, false);

		if (character.getFacing() == Facing.LEFT) {
			weaponSprite.setOrigin(.6f, .2f);
		} else {
			weaponSprite.setOrigin(-.1f, .2f);
		}
	}

	private void axeAnimation(float delta) {
		int facing = character.getFacing() == Facing.LEFT ? -1 : 1;
		if (animStage == 1) {
			rot -= delta * 500 * facing;

			if (character.getFacing() == Facing.RIGHT) {
				if (rot < -130) {
					rot = -130;
					animStage = 2;
				}
			} else {
				if (rot > 130) {
					rot = 130;
					animStage = 2;
				}
			}
		} else if (animStage == 2) {
			rot += delta * 400 * facing;

			if (character.getFacing() == Facing.RIGHT && rot > 0) {
				rot = 0;
				animStage = 0;
			}

			if (character.getFacing() == Facing.LEFT && rot < 0) {
				rot = 0;
				animStage = 0;
			}
		}
	}

	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);
		weaponSprite.draw(batch);
	}

	@Override
	public boolean attack() {
		if (animStage == 0) {
			rot = 0;
			animStage = 1;
			return true;
		}
		return false;
	}

	@Override
	public void update(float delta, Vector2 offset) {
		if (animStage > 0) {
			axeAnimation(delta);
		}
		weaponSprite.setPosition(character.getPosition().x + offset.x, character.getPosition().y + offset.y);
		weaponSprite.setRotation(rot);
		super.update(delta, offset);
	}
}
