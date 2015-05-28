package com.ineentho.shitstorm.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.ineentho.shitstorm.entity.AiEntity;
import com.ineentho.shitstorm.entity.PlayerCharacter;
import com.ineentho.shitstorm.ProjectShitstorm;
import com.ineentho.shitstorm.entity.LivingEntity;
import com.ineentho.shitstorm.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends ScreenAdapter {
	private final OrthographicCamera cam;
	private final World world;
	private final Box2DDebugRenderer debugRenderer;
	private final List<LivingEntity> entities = new ArrayList<LivingEntity>();
	private final PlayerCharacter mainChar;

	public GameScreen() {
		ProjectShitstorm game = ProjectShitstorm.getInstance();

		world = new World(new Vector2(0f, -9.82f), true);
		mainChar = new PlayerCharacter(world, game.getTexture("cooldude1"), new Vector2(34 / 34f, 56 / 34f));
		cam = createCamera();


		for (int i = 0; i < 50; i++) {
			LivingEntity enemy = new AiEntity(world, game.getTexture("bomb"), new Vector2(1, 1));
			enemy.teleport(new Vector2((float) (Math.random() * 200) - 100, 0), 0);
			entities.add(enemy);
		}

		entities.add(mainChar);

		LivingEntity eyes = new LivingEntity(world, game.getTexture("eyes"), new Vector2(1, 1));
		eyes.teleport(new Vector2(5, 0), 0);
		entities.add(eyes);


		debugRenderer = new Box2DDebugRenderer();

		createGround();
	}

	private void createGround() {
		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.position.set(new Vector2(-50, -1));

		Body groundBody = world.createBody(groundBodyDef);

		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(100f, 1f);
		groundBody.createFixture(groundBox, 0.0f);
		groundBox.dispose();
	}

	private OrthographicCamera createCamera() {
		OrthographicCamera cam = new OrthographicCamera();
		cam.position.set(mainChar.getPosition(), 0);
		return cam;
	}


	@Override
	public void render(float delta) {
		SpriteBatch batch = ProjectShitstorm.getInstance().getSpriteBatch();
		clear();

		handleInput();

		world.step(1f / 60f, 6, 2);

		for (LivingEntity entity : entities) {
			entity.update(delta);
		}

		cam.position.set(Utils.moveTowards(mainChar.getPosition(), new Vector2(cam.position.x, cam.position.y)), 0);

		cam.update();
		batch.setProjectionMatrix(cam.combined);

		batch.begin();
		for (LivingEntity entity : entities) {
			entity.draw(batch);
		}
		batch.end();

		debugRenderer.render(world, cam.combined);

	}

	private void clear() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	private void handleInput() {
		// InputHandler.handleCam(cam);
	}

	@Override
	public void resize(int width, int height) {
		float scale = 0.03f;
		cam.viewportWidth = scale * width;
		cam.viewportHeight = scale * height;
		cam.update();
	}
}
