package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.io.IOException;

public class MyGdxGame extends ApplicationAdapter {
	ShapeRenderer batch;
	SubGame subGame;

	@Override
	public void create () {
		batch = new ShapeRenderer();
		try {
			subGame=new SubGame();
		}catch(IOException e){

		}
		Gdx.graphics.setResizable(false);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		subGame.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
