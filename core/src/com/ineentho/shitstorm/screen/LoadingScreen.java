package com.ineentho.shitstorm.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ineentho.shitstorm.ProjectShitstorm;

public class LoadingScreen extends ScreenAdapter {
	private final Sprite teemoSprite;
	private ProjectShitstorm game;

	public LoadingScreen(ProjectShitstorm game) {
		this.game = game;
		teemoSprite = new Sprite(new Texture(Gdx.files.internal("textures/teemo.png")));
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		float sw = teemoSprite.getWidth();
		float sh = teemoSprite.getHeight();
		teemoSprite.setPosition(w / 2 - sw / 2, h / 2 - sh / 2);
	}

	@Override
	public void render(float delta) {
		update(delta);
		SpriteBatch batch = game.getSpriteBatch();

		clear();

		batch.begin();

		teemoSprite.draw(batch);
		batch.end();
	}

	private void clear() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	private void update(float delta) {
		teemoSprite.rotate(300f * delta);
	}
}
