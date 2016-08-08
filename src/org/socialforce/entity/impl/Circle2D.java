package org.socialforce.entity.impl;

import org.socialforce.entity.Box;
import org.socialforce.entity.Drawer;
import org.socialforce.entity.Point;
import org.socialforce.entity.Shape;

/**
 * this is a 2D-circle set by its radius and center
 * Created by Whatever on 2016/8/8.
 */
public class Circle2D implements Shape {
    protected double radius;//,center[];
    protected Point center;

    @Override
    public int dimension() {
        return 2;
    }

    @Override
    public void setDrawer(Drawer drawer) {
    //drawer not initialized
    }

    @Override
    public Drawer getDrawer() {
        return null;
        //drawer not initialized
    }

    @Override
    public boolean contains(Point point) {
        return (point.distanceTo(center)<radius);
    }

    @Override
    public double getDistance(Point point) {
        if(this.contains(point)){
        return 0;}
        else return point.distanceTo(center)-radius;

    }

    @Override
    public Point getReferencePoint() {
        return center;
    }


    public  void getRadius(double radius){
        this.radius = radius;
    }

    @Override
    public Box getBounds() {
        return null;
    }

    @Override
    public boolean hits(Box hitbox) {
        return false;
    }

    @Override
    public void moveTo(Point location) {
        center = location.clone();
    }

    @Override
    public Shape clone() {
        Circle2D circle = new Circle2D();
        circle.moveTo(center);
        circle.getRadius(radius);
        return circle;
    }
}
