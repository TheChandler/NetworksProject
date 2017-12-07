package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Created by chandler on 12/5/2017.
 */

public class Client extends Thread {
    JFrame frame;
    JButton button;
    int port;
    byte[] buffer;
    boolean connected;

    Client(){
        buffer=new byte[256];
        makeFrame();
        connected=false;
    }
    void makeFrame(){
        frame=new JFrame();
        frame.setVisible(true);
        frame.setSize(300,300);
        button=new JButton("Connect");
        button.setPreferredSize(new Dimension(100,20));
        button.addActionListener(new ConnectAction(this));
        frame.add((button));
    }
    public void run(){
    }
    void gameConnection(){
        try {
            DatagramSocket socket = new DatagramSocket(port);

        }catch(SocketException se){

        }
    }
    void connectToServer(){
        byte[] buffer=new byte[256];
        Connection connection=new Connection(5656,buffer);
        connection.start();

        while(!connection.isNewMessage()){
        }
        System.out.println(buffer.toString());
    }
    void updateButton(String s){
        button.setText(s);
    }
    void render(ShapeRenderer sr){

    }
}
class ConnectAction implements ActionListener{

    Client client;
    ConnectAction(Client c){
        this.client=c;
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        client.connectToServer();
    }
}
