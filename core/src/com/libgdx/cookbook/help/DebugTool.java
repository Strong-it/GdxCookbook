package com.libgdx.cookbook.help;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;

public class DebugTool {
	private final String TAG = DebugTool.class.getSimpleName();
	OrthographicCamera camera;
	Vector3 velocityCam = new Vector3(0.1f, 0.1f, 0.1f); // 由于世界的大小为12.8*7.2 pixel，因此照相机的移动速度要变小
	public ShapeRenderer shapeRenderer;

	public DebugTool(OrthographicCamera camera) {
		this.camera = camera;
		shapeRenderer = new ShapeRenderer();
	}

	public void operateCamera() {
		if (Gdx.app.getType() != ApplicationType.Desktop) {
			return;
		}

		if (Gdx.input.isKeyPressed(Keys.A)) {
			Gdx.app.log(TAG, "now camera positon is x=" + camera.position.x
					+ "  y=" + camera.position.y + "  z=" + camera.position.z);
			camera.position.x += velocityCam.x; // 相机向右移动， 人物向左移动
			camera.update();
		} else if (Gdx.input.isKeyJustPressed(Keys.D)) {
			Gdx.app.log(TAG, "now camera positon is x=" + camera.position.x
					+ "  y=" + camera.position.y + "  z=" + camera.position.z);
			camera.position.x -= velocityCam.x; // 相机向右移动， 人物向左移动
			camera.update();
		} else if (Gdx.input.isKeyJustPressed(Keys.S)) {
			Gdx.app.log(TAG, "now camera positon is x=" + camera.position.x
					+ "  y=" + camera.position.y + "  z=" + camera.position.z);
			camera.position.y += velocityCam.y;
			camera.update();
		} else if (Gdx.input.isKeyJustPressed(Keys.W)) {
			Gdx.app.log(TAG, "now camera positon is x=" + camera.position.x
					+ "  y=" + camera.position.y + "  z=" + camera.position.z);
			camera.position.y -= velocityCam.y;
			camera.update();
		} else if (Gdx.input.isKeyJustPressed(Keys.Z)) {
			Gdx.app.log(TAG, "now camera positon is x=" + camera.position.x
					+ "  y=" + camera.position.y + "  z=" + camera.position.z);
			camera.zoom += velocityCam.z; // 缩小
			camera.update();
		} else if (Gdx.input.isKeyPressed(Keys.X)) {
			Gdx.app.log(TAG, "now camera positon is x=" + camera.position.x
					+ "  y=" + camera.position.y + "  z=" + camera.position.z);
			camera.zoom -= velocityCam.z; // 放大
			camera.update();
		}
	}
	
	public void drawGrid(float scene_width, float scene_height) {
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Line);

		shapeRenderer.setColor(Color.RED);
		shapeRenderer.line(-scene_width, 0.0f, scene_height, 0.0f);
		shapeRenderer.line(0.0f, -scene_width, 0.0f, scene_height);
		
		shapeRenderer.setColor(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, Color.WHITE.a);
		
		for (int i = -7; i <= 7; i++) {
			if (i == 0)
				continue;
			
			shapeRenderer.line(-scene_width, i, scene_height, i);  // 画横线
		}
		
		for (int i = -7; i <= 7; i++) {
			if (i == 0)
				continue;
			
			shapeRenderer.line(i, -scene_width, i, scene_height);  // 画竖线
		}
		
		shapeRenderer.end();
	}
	
	public void dispose() {
		shapeRenderer.dispose();
	}
}
