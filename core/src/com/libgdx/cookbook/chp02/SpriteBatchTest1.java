package com.libgdx.cookbook.chp02;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.libgdx.cookbook.help.BaseScreen;

public class SpriteBatchTest1 extends BaseScreen {

	private static final String TAG = SpriteBatchTest1.class.getSimpleName();
	
	Texture img;
	int width, height;
	int screenWidth, screenHeight;
	float angle = 10f;
	
	@Override
	public void show() {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		
		// 图片资源的大小
		width = img.getWidth();
		height = img.getHeight();
		Gdx.app.log(TAG, "Texture widht="+ width + "  height="+height);
		
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		Gdx.app.log(TAG, "screen width="+ screenWidth + " height="+ Gdx.graphics.getHeight());

	}

	@Override
	public void render(float dt) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		angle = (angle + 2) % 360;
		batch.begin();
		// 以默认Texture的大小来绘制图片
//		batch.draw(img, 0, 0);
		// 这个效果和上面的效果一样
		batch.draw(img, 0, 0, width * 0.5f, height * 0.5f, width, height, 
				1.0f, 1.0f, 0.0f, 0, 0, width, height, false, false);
		// 从屏幕中心开始，以Texture的一半来绘制图像,这个相当于图像缩小了一半
		batch.draw(img, screenWidth * 0.5f, screenHeight * 0.5f, width *0.5f, height * 0.5f);
		// 在(350,0)这个位置以Texture的大小，但是只绘制纹理100*100
		batch.draw(img, 350, 0, width * 0.5f, height * 0.5f, width, height, 
				1.0f, 1.0f, 0.0f, 0, 0, 100, 100, false, false);
		// 在(0,300)这个位置以100*100的大小绘制图片，同时origin大小也要换成100*0.5,同时图片以固定速度旋转
		batch.draw(img, 0, 300, 100 * 0.5f, 100 * 0.5f, 100, 100, 
					1.0f, 1.0f, angle, 0, 0, width, height, false, false);
		batch.end();
		
		 if (Gdx.input.isKeyJustPressed(Keys.B)) {
	            goMainScreen();
	        }
	}

	@Override
	public void hide() {
		// 不用的资源要销毁，它们的共同点事implements Disposable
		batch.dispose();
		img.dispose();
	}

}
