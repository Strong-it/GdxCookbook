package com.libgdx.cookbook.chp02;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.libgdx.cookbook.help.BaseScreen;

public class ViewportSample extends BaseScreen {

    private final String TAG = Viewport.class.getSimpleName();
    
	private static final float MIN_SCENE_WIDTH = 800.0f;
	private static final float MIN_SCENE_HEIGHT = 600.0f;
	private static final float MAX_SCENE_WIDTH = 1280.0f;
	private static final float MAX_SCENE_HEIGHT = 720.0f;

	private Texture background;
	private ArrayMap<String, Viewport> viewports;
	private int currentViewport;
	
    @Override
    public void show() {
        camera = new OrthographicCamera();
        batch =new SpriteBatch();
        createViewports();
        selectNextViewport();
        
        background = new Texture(Gdx.files.internal("data/menu.png"));
        Gdx.input.setInputProcessor(this);
    }

    private void createViewports() {
        viewports = new ArrayMap<String, Viewport>();
        viewports.put("StretchViewport", new StretchViewport(MIN_SCENE_WIDTH, MIN_SCENE_HEIGHT, camera));
        viewports.put("FitViewport", new FitViewport(MIN_SCENE_WIDTH, MIN_SCENE_HEIGHT, camera));
        viewports.put("FillViewport", new FillViewport(MIN_SCENE_WIDTH, MIN_SCENE_HEIGHT, camera));
        viewports.put("ScreenViewport", new ScreenViewport(camera));
        viewports.put("ExtendViewport (no max)", new ExtendViewport(MIN_SCENE_WIDTH, MIN_SCENE_HEIGHT, camera));
        viewports.put("ExtendViewport (max)", new ExtendViewport(MIN_SCENE_WIDTH, MIN_SCENE_HEIGHT, MAX_SCENE_HEIGHT, MAX_SCENE_WIDTH, camera));
        
        currentViewport = -1;
    }

    private void selectNextViewport() {
        currentViewport = (currentViewport + 1) % viewports.size;
        
        viewports.getValueAt(currentViewport).update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.app.log(TAG, "selcected " + viewports.getKeyAt(currentViewport));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.setProjectionMatrix(camera.combined);
        
        batch.begin();
        batch.draw(background, -background.getWidth() * 0.5f, -background.getHeight() * 0.5f);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewports.getValueAt(currentViewport).update(width, height);
    }

    @Override
    public void hide() {
        batch.dispose();
        background.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        selectNextViewport();
        return true;
    }
	
}
