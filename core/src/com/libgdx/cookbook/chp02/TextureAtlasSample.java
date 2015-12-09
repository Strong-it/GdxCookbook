package com.libgdx.cookbook.chp02;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.libgdx.cookbook.help.BaseScreen;
import com.libgdx.cookbook.help.DebugTool;
/*
Game: create
TextureAtlasSample: constructor
TextureAtlasSample: show
TextureAtlasSample: Max texture size: 3379 x 3379
BaseScreen: resize
BaseScreen: resize
SpriteBatchSample: constructor
TextureAtlasSample: hide
SpriteBatchSample: show
BaseScreen: resize
TextureAtlasSample: constructor
SpriteBatchSample: hide
TextureAtlasSample: show
TextureAtlasSample: Max texture size: 3379 x 3379
BaseScreen: resize

// 从log可以看出当从一个screen1跳到screen2时先调用此screen2的构造函数
// 然后调用要被隐藏的screen1的hide函数，再调用screen1的show函数
*/
public class TextureAtlasSample extends BaseScreen {
	private final String TAG = TextureAtlasSample.class.getSimpleName();
	
	private Game game;
	private TextureAtlas atlas;
	private TextureRegion backgroundRegion;
	private TextureRegion cavemanRegion;
	private TextureRegion dinosaurRegion;
	
	float width, height, originX, originY;
	
	public TextureAtlasSample(Game game) {
		Gdx.app.log(TAG, "constructor");
		this.game = game;
	}
	
	@Override
	public void show() {
		Gdx.app.log(TAG, "show");
		camera = new OrthographicCamera();
		viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
		batch = new SpriteBatch();
		debugTool = new DebugTool(camera);
		
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
		
		if (Gdx.input.isKeyJustPressed(Keys.G)) {
			gotoScreen();
		}
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void hide() {
		Gdx.app.log(TAG, "hide");
		batch.dispose();
		atlas.dispose();
	}

	private void gotoScreen() {
		game.setScreen(new SpriteBatchSample(game));
	}
}
