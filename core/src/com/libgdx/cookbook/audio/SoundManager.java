package com.libgdx.cookbook.audio;

import java.io.File;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.JsonIterator;
import com.badlogic.gdx.utils.ObjectMap;

public class SoundManager implements Disposable {

    private Listener listener;
    private Array<SoundInstance> sounds;
    private ObjectMap<String, SoundData> soundsData;

    public SoundManager(FileHandle handle) {
        listener = new Listener();
        sounds = new Array<SoundInstance>();
        soundsData = new ObjectMap<String, SoundData>();

        loadSoundData(handle);
    }

    private void loadSoundData(FileHandle handle) {
        try {
            JsonReader reader = new JsonReader(); // JsonReader主要json文件转换JsonValue的值
            JsonIterator iterator = reader.parse(handle).iterator();

            while (iterator.hasNext()) {
                JsonValue value = iterator.next();

                String name = value.getString("name");
                Sound sound = Gdx.audio.newSound(Gdx.files.internal(name));
                float duration = value.getFloat("duration");
                float falloffStart = value.getFloat("falloffStart", 0.0f);
                float maxDistance = value.getFloat("maxDistance", 10.0f);

                SoundData soundData = new SoundData(sound, duration,
                        falloffStart, maxDistance);
                soundsData.put(name, soundData);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void updateListener(Vector2 position, Vector2 direction) {
        listener.update(position, direction);
    }

    public void update() {
        for (int i = 0; i < sounds.size;) {
            SoundInstance instance = sounds.get(i);

            if (instance.update(listener)) {
                sounds.removeIndex(i);
            } else {
                ++i;
            }
        }
    }

    public SoundInstance play(String soundName) {
        SoundData data = soundsData.get(soundName);

        if (data != null) {
            SoundInstance instance = new SoundInstance(data);
            instance.update(listener);
            sounds.add(instance);
            return instance;
        }

        return null;
    }

    @Override
    public void dispose() {
        Iterator<SoundData> iterator = soundsData.values().iterator();

        while (iterator.hasNext()) {
            iterator.next().getSound().dispose();
        }

        soundsData.clear();
        sounds.clear();
    }

}
