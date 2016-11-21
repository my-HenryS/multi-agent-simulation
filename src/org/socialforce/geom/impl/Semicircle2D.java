package org.socialforce.geom.impl;

import org.socialforce.drawer.Drawer;
import org.socialforce.geom.Box;
import org.socialforce.geom.Point;
import org.socialforce.geom.Shape;
import org.socialforce.geom.Vector;

/**
 * Created by sunjh1999 on 2016/11/12.
 */
public class Semicircle2D implements Shape {
    protected Drawer drawer;
    protected Rectangle2D bounding_box;
    protected Circle2D bounding_circle;
    protected Point2D center;
    protected double radius,angle;

    public Semicircle2D(Point2D center, double radius, double angle){
        this.radius = radius;
        this.center = center;
        this.angle = angle;
        bounding_circle = new Circle2D(center,radius);
        bounding_box = new Rectangle2D(new Point2D(center.getX()-0.5*radius*Math.sin(angle),center.getY()+0.5*radius*Math.cos(angle)),2*radius,radius,angle);
    }
    @Override
    public void setDrawer(Drawer drawer) {
        this.drawer = drawer
        ;
    }

    @Override
    public Drawer getDrawer() {
        return this.drawer;
    }

    @Override
    public int dimension() {
        return 2;
    }

    @Override
    public boolean contains(Point point) {
        return bounding_box.contains(point) && bounding_circle.contains(point);
    }

    @Override
    public Vector getDistance(Point point) {
        return null;
    }

    @Override
    public Point getReferencePoint() {
        return null;
    }

    @Override
    public Box getBounds() {
        return null;
    }

    @Override
    public boolean hits(Box hitbox) {
        return bounding_box.hits(hitbox) && bounding_circle.hits(hitbox);
    }

    @Override
    public void moveTo(Point location) {
         //并不建议在实际应用中用此方法 最好新建一个半圆形区域
        bounding_circle.moveTo(location);
        bounding_box.moveTo(new Point2D(center.getX()-radius*Math.sin(angle),center.getY()+radius*Math.cos(angle)));
    }

    public void spin(double angle){
        this.angle = this.angle +angle;
    }

    public double getAngle(){
        return angle;
    }

    @Override
    public Shape clone() {
        return new Semicircle2D(center, radius, angle);
    }
}
