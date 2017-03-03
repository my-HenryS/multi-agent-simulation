package org.socialforce.geom.impl;

import org.socialforce.geom.Point;
import org.socialforce.geom.Vector;

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
     * 方向是指向目标点
     * @param other 要计算距离的另外的一个点
     * @return 距离.
     */
    @Override
    public double distanceTo(Point other) {
        double dx = this.getX() - other.getX();
        double dy = this.getY() - other.getY();
        return new Vector2D(-dx,-dy).length();
    }

    public Vector directionTo(Point other){
        double dx = this.getX() - other.getX();
        double dy = this.getY() - other.getY();
        return new Vector2D(-dx,-dy).getRefVector();
    }


    @Override
    public Point moveTo(double x, double y) {
        this.values[0] = x;
        this.values[1] = y;
        return this;
    }

    @Override
    public Point moveBy(double x, double y) {
        this.values[0] += x;
        this.values[1] += y;
        return this;
    }

    @Override
    public Point scaleBy(double s) {
        this.values[0] *= s;
        this.values[1] *= s;
        return this;
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
    public Point2D clone() {
        Point2D point2D = new Point2D();
        this.get(point2D.values);
        return point2D;
    }

    /**
     * 计算该点和其他点的曼哈顿距离
     * @param point 其他点
     * @return 曼哈顿距离
     */
    public double Manhattan_Distance(Point point){
        return Math.abs(this.getX() - point.getX()) + Math.abs(this.getY() - point.getY());
    }

    public String toString(){
        return "坐标为： ("+values[0]+"," +values[1]+")";
    }
}
