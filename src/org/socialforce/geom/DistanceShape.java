package org.socialforce.geom;

/**
 * Created by Ledenel on 2016/8/19.
 */

/**
 * get the distance between this shape and  other shape
 * @return the distanceto
 */
public interface DistanceShape extends Shape {
    double distanceTo(Shape other);
}
