package com.libgdx.cookbook.chp02;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.libgdx.cookbook.help.BaseScreen;
import com.libgdx.cookbook.help.DebugTool;

/**
 * Taking advantage of Libgdx sprites
 *
 */
public class SpriteSample extends BaseScreen {

    private TextureAtlas atlas;
    private Sprite background;
    private Sprite dinosaur;
    private Sprite caveman;
    private Array<Color> colors;
    private int currentColor;
    
    private Vector3 tmp;
    
    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(SCENE_WIDTH * SCREEN_TO_WORLD, SCENE_HEIGHT * SCREEN_TO_WORLD, camera);
        batch = new SpriteBatch();
        debugTool = new DebugTool(camera);
        
        atlas = new TextureAtlas(Gdx.files.internal("data/prehistoric.atlas"));
        background = new Sprite(atlas.findRegion("background"));
        caveman = new Sprite(atlas.findRegion("caveman"));
        dinosaur = new Sprite(atlas.findRegion("trex"));
        
        background.setPosition(-background.getWidth() * 0.5f, -background.getHeight() * 0.5f);
        caveman.setOrigin(caveman.getWidth() * 0.5f, caveman.getHeight() * 0.5f);
        dinosaur.setPosition(100.0f, -85.0f);
        
        currentColor = 0;
        colors = new Array<Color>();
        colors.add(new Color(Color.WHITE));
        colors.add(new Color(Color.WHITE));
        colors.add(new Color(0.0f, 0.0f, 0.0f, 1.0f));
        colors.add(new Color(1.0f, 0.0f, 0.0f, 1.0f));
        colors.add(new Color(0.0f, 1.0f, 0.0f, 1.0f));
        colors.add(new Color(0.0f, 0.0f, 1.0f, 1.0f));
        
        tmp = new Vector3();
        
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Rectangle cavemanRect = caveman.getBoundingRectangle();
        Rectangle dinosaurRect = dinosaur.getBoundingRectangle();
        boolean overlap = cavemanRect.overlaps(dinosaurRect);
        
        tmp.set(Gdx.input.getX(), Gdx.input.getY(), 0);  // 每次鼠标在屏幕上划过时就会触发该函数
        camera.unproject(tmp);
        caveman.setPosition(tmp.x - caveman.getWidth() * 0.5f, tmp.y - caveman.getHeight() * 0.5f);
        
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.setProjectionMatrix(camera.combined);
        
        batch.begin();
        background.draw(batch);
//        debugTool.drawGrid(SCENE_WIDTH * SCREEN_TO_WORLD,  SCENE_HEIGHT * SCREEN_TO_WORLD);
        caveman.draw(batch);
        dinosaur.draw(batch);
        
        batch.end();
        
        if (overlap) {
            debugTool.shapeRenderer.setProjectionMatrix(camera.combined);
            debugTool.shapeRenderer.setColor(1.0f, 0.0f, 0.0f, 1.0f);
            debugTool.shapeRenderer.begin(ShapeType.Line);
            debugTool.shapeRenderer.rect(cavemanRect.x, cavemanRect.y, cavemanRect.width, cavemanRect.height);
            debugTool.shapeRenderer.rect(dinosaurRect.x, dinosaurRect.y, dinosaurRect.width, dinosaurRect.height);
            debugTool.shapeRenderer.end();
        }
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void hide() {
        atlas.dispose();
        batch.dispose();
        debugTool.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Buttons.LEFT) {
            currentColor = (currentColor + 1) % colors.size;
            dinosaur.setColor(colors.get(currentColor));
        }
        
        return true;
    }

    @Override
    public boolean scrolled(int amount) {  // 触发鼠标中间滚轮事件
        if (Gdx.input.isButtonPressed(Buttons.RIGHT)) {
            caveman.scale(amount * 0.5f);
        }
        else {
            caveman.rotate(amount * 5.0f);
        }
        
        return true;
    }
    
}
