package com.mygdx.game;

/**
 * Created by chandler on 12/6/2017.
 */

public class Player {
    float x;
    float y;
    float moveSpeed;
    public Player(float x,float y){
        this.x=x;
        this.y=y;
        moveSpeed=10;
    }
    public Player(){
        x=10;
        y=10;
        moveSpeed=10;
    }
    void setPosition(int x, int y){
        this.x=x;
        this.y=y;
    }
    void moveUp(float dt){
        y+=moveSpeed*dt;
    }
    void moveDown(float dt){
        y-=moveSpeed*dt;
    }
    void moveRight(float dt){
        x+=moveSpeed*dt;
    }
    void moveLeft(float dt){
        x-=moveSpeed*dt;
    }
}
