package org.socialforce.geom.impl;

import org.socialforce.drawer.Drawer;
import org.socialforce.geom.Box;
import org.socialforce.geom.DistanceShape;
import org.socialforce.geom.Point;
import org.socialforce.geom.Shape;

/**
 * this is a 2D-circle set by its radius and center
 * Created by Whatever on 2016/8/8.
 */

/**
 *
 */
public class Circle2D implements DistanceShape {
    /**
     * @param radius:the radius of a Circle2D
     * @param center:the center of a Circle2D
     * @see Drawer
     */
    protected double radius=0;//,center[];
    protected Point center = new Point2D(0,0);
    protected Drawer drawer;

    /**
     * get the drawer
     * @return the drawer
     */
    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    /**
     * set the drawer
     * @param drawer the drawer.
     */
    @Override
    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
    }

    /**
     * get the dimension
     * @return
     */
    @Override
    public int dimension() {
        return 2;
    }


    /**
     *cacluate the distance between the point which in the circle and the center of the circle.
     * @param point the point to be checked.
     *
     * @return point.distanceto : the distance between a point and the center of a circle
     */
    @Override
    public boolean contains(Point point) {
        return (point.distanceTo(center)<radius);
    }

    /**
     * get the distance between a point,which out of the circle ,and the circle
     * @param point the point to be checked.
     * @return the distance.
     *  return 0; if the point is contain in the circle
     *  return point.distanceTo(center)-radius: if the point is out of the circle
     */
    @Override
    public double getDistance(Point point) {
        if(this.contains(point)){
            return 0;}
        else return point.distanceTo(center)-radius;

    }

    /**
     * get the reference circle of a point.
     * @return center, the center of a circle.
     */
    @Override
    public Point getReferencePoint() {
        return center;
    }

    /**
     * get the radius of a circle
     * @return radius, the radius of a circle
     */
    public  double getRadius(){
        return radius;
    }

    /**
     * get the bound of box
     * @return
     */
    @Override
    public Box getBounds() {
        return new Box2D(getReferencePoint().getX()-radius,getReferencePoint().getY()-radius,2*radius,2*radius);
    }

    /**
     *judge whether hit the box or not
     * @param hitbox the box to be checked.
     * @return tree if hit the box
     */
    @Override
    public boolean hits(Box hitbox) {
        return getBounds().hits(hitbox);
    }

    /**
     *
     * @param location the specified location
     */
    @Override
    public void moveTo(Point location) {
        center = location.clone();
    }

    public void setRadius(double radius){
        this.radius = radius;
    }

    /**
     *
     * @return
     */
    @Override
    public Shape clone() {
        Circle2D circle = new Circle2D();
        circle.moveTo(center);
        circle.setRadius(radius);
        return circle;
    }

    /**
     *
     * @param other
     * @return
     */
    @Override
    public double distanceTo(Shape other) {
        return other.getDistance(this.center) - this.radius;
    }
}
