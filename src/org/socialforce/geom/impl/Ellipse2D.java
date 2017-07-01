package org.socialforce.geom.impl;

import org.socialforce.drawer.Drawer;
import org.socialforce.geom.*;


/**
 * Created by Administrator on 2017/5/10 0010.
 */
public class Ellipse2D implements MoveableShape {
    private double a;
    private double b;
    public Point center;
    private double angle;  //椭圆长轴与X轴正方向的夹角（逆时针为正）
    protected Drawer drawer;

    public double getA(){
        return a;
    }

    public double getB(){
        return b;
    }

    public double getAngle(){
        return angle;
    }

    public Ellipse2D(){}

    public Ellipse2D(double a, double b, Point center, double angle){
        if (a>=b){
            this.a = a;
            this.b = b;
        }
        else {
            this.a =b;
            this.b =a;
        }
        this.center = center;
        this.angle = angle;
    }

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
     * @param drawer the drawer.
     */
    @Override
    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
    }

    /**
     * 获取维度实体的维度
     * @return 维度值
     */
    @Override
    public int dimension() {
        return 2;
    }

    /**
     * 获取椭圆的参考点
     * @return 椭圆中心
     */
    @Override
    public Point getReferencePoint() {
        return center;
    }

    /**
     * 移动椭圆中心到指定位置
     * @param location 指定的位置
     */
    @Override
    public void moveTo(Point location) {
        center = location.clone();
    }

    /**
     * 判断点是否位于椭圆内部
     * @param point 将被检查的点
     * @return 位于椭圆内部则返回true
     */
    @Override
    public boolean contains(Point point) {
        Point P = point.clone().coordinateTransfer(center,angle);
        Point2D O = new Point2D(0,0);
        double actualDistance = P.distanceTo(O);  //椭圆外一点到中心的距离
        Point2D A1 = new Point2D(1*a,0);
        double sampleAngle = O.getAngle(P,A1);
        double sampleDistance = Math.sqrt((a*Math.cos(sampleAngle))*(a*Math.cos(sampleAngle))+(b*Math.sin(sampleAngle))*(b*Math.sin(sampleAngle)));  //椭圆上一点到中心的距离
        if(actualDistance <= sampleDistance)
            return true;
        else
            return false;
    }

    /**
     * 给定平面上一点，找到椭圆上与此点距离最近的一点
     * @param point 将被检查的点
     * @return 最近的一点
     */
    public Point getPoint(Point point) {
        Point P = point.clone().coordinateTransfer(center, angle);
        Point2D Q = new Point2D();
        double x = P.getX();
        double y = P.getY();
        double startAngle;    //初始采样点的角度（都是从长轴的端点开始）
        double sampleAngle;   //初始采样的角度步长
        if(Math.abs(x) < 1.0e-10){
            if(y > 0){
                Q.moveTo(0,1*b);
                return Q.inverseCoordinateTransfer(center,angle);
            }
            else{
                Q.moveTo(0,(-1)*b);
                return Q.inverseCoordinateTransfer(center,angle);
            }
        }
        else if(x > 0){
            if(Math.abs(y) < 1.0e-10){
                Q.moveTo(1*a,0);
                return Q.inverseCoordinateTransfer(center,angle);
            }
            else if(y > 0){
                startAngle = 0;
                sampleAngle = 10*(Math.PI)/180;
            }
            else{
                startAngle = 0;
                sampleAngle = (-10)*(Math.PI)/180;
            }
        }
        else{
            if(Math.abs(y) < 1.0e-10){
                Q.moveTo((-1)*a,0);
                return Q.inverseCoordinateTransfer(center,angle);
            }
            else if(y > 0){
                startAngle = Math.PI;
                sampleAngle = (-10)*(Math.PI)/180;
            }
            else{
                startAngle = Math.PI;
                sampleAngle = 10*(Math.PI)/180;
            }
        }
        double differDistance = 1.0e-2;         //误差精度
        double distancePQ,minAngle;             //最短距离
        Point2D tempPoint = new Point2D();      //采样点
        double tempDistance,tempAngle=startAngle;
        while (true){
            minAngle = tempAngle;
            Q.moveTo(a*Math.cos(minAngle),b*Math.sin(minAngle));
            distancePQ = P.distanceTo(Q);
            tempAngle = minAngle+sampleAngle;
            tempPoint.moveTo(a*Math.cos(tempAngle),b*Math.sin(tempAngle));
            tempDistance = P.distanceTo(tempPoint);
            if (Math.abs(tempDistance-distancePQ) < differDistance){
                return Q.inverseCoordinateTransfer(center,angle);
            }
            if (tempDistance > distancePQ){
                tempAngle = tempAngle-sampleAngle;
                sampleAngle = sampleAngle/2;
            }
        }
    }

    /**
     * 平面上一点至椭圆的最短距离
     * @param point 将被检查的点
     * @return 距离
     */
    @Override
    public double getDistance(Point point) {
        Point p = this.getPoint(point);
        double distance = point.distanceTo(p);
        if(this.contains(point))
            distance = (-1)*distance;
        return distance;
    }

    /**
     * 获取该点到椭圆上与此点距离最近点的方向矢量
     * @param point 将被检查的点
     * @return 单位矢量
     */
    @Override
    public Vector getDirection(Point point) {
        Point p = this.getPoint(point);
        Vector vector = p.directionTo(point);
        if(this.contains(point))
            vector.scale(-1);
        return vector;
    }

    /**
     * 分别获得椭圆与其它图形距离最近的点
     * 存储到一个2*2的数组(第一行是其它图形上的点，第二行是本椭圆上的点)
     * @param other 另一个形状
     * @return 存储最近两点的数组
     */

    public double[][] closePoint(Shape other) {
        Point Point_2 = this.getPoint(other.getReferencePoint());     //本椭圆上的点
        double distance = other.getDistance(Point_2);                 //椭圆上的采样点到other的最短距离
        double minAngle = (new Point2D(0,0)).getAngle(Point_2.coordinateTransfer(center,angle),new Point2D(1*a,0));
        double tempDistance = other.getDistance(new Point2D(a*Math.cos(minAngle+0.01),b*Math.sin(minAngle+0.01)).inverseCoordinateTransfer(center,angle));
        double sampleAngle;     //初始采样的角度步长
        if(tempDistance<distance)
            sampleAngle = 5*(Math.PI)/180;
        else
            sampleAngle = (-5)*(Math.PI)/180;
        double differDistance = 1.0e-2;          //误差精度
        double tempAngle = minAngle;             //采样角度
        Point tempPoint = new Point2D();         //采样点（椭圆坐标系下）
        while (true){
            tempAngle = tempAngle+sampleAngle;
            tempPoint.moveTo(a*Math.cos(tempAngle),b*Math.sin(tempAngle));
            tempDistance = other.getDistance(tempPoint.inverseCoordinateTransfer(center,angle));
            if(tempDistance <= distance){
                if(Math.abs(tempDistance-distance) < differDistance){
                    Point_2.moveTo(tempPoint.inverseCoordinateTransfer(center,angle).getX(),tempPoint.inverseCoordinateTransfer(center,angle).getY());
                    break;
                }
                else{
                    distance = tempDistance;
                }
            }
            else{
                tempAngle = tempAngle-sampleAngle;
                sampleAngle = sampleAngle/2;
            }
        }
        Point Point_1 = Shape.getPoint(other, Point_2);              //图形other上的点
        double nearPoint[][] = {{Point_1.getX(), Point_1.getY()}, {Point_2.getX(), Point_2.getY()}};
        return nearPoint;
    }

    /**
     * 椭圆与其他图形之间的最短距离
     * @param other 另一个形状
     * @return 距离
     */
    @Override
    public double distanceTo(Shape other) {
        double nearPoint[][] = this.closePoint(other);
        Point2D Point_1 = new Point2D(nearPoint[0][0],nearPoint[0][1]);  //图形other上的点
        Point2D Point_2 = new Point2D(nearPoint[1][0],nearPoint[1][1]);  //本椭圆上的点
        double distance = Point_1.distanceTo(Point_2);
        if(this.intersects(other))
            distance = (-1)*distance;
        return distance;
    }

    /**
     * 椭圆与其它图形距离最近的点构成的方向向量
     * @param other 另一个形状
     * @return 单位矢量
     */
    @Override
    public Vector directionTo(Shape other) {
        /*double nearPoint[][] = this.closePoint(other);
        Point2D Point_1 = new Point2D(nearPoint[0][0],nearPoint[0][1]);  //图形other上的点
        Point2D Point_2 = new Point2D(nearPoint[1][0],nearPoint[1][1]);  //本椭圆上的点
        Vector vector = Point_1.directionTo(Point_2);
        if(this.intersects(other))
            vector.scale(-1);
        return vector;*/

        Vector2D vector2D = (Vector2D) other.getDirection(center);
        if(other.contains(center)) vector2D.scale(-1);
        return vector2D;

    }

    /**
     * 获取box的边界
     * @return 椭圆的外切矩形
     */
    @Override
    public Box getBounds() {
        double halfWidth,halfHeight;
        if(Math.abs(angle%(Math.PI)) < 1.0e-10){
            halfWidth = a;
            halfHeight = b;
        }
        else if(Math.abs((angle%(Math.PI))-((Math.PI)/2)) < 1.0e-10){
            halfWidth = b;
            halfHeight = a;
        }
        else{
            double k = (-1)*Math.tan(angle);  //椭圆坐标系中，上下切线的斜率
            halfHeight = Math.sqrt((a*a*k*k+b*b)/(k*k+1));
            halfWidth = Math.sqrt(a*a+b*b-halfHeight*halfHeight);
        }
        return new Box2D(getReferencePoint().getX()-halfWidth,getReferencePoint().getY()-halfHeight,2*halfWidth,2*halfHeight);
    }

    /**
     * 判断是否是碰撞到box
     * @param hitbox 将要被检查的box
     * @return 碰撞到box返回true
     */
    @Override
    public boolean hits(Box hitbox) {
        if((this.contains(hitbox.getReferencePoint()))||(hitbox.contains(center)))
            return true;
        double closePoint[][] = this.closePoint(hitbox);
        Point2D Point_1 = new Point2D(closePoint[0][0],closePoint[0][1]);     //hitbox上的点
        Point2D Point_2 = new Point2D(closePoint[1][0],closePoint[1][1]);    //本椭圆上的点
        if((this.contains(Point_1))||(hitbox.contains(Point_2)))
            return true;
        else
            return false;
    }

    /**
     * 判断该形状是否与另一个形状严格相交
     * @param other 另一个形状
     * @return 相交返回true
     */
    @Override
    public boolean intersects(Shape other) {
        if((this.contains(other.getReferencePoint()))||(other.contains(center)))
            return true;
        double closePoint[][] = this.closePoint(other);
        Point2D Point_1 = new Point2D(closePoint[0][0],closePoint[0][1]);     //图形other上的点
        Point2D Point_2 = new Point2D(closePoint[1][0],closePoint[1][1]);     //本椭圆上的点
        if((this.contains(Point_1))||(other.contains(Point_2)))
            return true;
        else
            return false;
    }

    /**
     * 创建并返回圆的副本
     * @return 椭圆的副本
     */
    @Override
    public Ellipse2D clone() {
        return new Ellipse2D(a,b,center.clone(),angle);
    }

    @Override
    public Shape expandBy(double extent) {
        return null;
    }

    /**
     * 逆时针旋转某个角度
     * @param angle 旋转的角度，为弧度制
     */
    @Override
    public void spin(double angle){
        this.angle = this.angle+angle;
    }


}
