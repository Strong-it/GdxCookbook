package com.libgdx.cookbook.chp04;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.libgdx.cookbook.help.BaseScreen;
import com.libgdx.cookbook.inputmapping.InputActionListener;
import com.libgdx.cookbook.inputmapping.InputContext;
import com.libgdx.cookbook.inputmapping.InputProfile;

/**
 * Input mapping for cross-platform development
 *
 */
public class InputMappingSample extends BaseScreen implements
        InputActionListener {

    private static final int MEASSAGE_MAX = 15;
    
    private BitmapFont font;
    private InputProfile profile;
    private InputContext gameContext;
    
    private Array<String> messages;
    
    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(SCENE_WIDTH * SCREEN_TO_WORLD, SCENE_HEIGHT * SCREEN_TO_WORLD, camera);
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2.0f);
        font.setColor(Color.WHITE);
        messages = new Array<String>();
        
        camera.position.set(SCENE_WIDTH * SCREEN_TO_WORLD * 0.5f, SCENE_HEIGHT * SCREEN_TO_WORLD  * 0.5f, 0.0f);
        camera.update();
        
        profile = new InputProfile(Gdx.files.internal("data/input/profile.xml")); // 会将MainMenu 和 Game都解析出来
        
        profile.setContext("Game");  // 设置context为Game
        gameContext = profile.getContext();
        
        gameContext.addListener(this);
        
        Gdx.input.setInputProcessor(profile);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.39f, 0.58f, 0.92f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        
        font.draw(batch, gameContext.getState("Crouch") ? "crouching" : "not crouching", 50.0f, SCENE_HEIGHT * SCREEN_TO_WORLD - 20.0f);
        font.draw(batch, gameContext.getState("LookUp") ? "looking up" : "not looking up", 50.0f, SCENE_HEIGHT * SCREEN_TO_WORLD - 50.0f);
        font.draw(batch, gameContext.getState("MoveRight") ? "moving right" : "not moving right", 50.0f, SCENE_HEIGHT * SCREEN_TO_WORLD - 80.0f);
        font.draw(batch, gameContext.getState("MoveLeft") ? "moving left" : "not moving left", 50.0f, SCENE_HEIGHT * SCREEN_TO_WORLD - 110.0f);
        
        int numMessages = messages.size;
        for (int i = 0; i < numMessages; ++i) {
            font.draw(batch, messages.get(i), 50.0f, SCENE_HEIGHT * SCREEN_TO_WORLD - 160.0f - 30.0f * i);
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
        font.dispose();
    }

    @Override
    public boolean OnAction(String action) {
        addMessage("Action -> " + action);
        return false;
    }

    private void addMessage(String message) {
        messages.add(message);
        
        if (messages.size > MEASSAGE_MAX) {
            messages.removeIndex(0);
        }
    }
}
