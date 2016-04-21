package com.libgdx.cookbook.chp04;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.libgdx.cookbook.help.BaseScreen;

/**
 * Detecting more complex gesture
 *
 */
public class GestureDetectorSample extends BaseScreen {

    private static final int MESSAGE_MAX = 30;
    private static final float HALF_TAP_SQUARE_SIZE = 20.0f; // (pixels) touch事件的面积，如果 finger/mouse移动的距离超过这个，event不会考虑认为是long press
    private static final float TAP_COUNT_INTERVAL = 0.4f;  // (second)连续点击屏幕的时间间隔，否则tap count会被重置
    private static final float LONG_PRESS_DURATION = 1.1f;  // (second)user has to press for the long press event to fire
    private static final float MAX_FLING_DELAY = 0.15f;
    
    private BitmapFont font;
    private GestureDetector gestureDetector;
    private Array<String> messages;
    
    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(SCENE_WIDTH * SCREEN_TO_WORLD, SCENE_HEIGHT * SCREEN_TO_WORLD, camera);
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("data/fonts/oswald-32.fnt"));
        font.getData().setScale(0.8f);
        messages = new Array<String>();
        
        camera.position.set(SCENE_WIDTH * SCREEN_TO_WORLD * 0.5f, SCENE_HEIGHT * SCREEN_TO_WORLD  * 0.5f, 0.0f);
        camera.update();
        
        font.setColor(Color.WHITE);
        font.getData().setScale(0.8f);
        // 在缺省默认的情况数值也是这些
        gestureDetector = new GestureDetector(HALF_TAP_SQUARE_SIZE, TAP_COUNT_INTERVAL, 
                                           LONG_PRESS_DURATION, MAX_FLING_DELAY, new GestureHandler());
        Gdx.input.setInputProcessor(gestureDetector);
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.39f, 0.58f, 0.92f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (int i = 0; i < messages.size; ++i) {
            font.draw(batch, messages.get(i), 20.0f, SCENE_HEIGHT * SCREEN_TO_WORLD - 30.0f * (i + 1));
        }
        batch.end();
        
        goBackMainScreen();
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
    
    // 也可以extends GestureAdapter
    private class GestureHandler implements GestureListener{

        @Override
        public boolean touchDown(float x, float y, int pointer, int button) {
            addMessage("touchDown: x(" + x + ") y(" + y + ") pointer(" + pointer + ") button(" + button +")");
            return false;
        }

        // tap轻巧  可以认为是touchUp事件
        // called every time user touches and lifts their finger without dragging it outside the tap square
        // the number of taps in the current sequence, and the button used to tap
        @Override
        public boolean tap(float x, float y, int count, int button) {
            addMessage("tap: x(" + x + ") y(" + y + ") count(" + count + ") button(" + button +")");
            return false;
        }

        @Override
        public boolean longPress(float x, float y) {
            addMessage("longPress: x(" + x + ") y(" + y + ")");
            return false;
        }

        // called when user drags and lifts their finger or the mouse
        // this receives the velocity along x and y in pixels per second
        @Override
        public boolean fling(float velocityX, float velocityY, int button) {
            addMessage("fling: velX(" + velocityX + ") velY(" + velocityY + ") button(" + button +")");
            return false;
        }

        // called when user drags a finger across the screen
        // it takes the last known screen coordinates and the deltas for both axes since the last pan event
        @Override
        public boolean pan(float x, float y, float deltaX, float deltaY) {
            addMessage("pan: x(" + x + ") y(" + y + ") deltaX(" + deltaX + ") deltaY(" + deltaY +")");
            return false;
        }

        // called when user stop panning, it takes screen coordinates, finger pointer and the button
        @Override
        public boolean panStop(float x, float y, int pointer, int button) {
            addMessage("panStop: x(" + x + ") y(" + y + ") pointer(" + pointer + ") button(" + button +")");
            return false;
        }

        // called when user performs a zoom gesture with two fingers
        @Override
        public boolean zoom(float initialDistance, float distance) {
            addMessage("zoom: initialDistance(" + initialDistance + ") distance(" + distance + ")");
            return false;
        }

        @Override
        public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
                Vector2 pointer1, Vector2 pointer2) {
            addMessage("pinch: initialP1(" + initialPointer1 + ") initialP2(" + initialPointer2 + ") p1(" + pointer1 + ") p2(" + pointer2 +")");
            return false;
        }
        
    }
    
    private void addMessage(String message) {
        messages.add(message + " time: " + System.currentTimeMillis());
        
        if (messages.size > MESSAGE_MAX) {
            messages.removeIndex(0);
        }
    }
}
