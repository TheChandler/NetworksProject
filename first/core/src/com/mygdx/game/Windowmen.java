package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Action;
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
    SubGame subGame;
    Windowmen(SubGame sgame){
        subGame=sgame;
        frame=new JFrame();
        frame.setSize(300,400);
        frame.setVisible(true);
        frame.add(makePanel());
    }
    JPanel makePanel(){
        JPanel pan=new JPanel();
        pan.setPreferredSize(new Dimension(300,400));
        JButton clientButton=new JButton("Create Client");
        clientButton.addActionListener(new CreateClient(subGame));

        JButton serverButton=new JButton("Create Server");
        serverButton.addActionListener(new CreateServer(subGame));

        pan.add(clientButton);
        pan.add(serverButton);
        return pan;
    }

    static void setPort(int p){
        port=p;
    }
}

class CreateClient implements ActionListener{
    SubGame subgame;
    CreateClient(SubGame s){
        subgame=s;
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        subgame.becomeClient();
    }
}
class CreateServer implements ActionListener{
    SubGame subGame;
    CreateServer(SubGame s){
        subGame=s;

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        subGame.becomeServer();
    }
}