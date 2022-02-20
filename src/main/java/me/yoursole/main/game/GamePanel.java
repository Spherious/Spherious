package me.yoursole.main.game;

import me.yoursole.main.game.gameValues.GameData;
import me.yoursole.main.resources.Player;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public abstract class GamePanel extends JPanel {
    protected int dimx;
    protected int dimy;

    public GamePanel(int playerSize, int playerX, int playerY){
        super();
        GameData.p = new Player(0,0,playerSize, playerX, playerY,playerSize);
    }

    public void startTick(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                onTick();
            }
        },0, 10);
    }


    public abstract void onTick();
}
