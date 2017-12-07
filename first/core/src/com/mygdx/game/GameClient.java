package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by chandler on 12/5/2017.
 */

public class GameClient implements State {
    ShapeRenderer sr;
    Client client;
    Player player;

    float[][] actionLog;
    int currentAction;
    float totalTime;
    GameClient(ShapeRenderer s){
        sr=s;
        player=new Player(200,200);
        establishConnection();
    }
    void establishConnection(){
        
    }
    @Override
    public void update(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.moveLeft(dt);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.moveRight(dt);
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.moveUp(dt);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.moveDown(dt);
        }
    }
     @Override
    public void render() {
        sr.setAutoShapeType(true);
        sr.begin();
        sr.set(ShapeRenderer.ShapeType.Filled);
        sr.box(player.x,player.y,0,100,100,0);
        sr.end();
    }
}
