package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by chandler on 12/5/2017.
 */

public class GameClient implements State {
    ShapeRenderer sr;
    Player player;
    Connection serverConnection;


    float[][] actionLog;
    int currentAction;
    float totalTime;
    byte[] buffer;
    boolean isConnected;


    GameClient(ShapeRenderer s){
        sr=s;
        player=new Player(200,200);
        buffer=new byte[256];
        isConnected=false;
        establishConnection();
    }
    void establishConnection(){
        Connection connection=new Connection(2727,buffer, "Client establishment");
        connection.start();
        buffer[1]=120;//arbitrary numbers I chose to represent a connectin request
        buffer[0]=120;
        connection.send(6565);

        while(!connection.isNewMessage()){
            //waits for a response from the server
        }
        connection.resetMessage();
        System.out.println(buffer);
        if (buffer[0]!=-1){
            serverConnection=new Connection(buffer[1],this.buffer,"Client's connection to gameServer");
            isConnected=true;
        }


    }
    @Override
    public void update(float dt) {
        if (isConnected) {
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
