package com.libgdx.cookbook.chp03;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.libgdx.cookbook.help.BaseScreen;

public class FrameBufferParticleEffectSample extends BaseScreen {

    private static final int VIRTUAL_WIDTH = 1280;
    private static final int VIRTUAL_HEIGHT = 720;
    
    private Texture background;
    private ParticleEffectPool pool;
    private Array<PooledEffect> activeEffects;
    private Vector3 touchPos;
    private FrameBuffer particleBuffer;
    private TextureRegion particleRegion;
    
    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, camera);
        batch = new SpriteBatch();
        touchPos = new Vector3();
        particleRegion = new TextureRegion();
        background = new Texture(Gdx.files.internal("data/jungle-level.png"));
        
        particleBuffer = new FrameBuffer(Format.RGBA8888, VIRTUAL_WIDTH, VIRTUAL_HEIGHT, false);
        
        ParticleEffect particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("data/explosion.particle"), Gdx.files.internal("data"));
        pool = new ParticleEffectPool(particleEffect, 10, 100);
        activeEffects = new Array<PooledEffect>();
        
        camera.position.set(SCENE_WIDTH * 0.5f, SCENE_HEIGHT * 0.5f, 0);
        camera.update();
        
        Gdx.input.setInputProcessor(this);
    }
    
    @Override
    public void render(float delta) {
        particleBuffer.bind();
        
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        
        for (int i = 0; i < activeEffects.size; ) {
            PooledEffect effect = activeEffects.get(i);
            
            if (effect.isComplete()) {
                pool.free(effect);
                activeEffects.removeIndex(i);
            } else {
                effect.draw(batch, delta);
                ++i;
            }
        }
        
        particleRegion.setRegion(particleBuffer.getColorBufferTexture());
        particleRegion.flip(false, true);
        
        batch.end();
        FrameBuffer.unbind();
        
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        
        int width = background.getWidth();
        int height = background.getHeight();
        
        batch.draw(background, 0.0f, 0.0f,
                    0.0f, 0.0f,
                    width, height, 
                    WORLD_TO_SCREEN, WORLD_TO_SCREEN, 
                    0.0f, 
                    0, 0, 
                    width, height, 
                    false, false);
        
        width = particleRegion.getRegionWidth();
        height = particleRegion.getRegionHeight();
        
        batch.draw(particleRegion, 
                    0.0f, 0.0f, 
                    0.0f, 0.0f, 
                    width, height, 
                    WORLD_TO_SCREEN, WORLD_TO_SCREEN,
                    0.0f);
        batch.end();
        
        goBackMainScreen();
    }
    
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        PooledEffect effect = pool.obtain();
        
        if (effect != null) {
            touchPos.set(screenX, screenY, 0.0f);
            camera.unproject(touchPos);
            
            activeEffects.add(effect);
            effect.setPosition(touchPos.x, touchPos.y);
        }
        return true;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
    
    @Override
    public void hide() {
        batch.dispose();
        background.dispose();
        particleBuffer.dispose();
    }
}
