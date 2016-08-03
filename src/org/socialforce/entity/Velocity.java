package org.socialforce.entity;

/**
 * Created by Ledenel on 2016/7/30.
 */
public interface Velocity extends Vector {
    Vector deltaDistance(double time);
    Velocity clone();
}
