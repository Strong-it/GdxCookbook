package com.libgdx.cookbook;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.libgdx.cookbook.chp02.SpriteBatchSample;

public class GdxCookbook extends Game {
	final String TAG = "Game";
	
	SpriteBatchSample sbSample;
	
	@Override
	public void create() {
		Gdx.app.log(TAG, "create");
		sbSample = new SpriteBatchSample();
		setScreen(sbSample);
	}

	@Override
	public void dispose() {
		Gdx.app.log(TAG, "dispose"); // 先执行Game的disopose，之后执行screen的hide
		super.dispose();
	}

	@Override
	public void pause() {
		Gdx.app.log(TAG, "pause"); // 先执行Game的Pause,之后再分发到各个screen的pause
		super.pause();
	}

	@Override
	public void resume() {
		Gdx.app.log(TAG, "resume");
		super.resume();
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		super.render();
	}
	
}
