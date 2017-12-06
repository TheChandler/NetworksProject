package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by chandler on 12/5/2017.
 */

public class GameServer implements State {
    ShapeRenderer sr;
    Server server;
    GameServer(ShapeRenderer s){
        sr=s;
        server=new Server("Seerverrrr");
    }
    @Override
    public void update(float dt) {

    }

    @Override
    public void render() {
        sr.setAutoShapeType(true);
        sr.begin();
        sr.set(ShapeRenderer.ShapeType.Filled);
        sr.box(0,0,0,200,200,0);
        sr.end();
    }
}