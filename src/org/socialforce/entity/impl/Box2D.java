package org.socialforce.entity.impl;

import org.socialforce.entity.*;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Ledenel on 2016/8/8.
 */
public class Box2D implements Box {

    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    @Override
    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
    }

    protected Drawer drawer;

    protected double xmin, ymin, xmax, ymax;

    public Box2D() {

    }

    public Box2D(double left, double bottom, double width, double height) {
        double right = left + width;
        double top = bottom + height;
        double temp;
        if (right < left) {
            temp = right;
            right = left;
            left = temp;
        }
        if (top < bottom) {
            temp = top;
            top = bottom;
            bottom = temp;
        }
        xmin = left;
        xmax = right;
        ymin = bottom;
        ymax = top;
    }

    public Box2D(Point start, Point end) {
        this(start.getX(),
                start.getY(),
                end.getX() - start.getX(),
                end.getY() - start.getY());
    }

    /**
     * check if a point belongs to this <code>Shape</code>.
     *
     * @param point
     * @return true if the point is a part of the shape; otherwise false.
     */
    @Override
    public boolean contains(Point point) {
        return xmin <= point.getX()
                && point.getX() <= xmax
                && ymin <= point.getY()
                && point.getY() <= ymax;
    }

    /**
     * get the distance between a point and this shape.
     * the distance is 0 if the point is in this shape.
     * Double.NaN for the void shape.
     *
     * @param point the point to be checked.
     * @return the distance. return Double.NaN if the point can't reach the shape.
     */
    @Override
    public double getDistance(Point point) {
        if (contains(point)) {
            return 0;
        }
        double dsx = point.getX() - xmin;
        double dsy = point.getY() - ymin;
        double dex = point.getX() - xmax;
        double dey = point.getY() - ymax;
        if (dsx > 0) {
            if (dsy > 0) {
                if (dex > 0) {
                    if (dey > 0) {
                        return point.distanceTo(new Point2D(xmax, ymax));
                    } else { // dey <= 0
                        return dex;
                    }
                } else { // dex <= 0
                    return dey;
                }
            } else { // dsy <= 0
                if (dex > 0) {
                    return point.distanceTo(new Point2D(xmax, ymin));
                } else { // dex <= 0
                    return -dsy;
                }
            }
        } else { // dsx <= 0
            if (dsy > 0) {
                if (dey > 0) {
                    return point.distanceTo(new Point2D(xmin, ymax));
                } else { // dey <= 0
                    return -dsx;
                }
            } else { // dsy <= 0
                return point.distanceTo(new Point2D(xmin, ymin));
            }
        }
        //return Double.NaN;
    }

    /**
     * get the reference point of this shape.
     * usually a reference point is the center point of this shape.
     * for a circle/ball, it is the center.
     *
     * @return the reference point. returns null if the shape is void.
     */
    @Override
    public Point getReferencePoint() {
        return new Point2D((xmin + xmax) / 2, (ymin + ymax) / 2);
    }

    /**
     * get the bound of this shape.
     * returns null if the shape can't be put into a box.
     * returns a void shape if the shape itself is void.
     *
     * @return a box represent the bounds of this shape.
     */
    @Override
    public Box getBounds() {
        return (Box) this.clone();
    }

    protected Box2D quiteConvert(Box box) {
        if (box instanceof Box2D) {
            return (Box2D) box;
        } else {
            throw new IllegalArgumentException("Box2D could only calculated with Box2D.");
        }
    }

    /**
     * checks if this shape intersects with a box called hitbox.
     *
     * @param hitbox the box to be checked.
     * @return true if intersects; otherwise false.
     */
    @Override
    public boolean hits(Box hitbox) {
        Box2D in = quiteConvert(hitbox);
        return ymin <= in.ymin && in.ymin <= ymax ||
                ymin <= in.ymax && in.ymax <= ymax ||
                xmin <= in.xmin && in.xmin <= xmax ||
                xmin <= in.xmax && in.xmax <= xmax;
    }

    /**
     * move the shape to a specified location.
     * for the non-void shape, the reference point is equals to the location point.
     * for the void shape, do nothing.
     *
     * @param location the specified location
     */
    @Override
    public void moveTo(Point location) {
        double xsum = location.getX() * 2;
        double ysum = location.getY() * 2;
        double xsub = xmin - xmax;
        double ysub = ymin - ymax;
        xmin = (xsum + xsub) / 2;
        ymin = (ysum + ysub) / 2;
        xmax = (xsum - xsub) / 2;
        xmin = (ysum - ysub) / 2;
    }

    /**
     * creates and returns a copy of this shape.
     *
     * @return the copied shape.
     */
    @Override
    public Shape clone() {
        Box2D cloned = new Box2D();
        cloned.xmin = xmin;
        cloned.xmax = xmax;
        cloned.ymin = ymin;
        cloned.ymax = ymax;
        return cloned;
    }

    /**
     * get the start point of the box.
     *
     * @return the start point.
     */
    @Override
    public Point getStartPoint() {
        return new Point2D(xmin, ymin);
    }

    /**
     * get the end point of the box.
     *
     * @return the end point.
     */
    @Override
    public Point getEndPoint() {
        return new Point2D(xmax, ymax);
    }

    /**
     * get the size of the box.
     *
     * @return the vector represented the size.
     */
    @Override
    public Vector getSize() {
        return new Vector2D(xmax - xmin, ymax - ymin);
    }

    /**
     * returns the intersection of this box and other box.
     *
     * @param other the box to be intersected.
     * @return the intersection.
     */
    @Override
    public Box intersect(Box other) {
        Box2D in = quiteConvert(other);
        if (hits(in)) {
            double[] xarr = new double[]{xmin, xmax, in.xmin, in.xmax};
            double[] yarr = new double[]{ymin, ymax, in.ymin, in.ymax};
            Arrays.sort(xarr);
            Arrays.sort(yarr);
            Box2D box = new Box2D();
            box.xmin = xarr[1];
            box.xmax = xarr[2];
            box.ymin = yarr[1];
            box.ymax = yarr[2];
            return box;
        }
        return null;
    }

    /**
     * get the dimension of the DimensionEntity.
     *
     * @return the dimension.
     */
    @Override
    public int dimension() {
        return 2;
    }
}
