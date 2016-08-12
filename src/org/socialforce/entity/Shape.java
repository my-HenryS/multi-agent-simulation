package org.socialforce.entity;

import java.io.Serializable;

/**
 * represent a geometry shape in the coordinate.
 * in 2-Dimensional space, may be a circle, line, triangle, rectangle, etc.
 * in 3-Dimensional space, may be a sphere, ball, cuboid, etc.
 * especially, a void shape contains nothing.
 * @author Ledenel
 * @see Box
 * @see InteractiveEntity
 * Created by Ledenel on 2016/7/28.
 */
public interface Shape extends Serializable, Cloneable, DimensionEntity, Drawable {
    /**
     * check if a point belongs to this <code>Shape</code>.
     *
     * @param point the point to be checked.
     *
     * @return true if the point is a part of the shape; otherwise false.
     */
    boolean contains(Point point);

    /**
     * get the distance between a point and this shape.
     * the distance is 0 if the point is in this shape.
     * Double.NaN for the void shape.
     *
     * @param point the point to be checked.
     * @return the distance. return Double.NaN if the point can't reach the shape.
     */
    double getDistance(Point point);

    /**
     * get the reference point of this shape.
     * usually a reference point is the center point of this shape.
     * for a circle/ball, it is the center.
     *
     * @return the reference point. returns null if the shape is void.
     */
    Point getReferencePoint();

    /**
     * get the bound of this shape.
     * returns null if the shape can't be put into a box.
     * returns a void shape if the shape itself is void.
     *
     * @return a box represent the bounds of this shape.
     */
    Box getBounds();

    /**
     * checks if this shape intersects with a box called hitbox.
     *
     * @param hitbox the box to be checked.
     * @return true if intersects; otherwise false.
     */
    boolean hits(Box hitbox);

    /**
     * move the shape to a specified location.
     * for the non-void shape, the reference point is equals to the location point.
     * for the void shape, do nothing.
     * @param location the specified location
     */
    void moveTo(Point location);

    /**
     * creates and returns a copy of this shape.
     * @return the copied shape.
     */
    Shape clone();
}
