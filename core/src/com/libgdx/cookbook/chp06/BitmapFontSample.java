package com.libgdx.cookbook.chp06;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.libgdx.cookbook.help.BaseScreen;

/**
 * Rendering Bitmapfont
 *
 */
public class BitmapFontSample extends BaseScreen {
    
    private static final int VIRTUAL_WIDTH = 1280;
    private static final int VIRTUAL_HEIGHT = 720;
    
    private BitmapFont font;
    
    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.position.set(VIRTUAL_WIDTH  * 0.5f, VIRTUAL_HEIGHT * 0.5f, 0.0f);
        camera.update();
        
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        batch = new SpriteBatch();
        
        font = new BitmapFont(Gdx.files.internal("data/fonts/play.fnt"));
        font.setColor(Color.WHITE);
        
        System.out.println("================");
        System.out.println("Font information");
        System.out.println("================");
        System.out.println("Ascent: " + font.getAscent());
        System.out.println("Descent: " + font.getDescent());
        System.out.println("Cap height: " + font.getCapHeight());
        System.out.println("Line height: " + font.getLineHeight());
        System.out.println("Space width: " + font.getSpaceWidth());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.6f, 0.6f, 0.6f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.setColor(Color.WHITE);
        font.draw(batch, "This is a one line string", 20.0f, VIRTUAL_HEIGHT - 50.0f);
        font.setColor(Color.RED);
        font.getData().setScale(1.5f);  // ·Å´ó×ÖÌå
        font.draw(batch, "This is a scaled red\nmultiline string", 20.0f, VIRTUAL_HEIGHT - 150.0f);
        font.setColor(Color.BLUE);
        font.getData().setScale(1.0f);
        font.draw(batch,
                  "This is a blue string wrapped to fit inside a 900px box. BitmapFont will have to insert line breaks somewhere",
                  20.0f,
                  VIRTUAL_HEIGHT - 400.0f,
                  900.0f,
                  Align.left,
                  true);
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
        font.dispose();
    }
    
}
