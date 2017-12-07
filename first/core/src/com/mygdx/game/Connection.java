package com.mygdx.game;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by chandler on 12/6/2017.
 */

class Connection extends Thread {
    int port;
    boolean newMessage;
    byte[] buffer;
    DatagramSocket socket;
    InetAddress address;
    String name;

    Connection(int port,byte[] b,String name){
        this.port=port;
        newMessage=false;
        buffer=b;
        this.name=name;
        try{
            address=InetAddress.getLocalHost();
        }catch (UnknownHostException uhe){

        }
    }
    boolean isNewMessage(){
        return newMessage;
    }
    byte[] getMessage(){
        newMessage=false;
        return buffer;
    }
    void send(byte[] sendBuffer){
        DatagramPacket packet=new DatagramPacket(sendBuffer,sendBuffer.length,address,port);
        try{
            socket.send(packet);
        }catch(IOException io){
            System.out.println(name+" Packet not snent");
        }
    }
    public void run(){
        try {
            socket=new DatagramSocket(port);
            while (true){
                DatagramPacket packet=new DatagramPacket(buffer,buffer.length,port);
                socket.receive(packet);
                newMessage=true;
            }
        }catch(SocketException se){

        }catch(IOException ioe){

        }
    }
}
