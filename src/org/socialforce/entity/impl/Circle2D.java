package org.socialforce.entity.impl;

import org.socialforce.entity.*;

/**
 * this is a 2D-circle set by its radius and center
 * Created by Whatever on 2016/8/8.
 */
public class Circle2D implements DistanceShape {
    protected double radius=0;//,center[];
    protected Point center = new Point2D(0,0);
    protected Drawer drawer;

    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    @Override
    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
    }




    @Override
    public int dimension() {
        return 2;
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


    public  double getRadius(){
        return radius;
    }

    @Override
    public Box getBounds() {
        Box2D hitbox = new Box2D(getReferencePoint().getX()-radius,getReferencePoint().getY()-radius,2*radius,2*radius);
        return hitbox;
    }

    @Override
    public boolean hits(Box hitbox) {
        return getBounds().hits(hitbox);
    }

    @Override
    public void moveTo(Point location) {
        center = location.clone();
    }

    public void setRadius(double radius){
        this.radius = radius;
    }

    @Override
    public Shape clone() {
        Circle2D circle = new Circle2D();
        circle.moveTo(center);
        circle.setRadius(radius);
        return circle;
    }

    @Override
    public double distanceTo(Shape other) {
        return other.getDistance(this.center) - this.radius;
    }
}
