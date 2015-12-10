package com.libgdx.cookbook.chp03;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.libgdx.cookbook.help.BaseScreen;

public class ParticleEffectsSample extends BaseScreen {

    private ParticleEffect[] effects;
    private ParticleEffectPool pool;
    private Array<PooledEffect> activeEffects;
    private int currentEffect;
    private Vector3 touchPos;
    
    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
        batch = new SpriteBatch();
        
        effects = new ParticleEffect[3];  // 装载粒子的容器,即加载.particle文件  最好要调用dispose
        currentEffect = 0;
        touchPos = new Vector3();
        
        effects[0] = new ParticleEffect();  // 在初始化的时候
        effects[0].load(Gdx.files.internal("data/fire.particle"), Gdx.files.internal("data"));  // 第二个参数是粒子所在的目录
        
        effects[1] = new ParticleEffect();
        effects[1].load(Gdx.files.internal("data/stars.particle"), Gdx.files.internal("data"));
        
        effects[2] = new ParticleEffect();
        effects[2].load(Gdx.files.internal("data/ice.particle"), Gdx.files.internal("data"));
        
        for (ParticleEffect particleEffect : effects) {
            particleEffect.start(); // 粒子发射器开始发射粒子 如果不启用start只会有一个粒子系统
        }
        
        Gdx.input.setInputProcessor(this);  // 其实BaseScreen可以不继承InputAdapter,在类内部声明一个 InputAdapter inputAdapter
                                            // Gdx.input.setInputProcessor(inputAdapter) 但此时是内部类
        
        ParticleEffect explosionEffect = new ParticleEffect();
        explosionEffect.load(Gdx.files.internal("data/explosion.particle"), Gdx.files.internal("data"));
        pool = new ParticleEffectPool(explosionEffect, 10, 100);
        activeEffects = new Array<PooledEffect>();
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
//        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
//        camera.unproject(touchPos);   // screen坐标和world坐标不是对应的，比如scal的时候，这时候需要将屏幕坐标转换到world坐标
        
        for (ParticleEffect particleEffect : effects) {
            particleEffect.setPosition(touchPos.x, touchPos.y);
            
            if (particleEffect.isComplete()) {
                particleEffect.reset();  // 粒子生命周期结束，自动重置，这样就能不断刷新
            }
        }
        
        batch.setProjectionMatrix(camera.combined);
        
        batch.begin();
        //测试ParticleEffectPool用例暂时屏蔽掉
        effects[currentEffect].draw(batch, delta);  // 每个delta时间就刷新一次
        
        
        for (int i = 0; i < activeEffects.size; ) {
            PooledEffect effect = activeEffects.get(i);
            
            if (effect.isComplete()) {
                pool.free((PooledEffect) effect);
                activeEffects.removeIndex(i);
            } else {
                effect.draw(batch, delta);
                ++i;
            }
        }
        batch.end();
    }
    
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
    
    @Override
    public void hide() {
        batch.dispose();
        
        for (ParticleEffect particleEffect : effects) {
            particleEffect.dispose();
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        currentEffect = (currentEffect + 1) % effects.length;
        
        PooledEffect effect = pool.obtain();
        
        if (effect != null) {
            touchPos.set(screenX, screenY, 0.0f);
            camera.unproject(touchPos);
            
            activeEffects.add(effect);
            effect.setPosition(touchPos.x, touchPos.y);
        }
        return true;
    }
    
}
