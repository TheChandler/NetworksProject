package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.Widget;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * Created by chandler on 12/4/2017.
 */

public class Windowmen {
    JFrame frame;
    static int port;
    Windowmen(){
        frame=new JFrame();
        frame.setSize(300,400);
        frame.setVisible(true);
        frame.add(makePanel());
    }
    JPanel makePanel(){
        JSpinner port=new JSpinner(new SpinnerNumberModel());
        port.setPreferredSize(new Dimension(200,20));

        JPanel pan=new JPanel();
        JButton butt=new JButton("Connect");
        pan.add(butt);
        pan.add(port);
        butt.addActionListener(new Connect(port));
        return pan;
    }
    static void setPort(int p){
        port=p;
    }
}
class Connect implements ActionListener {
    JSpinner spinner;
    Server server;
    Connect(JSpinner s){
        this.spinner=s;
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Windowmen.setPort((Integer)this.spinner.getValue());
        server=new Server("Seerve");
        server.start();
    }
}
