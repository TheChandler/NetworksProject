package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class GameServer implements State {
    ShapeRenderer sr;
    Connection connection;

    Player[] players;
    int connectionsPlace;
    int[] playerPorts;
    Connection[] connections;
    byte[][] buffers;

    byte[] buffer;
    ByteBuffer b=ByteBuffer.allocate(256);

    float timePassed=0;
    GameServer(ShapeRenderer s){
        sr=s;
        buffer=new byte[256];
        connection=new Connection(6767,buffer,"Server");
        connection.start();

        connectionsPlace=0;
        connections=new Connection[10];
        playerPorts=new int[10];
        players=new Player[10];
        buffers=new byte[10][256];

        System.out.println("Server started");
    }
    @Override
    public void update(float dt) {
        checkForNewConnection();
        for (int i=0;i<connectionsPlace;i++){
            if (connections[i].newMessage){
                handleMessage(i);
            }
        }
        timePassed+=dt;
        if (timePassed>1) {
            timePassed %= 1;
            ByteBuffer b=ByteBuffer.allocate(256);
            b.putInt(4);
            for (int i=0;i<connectionsPlace;i++){
                b.putFloat(players[i].x);
                b.putFloat(players[i].y);
            }
        }


    }
    void handleMessage(int i){
        connections[i].resetMessage();
        b.clear();
        b.put(buffers[i]);
        b.position(0);
        int command=b.getInt();
        int seq=b.getInt();
        System.out.println(command);

        switch (command){
            case 1:
                players[i].moveUp(b.getFloat());
                break;
            case 2:
                players[i].moveDown(b.getFloat());
                break;
            case 3:
                players[i].moveLeft(b.getFloat());
                break;
            case 4:
                players[i].moveRight(b.getFloat());
                break;

        }
        b=ByteBuffer.allocate(256);
        b.putInt(2);
        b.putInt(seq);
        b.putFloat(players[i].x);
        b.putFloat(players[i].y);
    }
    void checkForNewConnection(){
        if (connection.isNewMessage()){
            connection.resetMessage();
            b.position(0);
            b.put(buffer);
            b.position(0);
            if (b.getInt()==1010){
                addPlayer(connectionsPlace);
                connectionsPlace++;
            }
        }
    }

    void addPlayer(int i){
        playerPorts[i]=b.getInt(4);
        System.out.println("Adding player: "+playerPorts[i]);
        players[i]=new Player(100,100);
        b.clear();
        b.position(0);
        b.putInt(4000+i);
        b.putInt(100);
        b.putInt(100);
        b.putInt(connectionsPlace);
        for (int j=0;i<connectionsPlace;j++){
            b.putFloat(players[j].x);
            b.putFloat(players[j].y);
        }
        b.position(0);

        try {
            DatagramPacket packet=new DatagramPacket(b.array(),b.array().length, InetAddress.getLocalHost(),playerPorts[i]);
            connection.send(packet);
        } catch (UnknownHostException e) {
            e.printStackTrace();

        }
        connections[i]=new Connection(4000+i,buffers[i],"Server connection "+i);
        connections[i].start();
        notifyPlayers();
    }
    void notifyPlayers(){
        ByteBuffer b=ByteBuffer.allocate(256);
        b.putInt(3);
        b.putFloat(100);
        b.putFloat(100);
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