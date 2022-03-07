package io.spherious.main;

import io.spherious.engine.math.Vector2D;
import io.spherious.main.game.GameFrame;

public class Application {
    public static void main(String[] args) {
        new GameFrame();

        //System.out.println(reflect(new Vector2D(-10,.5), new Vector2D(1,0)));
//        for (int i = -100; i < 100; i++) {
//            for (int j = -100; j < 100; j++) {
//                for (int k = -100; k < 100; k++) {
//                    for (int l = -100; l < 100; l++) {
//                        Vector2D a = new Vector2D(i,j);
//                        Vector2D b = new Vector2D(k,l);
//                        a.normalize();
//                        b.normalize();
//                        Vector2D reflected = reflect(a, b);
//
//                        if(a.dot(b) - b.dot(reflected) > 0.001)
//                            System.out.println(a+"__"+b+"__"+reflected);
//                    }
//                }
//            }
//        }


    }
    //flipping wrong way

    private static Vector2D reflect(Vector2D a, Vector2D b){
        double magA = a.magnitude();
        a.negate();
        a.normalize();
        b.normalize();

        double c = a.dot(b) * 2;
        Vector2D d = b.multiply(c);
        d.subtract(a);
        d.normalize();

        return d.multiply(magA);


    }
//    private static Vector2D reflect(Vector2D a, Vector2D b){
//        b = new Vector2D(b.getY(), b.getX());
//        b.normalize();
//
//        Vector2D vec2 = new Vector2D(b.getY(), -1*b.getX());
//        Vector2D normal = b.copy();
//
//        double dpa = a.dot(vec2);
//        Vector2D pra = vec2.multiply(dpa);
//        double dpb = a.dot(normal);
//        Vector2D prb = normal.multiply(dpb);
//        pra.subtract(prb);
//
//        return pra;
//    }
}
