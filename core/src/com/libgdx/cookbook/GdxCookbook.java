package com.libgdx.cookbook;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.libgdx.cookbook.chp02.SpriteBatchSample;
import com.libgdx.cookbook.chp02.TextureAtlasSample;

public class GdxCookbook extends Game {
	final String TAG = "Game";
	
	SpriteBatchSample sbSample;
	TextureAtlasSample textureAtlasSample;
	
	@Override
	public void create() {
		Gdx.app.log(TAG, "create");
//		sbSample = new SpriteBatchSample();
		textureAtlasSample = new TextureAtlasSample();
		setScreen(textureAtlasSample);
	}

	@Override
	public void dispose() {
		Gdx.app.log(TAG, "dispose"); // 先执行Game的disopose，之后执行screen的hide
		super.dispose();
	}

	@Override
	public void pause() {          //  GdxCookbook接受pause事件，然后调用Game的pause，再分发给各个screen
								   //  具体的screen如果实现了父类的pause方法，那么就不会调用Basescreen的方法，如果没有实现，默认调用父类的pause方法
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
		super.render();  // 继承自Game的life流程都要super.来执行， 但是basescreen里面的life流程不需要执行
	}
	
}
