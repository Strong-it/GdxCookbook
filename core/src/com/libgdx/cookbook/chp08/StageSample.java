package com.libgdx.cookbook.chp08;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.libgdx.cookbook.help.BaseScreen;

public class StageSample extends BaseScreen {

    private static final String TAG = StageSample.class.getSimpleName();
    
    Viewport viewport;
    MyStage myStage;
    Label label;
    BitmapFont font;
    
    @Override
    public void show() {
        viewport = new StretchViewport(12.0f, 7.2f);
        batch = new SpriteBatch();
        font = new BitmapFont();
        myStage = new MyStage(viewport);
        Gdx.input.setInputProcessor(myStage);
        
        /**
         * using a scale camera for your game objects. 
         * That's fine. However fonts in libgdx are scaled in pixel dimensions and not your game world units. 
         * The best course of action here is to maintain two sperate cameras to render your game: world, and ui.
         **/
         Label.LabelStyle style = new Label.LabelStyle();
         style.font = font;
         label = new Label("H..Hide, V..Visiable", style);
         Gdx.app.log(TAG, "Label width=" + label.getWidth() +
                  " preWdithd="+label.getPrefWidth());
         label.setX(Gdx.graphics.getWidth() / 2 - label.getWidth() / 2);
         label.setY(Gdx.graphics.getHeight() - label.getHeight() - 20);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.39f, 0.58f, 0.92f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        label.draw(batch, 1.0f);
        batch.end();
        
        if (myStage.getVisiable()) {
            myStage.act();
            myStage.draw();
        }
        
    }

    @Override
    public void resize(int width, int height) {
        myStage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        myStage.dispose();
        font.dispose();
        batch.dispose();
    }

    public class MyStage extends Stage {
        Texture texture;
        Color saveColor;
        
        Image badlogicImg;
        
        boolean isVisible;
        float scale = 1 / 100.0f;
        int count = 0;
        
        public MyStage(Viewport viewport) {
            super(viewport);
            init();
        }
        
        private void init() {
            // ¿ªÆôdebugÄ£Ê½
            setDebugAll(true);
            
            texture = new Texture("badlogic.jpg");
            saveColor = new Color();
            
            badlogicImg = new Image(texture);
            badlogicImg.setSize(badlogicImg.getWidth() * scale, badlogicImg.getHeight() * scale);
            Gdx.app.log(TAG, "width=" + badlogicImg.getWidth() + 
                    " preWidth=" + badlogicImg.getPrefWidth() +
                    " imageWidth=" + badlogicImg.getImageWidth());
            badlogicImg.setX(getViewport().getWorldWidth() /2 - badlogicImg.getWidth() / 2);
            badlogicImg.setY(getViewport().getWorldHeight() / 2 - badlogicImg.getHeight() / 2);
            saveColor.set(badlogicImg.getColor());
            badlogicImg.addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (count % 2 == 0) {
                        badlogicImg.setColor(Color.RED);
                    } else if (count % 2 == 1){
                        badlogicImg.setColor(saveColor);
                    }
                    count++;
                }
                
            });
            addActor(badlogicImg);
            
            isVisible = true;
        }

        public boolean getVisiable() {
            return isVisible;
        }
        
        @Override
        public void dispose() {
            super.dispose();
            texture.dispose();
            font.dispose();
        }

        @Override
        public boolean keyDown(int keyCode) {
            if (keyCode == Keys.H && isVisible) {
                isVisible = false;
            } else if (keyCode == Keys.V && !isVisible) {
                isVisible = true;
            } else if (keyCode == Keys.B) {
                goMainScreen();
            }
            return super.keyDown(keyCode);
        }
  
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer,
                int button) {
            Gdx.app.log(TAG, "touchDown screenX=" + screenX);
            return super.touchDown(screenX, screenY, pointer, button);
        }
        
    }
}
