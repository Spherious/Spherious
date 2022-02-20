package me.yoursole.main.resources;

import java.awt.*;
import java.io.File;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;

public class RectObject extends GameObject {
    private int[] xyLower;
    private int[] xyUpper;

    public RectObject(int x1, int y1, int x2, int y2, float bouncyness, boolean isWinner, boolean isVisible, int sizeSet, boolean kills, boolean isCollidable, Runnable custom, String texturePath, boolean compute){
        super(bouncyness,isWinner,isVisible,sizeSet,kills,isCollidable,custom,texturePath, compute);

        this.xyLower = new int[]{x1, y1};
        this.xyUpper = new int[]{x2, y2};

    }

    public void setXys(int x1, int y1, int x2, int y2){
        this.xyLower = new int[]{x1, y1};
        this.xyUpper = new int[]{x2, y2};
    }
    public int[] getXyLower(){
        return this.xyLower;
    }

    public int[] getXyUpper() {
        return this.xyUpper;
    }


    @Override
    public boolean insideObject(Point p) {
        int x = p.x;
        int y = p.y;

        return (x > this.xyLower[0] && x < this.xyUpper[0]) && (y < this.xyUpper[1] && y > this.xyLower[1]);
    }
}
