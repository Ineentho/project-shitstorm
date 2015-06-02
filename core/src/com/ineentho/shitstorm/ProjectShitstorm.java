package com.ineentho.shitstorm;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.ineentho.shitstorm.screen.GameScreen;
import com.ineentho.shitstorm.screen.LoadingScreen;

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
		FileHandleResolver resolver = new InternalFileHandleResolver();
		assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

		for(String texture : textures) {
			assetManager.load("textures/" + texture + ".png", Texture.class);
		}

		assetManager.load("audio/axeSwing.mp3", Sound.class);

		FreetypeFontLoader.FreeTypeFontLoaderParameter normal = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
		normal.fontFileName = "font/Roboto-Regular.ttf";
		normal.fontParameters.size = 12;
		normal.fontParameters.color = Color.BLACK;
		assetManager.load("size12.ttf", BitmapFont.class, normal);
	}

	@Override
	public void render() {
		if (isLoading) {
			if (assetManager.update()) {
				restartGame();
				isLoading = false;
			}
		}
		super.render();
	}

	public void restartGame() {
		setScreen(new GameScreen());
	}

	public Texture getTexture(String texture) {
		return assetManager.get("textures/" + texture + ".png", Texture.class);
	}

	public BitmapFont getFont(String font) {
		return assetManager.get(font + ".ttf", BitmapFont.class);
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
