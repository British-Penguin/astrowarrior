package com.britishpenguin.astrowarrior.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.britishpenguin.astrowarrior.AstroWarrior;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Astro Warrior";
		config.width = 1280;
		config.height = 720;
		config.forceExit = false;
		config.foregroundFPS = 0;
		new LwjglApplication(new AstroWarrior(), config);
	}
}
