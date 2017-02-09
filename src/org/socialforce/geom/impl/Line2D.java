package org.socialforce.geom.impl;

import org.socialforce.geom.Point;
import org.socialforce.geom.PrimitiveShape;
import org.socialforce.geom.Vector;

/**
 * Created by Administrator on 2017/2/9.
 */
public class Line2D implements PrimitiveShape {
    @Override
    public boolean intersect(PrimitiveShape shape) {
        return false;
    }

    @Override
    public Point[] intersectPoint(PrimitiveShape shape) {
        return new Point[0];
    }

    @Override
    public double distanceTo(PrimitiveShape shape) {
        return 0;
    }

    @Override
    public Vector directionTo(PrimitiveShape shape) {
        return null;
    }

    @Override
    public void rotate(Point point, double angel) {

    }

    @Override
    public void relativeExpand(double scale) {

    }

    @Override
    public void absoluteExpand(double value) {

    }

    @Override
    public Point getReferencePoint() {
        return null;
    }

    @Override
    public int dimension() {
        return 0;
    }
}
