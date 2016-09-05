package org.socialforce.geom.impl;

import org.socialforce.drawer.Drawer;
import org.socialforce.geom.Box;
import org.socialforce.geom.DistanceShape;
import org.socialforce.geom.Point;
import org.socialforce.geom.Shape;

/**
 * 这是一个由它的半径和中心点设置的二维圆 .
 * Created by Whatever on 2016/8/8.
 */


public class Circle2D implements DistanceShape {
    /**
     * 半径:二维圆的半径.
     * @see Drawer
     */
    protected double radius=0;//,center[];
    /**
     * 二维圆的中心点.
     * @see Drawer
     */
    protected Point center = new Point2D(0,0);
    protected Drawer drawer;

    /**
     * 获取绘制器
     * @return 绘制器
     */
    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    /**
     * 设置绘制器
     * @param drawer 绘制器.
     */
    @Override
    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
    }

    /**
     * 获取绘制器
     * @return
     */
    @Override
    public int dimension() {
        return 2;
    }


    /**
     *计算圆圈内的点到圆心的距离.
     * @param point 将被检查的点.
     *
     * @return point.distanceto : 一个点到圆心的距离
     */
    @Override
    public boolean contains(Point point) {
        return (point.distanceTo(center)<radius);
    }

    /**
     * 获取一个圆外的点到圆的距离
     * @param point 将被检查的点.
     * @return 距离.
     * 如果点再圆内： return 0;
     *  如果点再圆外： return point.distanceTo(center)-radius
     */
    @Override
    public double getDistance(Point point) {
        if(this.contains(point)){
            return 0;}
        else return point.distanceTo(center)-radius;

    }

    /**
     * 获取圆的参考点.
     * @return 中心点，即圆心.
     */
    @Override
    public Point getReferencePoint() {
        return center;
    }

    /**
     * 获取一个圆的半径
     * @return 半径, 圆的半径
     */
    public  double getRadius(){
        return radius;
    }

    /**
     * 获取box的边界.
     * @return
     */
    @Override
    public Box getBounds() {
        return new Box2D(getReferencePoint().getX()-radius,getReferencePoint().getY()-radius,2*radius,2*radius);
    }

    /**
     * 判断是否是碰撞到box.
     * @param hitbox 将被检查的box.
     * @return 如果碰撞到box，为真.
     */
    @Override
    public boolean hits(Box hitbox) {
        return getBounds().hits(hitbox);
    }

    /**
     * 移动圆心到指定位置.
     * @param location 指定的位置
     */
    @Override
    public void moveTo(Point location) {
        center = location.clone();
    }

    public void setRadius(double radius){
        this.radius = radius;
    }

    /**
     * 创建并返回圆的副本.
     * “复制”的精确含义可能取决于这个圆的类.
     * @return 圆的副本
     */
    @Override
    public Shape clone() {
        Circle2D circle = new Circle2D();
        circle.moveTo(center);
        circle.setRadius(radius);
        return circle;
    }

    /**
     * 计算到圆边界上的距离
     * @param other
     * @return 距离
     */
    @Override
    public double distanceTo(Shape other) {
        return other.getDistance(this.center) - this.radius;
    }

    @Override
    public boolean intersects(Shape other) {
        return distanceTo(other) <= 0;
    }
}
