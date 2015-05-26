package com.ineentho.shitstorm;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProjectShitstorm extends Game {

	private static ProjectShitstorm instance;
	public static ProjectShitstorm getInstance() {
		return instance;
	}

	public ProjectShitstorm() {
		instance = this;
	}

	private SpriteBatch spriteBatch;

	private AssetManager assetManager;
	private boolean isLoading = true;

	@Override
	public void create() {
		loadAssets();
		spriteBatch = new SpriteBatch();

		setScreen(new LoadingScreen(this));
	}

	private void loadAssets() {
		List<String> textures = new ArrayList<String>(Arrays.asList(new String[]{"bomb", "boss", "cooldude-projektil", "cooldude1", "cooldude2", "drill", "eyes", "grass",
				"knifr", "lance", "madapotatoh", "ninjapotatis", "ninjaslime", "saltkaren", "shield", "skull golem",
				"skull", "slem", "stone block", "teemo", "yxa", "pivot"}));

		for (int i = 1; i < 22; i++) {
			textures.add("explozionnn/exp" + i);
		}

		assetManager = new AssetManager();
		for(String texture : textures) {
			assetManager.load("textures/" + texture + ".png", Texture.class);
		}

		assetManager.load("audio/axeSwing.mp3", Sound.class);
	}

	@Override
	public void render() {
		if (isLoading) {
			if (assetManager.update()) {
				setScreen(new GameScreen());
				isLoading = false;
			}
		}
		super.render();
	}

	public Texture getTexture(String texture) {
		return assetManager.get("textures/" + texture + ".png", Texture.class);
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

	public void playSound(String file) {
		assetManager.get("audio/" + file + ".mp3", Sound.class).play();
	}
}
