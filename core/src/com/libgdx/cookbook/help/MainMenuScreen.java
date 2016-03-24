package com.libgdx.cookbook.help;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.libgdx.cookbook.GdxCookbook;

public class MainMenuScreen extends ScreenAdapter {

    GdxCookbook game;
    Stage stage;
    BitmapFont font;
    ScrollPane scrollPane;
    Texture scrTexture;
    String[] sampleName;
    
    public MainMenuScreen(GdxCookbook game) {
        this.game = game;
    }
    
    @Override
    public void show() {
        int sampleNum = SampleList.sampleList.size();
        sampleName = new String[sampleNum];
        for (int i = 0; i < sampleNum; i++) {
            sampleName[i] = SampleList.sampleList.get(i).getSimpleName();
        }
        
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        
        font = new BitmapFont(Gdx.files.internal("data/fonts/oswald.fnt"));
        TextButtonStyle style = new TextButtonStyle();
        style.font = font;
        Table table = new Table();
        table.debug();

        scrTexture = new Texture(Gdx.files.internal("data/scene2d/tfbackground.png"));
        List.ListStyle listS = new List.ListStyle();
        listS.font = font;
        listS.fontColorSelected = Color.BLACK;
        listS.fontColorUnselected = Color.WHITE;
        listS.selection = new TextureRegionDrawable(new TextureRegion(scrTexture));
        
        final List<String> list = new List<String>(listS);
        list.setItems(sampleName);
        list.pack();
        
        scrollPane = new ScrollPane(list);
        scrollPane.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("selected: " + list.getSelected());
                BaseScreen baseScreen = SampleList.newSample(list.getSelected());
                baseScreen.setGame(game);
                game.setScreen(baseScreen);
            }
            
        });
        table.add(scrollPane);
        table.setFillParent(true);
        table.pack();
        stage.addActor(table);
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.39f, 0.58f, 0.92f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void hide() {
        font.dispose();
        stage.dispose();
        scrTexture.dispose();
        Gdx.input.setInputProcessor(null);
    }

}
