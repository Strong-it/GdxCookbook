package com.libgdx.cookbook.chp02;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.libgdx.cookbook.help.BaseScreen;

public class TextureAtlasSample extends BaseScreen {

	private TextureAtlas atlas;
	private TextureRegion backgroundRegion;
	private TextureRegion cavemanRegion;
	private TextureRegion dinosaurRegion;
	
	float width, height, originX, originY;
	
	@Override
	public void show() {
		camera = new OrthographicCamera();
		viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
		batch = new SpriteBatch();
		
		atlas = new TextureAtlas(Gdx.files.internal("data/prehistoric.atlas"));
		backgroundRegion = atlas.findRegion("background");
		cavemanRegion = atlas.findRegion("caveman");
		dinosaurRegion = atlas.findRegion("trex");
		
		int maxSize = GL20.GL_MAX_TEXTURE_SIZE;
		Gdx.app.log("TextureAtlasSample", "Max texture size: " + maxSize + " x " + maxSize);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		
		width = backgroundRegion.getRegionWidth();  //  获取背景单张图片的width
		height = backgroundRegion.getRegionHeight();  // 获取背景单张图片的height
		originX = width * 0.5f;
		originY = height * 0.5f;
		batch.draw(backgroundRegion, 
				-originX, -originY,
				originX, originY,
				width, height,
				WORLD_TO_SCREEN, WORLD_TO_SCREEN,
				0.0f);
		
		width = cavemanRegion.getRegionWidth();
		height = cavemanRegion.getRegionHeight();
		originX = width * 0.5f;
		originY = height * 0.5f;
		batch.draw(cavemanRegion,
				-5.0f -originX, -2.0f -originY, 
				originX, originY,
				width, height, 
				WORLD_TO_SCREEN, WORLD_TO_SCREEN, 
				0.0f);
		
		width = dinosaurRegion.getRegionWidth();
		height = dinosaurRegion.getRegionHeight();
		originX = width * 0.5f;
		originY = height * 0.5f;
		batch.draw(dinosaurRegion,
				1.5f -originX, 0.35f -originY, 
				originX, originY,
				width, height, 
				WORLD_TO_SCREEN, WORLD_TO_SCREEN, 
				0.0f);
		
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void hide() {
		batch.dispose();
		atlas.dispose();
	}

}
