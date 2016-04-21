package com.libgdx.cookbook.chp08;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.libgdx.cookbook.help.BaseScreen;

public class LabelScaleSample extends BaseScreen {

    Skin skin;
    Stage stage;
    
    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        stage.addActor(table);
        table.setPosition(200, 65);

        Label label1 = new Label("This text is scaled 2x.", skin);
        label1.setFontScale(2);
        Label label2 = new Label(
            "This text is scaled. This text is scaled. This text is scaled. This text is scaled. This text is scaled. ", skin);
        label2.setWrap(true);
        label2.setFontScale(0.75f, 0.75f);

        table.debug();
        table.add(label1);
        table.row();
        table.add(label2).fill();
        table.pack();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        
        goBackMainScreen();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        stage.dispose();
        skin.dispose();
    }

}
