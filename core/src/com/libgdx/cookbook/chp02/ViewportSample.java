package com.libgdx.cookbook.chp02;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.libgdx.cookbook.help.BaseScreen;

/**
 * Handing multiple screen sizes with viewports
 *
 */
public class ViewportSample extends BaseScreen {

    private final String TAG = Viewport.class.getSimpleName();
    
	private static final float MIN_SCENE_WIDTH = 800.0f;
	private static final float MIN_SCENE_HEIGHT = 600.0f;
	private static final float MAX_SCENE_WIDTH = 1280.0f;
	private static final float MAX_SCENE_HEIGHT = 720.0f;

	private Texture background, sprite;
	private ArrayMap<String, Viewport> viewports;
	private int currentViewport;
	private BitmapFont font;
	
    @Override
    public void show() {
        camera = new OrthographicCamera();
        batch =new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2.0f);
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        createViewports();
        selectNextViewport();
        
        background = new Texture(Gdx.files.internal("data/menu.png"));
        sprite = new Texture(Gdx.files.internal("badlogic.jpg"));
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
        getTextLog("selectNextViewport");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.setProjectionMatrix(camera.combined);
        
        batch.begin();
        batch.draw(background, -background.getWidth() * 0.5f, -background.getHeight() * 0.5f);
        batch.draw(sprite, 0, 0);
        batch.draw(sprite, -390, -290);
        font.draw(batch, viewports.getKeyAt(currentViewport), -360, 260);
        batch.end();
        
        if (Gdx.input.isKeyJustPressed(Keys.B)) {
            goMainScreen();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewports.getValueAt(currentViewport).update(width, height);
        /**
         * 通过log发现ScalingViewport变的只有viewport，world size没有发生变化
         * 通过源码可以看出update函数只是重新获取screenWidth，然后来重新计算缩放比例
         * 
         * ScreenViewport和ExtendViewport的viewport 和 world size都会发生变化
         * 源码发现都会重新调用setWorldSize
         * 
         */
        getTextLog("resize");
    }

    @Override
    public void hide() {
        batch.dispose();
        background.dispose();
        sprite.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        selectNextViewport();
        return true;
    }
    
    private void getTextLog(String flag) {
        Gdx.app.log(flag, "selcected " + viewports.getKeyAt(currentViewport));

        Gdx.app.log(flag, "screentWidth= " + viewports.getValueAt(currentViewport).getScreenWidth() +
                "  screentHeight= " + viewports.getValueAt(currentViewport).getScreenHeight());

        Gdx.app.log(flag, "wordWidth= " + viewports.getValueAt(currentViewport).getWorldWidth() +
                "  wordHeight= " + viewports.getValueAt(currentViewport).getWorldHeight());

        Gdx.app.log(flag, "camera.x=" + camera.position.x + " camera.y="
                + camera.position.y);

        Gdx.app.log(flag, "screen x="+ viewports.getValueAt(currentViewport).getScreenX() + " y="+ viewports.getValueAt(currentViewport).getScreenY());

    }
	
}
