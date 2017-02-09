package org.socialforce.geom.impl;

import org.socialforce.geom.Point;
import org.socialforce.geom.PrimitiveShape;
import org.socialforce.geom.Vector;

/**
 * Created by Administrator on 2017/2/8.
 */
public class Arc2D implements PrimitiveShape {
    protected double startangle,angle,radius;
    protected Point2D center;

    public Arc2D(double startangle, double angle, double radius, Point2D center) {
        this.startangle = startangle;
        this.angle = angle;
        this.radius = radius;
        this.center = center;
    }

    @Override
    public boolean intersect(PrimitiveShape shape) {
        if (shape instanceof Segment2D){

        }
        if (shape instanceof Point2D){
            return shape.distanceTo(this) == 0;
        }
        if (shape instanceof Arc2D){

        }
        return false;
    }

    @Override
    public Point[] intersectPoint(PrimitiveShape shape) {
        return null;
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
    public void rotate(Point point, double angle) {
        center.rotate(point,angle);
        startangle += angle;
    }

    @Override
    public void relativeExpand(double scale) {
        radius = radius*scale;
    }

    @Override
    public void absoluteExpand(double value) {
        radius = value;
    }

    @Override
    public Point getReferencePoint() {
        return center;
    }

    @Override
    public int dimension() {
        return 2;
    }

    public Circle2D getCircle(){
        return new Circle2D(center,radius);
    }

    public Point2D[] getEndpoints(){
        Point2D a,b;
        double x1,x2,y1,y2;
        x1 = radius*Math.cos(startangle);
        x2 = radius*Math.cos(startangle+angle);
        y1 = radius*Math.sin(startangle);
        y2 = radius*Math.sin(startangle+angle);
        a = (Point2D) center.moveBy(x1,y1);
        b = (Point2D) center.moveBy(x2,y2);
        return new Point2D[]{a,b};
    }
}
