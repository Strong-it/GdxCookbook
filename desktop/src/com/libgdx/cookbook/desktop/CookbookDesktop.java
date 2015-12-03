package com.libgdx.cookbook.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.libgdx.cookbook.GdxCookbook;

public class CookbookDesktop {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "CookBookTest";
		new LwjglApplication(new GdxCookbook(), config);
	}
}
