package com.mygdx.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by chandler on 12/4/2017.
 */

public class Server extends Thread{
    DatagramSocket socket;
    InetAddress[] addresses;
    int nextSpot=0;
    boolean keepServing=true;
    Server(String name){
        super(name);
        try {
            socket = new DatagramSocket(Windowmen.port);
        }catch(SocketException e){
            System.out.println("Socket not opened;");
        }
        addresses = new InetAddress[10];
    }
    int addAddress(InetAddress add){
        for (int i=0;i<10;i++){
            if (add.equals(addresses[i])){
                return i;
            }
        }
        if (nextSpot<10){
            addresses[nextSpot]=add;
            nextSpot++;
            return nextSpot;
        }else{
            return -1;
        }
    }
    public void run(){
        System.out.println("Server Started, probably");
        while (keepServing){
            try{
                byte[] buffer=new byte[256];
                DatagramPacket packet=new DatagramPacket(buffer,buffer.length);
                socket.receive(packet);
                System.out.println(packet.toString());
                InetAddress address=packet.getAddress();
                int port=packet.getPort();;
                if (addAddress(address)==-1){
                    buffer="Sorry, server is full".getBytes();
                    packet=new DatagramPacket(buffer,buffer.length,address,port);
                }else{
                    buffer="Congrats".getBytes();
                    packet=new DatagramPacket(buffer,buffer.length,address,port);
                }
                socket.send(packet);
            }catch(IOException e){
                System.out.println("IOException");
                keepServing=false;
            }
        }
        socket.close();
    }
}