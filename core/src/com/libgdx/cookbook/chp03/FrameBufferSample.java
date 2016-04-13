package com.libgdx.cookbook.chp03;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.libgdx.cookbook.help.BaseScreen;

/**
 * Frame buffers and image composition
 *
 */
public class FrameBufferSample extends BaseScreen {

    private enum GalleryState {
        PICTURE,
        TRANSITIONING,
    }
    
    private final String TAG = FrameBufferSample.class.getSimpleName();
    
    private static final int VIRTUAL_WIDTH = 1280;
    private static final int VIRTUAL_HEIGHT = 720;
    
    private static final int GALLERY_NUM_PICTURES = 4;
    private static final float GALLERY_PICTURE_TIME = 3.0f;
    private static final float GALLERY_TRANSITION_TIME = 2.0f; // 过渡时间要比显示时间少，这样才能突出显示
    
    private TextureRegion[] gallery;
    private FrameBuffer currentFrameBuffer;
    private FrameBuffer nextFrameBuffer;
    
    private int currentPicture;
    private float time;
    private GalleryState state;
    
    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, camera);
        batch = new SpriteBatch();
        
        gallery = new TextureRegion[GALLERY_NUM_PICTURES];
        
        for (int i = 0; i < GALLERY_NUM_PICTURES; ++i) {
            gallery[i] = new TextureRegion(new Texture(Gdx.files.internal("data/gallery/gallery" + (i + 1) + ".jpg")));
        }
        
        currentFrameBuffer = new FrameBuffer(Format.RGB888, VIRTUAL_WIDTH, VIRTUAL_HEIGHT, false);
        nextFrameBuffer = new FrameBuffer(Format.RGB888, VIRTUAL_WIDTH, VIRTUAL_HEIGHT, false);
        
        currentPicture = 0;
        time = 0.0f;
        state = GalleryState.PICTURE;
        
        viewport.apply(true);
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        
        time += delta;
        
        switch (state) {
        case PICTURE:
            updateStatePicture();
            break;
        case TRANSITIONING:
            updateStateTransitioning();
            break;
        }
        
        if (Gdx.input.isKeyJustPressed(Keys.B)) {
            goMainScreen();
        }
    }
    
    private void updateStatePicture() {
        batch.begin();
        drawRegion(gallery[currentPicture]);
        batch.end();
        
        if (time > GALLERY_PICTURE_TIME) {
            time = 0.0f;
            state = GalleryState.TRANSITIONING;
            
            TextureRegion region;
            region = gallery[currentPicture];
            region.flip(false, true);

            currentFrameBuffer.bind();   // tell OpenGL that an FBO is now the active render target
            
            Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            
            batch.begin();
            drawRegion(region);
            batch.end();
            
            region.flip(false, true);
            
            currentPicture = (currentPicture + 1) % GALLERY_NUM_PICTURES; // gallery.length
            
            region = gallery[currentPicture];
            region.flip(false, true);
            
            nextFrameBuffer.bind();
            
            Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            
            batch.begin();
            drawRegion(gallery[currentPicture]);
            batch.end();
            
            FrameBuffer.unbind();      // go back to rendering on the screen
            region.flip(false, true);
        }
    }

    private void updateStateTransitioning() {
        float alpha = Math.min( time / GALLERY_TRANSITION_TIME, 1.0f);
        
        batch.begin();
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f - alpha);
        drawTexture(currentFrameBuffer.getColorBufferTexture());
        
        batch.setColor(1.0f, 1.0f, 1.0f, alpha);
        drawTexture(nextFrameBuffer.getColorBufferTexture());
        batch.end();
        
        if (time > GALLERY_TRANSITION_TIME) {
            time = 0.0f;
            state = GalleryState.PICTURE;
        }
    }
    
    private void drawRegion(TextureRegion region) {
        int width = region.getRegionWidth();
        int height = region.getRegionHeight();
        
        batch.draw(region,
                   0.0f, 0.0f,
                   0.0f, 0.0f,
                   VIRTUAL_WIDTH, height,
                   WORLD_TO_SCREEN, WORLD_TO_SCREEN,
                   0.0f);
    }

    private void drawTexture(Texture texture) {
        int width = texture.getWidth();
        int height = texture.getHeight();
        
        batch.draw(texture,
                0.0f, 0.0f,
                0.0f, 0.0f,
                width, height,
                WORLD_TO_SCREEN, WORLD_TO_SCREEN,
                0.0f,
                0, 0,
                width, height,
                false, false);
    }
    
    @Override
    public void hide() {
        batch.dispose();
        
        for (TextureRegion textureRegion : gallery) {
            textureRegion.getTexture().dispose();
        }
        
        currentFrameBuffer.dispose();
        nextFrameBuffer.dispose();
    }
}
