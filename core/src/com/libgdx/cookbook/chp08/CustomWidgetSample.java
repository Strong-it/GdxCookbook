package com.libgdx.cookbook.chp08;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData.TextureDataType;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.libgdx.cookbook.help.BaseScreen;
import com.libgdx.cookbook.scene2d.LevelSelector;

/**
 * customUI.json文件里面有LevelSelectorStyle，如果修改了包名要修改此文件里面的路径
 *
 */
public class CustomWidgetSample extends BaseScreen {

    private static final String TAG = "Custom Widget";
    private static final int SCENE_WIDTH = 1280;
    private static final int SCENE_HEIGHT = 720;
    
    private Skin skin;
    
    private Table table;
    private Stage stage;
    
    LevelSelector levelSelector;
    Texture jungleTex, mountainsTex;
    
    @Override
    public void show() {
        
        viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT);
        batch = new SpriteBatch();
        
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("data/scene2d/customUI.json"));
        
        // Create table
        table = new Table();
        
        //Level selection menu
        Label level_menu = new Label("Level Selection Menu", skin);
        
        jungleTex = new Texture(Gdx.files.internal("data/jungle-level.png"));
        Gdx.app.log(TAG, "type:" + (jungleTex.getTextureData().equals(TextureDataType.Pixmap) ? "Pixmap" : "Custom"));
        mountainsTex = new Texture(Gdx.files.internal("data/blur/mountains.png"));
        
        // Populate level container
        Array<LevelSelector.Level> levels = new Array<LevelSelector.Level>();
        
        LevelSelector.Level level1 = new LevelSelector.Level("Level1", skin);
        level1.setImage( new Image(new TextureRegionDrawable(new TextureRegion(jungleTex))));
        
        LevelSelector.Level level2 = new LevelSelector.Level("Level2", skin);
        level2.setImage(new Image(new TextureRegionDrawable(new TextureRegion(mountainsTex))));
        
        LevelSelector.Level level3 = new LevelSelector.Level("Level3", skin);
        level3.setImage(new Image(new TextureRegionDrawable(new TextureRegion(jungleTex))));
        
        levels.addAll(level1, level2, level3);
        
        levelSelector = new LevelSelector(skin);
        levelSelector.addLevels(levels);
        
        table.row();
        table.add(level_menu).padBottom(20f);
        table.row();
        table.add(levelSelector);
        
        table.setFillParent(true);
        table.pack();
        
        stage.addActor(table);
        
        // Start game button listener
        levelSelector.getButton().addListener( new ClickListener() {             
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log(TAG, "START LEVEL " + levelSelector.getCurrentLevel());
            };
        });
        
        table.debug();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(delta, 1 / 60f));
        stage.draw();
        
        if (Gdx.input.isKeyJustPressed(Keys.B)) {
            goMainScreen();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void hide() {
        jungleTex.dispose();
        mountainsTex.dispose();
        batch.dispose();
        skin.dispose();
        stage.dispose();
    }
   
}
