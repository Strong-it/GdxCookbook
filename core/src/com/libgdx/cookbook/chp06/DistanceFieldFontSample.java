package com.libgdx.cookbook.chp06;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.libgdx.cookbook.help.BaseScreen;

/**
 * 两个Sharder脚本 data/fonts/font.vert   data/fonts/font.frag
 *
 */
public class DistanceFieldFontSample extends BaseScreen {
    
    private static final int VIRTUAL_WIDTH = 1280;
    private static final int VIRTUAL_HEIGHT = 720;
    
    private BitmapFont distanceFont;
    private BitmapFont normalFont;
    private ShaderProgram fontShader;

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.position.set(VIRTUAL_WIDTH  * 0.5f, VIRTUAL_HEIGHT * 0.5f, 0.0f);
        camera.update();
        
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        batch = new SpriteBatch();
        
        normalFont = new BitmapFont(Gdx.files.internal("data/fonts/oswald.fnt"));
        normalFont.setColor(0.0f, 0.56f, 1.0f, 1.0f);
        normalFont.getData().setScale(4.5f);    // 缩放图像
//        normalFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        distanceFont = new BitmapFont(Gdx.files.internal("data/fonts/oswald-distance.fnt"));
        distanceFont.setColor(0.0f, 0.56f, 1.0f, 1.0f);
        distanceFont.getData().setScale(4.5f);
        
        Texture texture = distanceFont.getRegion().getTexture();
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        fontShader = new ShaderProgram(Gdx.files.internal("data/fonts/font.vert"), Gdx.files.internal("data/fonts/font.frag"));
        
        if (!fontShader.isCompiled()) {
            Gdx.app.error(DistanceFieldFontSample.class.getSimpleName(), "Shader compilation failed:\n" + fontShader.getLog());
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.setShader(null);
        normalFont.draw(batch, "Distance field fonts!", 20.0f, VIRTUAL_HEIGHT - 50.0f);
        
        batch.setShader(fontShader);
        distanceFont.draw(batch, "Distance field fonts!", 20.0f, VIRTUAL_HEIGHT - 250.0f);
        batch.end();
        
        goBackMainScreen();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void hide() {
        batch.dispose();
        normalFont.dispose();
        distanceFont.dispose();
        fontShader.dispose();
    }

}
