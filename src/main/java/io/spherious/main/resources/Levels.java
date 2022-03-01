package io.spherious.main.resources;

import io.spherious.engine.resources.EllipseObject;
import io.spherious.engine.resources.RectObject;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public enum Levels {
    LEVEL1(new Level(new ArrayList<>() {{
        add(new RectObject(0, 0, 1500, 1000, 0f, false, true, 50, false, false, (Runnable & Serializable) () -> {

        }, "assets/background.jpg", false));
        add(new RectObject(0, 100, 600, 150, 0.1f, false, true, 50, false, true, (Runnable & Serializable) () -> {

        }, "", true));
        add(new RectObject(620, 100, 1400, 150, 0.1f, false, true, 50, false, true, (Runnable & Serializable) () -> {

        }, "", true));
        add(new RectObject(1300, 150, 1400, 1000, 0.1f, false, true, 50, false, true, (Runnable & Serializable) () -> {

        }, "", true));
        add(new RectObject(1400, 900, 1500, 1000, 0.1f, false, false, 15, false, false, (Runnable & Serializable) () -> {

        }, "", true));

        add(new RectObject(500, 500, 600, 600, 0.1f, true, true, 15, false, true, (Runnable & Serializable) () -> {

        }, "assets/grass.jpg", true));


    }}, new Point(50, 50), false, 1500, 1000, 1)),


    LEVEL2(new Level(new ArrayList<>() {{


        add(new RectObject(530, 1, 560, 30, 0.1f, true, true, 50, false, true, () -> {

        }, "assets/grass.jpg", true));

    }}, new Point(650, 450), false, 1500, 1000, 2)),



    LEVEL3(new Level(new ArrayList<>() {{
        add(new EllipseObject(new Point(250,250),20,40,1,false,true, 50,false,true,()->{

        },"",true));

    }}, new Point(50, 50), false, 1500, 1000, 3));


    private final Level l;

    Levels(Level l) {
        this.l = l;
    }

    public Level getLevel() {
        return this.l;
    }
}
