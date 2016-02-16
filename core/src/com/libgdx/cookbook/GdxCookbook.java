package com.libgdx.cookbook;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.libgdx.cookbook.chp02.TextureAtlasSample;
import com.libgdx.cookbook.help.SampleList;

public class GdxCookbook extends Game {
	final String TAG = "Game";
	
	String[] sampleName;
	
	@Override
	public void create() {
		Gdx.app.log(TAG, "create");
		int sampleNum = SampleList.sampleList.size();
		sampleName = new String[sampleNum];
		for (int i = 0; i < sampleNum; i++) {
			sampleName[i] = SampleList.sampleList.get(i).getSimpleName();
		}
		// TODO: 以后学习UI部分之后做一个List
		//Gdx.app.log(TAG, "class" + SampleList.newSample("TextureAtlasSample")); // 这是获取一个对象,后面会带@字符串
		//Gdx.app.log(TAG, "class" + SampleList.sampleList.get(1));                // 这是获取一个类
		setScreen(SampleList.newSample("LocalizationSample"));
//		setScreen(new TextureAtlasSample(this));
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
