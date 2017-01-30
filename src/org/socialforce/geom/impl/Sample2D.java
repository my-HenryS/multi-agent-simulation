package org.socialforce.geom.impl;

import org.socialforce.geom.DistanceShape;
import org.socialforce.geom.Point;
import org.socialforce.geom.Shape;
import org.socialforce.geom.Vector;

/**
 * Created by Administrator on 2017/1/30.
 */
public class Sample2D extends Box2D implements DistanceShape {
    public Sample2D(double left, double bottom, double width, double height) {
        super(left, bottom, width, height);
    }

    public Sample2D(Point start, Point end) {
        super(start, end);
    }


    @Override
    public double distanceTo(Shape other) {
        return 0;
    }

    @Override
    public Vector directionTo(Shape other) {
        return null;
    }

    @Override
    public boolean intersects(Shape other) {
        Point2D point = (Point2D) other.getReferencePoint();
        if (contains(point)){
            /*Segment2D[]bounds = new Segment2D[]{new Segment2D(new Point2D(xmin,ymin),new Point2D(xmin,ymax))
            ,new Segment2D(new Point2D(xmax,ymin),new Point2D(xmin,ymin))};
            if (bounds[0].contains(point) || bounds[1].contains(point)){
                return false;
            }
            else */return true;
        }
        else return false;
    }

    @Override
    public DistanceShape clone(){
        return new Sample2D(xmin,ymin,xmax-xmin,ymax-ymin);
    }
}
