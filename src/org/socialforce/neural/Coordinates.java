package org.socialforce.neural;

/**
 * Created by micha on 2017/3/27.
 */
public class Coordinates {
    private double a;
    private double b;
    public Coordinates(double aIn, double bIn) {
        a = aIn;
        b = bIn;
    }
    public double X(){
        return a;
    }
    public double Y(){
        return b;
    }

    public boolean isZero(){ return a == 0 && b == 0; }
}
