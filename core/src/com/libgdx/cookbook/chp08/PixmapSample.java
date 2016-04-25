package com.libgdx.cookbook.chp08;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.libgdx.cookbook.help.BaseScreen;

public class PixmapSample extends BaseScreen {

    private static final String TAG = PixmapSample.class.getSimpleName();
    Texture texture;
    Pixmap pixmap;
    FileHandle file = Gdx.files.local("bin/img/pixmap.cim");
    
    @Override
    public void show() {
        batch = new SpriteBatch();
        /**
        *RGBA8888 - This is the format we described in the earlier Primer section. Each channel (R, G, B, A) is made up of a byte, or 8 bits. With this format, we have four bytes per pixel. We would use this for high-quality color that requires an alpha channel, for transparency. This is known as "True Color".
        *RGB888 - This is similar to the above, but discards the alpha channel (i.e. the image is opaque). This is useful for high-quality images that don't need an alpha channel.
        *RGBA4444 - This is similar to RGBA8888, but stores each channel in only 4 bits. This leads to lower color quality, but has performance and memory implications on low-end devices like Android and iOS.
        *RGB565 - This stores the red channel in 5 bits, the green in 6 bits, and the blue in 5 bits. We use an extra bit in the green channel since the human eye can generally perceive more gradations of green. This is known as "High Color", and is again mainly useful for low-end or embedded devices.
        *LuminanceAlpha - This is a grayscale image that includes an alpha channel. Grayscale colors have equal red, green and blue values, which we call "luminance." So a typical gray value of (R=127, G=127, B=127, A=255) would be represented like so with LuminanceAlpha: (L=127, A=255). Each uses 8 bits.
        *Alpha - This is a special type of image that only stores an alpha channel in 8 bits.
        *Intensity - This is another special type of image which only uses a single channel, but with the alpha channel equal to the luminance. For example, an Intensity color of (I=127) would be equivalent to a RGBA color of (R=127, G=127, B=127, A=127).
        */
        pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA4444);
        Gdx.input.setInputProcessor(this);
        
        //Fill it red
        pixmap.setColor(Color.RED);
        pixmap.fill();
        
        //Draw two lines forming an X
        pixmap.setColor(Color.BLACK);
        pixmap.drawLine(0, 0, pixmap.getWidth()-1, pixmap.getHeight()-1);
        pixmap.drawLine(0, pixmap.getHeight()-1, pixmap.getWidth()-1, 0);
        
        //Draw a circle about the middle
        pixmap.setColor(Color.YELLOW);
        pixmap.drawCircle(pixmap.getWidth()/2, pixmap.getHeight()/2, pixmap.getHeight()/2 - 1);
        
//        pixmap = new Pixmap(Gdx.files.internal("badlogic.jpg"));
        texture = new Texture(pixmap);
    }

    
    @Override
    public boolean keyTyped(char character) {
        switch (character) {
        case 's':
            PixmapIO.writeCIM(file, pixmap);
            Gdx.app.log(TAG, "saved to " + file);
            return true;
        case 'l':
            pixmap.dispose();
            if (file.exists()) {
                pixmap = PixmapIO.readCIM(file);
                texture.draw(pixmap, 0, 0);
                Gdx.app.log(TAG, "load from " + file);
            } else {
                Gdx.app.log(TAG, file + " doesn't exit");
            }
            return true;
        default:
            return false;
        }
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // 在text纹理上可以自己绘制
        pixmap.setColor(Color.GREEN);
        pixmap.fillCircle(screenX, screenY - (Gdx.graphics.getHeight() - pixmap.getHeight()), 5);
        texture.draw(pixmap, 0, 0);
        return super.touchDragged(screenX, screenY, pointer);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.39f, 0.58f, 0.92f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        batch.draw(texture, 0, 0);
        batch.end();
        
        goBackMainScreen();
    }

    @Override
    public void hide() {
        batch.dispose();
        pixmap.dispose();
        texture.dispose();
    }

}
