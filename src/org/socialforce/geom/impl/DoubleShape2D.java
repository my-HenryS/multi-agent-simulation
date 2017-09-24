package org.socialforce.geom.impl;

import org.socialforce.drawer.Drawer;
import org.socialforce.geom.Box;
import org.socialforce.geom.PhysicalEntity;
import org.socialforce.geom.Point;
import org.socialforce.geom.Vector;

/**
 * Created by Administrator on 2017/9/22 0022.
 */
public class DoubleShape2D extends Shape2D implements PhysicalEntity {

    protected Drawer drawer;
    protected PhysicalEntity shape[];

    public DoubleShape2D(PhysicalEntity shape1, PhysicalEntity shape2){
        shape = new PhysicalEntity[2];
        shape[0] = shape1;
        shape[1] = shape2;
    }

    @Override
    public int dimension() {
        return 2;
    }

    @Override
    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
    }

    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    @Override
    public boolean contains(Point point) {
        return shape[0].contains(point)||shape[1].contains(point);
    }

    @Override
    public double getDistance(Point point) {
        if(shape[0].getDistance(point) < shape[1].getDistance(point))
            return shape[0].getDistance(point);
        return shape[1].getDistance(point);
    }

    @Override
    public Vector getDirection(Point point) {
        if(shape[0].getDistance(point) < shape[1].getDistance(point))
            return shape[0].getDirection(point);
        return shape[1].getDirection(point);
    }

    @Override
    public Point getReferencePoint() {
        return shape[0].getReferencePoint();
    }

    @Override
    public Box getBounds() {
        return null;
    }

    @Override
    public boolean hits(Box hitbox) {
        return shape[0].hits(hitbox)||shape[1].hits(hitbox);
    }

    /**
     * "图形1"的参考点指向"图形2"的参考点
     * @return
     */
    public Vector2D getLinkVector(){
        return new Vector2D((Point2D)shape[0].getReferencePoint(),(Point2D)shape[1].getReferencePoint());
    }

    @Override
    public void moveTo(Point location) {
        shape[1].moveTo(new Point2D(location.getX()+this.getLinkVector().getX(),location.getY()+this.getLinkVector().getY()));
        shape[0].moveTo(location.clone());
    }

    @Override
    public PhysicalEntity expandBy(double extent) {
        return null;
    }

    @Override
    public PhysicalEntity clone() {
        return new DoubleShape2D(shape[0].clone(),shape[1].clone());
    }

    public PhysicalEntity getMainShape(){
        return shape[0];
    }

    public PhysicalEntity getSubShape(){
        return shape[1];
    }


}
