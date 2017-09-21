package org.socialforce.geom.impl;

import org.socialforce.drawer.Drawer;
import org.socialforce.geom.*;

/**
 * Created by Administrator on 2017/5/10 0010.
 */
public class Ellipse2D extends RotatableShape2D implements RotatablePhysicalEntity,DistancePhysicalEntity {
    private double a;
    private double b;
    private Point center;
    private double angle;  //椭圆长轴与X轴正方向的夹角（逆时针为正）
    protected Drawer drawer;

    public Ellipse2D(double a, double b, Point center, double angle) {
        if (a >= b) {
            this.a = a;
            this.b = b;
        } else {
            this.a = b;
            this.b = a;
        }
        this.center = center;
        this.angle = angle;  //对angle取余数，将this.angle限制在：-π到π
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getAngle() {
        return angle;
    }

    public Point getLeftCenter(){
        return new Point2D((-1)*(a+b)/2,0).clone().inverseCoordinateTransfer(getReferencePoint(),getAngle());
    }

    public Point getRightCenter(){
        return new Point2D((a+b)/2,0).clone().inverseCoordinateTransfer(getReferencePoint(),getAngle());
    }

    public double getSideRadius(){
        return (a-b)/2;
    }

    public Vector2D getBasicDirection(){
        return new Vector2D(Math.cos(angle),Math.sin(angle));
    }


    /**
     * 获取绘制器
     *
     * @return 绘制器
     */
    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    /**
     * 设置绘制器
     *
     * @param drawer the drawer.
     */
    @Override
    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
    }

    /**
     * 获取维度实体的维度
     *
     * @return 维度值
     */
    @Override
    public int dimension() {
        return 2;
    }

    /**
     * 获取椭圆的参考点
     *
     * @return 椭圆中心
     */
    @Override
    public Point getReferencePoint() {
        return center;
    }

    /**
     * 移动椭圆中心到指定位置
     *
     * @param location 指定的位置
     */
    @Override
    public void moveTo(Point location) {
        center = location.clone();
    }

    /**
     * 创建并返回圆的副本
     *
     * @return 椭圆的副本
     */
    @Override
    public DistancePhysicalEntity clone() {
        return new Ellipse2D(a, b, center.clone(), angle);
    }

    /**
     * 获取box的边界
     * @return 椭圆的外切矩形
     */
    @Override
    public Box getBounds() {
        double halfWidth, halfHeight;
        if (Math.abs(angle % (Math.PI)) < 1.0e-10) {
            halfWidth = a;
            halfHeight = b;
        } else if (Math.abs((angle % (Math.PI)) - ((Math.PI) / 2)) < 1.0e-10) {
            halfWidth = b;
            halfHeight = a;
        } else {
            double k = (-1) * Math.tan(angle);  //椭圆坐标系中，上下切线的斜率
            halfHeight = Math.sqrt((a * a * k * k + b * b) / (k * k + 1));
            halfWidth = Math.sqrt(a * a + b * b - halfHeight * halfHeight);
        }
        return new Box2D(getReferencePoint().getX() - halfWidth, getReferencePoint().getY() - halfHeight, 2 * halfWidth, 2 * halfHeight);
    }


    @Override
    public void spin(double angle) {
        this.angle = this.angle + angle;
    }

    @Override
    public PhysicalEntity expandBy(double extent) {
        return null;
    }


    @Override
    public boolean contains(Point point) {
        return (point.distanceTo(this.getLeftCenter()) <= this.getSideRadius()) || (point.distanceTo(this.getRightCenter()) <= this.getSideRadius()) || (point.distanceTo(center) <= b);
    }

    @Override
    public double getDistance(Point point) {
        double distance[] = new double[]{point.distanceTo(this.getLeftCenter())-this.getSideRadius(),point.distanceTo(this.getRightCenter())-this.getSideRadius(),point.distanceTo(center)-b};
        //Arrays.sort(distance);
        return findMinOf3(distance);
    }

    @Override
    public Vector getDirection(Point point) {
        double leftDistance = point.distanceTo(this.getLeftCenter()) - this.getSideRadius();
        double rightDistance = point.distanceTo(this.getRightCenter()) - this.getSideRadius();
        double distance[] = new double[]{leftDistance, rightDistance,point.distanceTo(center)-b};
        double mindist = findMinOf3(distance);
        //Arrays.sort(distance);
        Vector direction;
        //double mindist = distance[0];
        if(mindist == leftDistance)
            direction = this.getLeftCenter().directionTo(point);
        else if(mindist == rightDistance)
            direction = this.getRightCenter().directionTo(point);
        else
            direction = center.directionTo(point);
        if(mindist <= 0)
            direction.scale(-1);
        return direction;
    }

    @Override
    public double distanceTo(PhysicalEntity other) {
        double distance[] = new double[]{other.getDistance(this.getLeftCenter())-this.getSideRadius(),other.getDistance(this.getRightCenter())-this.getSideRadius(),other.getDistance(center)-b};
        return findMinOf3(distance);

    }

    private double findMinOf3(double[] distance) {
        if(distance[0] < distance[1]) {
            if(distance[0] < distance[2]) {
                return distance[0];
            } else {
                return distance[2];
            }
        } else {
            if(distance[1] < distance[2]) {
                return distance[1];
            } else {
                return distance[2];
            }
        }
    }


    @Override
    public Vector directionTo(PhysicalEntity other) {
        double leftDistance = other.getDistance(this.getLeftCenter()) - this.getSideRadius();
        double rightDistance = other.getDistance(this.getRightCenter()) - this.getSideRadius();
        double distance[] = new double[]{leftDistance, rightDistance,other.getDistance(center)-b};
        //Arrays.sort(distance);
        Vector direction;

        double mindist = findMinOf3(distance);//distance[0];
        if(mindist == leftDistance){
            direction = other.getDirection(this.getLeftCenter());
            if(mindist < (-1)*this.getSideRadius())
                direction.scale(-1);
        }
        else if(mindist == rightDistance){
            direction = other.getDirection(this.getRightCenter());
            if(mindist < (-1)*this.getSideRadius())
                direction.scale(-1);
        }
        else{
            direction = other.getDirection(center);
            if(mindist < (-1)*b)
                direction.scale(-1);
        }
        return direction;
    }

    @Override
    public boolean hits(Box hitbox) {
        return this.distanceTo(hitbox) <= 0;
    }

    @Override
    public boolean intersects(PhysicalEntity other) {
        return this.distanceTo(other) <= 0;
    }

    /**
     * 找的本椭圆的受力点
     * @param other
     * @return
     */
    public Point ForcePoint(PhysicalEntity other){
        double leftDistance = other.getDistance(this.getLeftCenter()) - this.getSideRadius();
        double rightDistance = other.getDistance(this.getRightCenter()) - this.getSideRadius();
        double distance[] = new double[]{leftDistance, rightDistance,other.getDistance(center)-b};
        //Arrays.sort(distance);
        double mindist = findMinOf3(distance);//distance[0];
        if(mindist == leftDistance)
            return this.getLeftCenter();
        else if(mindist == rightDistance)
            return this.getRightCenter();
        else
            return center;
    }



}



