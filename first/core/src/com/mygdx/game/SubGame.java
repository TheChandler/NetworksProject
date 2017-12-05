package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Created by chandler on 12/4/2017.
 */

public class SubGame{
    SubGame()throws IOException{
        new Windowmen();
        System.out.println("yeeeah");
        JFrame frame=new JFrame();
        frame.setVisible(true);
        JButton butt = new JButton("Send packet");
        butt.addActionListener(new SendPacket());

        frame.add(butt);
    }
    void render(ShapeRenderer sr){
        sr.setAutoShapeType(true);
        sr.begin();
        sr.setColor(1f,1f,.5f,1f);
        sr.set(ShapeRenderer.ShapeType.Filled);
        sr.box(0,0,0,100,100,0);
        sr.end();
    }
}

class SendPacket implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        try {
            DatagramSocket socket = new DatagramSocket();
            byte[] buffer = new byte[256];
            buffer = "have nice day".getBytes();

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getLocalHost(), 5555);
            socket.send(packet);
            System.out.println("Packet Sent");
        }catch(IOException e){

        }
    }
}