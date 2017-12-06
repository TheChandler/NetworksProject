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
        client=new Client();
        player=new Player(200,200);
        actionLog=new float[100][4];//action/time/resultx/resulty
        currentAction =0;
        totalTime=0;
    }
    void logAction(float action,float time,float resultX,float resultY){
        actionLog[currentAction][0]=action;
        actionLog[currentAction][1]=time;
        actionLog[currentAction][2]=resultX;
        actionLog[currentAction][3]=resultY;

    }


    @Override
    public void update(float dt) {
        totalTime+=dt;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            logAction(0,totalTime,player.x,player.y);
            player.moveLeft(dt);
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
