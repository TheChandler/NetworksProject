package com.mygdx.game;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
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
            socket=new DatagramSocket();
        }catch (UnknownHostException uhe){
            System.out.println(name+" Unknown Host can't start connection");
        }catch(SocketException se){
            System.out.println(name+" Socket exception can't start connection");
        }
    }
    boolean isNewMessage(){
        return newMessage;
    }
    void resetMessage(){
        newMessage=false;
    }
    void send(int p){
        DatagramPacket packet=new DatagramPacket(buffer,buffer.length,address,p);
        try{
            socket.send(packet);
        }catch(IOException io){
            System.out.println(name+" Packet not sent");
        }
    }
    public void run(){
        try {
            socket=new DatagramSocket(port);
            while (true){
                DatagramPacket packet=new DatagramPacket(buffer,buffer.length);
                socket.receive(packet);
                System.out.println(name+" "+buffer[0]+" "+buffer[1]+" "+packet.getPort());
                newMessage=true;
            }
        }catch(SocketException se){

        }catch(IOException ioe){

        }
    }
}
