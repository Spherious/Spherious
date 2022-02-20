package me.yoursole.main.game;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {


    GameFrameMainPanel p;

    public GameFrame() {
        setupMainPanel();
    }

    private void setupMainPanel() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        p = new GameFrameMainPanel();
        this.add(p);
        this.pack();
        p.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

        p.setBackground(Color.BLACK);
    }
}


