package com.libgdx.cookbook.chp08;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.libgdx.cookbook.help.BaseScreen;

public class GroupSample extends BaseScreen {
    Stage stage;
    Group group;
    TextureRegion jetRegion;
    TextureRegion flameRegion;
    
    @Override
    public void show() {
        stage = new Stage();
        stage.setDebugAll(true);
        Gdx.input.setInputProcessor(stage);
        
        jetRegion = new TextureRegion(new Texture("data/plane/jet.png"));
        flameRegion = new TextureRegion(new Texture("data/plane/flame.png"));
        
        final Actor jet = new Actor() {

            @Override
            public void draw(Batch batch, float parentAlpha) {
                batch.draw(jetRegion, getX(), getY(), getOriginX(), getOriginY(),
                            getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation()); 
            }
            
        };
        Gdx.app.log("GroupSample", "jetWidth="+jet.getWidth() + " jetRegion Width=" + jetRegion.getRegionWidth());
        // 要设置Bounds的大小，否则不会显示
        // setBounds包含2个操作一个是设置position，一个是设置size
        jet.setBounds(jet.getX(), jet.getY(), jetRegion.getRegionWidth(), jetRegion.getRegionHeight());
        
        final Actor flame = new Actor() {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                batch.draw(flameRegion, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
                        getScaleX(), getScaleY(), getRotation());
            }
        };
        flame.setBounds(flame.getX(), flame.getY(), flameRegion.getRegionWidth(), flameRegion.getRegionHeight());
        flame.setPosition(jet.getWidth() - 25, 25);
        
        group = new Group();
        group.addActor(jet);
        group.addActor(flame);
        
        group.addAction(Actions.parallel(Actions.moveTo(200,0,5),Actions.rotateBy(90,5)));
        
        stage.addActor(group);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.39f, 0.58f, 0.92f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act();
        stage.draw();
        
        goBackMainScreen();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        jetRegion.getTexture().dispose();
        flameRegion.getTexture().dispose();
    }

}
