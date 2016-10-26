package org.socialforce.geom.impl;

import org.socialforce.geom.Point;

/**
 * Created by Ledenel on 2016/8/8.
 */
public class Point2D extends Vector2D implements Point {
    /**
     * 获取点的X坐标.
     *
     * @return 坐标X
     */
    @Override
    public double getX() {
        return values[0];
    }

    /**
     * 获取点的Y坐标
     *
     * @return Y坐标
     */
    @Override
    public double getY() {
        return values[1];
    }

    /**
     * 获取该点到其他点的距离.
     * 可以是多维度下的距离.
     *
     * @param other 要计算距离的另外的一个点
     * @return 距离.
     */
    @Override
    public double distanceTo(Point other) {
        double dx = this.getX() - other.getX();
        double dy = this.getY() - other.getY();
        return Math.sqrt(dx*dx+dy*dy);
    }

    public void moveTo(double x, double y) {
        this.values[0] = x;
        this.values[1] = y;
    }

    public void moveB(double x, double y) {
        this.values[0] += x;
        this.values[1] += y;
    }


    public Point2D() {
        super();
    }

    public Point2D(double x, double y) {
        super(x, y);
    }

    /**
     * 创建并返回这个向量的副本.
     * “复制”的确切含义可能取决于向量的类.
     * 对于任意向量x，它的一般含义表达式是: <br>
     * x.clone() != x <br>
     * 将为真.
     *
     * @return the copied vector.
     */
    @Override
    public Point clone() {
        Point2D point2D = new Point2D();
        this.get(point2D.values);
        return point2D;
    }

    public String toString(){
        return "坐标为： ("+values[0]+"," +values[1]+")";
    }
}
