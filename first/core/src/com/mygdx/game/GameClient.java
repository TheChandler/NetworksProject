package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class GameClient implements State {
    Random random =new Random();
    ShapeRenderer sr;
    Player player;
    Connection serverConnection;
    byte[] buffer;
    boolean isConnected;

    int recievePort;
    ByteBuffer b=ByteBuffer.allocate(256);

    GameClient(ShapeRenderer s){
        sr=s;
        player=new Player(200,200);
        buffer=new byte[256];
        isConnected=false;
        recievePort=random.nextInt(300)+4000;
        establishConnection();
    }
    void establishConnection(){
        serverConnection=new Connection(recievePort,buffer,"Client "+recievePort);
        b.putInt(0,127);
        buffer=b.array();
        DatagramPacket packet=new DatagramPacket(buffer,buffer.length);
    }
    @Override
    public void update(float dt) {
        if (isConnected) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                b.putInt(1);
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
