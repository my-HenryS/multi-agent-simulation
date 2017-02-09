package org.socialforce.geom.impl;

import org.socialforce.drawer.Drawer;
import org.socialforce.geom.*;

/**
 * Created by Administrator on 2017/2/9.
 */
public class AnyFinCanHappen2D implements ModelShape{
    @Override
    public int dimension() {
        return 2;
    }

    protected Drawer drawer;
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
        return false;
    }

    @Override
    public double getDistanceToPoint(Point point) {
        return 0;
    }

    @Override
    public Vector getDirectionToPoint(Point point) {
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
        return false;
    }

    @Override
    public void moveTo(Point location) {

    }

    @Override
    public ModelShape clone() {
        return null;
    }

    @Override
    public PrimitiveShape[] breakdown() {
        return new PrimitiveShape[0];
    }

    @Override
    public ModelShape abstractShape() {
        return null;
    }

    @Override
    public ModelShape[] minus(ModelShape clipper) {
        return new ModelShape[0];
    }

    @Override
    public ModelShape[] And(ModelShape other) {
        return new ModelShape[0];
    }

    @Override
    public ModelShape intersect(ModelShape other) {
        return null;
    }

    @Override
    public void Not() {

    }

    @Override
    public Point getInsidePoint() {
        return null;
    }

    @Override
    public Point getOutsidePoint() {
        return null;
    }
}
