package com.britishpenguin.astrowarrior;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.britishpenguin.astrowarrior.config.GameSettings;
import com.britishpenguin.astrowarrior.config.KeyInputProcessor;
import com.britishpenguin.astrowarrior.entity.Entity;
import com.britishpenguin.astrowarrior.entity.enemy.SpaceshipEnemy;
import com.britishpenguin.astrowarrior.entity.player.SpaceshipSelf;

import java.util.ArrayList;
import java.util.List;

public class AstroWarrior extends ApplicationAdapter {

	private static AstroWarrior instance;

	public SpriteBatch batch;
	public BitmapFont fontRenderer;

	public GameSettings gameSettings;

	public SpaceshipSelf player;

	public OrthographicCamera camera;
	public List<Entity> renderedSprites;

	public AstroWarrior() {
		instance = this;
		this.renderedSprites = new ArrayList<>();
		this.gameSettings = new GameSettings();
	}
	
	@Override
	public void create() {
		Gdx.input.setInputProcessor(new KeyInputProcessor(this.gameSettings));
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		player = new SpaceshipSelf(this, Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 90f, 100);
		this.renderedSprites.add(player);
		for (int i = 0; i < 6; i++)
			this.renderedSprites.add(new SpaceshipEnemy(this, MathUtils.random(0, Gdx.graphics.getWidth()), MathUtils.random(0, Gdx.graphics.getHeight()), 180));
		batch = new SpriteBatch();
		//batch.setProjectionMatrix(camera.projection);
		fontRenderer = new BitmapFont();
		fontRenderer.setColor(Color.WHITE);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		for (Entity entity : new ArrayList<>(renderedSprites)) {
			entity.update(Gdx.graphics.getDeltaTime());
			entity.draw(batch);
		}

		fontRenderer.draw(batch, "Health: " + player.getCurrentHealth() + " | Mana: " + player.mana, 10, Gdx.graphics.getHeight() - 10);
		batch.end();

	}

	public void playSound(String internalPath, float volume, float pitch, float pan) {
		Sound sound = Gdx.audio.newSound(Gdx.files.internal(internalPath));
		sound.play(volume, pitch, pan);
	}

	public void playSound(String internalPath, float volume, float pitch) {
		playSound(internalPath, volume, pitch, 0.5f);
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		fontRenderer.dispose();
	}

	public void addEntityToRenderList(Entity entity) {
		this.renderedSprites.add(entity);
	}

	public void removeEntityFromRenderList(Entity entity) {
		this.renderedSprites.remove(entity);
	}

	public static AstroWarrior getInstance() {
		return instance;
	}
}
