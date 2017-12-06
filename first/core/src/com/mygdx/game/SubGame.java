package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.io.IOException;

/**
 * Created by chandler on 12/4/2017.
 */

public class SubGame{
    State state;
    ShapeRenderer sr;
    SubGame()throws IOException{
        sr=new ShapeRenderer();
        new Windowmen(this);
    }
    void becomeServer(){
        state=new GameServer(sr);
    }
    void becomeClient(){
        state=new GameClient(sr);
    }
    void render(){
        if (state!=null) {
            state.update(Gdx.graphics.getDeltaTime());
            state.render();
        }
    }
}