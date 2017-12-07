package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
    int sendPort;
    ByteBuffer b=ByteBuffer.allocate(256);
    int seqNum;
    float[][] history;
    float[][] players;
    int numPlayers;
    int myNumber;

    GameClient(ShapeRenderer s){
        sr=s;
        seqNum=0;
        history=new float[1000][3];
        player=new Player(200,200);
        buffer=new byte[256];
        isConnected=false;
        recievePort=random.nextInt(300)+4000;
        establishConnection();
    }
    void establishConnection(){
        serverConnection=new Connection(recievePort,buffer,"Client "+recievePort);
        serverConnection.start();
        b.putInt(0,1010);
        b.putInt(4,recievePort);
        buffer=b.array();
        try {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getLocalHost(), 6767);
            serverConnection.send(packet);
        }catch (UnknownHostException uh){
            System.out.println("Packet not Sent");
        }
        while(serverConnection.isNewMessage()==false){
            System.out.println("Wait");
        }
        b.clear();
        b.position(0);
        b.put(serverConnection.getBuffer());
        b.position(0);
        sendPort=b.getInt();
        player=new Player(b.getInt(),b.getInt());
        numPlayers=b.getInt();
        myNumber=numPlayers;
        for (int i=0;i<numPlayers;i++){
            players[i][0]=b.getFloat();
            players[i][1]=b.getFloat();
        }
        isConnected=true;
    }
    @Override
    public void update(float dt) {
        if (isConnected) {
            b.clear();
            b.position(0);
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                player.moveLeft(dt);
                sendDirection(3,dt);
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                player.moveRight(dt);
                sendDirection(4,dt);
            } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                player.moveUp(dt);
                sendDirection(1,dt);
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                player.moveDown(dt);
                sendDirection(2,dt);
            }

            if (serverConnection.isNewMessage()){
                serverConnection.resetMessage();
                ByteBuffer b=ByteBuffer.allocate(256);
                b.position(0);
                b.put(serverConnection.getBuffer());
                b.position(0);
                int choice=b.getInt();
                if (choice==1){
                    confirmSync(b);
                }else if(choice==2){
                    correctSync(b);
                }else if(choice==3){
                    addPlayer(b);
                }else if(choice==4){
                    updatePlayers(b);
                }
            }

        }
    }
    void updatePlayers(ByteBuffer b){
        for (int i=0;i<numPlayers;i++){
            if (i!=myNumber){
                players[i][0]=b.getFloat();
                players[i][1]=b.getFloat();
            }
        }
    }
    void addPlayer(ByteBuffer b){
        players[numPlayers][0]=b.getFloat();
        players[numPlayers][1]=b.getFloat();
        numPlayers++;

    }
    void correctSync(ByteBuffer b){
        int incorrectSeq=b.getInt();
        float xDifference=history[incorrectSeq][0]-b.getFloat();
        float yDifference=history[incorrectSeq][1]-b.getFloat();
        for (int i=incorrectSeq;i<seqNum;i++){
            history[i][0]-=xDifference;
            history[i][1]-=yDifference;
        }
        player.x=history[seqNum-1][0];
        player.y=history[seqNum-1][1];
    }
    void confirmSync(ByteBuffer b){
        history[b.getInt()][2]=1;
    }
    void addHistory(){
        history[seqNum][0]=player.x;
        history[seqNum][1]=player.y;
        history[seqNum][2]=0;
        seqNum++;
        seqNum%=1000;
    }
    void sendDirection(int i,float dt){
        ByteBuffer b=ByteBuffer.allocate(256);
        b.putInt(i);
        b.putInt(seqNum);
        b.putFloat(dt);
        b.putFloat(player.x);
        b.putFloat(player.y);
        addHistory();

        try {
            DatagramPacket packet=new DatagramPacket(b.array(),b.array().length,InetAddress.getLocalHost(),sendPort);
            serverConnection.send(packet);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
     @Override
    public void render() {
        sr.setAutoShapeType(true);
        sr.begin();
        sr.set(ShapeRenderer.ShapeType.Filled);
        sr.box(player.x,player.y,0,100,100,0);
        try {
            for (int i = 0; i < numPlayers; i++) {
                sr.box(players[i][0], players[i][1], 0, 100, 100, 0);
            }
        }catch (NullPointerException npe){

        }
        sr.end();
    }
}
