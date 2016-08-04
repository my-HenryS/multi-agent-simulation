package org.socialforce.entity;

/**
 * represent a point in coordinate.
 * @author Ledenel
 * @see Vector
 * Created by Ledenel on 2016/7/28.
 */
public interface Point extends Vector {
    
    /**
     * get the X coordinate of the point
     * @return the X coordinate
     */ 
    double getX();
    
    /**
     * get the Y coordinate of the point
     * @return the Y coordinate
     */ 
    double getY();

    /**
     * get the distance between this point and other point.
     * could be multi-dimensional distance.
     * @param other the other point that should calculate the distance from
     * @return distance.
     */
    double distanceTo(Point other);

    /**
     * creates and returns a copy of this point.
     * @return the cloned point.
     */
    Point clone();
}
