package com.libgdx.cookbook.help;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class HelpCamera {
	private final String TAG = HelpCamera.class.getSimpleName();
	OrthographicCamera camera;
	Vector3 velocityCam = new Vector3(0.1f, 0.1f, 0.1f); // 由于世界的大小为12.8*7.2
															// pixel，因此照相机的移动速度要变小

	public HelpCamera(OrthographicCamera camera) {
		this.camera = camera;
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
}
