package com.libgdx.cookbook.chp08;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.libgdx.cookbook.help.BaseScreen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class MainMenuSample extends BaseScreen {

    private static final String TAG = MainMenuSample.class.getSimpleName();
    private static final int VIRTUAL_WIDTH = 1280;
    private static final int VIRTUAL_HEIGHT = 720;
    
    private Image gameTitle, hamsty1, hamsty2;
    private TextButton btnPlay, btnSettings, btnExit;
    private Slider slider;
    
    private BitmapFont font;
    
    private Table table;
    private Stage stage;
    
    private Texture gameTitleTex, hamsty1Tex, hamsty2Tex, buttonUpTex, 
    buttonDownTex, buttonOverTex, sliderBackgroundTex, sliderKnobTex; 
    
    @Override
    public void show() {
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        font = new BitmapFont(Gdx.files.internal("data/font.fnt"));
        
        // Title Image
        gameTitleTex = new Texture(Gdx.files.internal("data/scene2d/gameTitle.png"));
        gameTitle = new Image(new TextureRegionDrawable(new TextureRegion(gameTitleTex)));
        
        // Hamster Image
        hamsty1Tex = new Texture(Gdx.files.internal("data/scene2d/hamsty.png"));
        hamsty2Tex = new Texture(Gdx.files.internal("data/scene2d/hamsty2.png"));
        hamsty1 = new Image(new TextureRegionDrawable(new TextureRegion(hamsty1Tex)));
        hamsty2 = new Image(new TextureRegionDrawable(new TextureRegion(hamsty2Tex)));
        
        // Set buttons' style
        buttonUpTex = new Texture(Gdx.files.internal("data/scene2d/myactor.png"));
        buttonOverTex = new Texture(Gdx.files.internal("data/scene2d/myactorOver.png"));
        buttonDownTex = new Texture(Gdx.files.internal("data/scene2d/myactorDown.png"));
        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
        tbs.font = font;
        tbs.up = new TextureRegionDrawable(new TextureRegion(buttonUpTex));
        tbs.over = new TextureRegionDrawable(new TextureRegion(buttonOverTex));
        tbs.down = new TextureRegionDrawable(new TextureRegion(buttonDownTex));
        
        //Define buttons
        btnPlay = new TextButton("PLAY", tbs);
        btnSettings = new TextButton("SETTINGS", tbs);
        btnExit = new TextButton("EXIT", tbs);
        
        //Slider
        sliderBackgroundTex =new Texture(Gdx.files.internal("data/scene2d/slider_background.png"));
        sliderKnobTex = new Texture(Gdx.files.internal("data/scene2d/slider_knob.png"));
        Slider.SliderStyle ss = new Slider.SliderStyle();
        ss.background = new TextureRegionDrawable(new TextureRegion(sliderBackgroundTex));
        ss.knob = new TextureRegionDrawable(new TextureRegion(sliderKnobTex));
        slider = new Slider(0f, 100f, 1f, false, ss);
        
        // Create table
        table = new Table();
        table.debug(); //Enables debug
        
        // Set table structure  整个蓝线是Stage world
        // the difference between add() and addActor(), as when using the former, the actors' position,
        // size, and so on will be ignored when it is put inside a table
        table.padTop(10f);
        table.row();    // row之间也有间隙
        table.add(gameTitle).padTop(30f).colspan(2).expand(); // colspan(1)那么只有一个cell，不会占据整个X
        table.row();
        table.add(hamsty1).padTop(10f).expandY().uniform();
        table.add(hamsty2).padTop(10f).expandY().uniform();
        table.row();
        table.add(btnPlay).padTop(10f).colspan(2);
        table.row();
        table.add(btnSettings).padTop(10f).colspan(2);
        table.row();
        table.add(btnExit).padTop(30f).colspan(2);
        table.row();
        table.add(slider).bottom().colspan(2).expandY();
        table.padBottom(30f);
        
        // Pack table
        table.setFillParent(true);  // 如果不设置此属性那么居左显示
        table.pack();
        
        // Set table's alpha to 0
        table.getColor().a = 0f;

        // Play button listener
        btnPlay.addListener( new ClickListener() {             
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log(TAG, "PLAY");
            };
        });
        
        // Settings button listener
        btnSettings.addListener( new ClickListener() {             
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log(TAG, "SETTINGS");
            };
        });
        
        // Exit button listener
        btnExit.addListener( new ClickListener() {             
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log(TAG, "EXIT");
                Gdx.app.exit();
            };
        });
        
        // Slider listener
        slider.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log(TAG, "slider changed to: " + slider.getValue());
                // Set volume to slider.getValue();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                // If touchDown returns true, the listener is ready to receive touchUp and
                // touchDragged events until the first one is executed. It will also prevent th
                // event from feeding other listeners outside the stage.
                return true;
            };
        });
        
        // Adds created table to stage
        stage.addActor(table);

        // To make the table appear smoothly
        table.addAction(fadeIn(2f));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void hide() {
        gameTitleTex.dispose();
        hamsty1Tex.dispose();
        hamsty2Tex.dispose();
        buttonUpTex.dispose();
        buttonDownTex.dispose();
        buttonOverTex.dispose();
        sliderBackgroundTex.dispose();
        sliderKnobTex.dispose(); 
        
        font.dispose();
        stage.dispose();
    }

}
