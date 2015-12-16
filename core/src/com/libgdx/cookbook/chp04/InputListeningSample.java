package com.libgdx.cookbook.chp04;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.libgdx.cookbook.help.BaseScreen;

/**
 * Defecting user input via event listening
 * Event Poll 是主动查询事件的触发
 * Event Listener 是被动事件触发，主要是Listener应用应用了Observer模式 当有事件发生时会主动notify
 * 
 * libgdx 并不是继承自不同的Activity，所以当按下Back和Menu的时候，会退出程序，因为我们需要捕捉事件处理
 */
public class InputListeningSample extends BaseScreen {

    public static final int MESSAGE_MAX = 45;
    
    private Array<String> messages;
    private BitmapFont font;
    
    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(SCENE_WIDTH * SCREEN_TO_WORLD, SCENE_HEIGHT * SCREEN_TO_WORLD, camera);
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("data/fonts/oswald-32.fnt"));
        messages = new Array<String>();  // 虽然是泛型如果是声明对象new (), 如果是数组 new [];
        
        font.setColor(Color.WHITE);
        camera.position.set(SCENE_WIDTH * SCREEN_TO_WORLD * 0.5f, SCENE_HEIGHT * SCREEN_TO_WORLD * 0.5f, 0);
        camera.update();
        
        Gdx.input.setInputProcessor(this); // 此方法一次只能是一个Input processor有效
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.39f, 0.58f, 0.92f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (int i = 0; i < messages.size; ++i) {
            font.draw(batch, messages.get(i), 20.0f, SCENE_HEIGHT * SCREEN_TO_WORLD - 40.0f * (i + 1));
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
    public boolean keyDown(int keycode) {
        addMessage("keyDown: keycode(" + keycode + ")");
        
        if (keycode == Keys.BACK) {
            // TODO: 处理返回事件
        } else if (keycode == Keys.MENU) {
            // TODO: 处理菜单事件
        }
        
        return true;
    }
    
    @Override
    public boolean keyUp(int keycode) {
        addMessage("keyUp: keycode(" + keycode + ")");
        return true;
    }
    
    @Override
    public boolean keyTyped(char character) {  // 可以输出按键的字母和数字，不过貌似不好使
        addMessage("keyTyped: character(" + character + ")");
        return true;
    }
    
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        addMessage("touchDown: screenX(" + screenX + ") screenY(" + screenY + ") pointer(" + pointer + ") button(" + button + ")");
        return true;
    }
    
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        addMessage("touchUp: screenX(" + screenX + ") screenY(" + screenY + ") pointer(" + pointer + ") button(" + button + ")");
        return true;
    }
    
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        addMessage("touchDragged: screenX(" + screenX + ") screenY(" + screenY + ") pointer(" + pointer + ")");
        return true;
    }
    
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        addMessage("mouseMoved: screenX(" + screenX + ") screenY(" + screenY + ")");
        return true;
    }
    
    @Override
    public boolean scrolled(int amount) {
        addMessage("scrolled: amount(" + amount + ")");
        return true;
    }
    
    private void addMessage(String message) {
        messages.add(message + "time: " + System.currentTimeMillis());
        
        if (messages.size > MESSAGE_MAX) {
            messages.removeIndex(0);
        }
    }
}
