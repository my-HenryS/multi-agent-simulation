package org.socialforce.geom;

/**
 * Created by Administrator on 2017/6/26 0026.
 */
public interface Palstance {
    double deltaAngle(double time);
    Palstance clone();
    void add(Palstance other);
    void scale(double rate);
}
