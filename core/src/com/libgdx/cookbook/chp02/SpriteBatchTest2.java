package com.libgdx.cookbook.chp02;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.libgdx.cookbook.help.BaseScreen;

public class SpriteBatchTest2 extends BaseScreen {
	private static final String TAG = SpriteBatchTest2.class.getSimpleName();
	
	private static final float WORLD_TO_SCREEN = 1.0f / 100.0f;
	private static final float SCENE_WIDTH = 12.80f;
	private static final float SCENE_HEIGHT = 7.20f;
	
	Texture img;
	OrthographicCamera camera;
	int width;
	int height;
	float originX;
	float originY;
	float angle = 10f;
	boolean first = true;
	
	@Override
	public void show () {
		Gdx.app.log(TAG, "create");
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		
		camera = new OrthographicCamera();
		camera.position.set(SCENE_WIDTH / 2f,  SCENE_HEIGHT / 2f, 0.0f);
		camera.update();
		Gdx.app.log(TAG, "camera.x" + camera.position.x);
		viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, camera);
		// 为了方便观察效果，将FitViewport转换为StretchViewport
		// SCENE_WIDTH, SCENE_HEIGHT 其实是调用setWorldSize(SCENE_WIDTH, SCENE_HEIGHT)
		viewport = new StretchViewport(SCENE_WIDTH, SCENE_HEIGHT, camera);
		
		width = img.getWidth();
		height = img.getHeight();
		originX = width * 0.5f;
		originY = height * 0.5f;
		
		Gdx.app.log(TAG, "width=" + width + " height="+height);
	}

	@Override
	public void render (float dt) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		angle = (angle + 2) % 360;
		batch.setProjectionMatrix(camera.combined);
		if (first) {
		    // 并不会因为viewport而改变屏幕size
		    Gdx.app.log(TAG, "screen width="+ Gdx.graphics.getWidth() + " height="+ Gdx.graphics.getHeight());
		    first = false;
        }
		batch.begin();
//		batch.draw(img, 0, 0, width * WORLD_TO_SCREEN, height * WORLD_TO_SCREEN);
		// 上面和下面的语句效果是一样的
		batch.draw(img, 0, 0, originX * WORLD_TO_SCREEN, originY * WORLD_TO_SCREEN, width  * WORLD_TO_SCREEN, 
				height * WORLD_TO_SCREEN, 1.0f, 1.0f, 0.0f, 0, 0, width, height, false, false);
//		batch.draw(img, 6.4f, 3.6f, width * WORLD_TO_SCREEN, height * WORLD_TO_SCREEN);
		// 上面和下面语句是一样的不过加了一个角度旋转图片，要想效果一样origin也需要*WORLD_TO_SCREEN,不过以固定角度旋转
		batch.draw(img, 6.4f, 3.6f, originX * WORLD_TO_SCREEN, originY * WORLD_TO_SCREEN, width  * WORLD_TO_SCREEN, 
				height * WORLD_TO_SCREEN, 1.0f, 1.0f, angle, 0, 0, width, height, false, false);
		batch.draw(img, 0.0f, 7.2f - height * WORLD_TO_SCREEN, width * WORLD_TO_SCREEN, height * WORLD_TO_SCREEN);
		batch.end();
		
		 if (Gdx.input.isKeyJustPressed(Keys.B)) {
	            goMainScreen();
	        }
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		// 这个地方设置true, false camera的位置都不会发生改变
		Gdx.app.log(TAG, "resize camera.x" + camera.position.x);
		Gdx.app.log(TAG, "resize");
	}

	@Override
	public void hide() {
		img.dispose();
		batch.dispose();
		Gdx.app.log(TAG, "dispose");
	}	
}
