package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by chandler on 12/5/2017.
 */

public class GameServer implements State {
    ShapeRenderer sr;
    Server server;
    Connection connection;
    byte[] buffer;
    GameServer(ShapeRenderer s){
        sr=s;
        buffer=new byte[256];
        connection=new Connection(6565,buffer,"Establsihment server");
        connection.start();
        System.out.println("Server started");
    }
    @Override
    public void update(float dt) {
        if (connection.isNewMessage()){
            connection.resetMessage();
            if (buffer[0]==120&&buffer[1]==120){
                buffer[1]=12;
                connection.send(2727);
                System.out.println("Sent");
            }
        }
    }

    @Override
    public void render() {
        sr.setAutoShapeType(true);
        sr.begin();
        sr.set(ShapeRenderer.ShapeType.Filled);
        sr.end();
    }
}