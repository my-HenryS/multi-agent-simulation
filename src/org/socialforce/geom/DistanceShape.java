package org.socialforce.geom;

public interface DistanceShape extends Shape {
    /**
     * get the distance between this shape and  other shape
     * @return the distanceto
     */
    double distanceTo(Shape other);
}
