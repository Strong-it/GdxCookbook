package com.libgdx.cookbook.chp05;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.libgdx.cookbook.help.BaseScreen;

/**
 * Audio streaming for background music
 * Sound直接加载到内存，因此适合播放短促的音效
 * Music编解码编完stream，适合播放游戏背景音乐
 */
public class MusicSample extends BaseScreen {

    private static final float VOLUME_CHANGE = 0.2f;
    
    private Array<Music> songs;
    private int currentSongIndex;
    private float volume;
    private SongListener listener;
    
    @Override
    public void show() {
        listener = new SongListener();
        
        songs = new Array<Music>();
        songs.add(Gdx.audio.newMusic(Gdx.files.internal("data/music/song_1.mp3")));
        songs.add(Gdx.audio.newMusic(Gdx.files.internal("data/music/song_2.mp3")));
        songs.add(Gdx.audio.newMusic(Gdx.files.internal("data/music/song_3.mp3")));
        songs.add(Gdx.audio.newMusic(Gdx.files.internal("data/music/song_4.mp3")));
        songs.add(Gdx.audio.newMusic(Gdx.files.internal("data/music/song_5.mp3")));
        
        currentSongIndex = 0;
        volume = 1.0f;
        
        Gdx.app.log("MusicSample", "Instructions");
        Gdx.app.log("MusicSample", "- Press right to play the next song");
        Gdx.app.log("MusicSample", "- Press left to play the previous song");
        Gdx.app.log("MusicSample", "- Press p to pause");
        Gdx.app.log("MusicSample", "- Press r to resume");
        Gdx.app.log("MusicSample", "- Press up to increase volume");
        Gdx.app.log("MusicSample", "- Press down to decrease volume");
        
        playSong(0);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void dispose() {
        for (Music music : songs) {
            music.dispose();
        }
    }
    
   private void playSong(int songIndex) {
       Music song = songs.get(currentSongIndex);
       song.setOnCompletionListener(null);
       song.stop();
       
       currentSongIndex = songIndex;
       song = songs.get(currentSongIndex);
       song.play();
       song.setVolume(volume);
       song.setOnCompletionListener(listener);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.P) {
            songs.get(currentSongIndex).pause();
            Gdx.app.log("MusicSample", "Song paused");
        }
        else if (keycode == Keys.R) {
            songs.get(currentSongIndex).play();
            Gdx.app.log("MusicSample", "Song resumed");
        }
        else if (keycode == Keys.UP) {
            changeVolume(VOLUME_CHANGE);
            Gdx.app.log("MusicSample", "Volume up");
        }
        else if (keycode == Keys.DOWN) {
            changeVolume(-VOLUME_CHANGE);
            Gdx.app.log("MusicSample", "Volume down");
        }
        else if (keycode == Keys.RIGHT) {
            playSong((currentSongIndex + 1) % songs.size);
            Gdx.app.log("MusicSample", "Next song");
        }
        else if (keycode == Keys.LEFT) {
            int songIdx = (currentSongIndex - 1) < 0 ? songs.size - 1 : currentSongIndex - 1;
            playSong(songIdx);
            Gdx.app.log("MusicSample", "Previous song");
        }
        
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        playSong((currentSongIndex + 1) % songs.size);
        Gdx.app.log("MusicSample", "Next song");
        return true;
    }

    private void changeVolume(float volumeChange) {
        Music song = songs.get(currentSongIndex);
        volume = MathUtils.clamp(song.getVolume() + volumeChange, 0.0f, 1.0f);
        song.setVolume(volume);
    }
    
    private class SongListener implements OnCompletionListener{  // 检测歌曲是否播放完毕

        @Override
        public void onCompletion(Music music) {
            playSong((currentSongIndex + 1) % songs.size);
            Gdx.app.log("MusicSample", "Song finished, play next song");
        }
        
    }

    @Override
    public void render(float delta) {
        goBackMainScreen();
    }
}
