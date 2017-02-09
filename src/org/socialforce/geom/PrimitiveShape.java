package org.socialforce.geom;

/**
 * 图元，目前只有点，线段，圆弧。
 * Created by Administrator on 2017/2/8.
 */
public interface PrimitiveShape extends Shape{
    public boolean intersect(PrimitiveShape shape);
    public Point[] intersectPoint(PrimitiveShape shape);
    public double distanceTo(PrimitiveShape shape);
    public Vector directionTo(PrimitiveShape shape);
    public void rotate(Point point,double angel);
    public void relativeExpand(double scale);
    public void absoluteExpand(double value);
    public Point getReferencePoint();
}
