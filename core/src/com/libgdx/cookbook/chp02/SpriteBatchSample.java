package com.libgdx.cookbook.chp02;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpriteBatchSample implements Screen {
	final String TAG = SpriteBatchSample.class.getSimpleName();
	
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void show() {
		Gdx.app.log(TAG, "show");
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		Gdx.app.log(TAG, "pause");
	}

	@Override
	public void resume() {
		Gdx.app.log(TAG, "resume");
	}

	@Override
	public void hide() {
		Gdx.app.log(TAG, "hide");
	}

	@Override
	public void dispose() {
		Gdx.app.log(TAG, "dispose");
	}

}
