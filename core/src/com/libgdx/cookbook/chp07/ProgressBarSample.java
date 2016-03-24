package com.libgdx.cookbook.chp07;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.libgdx.cookbook.help.BaseScreen;

/**
 * AssestManager异步加载资源，一般会先加载动画条
 * 1.先加载动画条资源，加载结束时再画出来
 * 2.加载游戏其它资源，比如游戏图片，游戏音效，游戏粒子系统
 */
public class ProgressBarSample extends BaseScreen {
    
    private static final String TAG = ProgressBarSample.class.getSimpleName();
    
    private Texture background, logo, progressBarImg, progressBarBaseImg;
    private TiledMap map;
    
    private float unitScale;
    private OrthogonalTiledMapRenderer renderer;
    
    private Vector2 logoPos, pbPos;
    
    private AssetManager manager;

    @Override
    public void show() {
        batch = new SpriteBatch();
        manager = new AssetManager();
        
        // Load assets, it is being loaded a map because it is a good example for dependencies between
        // assets, so don't focus on the map itself considering that it will be explained in later recipes
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("data/loading_screen/map.tmx", TiledMap.class);
        unitScale = 1/16f;
        
        manager.load("data/loading_screen/background.png", Texture.class);
        manager.load("data/loading_screen/logo.png", Texture.class);
        manager.load("data/loading_screen/progress_bar.png", Texture.class);
        manager.load("data/loading_screen/progress_bar_base.png", Texture.class);
        manager.finishLoading(); // Blocks until all resources are loaded into memory
        Gdx.app.log(TAG, "Assets loaded");
        
        // Get Assets
        background = manager.get("data/loading_screen/background.png");
        map = manager.get("data/loading_screen/map.tmx");
        logo = manager.get("data/loading_screen/logo.png");
        progressBarImg = manager.get("data/loading_screen/progress_bar.png");
        progressBarBaseImg = manager.get("data/loading_screen/progress_bar_base.png");
        
        renderer = new OrthogonalTiledMapRenderer(map, unitScale);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 80, 45);
        camera.update();
        
        // Get logo position
        logoPos = new Vector2();
        // >> bitwise operator bill just divide by 2, the explicitly written times, in this case 1
        logoPos.set((Gdx.graphics.getWidth()-logo.getWidth())>>1, Gdx.graphics.getHeight()>>1);

        // ProgressBar position
        pbPos = new Vector2();
        pbPos.set(logoPos.x, logoPos.y - (logo.getHeight()));
        
        // Load assets for the next screen (random assets example)
        manager.load("data/caveman.png", Texture.class);
        manager.load("data/facebook.png", Texture.class);
        manager.load("data/jungle-level.png", Texture.class);
        manager.load("data/libgdx.png", Texture.class);
        manager.load("data/menu.png", Texture.class);
        manager.load("data/star.png", Texture.class);
        manager.load("data/prehistoric.png", Texture.class);
        manager.load("data/trex-sheet.png", Texture.class);
        manager.load("data/uiskin.png", Texture.class);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Render background image
        batch.begin();
        batch.draw(background, 0, 0);
        batch.end();
        
        // Render map
        renderer.setView(camera);
        renderer.render();
        
        // Render Logo and Loading Bar
        batch.begin();
        batch.draw(logo, logoPos.x, logoPos.y);
        batch.draw(progressBarBaseImg, pbPos.x, pbPos.y);
        batch.draw(progressBarImg, pbPos.x, pbPos.y, progressBarImg.getWidth()*manager.getProgress(), progressBarImg.getHeight());  // 资源加载进度manager.getProgress()
        batch.end();
        
        if (manager.update()) { // 判断游戏所有资源是否加载完毕
            //Go to Game-screen
        }
        
        if (Gdx.input.isKeyJustPressed(Keys.B)) {
            goMainScreen();
        }
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        super.resize(width, height);
    }

    @Override
    public void hide() {
        manager.dispose();
        renderer.dispose();
        batch.dispose();
    }

}
