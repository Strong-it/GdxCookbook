package com.libgdx.cookbook.chp05;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.IntMap;
import com.libgdx.cookbook.help.BaseScreen;

public class SoundEffectSample extends BaseScreen {
    private final String TAG = SoundEffectSample.class.getSimpleName();
    private IntMap<Sound> sounds;

    @Override
    public void show() {
        sounds = new IntMap<Sound>();
        
        sounds.put(Keys.NUM_1, Gdx.audio.newSound(Gdx.files.internal("data/sfx/sfx_01.wav")));  // 加载短促播放的声音 newSound
        sounds.put(Keys.NUM_2, Gdx.audio.newSound(Gdx.files.internal("data/sfx/sfx_02.wav")));
        sounds.put(Keys.NUM_3, Gdx.audio.newSound(Gdx.files.internal("data/sfx/sfx_03.mp3")));
        sounds.put(Keys.NUM_4, Gdx.audio.newSound(Gdx.files.internal("data/sfx/sfx_04.wav")));
        sounds.put(Keys.NUM_5, Gdx.audio.newSound(Gdx.files.internal("data/sfx/sfx_05.mp3")));
        sounds.put(Keys.NUM_6, Gdx.audio.newSound(Gdx.files.internal("data/sfx/sfx_06.mp3")));
        sounds.put(Keys.NUM_7, Gdx.audio.newSound(Gdx.files.internal("data/sfx/sfx_07.wav")));
        
        Gdx.app.log(TAG, "Instructions");
        Gdx.app.log(TAG, "- Press keys 1-0 to play sounds");
        Gdx.app.log(TAG, "- Press s to stop all sounds");
        Gdx.app.log(TAG, "- Press p to pause all sounds");
        Gdx.app.log(TAG, "- Press r to resume all soud");
        
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void hide() {
        for (Sound sound : sounds.values()) {  // values返回迭代器， 对于一些数据结构不支持直接迭代，可以用此方法
            sound.dispose();
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        long soundId = 0;
        if (keycode == Keys.S) {
            for (Sound sound : sounds.values()) {
                sound.stop();
            }
            
            Gdx.app.log(TAG, "Sounds stopped");
        }
        else if (keycode == Keys.P) {
            for (Sound sound : sounds.values()) {
                sound.pause();
            }
            
            Gdx.app.log(TAG, "Sounds paused");
        }
        else if (keycode == Keys.R) {
            for (Sound sound : sounds.values()) {
                sound.resume();
//                sound.resume(soundId);
            }
            
            Gdx.app.log(TAG, "Sounds resumed");
        }
        else {
            Sound sound = sounds.get(keycode);
            
            if (sound != null) {
                sound.play();
//                soundId = sound.play();
                Gdx.app.log(TAG, "Playing sound");
            }
        }
        
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        int num = MathUtils.clamp(MathUtils.random(sounds.size - 1), 1, 7);
        
        Sound sound = sounds.get(Keys.valueOf(String.valueOf(num)));  // 事实上Keys.NUM_1 --> Keys.NUM_7对应的int值是8-->14 因此这里要做一个类型 转换或者用下面方法
//        Sound sound = sounds.get(MathUtils.random(8, 14));
        if (sound != null) {
            sound.play();
            Gdx.app.log(TAG, "Playing sound " + num);
        }
        
        return true;
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Keys.B)) {
            goMainScreen();
        }
    }

}
