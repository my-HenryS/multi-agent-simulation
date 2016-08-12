package org.socialforce.entity.impl;

import org.socialforce.entity.Box;
import org.socialforce.entity.Drawer;
import org.socialforce.entity.Point;
import org.socialforce.entity.Shape;

/**
 * this is a 2D segment
 * defined by two point
 * Created by Whatever on 2016/8/10.
 */
public class Line2D implements Shape {
    protected Point2D a,b;
    protected double k1=10000;
    protected double b1,startX,endX;
    protected Drawer drawer;

    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    @Override
    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
    }


    public Line2D(Point2D a, Point2D b){
        if(a == b){throw new IllegalArgumentException("a and b can not be the same point");}
        this.a = a;this.b = b;
        if(a.getX() != b.getX()){
        k1 = (a.getY() - b.getY())/(a.getX()-b.getX());
        b1 = a.getY() - k1*a.getX();
        startX = Math.min(a.getX(),b.getX());
        endX = Math.max(a.getX(),b.getX());
        }
    }

    public Line2D(double k1,double b1, double startX,double endX){
        if(startX == endX){throw new IllegalArgumentException("a and b can not be the same point");}
        this.k1 = k1; this.b1 = b1; this.startX = startX; this.endX = endX;
        a = new Point2D(startX,k1*startX+b1);
        b = new Point2D(endX,k1*endX+b1);
        this.k1 = 10000;
    }

    @Override
    public int dimension() {
        return 2;
    }


    @Override
    public boolean contains(Point point) {

        if(point.distanceTo(a)+point.distanceTo(b) == a.distanceTo(b)){
            return true;
        }
        else  return false;
    }

    @Override
    public double getDistance(Point point) {
        double distance = 0;int flag = 0;
        if(a.getX() == b.getX()){flag = 1;}
        if(a.getY() == b.getY()){flag = 2;}
        switch (flag) {
            case 0:
                distance = Math.abs((k1*point.getX()-point.getY()+b1)/Math.sqrt(k1*k1+1));
                break;
            case 1:
                distance = Math.abs(a.getY() - point.getY());
                break;
            case 2:
                distance = Math.abs(a.getX() - point.getX());
                break;
        }
        return distance;
    }

    @Override
    public Point getReferencePoint() {
        return new Point2D((a.getX()+b.getX())/2,(a.getY()+b.getY())/2);
    }

    @Override
    public Box getBounds() {
        return new Box2D(Math.min(a.getX(),b.getX()),Math.min(a.getY(),b.getY()),Math.abs(a.getX() - b.getX()),Math.abs(a.getY() - b.getY()));
    }

    @Override
    public boolean hits(Box hitbox) {
        return getBounds().hits(hitbox);
    }

    @Override
    public void moveTo(Point location) {
        double movedX,movedY;
        movedX = getReferencePoint().getX() - location.getX();
        movedY = getReferencePoint().getY() - location.getY();
        a = new Point2D(a.getX()-movedX,a.getY()-movedY);
        b = new Point2D(b.getX()-movedX,b.getY()-movedY);
        if(a.getX() != b.getX()) {
            k1 = (a.getY() - b.getY()) / (a.getX() - b.getX());
            b1 = a.getY() - k1 * a.getX();
            startX = Math.min(a.getX(), b.getX());
            endX = Math.max(a.getX(), b.getX());
        }
    }

    @Override
    public Line2D clone() {
        Line2D lineClone = new Line2D(a,b);
        return lineClone;
    }

    public boolean equals(Line2D testLine){
        if(testLine.getReferencePoint() == getReferencePoint() && a.distanceTo(testLine.getReferencePoint()) == a.distanceTo(getReferencePoint())
                && testLine.getDistance(a)==0){
            return true;
        }
        else {return false;}
    }

    public double distanceToSegment(Point2D point){
        double distance;
        distance = getDistance(point);
        if (distance > a.distanceTo(point)){
            distance = a.distanceTo(point);
        }
        if (distance > b.distanceTo(point)){
            distance = b.distanceTo(point);
        }
        return distance;
    }
    
    public void setParallelX(double Y,double startX,double endX){
    this.k1 = 0; this.b1 = Y;
        this.startX = startX; this.endX = endX;
        this.a = new Point2D(startX,Y); this.b = new Point2D(endX,Y);
    }
    
    public void  setParallelY(double X,double startY,double endY){
        this.a = new Point2D(X,startY);
        this.b = new Point2D(X,endY);
        this.k1 = 10000;
    }


    protected void quiteConvert(Line2D line){
            if (k1 < 9999) {
            //do nothing
            } else {
                throw new IllegalArgumentException("the slope of line is null or too big, please ues point A and B");
            }
        }

}
