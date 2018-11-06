package org.socialforce.geom.impl;
import org.socialforce.drawer.Drawer;
import org.socialforce.geom.*;
import java.util.Arrays;

/**
 * Created by Administrator on 2017/7/5 0005.
 */
public class MultiCircle2D extends RotatableShape2D implements RotatablePhysicalEntity,DistancePhysicalEntity {
    private double a;
    private double b;
    private Point center;
    private double angle;  //椭圆长轴与X轴正方向的夹角（逆时针为正）
    protected Drawer drawer;

    public MultiCircle2D(double a, double b, Point center, double angle) {
        if (a >= b) {
            this.a = a;
            this.b = b;
        } else {
            this.a = b;
            this.b = a;
        }
        this.center = center;
        this.angle = angle;
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
        return new Point2D((-1)*(a+b)/2,0).clone().inverseCoordinateTransfer(center,angle);
    }

    public Point getRightCenter(){
        return new Point2D((a+b)/2,0).clone().inverseCoordinateTransfer(center,angle);
    }

    public double getSideRadius(){
        return (a-b)/2;
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
    public double getArea() {
        return 0;   //TODO compute area
    }


    @Override
    public boolean contains(Point point) {
        if((point.distanceTo(this.getLeftCenter()) <= this.getSideRadius())||(point.distanceTo(this.getRightCenter()) <= this.getSideRadius())||(point.distanceTo(center) <= b))
            return true;
        return false;
    }

    @Override
    public double getDistance(Point point) {
        double distance[] = new double[]{point.distanceTo(this.getLeftCenter())-this.getSideRadius(),point.distanceTo(this.getRightCenter())-this.getSideRadius(),point.distanceTo(center)-b};
        Arrays.sort(distance);
        return distance[0];
    }

    @Override
    public Vector getDirection(Point point) {
        double distance[] = new double[]{point.distanceTo(this.getLeftCenter())-this.getSideRadius(),point.distanceTo(this.getRightCenter())-this.getSideRadius(),point.distanceTo(center)-b};
        Arrays.sort(distance);
        Vector direction;
        if(distance[0] == point.distanceTo(this.getLeftCenter())-this.getSideRadius())
            direction = this.getLeftCenter().directionTo(point);
        else if(distance[0] == point.distanceTo(this.getRightCenter())-this.getSideRadius())
            direction = this.getRightCenter().directionTo(point);
        else
            direction = center.directionTo(point);
        if(distance[0] <= 0)
            direction.scale(-1);
        return direction;
    }

    @Override
    public double distanceTo(PhysicalEntity other) {
        double distance[] = new double[]{other.getDistance(this.getLeftCenter())-this.getSideRadius(),other.getDistance(this.getRightCenter())-this.getSideRadius(),other.getDistance(center)-b};
        Arrays.sort(distance);
        return distance[0];
    }


    @Override
    public Vector directionTo(PhysicalEntity other) {
        double distance[] = new double[]{other.getDistance(this.getLeftCenter())-this.getSideRadius(),other.getDistance(this.getRightCenter())-this.getSideRadius(),other.getDistance(center)-b};
        Arrays.sort(distance);
        Vector direction;
        if(distance[0] == other.getDistance(this.getLeftCenter())-this.getSideRadius()){
            direction = other.getDirection(this.getLeftCenter());
            if(distance[0] < (-1)*this.getSideRadius())
                direction.scale(-1);
        }
        else if(distance[0] == other.getDistance(this.getRightCenter())-this.getSideRadius()){
            direction = other.getDirection(this.getRightCenter());
            if(distance[0] < (-1)*this.getSideRadius())
                direction.scale(-1);
        }
        else{
            direction = other.getDirection(center);
            if(distance[0] < (-1)*b)
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
    public Point forcePoint(PhysicalEntity other){
        double distance[] = new double[]{other.getDistance(this.getLeftCenter())-this.getSideRadius(),other.getDistance(this.getRightCenter())-this.getSideRadius(),other.getDistance(center)-b};
        Arrays.sort(distance);
        if(distance[0] == other.getDistance(this.getLeftCenter())-this.getSideRadius())
            return this.getLeftCenter();
        else if(distance[0] == other.getDistance(this.getRightCenter())-this.getSideRadius())
            return this.getRightCenter();
        else
            return center;
    }

}
