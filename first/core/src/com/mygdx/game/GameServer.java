package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

public class GameServer implements State {
    ShapeRenderer sr;
    Connection connection;

    Player[] players;
    int connectionsPlace;
    int[] playerPorts;

    byte[] buffer;
    ByteBuffer b=ByteBuffer.allocate(256);
    GameServer(ShapeRenderer s){
        sr=s;
        buffer=new byte[256];
        connection=new Connection(6565,buffer,"Server");
        connection.start();

        connectionsPlace=0;

        playerPorts=new int[10];
        players=new Player[10];

        System.out.println("Server started");
    }
    @Override
    public void update(float dt) {
        if (connection.newMessage){
            b.put(buffer);
            System.out.println(b.getInt(0));
        }
    }

    @Override
    public void render() {
        sr.setAutoShapeType(true);
        sr.begin();
        sr.set(ShapeRenderer.ShapeType.Filled);
        for (int i=0;i<connectionsPlace;i++){
            sr.box(players[i].x,players[i].y,0,100,100,0);
        }
        sr.end();
    }
}