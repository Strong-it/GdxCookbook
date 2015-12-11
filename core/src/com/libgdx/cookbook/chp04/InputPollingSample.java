package com.libgdx.cookbook.chp04;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.libgdx.cookbook.help.BaseScreen;

/***
 * 主要测试Input的基本操作 Gdx.input.getX()  Gdx.input.getY() 不依赖事件触发 可随时调用
 * 其余测试主要测试鼠标和键盘对libgdx的触发, 但是这些函数都要放在render函数中
 */
public class InputPollingSample extends BaseScreen {

    private static final float scene_width = 1280f;
    private static final float scene_height = 720f;
    private BitmapFont font;
    
    @Override
    public void show() {
        System.out.println("init show");
        camera = new OrthographicCamera();
        viewport = new FitViewport(scene_width, scene_height, camera);
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("data/fonts/oswald-32.fnt")); // 字体要加载.fnt文件，不是png文件
        font.getData().setScale(0.8f);   // 缩放字体
        font.setColor(Color.WHITE);
        camera.position.set(scene_width * 0.5f, scene_height * 0.5f, 0);
        camera.update();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.39f, 0.58f, 0.92f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        
        // Mouse / touch
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();
        boolean leftPressed = Gdx.input.isButtonPressed(Buttons.LEFT); // 鼠标左键触发事件
        boolean rightPressed = Gdx.input.isButtonPressed(Buttons.RIGHT);  // 鼠标右键触发事件
        boolean middlePressed = Gdx.input.isButtonPressed(Buttons.MIDDLE); // 鼠标中键触发事件
        
        font.draw(batch, "Mouse/Touch: x=" + mouseX + "  y=" + mouseY, 20.0f, SCENE_HEIGHT - 20.0f);
        font.draw(batch, leftPressed ? "Mouse left button pressed" : "Mouse left button not pressed", 20.0f, scene_height - 50.0f);
        font.draw(batch, rightPressed ? "Mouse right button pressed" : "Mouse right button not pressed", 20.0f, scene_height - 80.0f);
        font.draw(batch, middlePressed ? "Mouse middle button pressed" : "Mouse middle button not pressed", 20.0f, scene_height - 110.0f);
        
        boolean wPressed = Gdx.input.isKeyPressed(Keys.W);
        boolean aPressed = Gdx.input.isKeyPressed(Keys.A);
        boolean sPressed = Gdx.input.isKeyPressed(Keys.S);
        boolean dPressed = Gdx.input.isKeyPressed(Keys.D);
        
        // Keys
        font.draw(batch, wPressed? "W is pressed" : "W is not pressed", 20.0f, scene_height - 160.0f);
        font.draw(batch, aPressed? "A is pressed" : "A is not pressed", 20.0f, scene_height - 190.0f);
        font.draw(batch, sPressed? "S is pressed" : "S is not pressed", 20.0f, scene_height - 220.0f);
        font.draw(batch, dPressed? "D is pressed" : "D is not pressed", 20.0f, scene_height - 250.0f);
        
        // Accelerometer
        float accelerometerX = Gdx.input.getAccelerometerX();
        float accelerometerY = Gdx.input.getAccelerometerY();
        float accelerometerZ = Gdx.input.getAccelerometerZ();
        font.draw(batch, "Accelerometer x=" + accelerometerX, 20.0f, scene_height - 300.0f);
        font.draw(batch, "Accelerometer y=" + accelerometerY, 20.0f, scene_height - 330.0f);
        font.draw(batch, "Accelerometer z=" + accelerometerZ, 20.0f, scene_height - 360.0f);
        
        // Compass
        float pitch = Gdx.input.getPitch();
        float roll = Gdx.input.getRoll();
        float azimuth = Gdx.input.getAzimuth();
        font.draw(batch, "Compass pich: " + pitch, 20.0f, scene_height - 410.0f);
        font.draw(batch, "Compass roll: " + roll, 20.0f, scene_height - 440.0f);
        font.draw(batch, "Compass azimuth: " + azimuth, 20.0f, scene_height - 470.0f);
        
        // Available systems
        boolean accelerometer = Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer);
        boolean compass = Gdx.input.isPeripheralAvailable(Peripheral.Compass);
        boolean hardwareKeyboard = Gdx.input.isPeripheralAvailable(Peripheral.HardwareKeyboard);
        boolean multitouch = Gdx.input.isPeripheralAvailable(Peripheral.MultitouchScreen);
        boolean screenKeyboard = Gdx.input.isPeripheralAvailable(Peripheral.OnscreenKeyboard);
        boolean vibrator = Gdx.input.isPeripheralAvailable(Peripheral.Vibrator);
        
        font.draw(batch, accelerometer ? "Accelerometer available" : "Accelerometer unavailable", 20.0f, scene_height - 520.0f);
        font.draw(batch, compass ? "Compass available" : "Compass unavailable", 20.0f, scene_height - 550.0f);
        font.draw(batch, hardwareKeyboard ? "Hardware keyboard available" : "Hardware keyboard unavailable", 20.0f, scene_height - 580.0f);
        font.draw(batch, multitouch ? "Multitouch available" : "Multitouch unavailable", 20.0f, scene_height - 610.0f);
        font.draw(batch, screenKeyboard ? "On screen keyboard available" : "On screen keyboard unavailable", 20.0f, scene_height - 640.0f);
        font.draw(batch, vibrator ? "Vibrator available" : "Vibrator unavailable", 20.0f, scene_height - 670.0f);
        
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

}
