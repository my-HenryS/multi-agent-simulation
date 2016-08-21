package org.socialforce.geom;

/**
 * Created by Ledenel on 2016/8/19.
 */
public interface DistanceShape extends Shape {
    double distanceTo(Shape other);
}
