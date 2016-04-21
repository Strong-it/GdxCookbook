package com.libgdx.cookbook.help;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.libgdx.cookbook.GdxCookbook;

/**
 * 增加BaseScreen 将screen用到的数据都放在这里
 * 后面一些screen可能会用到touch函数，因此此处继承InputAdapter
 */
public abstract class BaseScreen extends InputAdapter implements Screen {
	private final String TAG = BaseScreen.class.getSimpleName();
	
	public static final Color BACKGROUND_COLOR = new Color(0.39f, 0.58f, 0.92f, 1.0f);
	public static final float WORLD_TO_SCREEN = 1.0f / 100.0f;
	public static final float SCREEN_TO_WORLD = 100.0f;
	public static final float SCENE_WIDTH = 12.80f;
	public static final float SCENE_HEIGHT = 7.20f;
	
	public OrthographicCamera camera;
	public DebugTool debugTool;
	public Viewport viewport;
	public SpriteBatch batch;
	public GdxCookbook game;
	
	@Override
	public void show() {
		Gdx.app.log(TAG, "show");
	}

	@Override
	public void render(float delta) {

	}

	@Override
	public void resize(int width, int height) {
		Gdx.app.log(TAG, "resize");
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
		
	}

	@Override
	public void dispose() {
		Gdx.app.log(TAG, "dispose");
	}

	public void setGame(GdxCookbook game) {
	    this.game = game;
	}
	
	public void goBackMainScreen() {
	    if (Gdx.input.isKeyJustPressed(Keys.B)) {
	        game.setScreen(new MainMenuScreen(game));
        }
	}
}
