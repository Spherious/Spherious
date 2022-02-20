package me.yoursole.main.resources;

import java.awt.*;
import java.util.ArrayList;

public class Player{
    private int size;
    private double locx;
    private double locy;
    private int defaultSize;

    private Vector movement;
    public Player(float x, float y, int size, int locx, int locy, int defaultSize) {
        this.movement = new Vector(x,y);
        this.size = size;
        this.defaultSize = defaultSize;

        this.locx = locx;
        this.locy = locy;
    }

    public int getSize(){
        return this.size;
    }

    public double getLocx(){
        return this.locx;
    }
    public double getLocy(){
        return this.locy;
    }

    public void setLocx(int locx){
        this.locx = locx;
    }
    public void setLocy(int locy){
        this.locy = locy;
    }
    public void setSize(int size){
        this.size = size;
    }
    public int getDefaultSize(){
        return this.defaultSize;
    }



    public Vector getMovement(){
        return this.movement;
    }
    public void setMovement(Vector v){
        this.movement = v;
    }

    public void resetSavedPoints(){
        this.savedPoints = null;
    }
    private ArrayList<Point> savedPoints = null;
    //save the points in a circle of the player size around 0,0 to optimize

    public ArrayList<Point> getPointsInside(){
        ArrayList<Point> points = new ArrayList<>();
        if(this.savedPoints == null){
            ArrayList<Point> pointsNonShift = new ArrayList<>();

            for (int i = -this.size; i < this.size; i++) {
                for (int j = -this.size; j < this.size; j++) {
                    if(i*i + j*j < (this.size/2)*(this.size/2)){
                        Point p = new Point((int)(i+this.locx), (int)(j+this.locy));
                        Point p2 = new Point(i, j);
                        points.add(p);
                        pointsNonShift.add(p2);
                    }
                }
            }
            this.savedPoints = pointsNonShift;

        }else{
            for(Point p : this.savedPoints){
                Point z = (Point) p.clone();
                z.setLocation(p.x+this.locx, p.y+this.locy);
                points.add(z);
            }
        }
        return points;

    }
}
