package com.libgdx.cookbook.chp02;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.libgdx.cookbook.help.BaseScreen;
import com.libgdx.cookbook.help.HelpCamera;

public class SpriteBatchSample extends BaseScreen {
	final String TAG = SpriteBatchSample.class.getSimpleName();
	
	private Texture cavemanTexture;
	
	private int width, height;
	private float originX, originY;
	
	@Override
	public void show() {
		camera = new OrthographicCamera();
		helpCamera = new HelpCamera(camera);
		viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
		batch = new SpriteBatch();
		
		cavemanTexture = new Texture(Gdx.files.internal("data/caveman.png"));
		cavemanTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		width = cavemanTexture.getWidth();
		height = cavemanTexture.getHeight();
		
		originX = width * 0.5f;
		originY = height * 0.5f;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(BACKGROUND_COLOR.r, BACKGROUND_COLOR.g, BACKGROUND_COLOR.b, BACKGROUND_COLOR.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);  // 设置batch的camera的正交投影
		
		batch.begin();
		batch.draw(cavemanTexture,   // texture本身
					-originX, -originY,  // word space coordinates,, considering camera centered at(0, 0)
					originX, originY,   // coordinates in pixels of our texture
					width , height,     // 人物在屏幕上画出的宽度，和高度，如果width / 2,那么人物的宽度会减半，但是仍然弄画出全部的人物
					WORLD_TO_SCREEN, WORLD_TO_SCREEN,  // scaleX, scaleY
					0f,      // rot(degress)  是否旋转
					0, 0,      // srcX, srcY  Texture本身的起始位置
					width, height,  // Texture的宽和高,如果width / 2,那么人物只能画出一般
					false, false);  //flipX, flipY  是否X轴翻转， 是否Y轴翻转
		helpCamera.operateCamera();
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// 将所有的resize都交给basescreen来处理
		super.resize(width, height);
	}

	@Override
	public void dispose() {
		batch.dispose();
		cavemanTexture.dispose();
	}

}
