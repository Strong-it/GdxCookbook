package com.libgdx.cookbook.chp09;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.libgdx.cookbook.help.BaseScreen;

public class TiledMapSample extends BaseScreen {

    private final String TAG = TiledMapSample.class.getSimpleName();
    
    private static final float VIRTUAL_WIDTH = 384.0f;
    private static final float VIRTUAL_HEIGHT = 216.0f; 
    
    private static final float CAMERA_SPEED = 100.0f;
    
    private TiledMap map;         // 容器装载txml文件类型   Represents a tiled map
    private TmxMapLoader loader;  // 加载解析tmx文件
    private OrthogonalTiledMapRenderer renderer;  // TiledMap需要此Render来渲染  batch需要ShapeRender渲染
    
    private Vector2 direction;

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        
        loader = new TmxMapLoader();
        map = loader.load("data/maps/tiled.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        
        direction = new Vector2();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        updateCamera();
        
        renderer.setView(camera);   // 设置render的摄像机
        renderer.render();  // go over all visible tile layers, drawing them from bottom to top
        
        if (Gdx.input.isKeyJustPressed(Keys.B)) {
            goMainScreen();
        }
    }

    private void updateCamera() {
        direction.set(0.0f, 0.0f);
        
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        
        if (Gdx.input.isKeyPressed(Keys.LEFT) || (Gdx.input.isTouched() && mouseX < width * 0.25f)) {
            direction.x = -1;
        }
        else if (Gdx.input.isKeyPressed(Keys.RIGHT) || (Gdx.input.isTouched() && mouseX > width * 0.75f)) {
            direction.x = 1;
        }
        
        if (Gdx.input.isKeyPressed(Keys.UP) || (Gdx.input.isTouched() && mouseY < height * 0.25f)) {
            direction.y = 1;
        }
        else if (Gdx.input.isKeyPressed(Keys.DOWN) || (Gdx.input.isTouched() && mouseY > height * 0.75f)) {
            direction.y = -1;
        }
        
        direction.nor().scl(CAMERA_SPEED * Gdx.graphics.getDeltaTime());  // 单位移动距离
        
        camera.position.x += direction.x;
        camera.position.y += direction.y;
        
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(0);
        
        // viewport.getWorldWidth() = VIRTUAL_WIDTH
        float cameraMinX = viewport.getWorldWidth() * 0.5f;
        float cameraMinY = viewport.getWorldHeight() * 0.5f;
        float cameraMaxX = layer.getWidth() * layer.getTileWidth() - cameraMinX;
        float cameraMaxY = layer.getHeight() * layer.getTileHeight() - cameraMinY;
        
        camera.position.x = MathUtils.clamp(camera.position.x, cameraMinX, cameraMaxX);
        camera.position.y= MathUtils.clamp(camera.position.y, cameraMinY, cameraMaxY);
        
        camera.update();
    }
    
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void hide() {
        map.dispose();
        renderer.dispose();
    }
}
