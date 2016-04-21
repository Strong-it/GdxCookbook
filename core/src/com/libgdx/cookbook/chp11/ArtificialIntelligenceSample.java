package com.libgdx.cookbook.chp11;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.libgdx.cookbook.ai.Caveman;
import com.libgdx.cookbook.ai.Dinosaur;
import com.libgdx.cookbook.help.BaseScreen;

public class ArtificialIntelligenceSample extends BaseScreen {

    private static final String TAG = "AISample";
    
    private Caveman caveman;
    private Dinosaur dinosaur;
    
    @Override
    public void show() {
        caveman = new Caveman();
        dinosaur = new Dinosaur((Caveman) caveman);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        caveman.update(Gdx.graphics.getDeltaTime());
        dinosaur.update(Gdx.graphics.getDeltaTime());
        
        goBackMainScreen();
    }

}
