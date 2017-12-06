package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.x=400;
		config.y=400;

		new LwjglApplication(new MyGdxGame(), config);

	}
}
