package io.spherious.main.resources;

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
        add(new RectObject(450, 100, 640, 150, 0.1f, false, true, 50, false, true, () -> {

        }, "", true));
        add(new RectObject(450, 1, 500, 100, 0.1f, false, true, 50, false, true, () -> {

        }, "", true));
        add(new RectObject(590, 1, 640, 100, 0.1f, false, true, 50, false, true, () -> {

        }, "", true));

        add(new RectObject(570, 400, 920, 420, 2, false, true, 50, false, true, () -> {

        }, "", true));
        add(new RectObject(570, 400, 590, 750, 0.1f, false, true, 50, true, true, () -> {

        }, "", true));
        add(new RectObject(570, 730, 920, 750, 0.1f, false, true, 50, true, true, () -> {

        }, "", true));
        add(new RectObject(900, 400, 920, 750, 0.1f, false, true, 50, true, true, () -> {

        }, "", true));

        add(new RectObject(530, 1, 560, 30, 0.1f, true, true, 50, false, true, () -> {

        }, "assets/grass.jpg", true));

    }}, new Point(650, 450), false, 1500, 1000, 2));


    private final Level l;

    Levels(Level l) {
        this.l = l;
    }

    public Level getLevel() {
        return this.l;
    }
}
