package com.mygdx.game;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

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
    DatagramPacket packet;


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
    byte[]  getBuffer(){
        return buffer;
    }
    DatagramPacket getPacket(){
        return packet;
    }
    void    resetMessage(){
        newMessage=false;
    }
    void send(DatagramPacket packet){
        System.out.println(name+" Sending packet on "+packet.getPort());
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void send(int p){
        packet=new DatagramPacket(buffer,buffer.length,address,p);
        System.out.println(name+" Sending buffer on "+p);
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
                packet=new DatagramPacket(buffer,buffer.length);
                socket.receive(packet);
                ByteBuffer b=ByteBuffer.allocate(256);
                b.position(0);
                b.put(packet.getData());
                b.position(0);
                System.out.println(name+" Recieved packet");
                newMessage=true;
            }
        }catch(SocketException se){

        }catch(IOException ioe){

        }
    }
}
