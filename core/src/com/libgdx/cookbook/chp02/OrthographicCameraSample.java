package com.libgdx.cookbook.chp02;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.libgdx.cookbook.help.BaseScreen;

/**
 * Understanding orthographic camera
 * OrthographicCamera(正交摄像机的基本操作)
 * HUD(heads up display)显示游戏界面的基本元素，保持元素位置保持不变
 *
 */
public class OrthographicCameraSample extends BaseScreen {

    private static final float CAMERA_SPEED = 2.0f;
    private static final float CAMERA_ZOOM_SPEED = 2.0F;
    private static final float CAMERA_ZOOM_MAX = 1.0f;
    private static final float CAMERA_ZOOM_MIN = 0.01f;
    private static final float CAMERA_MOVE_EDGE = 0.2f;
    
    private OrthographicCamera cameraHUD;
    private Viewport viewportHUD;
    private Texture hudTexture;
    private Texture levelTexture;
    private Vector3 touch;
    
    @Override
    public void show() {
        cameraHUD = new OrthographicCamera();
        camera = new OrthographicCamera();
        viewportHUD = new FitViewport(SCENE_WIDTH * SCREEN_TO_WORLD, SCENE_HEIGHT * SCREEN_TO_WORLD, cameraHUD);
        viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, camera);
        batch = new SpriteBatch();
        touch = new Vector3();
        
        hudTexture = new Texture(Gdx.files.internal("data/facebook.png"));
        levelTexture = new Texture(Gdx.files.internal("data/jungle-level.png"));
        hudTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        levelTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        
        cameraHUD.position.set(SCENE_WIDTH * SCREEN_TO_WORLD * 0.5f, SCENE_HEIGHT * SCREEN_TO_WORLD * 0.5f, 0);
        camera.position.set(SCENE_WIDTH * 0.5f, SCENE_HEIGHT * 0.5f, 0);
        cameraHUD.update();
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Arrow keys move the camera
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            camera.position.x -= CAMERA_SPEED * delta;
        } else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            camera.position.x += CAMERA_SPEED * delta;
        } else if (Gdx.input.isKeyPressed(Keys.UP)) {
            camera.position.y += CAMERA_SPEED * delta;
        } else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            camera.position.y -= CAMERA_SPEED * delta;
        }
        
        // Touching on the edges also moves the camera
        if (Gdx.input.isTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0.0f);
            camera.unproject(touch);
            
            if (touch.x > SCENE_WIDTH * ( 1.0f - CAMERA_MOVE_EDGE)) {
                camera.position.x += CAMERA_SPEED * delta;
            } else if(touch.x < SCENE_WIDTH * CAMERA_MOVE_EDGE) {
                camera.position.x -= CAMERA_SPEED * delta;
            }
            
            if (touch.y > SCENE_HEIGHT * (1.0f - CAMERA_MOVE_EDGE)) {
                camera.position.y += CAMERA_SPEED * delta;
            } else if (touch.y < SCENE_HEIGHT * CAMERA_MOVE_EDGE) {
                camera.position.y -= CAMERA_SPEED * delta; 
            }
        }
        
        // Page up/down control the zoom
        if (Gdx.input.isKeyPressed(Keys.PAGE_UP)) {
            camera.zoom -= CAMERA_ZOOM_SPEED * delta;
        }
        else if (Gdx.input.isKeyPressed(Keys.PAGE_DOWN)) {
            camera.zoom += CAMERA_ZOOM_SPEED * delta;
        }
        
        // Clamp position
        float halfWidth = SCENE_WIDTH * 0.5f;
        float halfHeight = SCENE_HEIGHT * 0.5f;
        
        camera.position.x = MathUtils.clamp(camera.position.x, halfWidth * camera.zoom, 
                                        levelTexture.getWidth() * WORLD_TO_SCREEN - halfWidth * camera.zoom);
        camera.position.y = MathUtils.clamp(camera.position.y, halfHeight * camera.zoom,
                                        levelTexture.getHeight() * WORLD_TO_SCREEN - halfHeight * camera.zoom);
        // Clamp zoom
        camera.zoom = MathUtils.clamp(camera.zoom, CAMERA_ZOOM_MIN, CAMERA_ZOOM_MAX);
        
        // Log position and zoom
        Gdx.app.log("position", camera.position.toString());
        Gdx.app.log("zoom", Float.toString(camera.zoom));
        
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        
        batch.begin();
        batch.draw(levelTexture, 
                    0.0f, 0.0f, 
                    0.0f, 0.0f, 
                    levelTexture.getWidth(), levelTexture.getHeight(), 
                    WORLD_TO_SCREEN, WORLD_TO_SCREEN, 
                    0.0f, 
                    0, 0, 
                    levelTexture.getWidth(), levelTexture.getHeight(), 
                    false, false);
        batch.end();
        
        // Render UI elements
        batch.setProjectionMatrix(cameraHUD.combined);
        batch.begin();
        batch.draw(hudTexture, 20, 20);
        batch.end();
        
        goBackMainScreen();
    }
    
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        viewportHUD.update(width, height);
    }
    
    @Override
    public void hide() {
        batch.dispose();
        levelTexture.dispose();
        hudTexture.dispose();
    }
}
