package com.libgdx.cookbook.chp08;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class ListTest extends ApplicationAdapter {
	List<String> list;
	Stage stage;
	StretchViewport viewport;
	String[] listString = {"Libgdx", "CSDN", "daXiao"};
	
	@Override
	public void create() {
		viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		viewport.getCamera().translate(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
		viewport.getCamera().update();
		
		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);
		
		List.ListStyle style = new List.ListStyle();
		style.font = new BitmapFont();
		style.selection = new TextureRegionDrawable(new TextureRegion(new Texture("badlogic.jpg")));
		list = new List<String>(style);
		list.setItems(listString);
		// 要设置大小，否则selection不能显示而且也不能响应click事件
		list.setSize(list.getPrefWidth(), list.getPrefHeight());
//		list.setPosition(0, list.getPrefHeight());
		
		Gdx.app.log("List", "List x=" + list.getX() + " y=" + list.getY());
		
		stage.addActor(list);
		list.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println(list.getSelectedIndex());
				super.clicked(event, x, y);
			}
			
		});
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.39f, 0.58f, 0.92f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act();
		stage.draw();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
