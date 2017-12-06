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
    static int port=0;
    JFrame frame;
    JButton button;
    Client(){
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
        try {
            DatagramSocket socket=new DatagramSocket(4545);
            byte[] buffer=new byte[256];
            buffer="Connect".getBytes();
            DatagramPacket packet=new DatagramPacket(buffer,buffer.length,InetAddress.getLocalHost(),6563);
            socket.send(packet);
            socket.receive(packet);
            Client.port=packet.getData()[1];
            gameConnection();
        }catch (SocketException se){
            System.out.println("Could not start client socket");
        }catch(UnknownHostException uhe){
            System.out.println("Unknown Host exception");
        }catch(IOException ioe){
            System.out.println("Some IOException");
        }
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
