package org.socialforce.geom.impl;

import org.socialforce.geom.Box;
import org.socialforce.drawer.Drawer;
import org.socialforce.geom.Point;
import org.socialforce.geom.Shape;

/**
 * this is a 2D segment
 * defined by two point
 * Created by Whatever on 2016/8/10.
 */
public class Line2D implements Shape {
    //protected Point2D a, b;
    //protected double k1, b1, startX, endX;
    protected double x1, x2, y1, y2;
    protected Drawer drawer;

    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    @Override
    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
    }

    public Line2D() {
    }

    public Line2D(Point2D a, Point2D b) {
        if (a == b) {
            throw new IllegalArgumentException("a and b can not be the same point");
        }
        //this.a = a;
        //this.b = b;
        x1 = a.getX();
        x2 = b.getX();
        y1 = a.getY();
        y2 = b.getY();
       /* if (x1 != x2) {
            k1 = (y1 - y2) / (x1 - x2);
            b1 = y1 - k1 * x1;
            startX = Math.min(x1, x2);
            endX = Math.max(x1, x2);
        }*/
    }

    public Line2D(double k1, double b1, double startX, double endX) {
        if (startX == endX) {
            throw new IllegalArgumentException("a and b can not be the same point");
        }
        /*this.k1 = k1;
        this.b1 = b1;
        this.startX = startX;
        this.endX = endX;*/
        x1 = startX;
        x2 = endX;
        y1 = k1 * startX + b1;
        y2 = k1 * endX + b1;
       /* a = new Point2D(startX, k1 * startX + b1);
        b = new Point2D(endX, k1 * endX + b1);*/
    }

    @Override
    public int dimension() {
        return 2;
    }

    @Override
    public boolean contains(Point point) {
        double x = point.getX();
        double y = point.getY();
        double dx1 = x - x1;
        double dx2 = x - x2;
        double dy1 = y - y1;
        double dy2 = y - y2;
        double dx12 = x1 - x2;
        double dy12 = y1 - y2;
        dx1 *= dx1;
        dx2 *= dx2;
        dy1 *= dy1;
        dy2 *= dy2;
        dx12 *= dx12;
        dy12 *= dy12;
        double delta = dx12 + dy12 - dx1 - dy1 - dx2 - dy2;
        return delta >= 0 && Math.abs(4 * (dx1 + dy1) * (dx2 + dy2) - delta * delta) < 1e-15;
    }

    @Override
    public double getDistance(Point point) {
        /*double distance = 0;
        int flag = 0;
        if (y1 == y2) {
            flag = 1;
        }
        if (x1 == x2) {
            flag = 2;
        }
        switch (flag) {
            case 0:
                distance = Math.abs((k1 * point.getX() - point.getY() + b1) / Math.sqrt(k1 * k1 + 1));
                break;
            case 1:
                distance = Math.abs(y1 - point.getY());
                break;
            case 2:
                distance = Math.abs(x1 - point.getX());
                break;
        }*/
        double x = point.getX(), y = point.getY();
        //equation: (x-x1)(y2-y1) - (x2-x1)(y-y1) = 0
        double distance;
        double dy12 = y2 - y1;
        double dx12 = x2 - x1;
        double dx1 = x - x1;
        double dy1 = y - y1;
       /* double dx2 = x - x2;
        double dy2 = y - y2;*/
        double dot = dx1*dx12+dy1*dy12;
        double len_sq = dx12*dx12+dy12*dy12;
        double scale = dot / len_sq;
        double tx,ty;
        if(scale < 0) {
            tx = x1;
            ty = y1;
        } else if(scale > 1) {
            tx = x2;
            ty = y2;
        } else {
            tx = x1 + scale * dx12;
            ty = y1+scale*dy12;
        }
        tx -= x;
        ty -= y;
        return Math.sqrt(tx*tx+ty*ty);
        //2016/8/19 fix distance with equation.
        /*distance = Math.abs(dx1 * a - dy1 * b) / Math.sqrt(a * a + b * b);
        double disA = Math.sqrt(dx1 * dx1 + dy1 * dy1);
        double disB = Math.sqrt(dx2 * dx2 + dy2 * dy2);
        return Math.min(Math.min(disA, disB), distance);*/
    }

    //  2016/8/19 refactored with x1,y1,x2,y2.;
    @Override
    public Point getReferencePoint() {
        return new Point2D((x1 + x2) / 2, (y1 + y2) / 2);
    }

    @Override
    public Box getBounds() {
        return new Box2D(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    @Override
    public boolean hits(Box hitbox) {
        return getBounds().hits(hitbox);
    }

    @Override
    public void moveTo(Point location) {
        double movedX, movedY;
        movedX = getReferencePoint().getX() - location.getX();
        movedY = getReferencePoint().getY() - location.getY();
        x1 -= movedX;
        y1 -= movedY;
        x2 -= movedX;
        y2 -= movedY;
        /*a = new Point2D(x1 - movedX, y1 - movedY);
        b = new Point2D(x2 - movedX, y2 - movedY);*/
/*        if (x1 != x2) {
            k1 = (y1 - y2) / (x1 - x2);
            b1 = y1 - k1 * x1;
            startX = Math.min(x1, x2);
            endX = Math.max(x1, x2);
        }*/
    }

    /**
     * * Creates and returns a copy of this Line.
     * The precise meaning of "copy" may depend on the class of the line.
     * @return the copied line
     */
    @Override
    public Line2D clone() {
        Line2D cloned = new Line2D();
        cloned.x1 = x1;
        cloned.x2 = x2;
        cloned.y1 = y1;
        cloned.y2 = y2;
        //Line2D lineClone = new Line2D(a, b);
        return cloned;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Line2D) {
            Line2D tg = (Line2D) obj;
            if (Math.abs(x1 - tg.x1) < 1e-15 && Math.abs(y1 - tg.y1) < 1e-15) {
                return Math.abs(x2 - tg.x2) < 1e-15 && Math.abs(y2 - tg.y2) < 1e-15;
            } else {
                return Math.abs(x1 - tg.x2) < 1e-15 && Math.abs(y1 - tg.y2) < 1e-15 && Math.abs(x2 - tg.x1) < 1e-15 && Math.abs(y2 - tg.y1) < 1e-15;
            }
/*if (getReferencePoint().equals(testLine.getReferencePoint()) &&
                    a.distanceTo(testLine.getReferencePoint()) == a.distanceTo(getReferencePoint())
                    && testLine.getDistance(a) == 0) {
                return true;
            } else {
                return false;
            }*/
        }
        return false;
    }
/*
    public double distanceToSegment(Point2D point) {
        double distance, temp;
        distance = getDistance(point);
        temp = a.distanceTo(point);
        if (b.distanceTo(point) < a.distanceTo(point)) {
            temp = b.distanceTo(point);
        }
        if (b.distanceTo(point) * b.distanceTo(point) - a.distanceTo(point) * a.distanceTo(point) + a.distanceTo(b) * a.distanceTo(b) < 0 ||
                -b.distanceTo(point) * b.distanceTo(point) + a.distanceTo(point) * a.distanceTo(point) + a.distanceTo(b) * a.distanceTo(b) < 0) {
            distance = temp;
        }
        return distance;
    }

    public void setParallelX(double Y, double startX, double endX) {
        this.k1 = 0;
        this.b1 = Y;
        this.startX = startX;
        this.endX = endX;
        this.a = new Point2D(startX, Y);
        this.b = new Point2D(endX, Y);
    }

    public void setParallelY(double X, double startY, double endY) {
        this.a = new Point2D(X, startY);
        this.b = new Point2D(X, endY);
        //this.k1 = 10000;
    }
*/
    /*这个方法从来没有被调用过，目前来说不会引发任何的问题，
    主要是怕在set的时候如果k是无穷，只设置ab，没设置k1b1，导致之后直接调用k1b1时出现问题。
    但是目前的方法里暂时没有这种情况出现。
    protected void quiteConvert(Line2D line){
            if (k1 < 9999) {
            //do nothing
            } else {
                throw new IllegalArgumentException("the slope of line is null or too big, please ues point A and B");
            }
            
        }*/

}
