package com.libgdx.cookbook.chp05;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.libgdx.cookbook.help.BaseScreen;

/**
 * Engine sounds with dynamic audio
 * pitch  调节音调
 * Pan    调节声音的声道
 *
 */
public class CarEngineSample extends BaseScreen {

    private static final int VIRTUAL_WORLD_WIDTH = 640;
    private static final int VIRTUAL_WORLD_HEIGHT = 360;

    private final static float MAX_SPEED = 200.0f;
    private final static float ACCELERATION = 25.0f;   // 一秒钟增加的速度，所以每一帧应该是 ACCELERATION * delta
    private final static float FRICTION = 15.0f;
    private final static float IDLE_THRESHOLD = 0.1f;

    private BitmapFont font;
    private Sound engine;
    private Sound idle;
    private float speed;
    private long soundId;

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WORLD_WIDTH, VIRTUAL_WORLD_HEIGHT, camera);
        batch = new SpriteBatch();
        font = new BitmapFont();
        
        font.setColor(Color.WHITE);
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        font.getData().setScale(2.5f);
        camera.position.set(VIRTUAL_WORLD_WIDTH * 0.5f, VIRTUAL_WORLD_HEIGHT * 0.5f, 0);
        camera.update();
        
        idle = Gdx.audio.newSound(Gdx.files.internal("data/sfx/car-idle.wav"));
        engine = Gdx.audio.newSound(Gdx.files.internal("data/sfx/car-engine.wav"));
        soundId = idle.play();
        idle.setLooping(soundId, true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.39f, 0.58f, 0.92f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        updateEngine(delta);
        
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Speed: " + speed + "km/h", 20.0f, 200.0f);
        font.draw(batch, "Press SPACE or touch to accelerate", 20.0f, 150.0f);
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
        engine.dispose();
        idle.dispose();
    }

    private void updateEngine(float delta) {
        boolean wasIdle = speed < IDLE_THRESHOLD;
        
        // update speed
        float acceleration = -FRICTION;
        
        if (Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.LEFT)) {
            acceleration = ACCELERATION;
        }
        
        speed = MathUtils.clamp(speed + acceleration * delta, 0.0f, MAX_SPEED);
        
        boolean isIdle = speed < IDLE_THRESHOLD;
        
        // update sound
        if (wasIdle && !isIdle) {  // 正在加速
            idle.stop();
            soundId = engine.play();
            engine.setLooping(soundId, true);
        }
        else if (!wasIdle && isIdle) {  // 速度停止
            engine.stop();
            soundId = idle.play();
            idle.setLooping(soundId, true);
        }
        
        if (!isIdle) {
            float pitch = 0.5f + speed / MAX_SPEED * 0.5f;
            engine.setPitch(soundId, pitch);  // 改变sound音调  
                                              // 1 == default, >1 == faster, <1 == slower, the value has to be between 0.5 and 2.0
        }
    }
}
