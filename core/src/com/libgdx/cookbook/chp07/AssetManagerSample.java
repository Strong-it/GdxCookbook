package com.libgdx.cookbook.chp07;

import com.badlogic.gdx.Gdx;
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
 * Assetmanager加载资源
 *
 */
public class AssetManagerSample extends BaseScreen {

    private static final String TAG = AssetManagerSample.class.getSimpleName();
    
    Texture background, logo;
    TiledMap map;
    AssetManager assetManager;
    OrthogonalTiledMapRenderer renderer;
    Vector2 logoPos;
    
    float unitScale;
    
    @Override
    public void show() {
        batch = new SpriteBatch();
        assetManager = new AssetManager();  // loading any kind of built-in Libgdx asset type, such as Texture, BitmapFont,
                                            // TextureAtlas, Music, Sound, Skin, and ParticleEffect
        
        // Load assets, it is being loaded a map because it is a good example for dependencies between
        // assets, so don't focus on the map itself considering that it will be explained in later recipes
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver())); // TiledMap不是基本类型，因此要设置loader
        assetManager.load("data/loading_screen/map.tmx", TiledMap.class);  
        unitScale = 1/16f;
        
        assetManager.load("data/loading_screen/background.png", Texture.class);
        assetManager.load("data/loading_screen/logo.png", Texture.class);
        assetManager.finishLoading(); // Blocks until all resources are loaded into memory
        Gdx.app.log(TAG, "Assets loaded");
        
        // Get Assets  与get (String fileName, Class<T> type)，下面方法更简洁，但是耗时间
        background = assetManager.get("data/loading_screen/background.png");  
        map = assetManager.get("data/loading_screen/map.tmx");
        logo = assetManager.get("data/loading_screen/logo.png");
        
        renderer = new OrthogonalTiledMapRenderer(map, unitScale);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 80, 45);  // 80, 45相当于设置了viewport,虽然resize里面没有设置，但是看起来缩放时跟着缩放
        camera.update();
        
        // Get logo position
        logoPos = new Vector2();
        // >> bitwise operator bill just divide by 2, the explicitly written times, in this case 1
        logoPos.set((Gdx.graphics.getWidth()-logo.getWidth())>>1, Gdx.graphics.getHeight()>>1);
        
        // Trace dependences 
        Gdx.app.log(TAG + ".Dependences:\n", assetManager.getDiagnostics());
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
        
        // Render logo
        batch.begin();
        batch.draw(logo, logoPos.x, logoPos.y);
        batch.end();
    }

    @Override
    public void hide() {
       assetManager.dispose();
       renderer.dispose();
       batch.dispose();
    }

}
