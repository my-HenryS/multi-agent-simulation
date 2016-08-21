package org.socialforce.geom.impl;

import org.socialforce.geom.Point;

/**
 * Created by Ledenel on 2016/8/8.
 */
public class Point2D extends Vector2D implements Point {
    /**
     * get the X coordinate of the point
     *
     * @return the X coordinate
     */
    @Override
    public double getX() {
        return values[0];
    }

    /**
     * get the Y coordinate of the point
     *
     * @return the Y coordinate
     */
    @Override
    public double getY() {
        return values[1];
    }

    /**
     * get the distance between this point and other point.
     * could be multi-dimensional distance.
     *
     * @param other the other point that should calculate the distance from
     * @return distance.
     */
    @Override
    public double distanceTo(Point other) {
        double dx = this.getX() - other.getX();
        double dy = this.getY() - other.getY();
        return Math.sqrt(dx*dx+dy*dy);
    }


    public Point2D() {
        super();
    }

    public Point2D(double x, double y) {
        super(x, y);
    }

    /**
     * Creates and returns a copy of this vector.
     * The precise meaning of "copy" may depend on the class of the vector.
     * The general intent is that, for any vector x, the expression: <br>
     * x.clone() != x <br>
     * will be true.
     *
     * @return the copied vector.
     */
    @Override
    public Point clone() {
        Point2D point2D = new Point2D();
        this.get(point2D.values);
        return point2D;
    }
}
