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
    public Point2D moveTo(double x, double y) {
        this.values[0] = x;
        this.values[1] = y;
        return this;
    }

    @Override
    public Point2D moveBy(double x, double y) {
        this.values[0] += x;
        this.values[1] += y;
        return this;
    }

    @Override
    public Point2D scaleBy(double s) {
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

    /**
     * 求点绕正半轴，逆时针转过的角度
     * @return
     */
    public double getAngle(){
        double x = this.getX();
        double y = this.getY();
        if(Math.abs(x) < 1.0e-10){
            if(Math.abs(y) < 1.0e-10)
                return 0;
            else if(y > 0)
                return (Math.PI)/2;
            else
                return (-1)*(Math.PI)/2;
        }
        else if(x > 0){
            if (Math.abs(y) < 1.0e-10)
                return 0;
            else
                return Math.atan(y/x);
        }
        else{
            if (Math.abs(y) < 1.0e-10) {
                return Math.PI;
            } else
                return (Math.PI)+(Math.atan(y/x));
        }

        /*if((p.getX()==this.getX())||(p.getY()==this.getY()))
            return 0;
        double side1 = this.distanceTo(p);
        double side2 = this.distanceTo(q);
        double bottomSide = p.distanceTo(q);
        double cosAngle = (side1 * side1 + side2 * side2 - bottomSide * bottomSide) / (2 * side1 * side2); //余弦定理
        //if (Math.abs(cosAngle) < 1.0e-10)
            //cosAngle = 0;
        double angle;
        if(p.getY() > 0)
            angle = Math.acos(cosAngle);
        else
            angle = (-1)*Math.acos(cosAngle);
        return angle;*/

    }

    /**
     * 以center为原点，angle为绕X轴正方向逆时针转过的角度，建立新的坐标系
     * @param center
     * @param angle
     * @return
     */
    public Point coordinateTransfer(Point center, double angle){
        double xTransfer = (this.getX()-center.getX())*Math.cos(angle)+(this.getY()-center.getY())*Math.sin(angle);
        double yTransfer = (-1)*(this.getX()-center.getX())*Math.sin(angle)+(this.getY()-center.getY())*Math.cos(angle);
        Point q = new Point2D(xTransfer, yTransfer);
        return q;
    }

    /**
     * 恢复为原点坐标系下的坐标
     * @param center
     * @param angle
     * @return
     */
    public Point inverseCoordinateTransfer(Point center, double angle){
        double xInverseTransfer = this.getX()*Math.cos(angle)-this.getY()*Math.sin(angle)+center.getX();
        double yInverseTransfer = this.getX()*Math.sin(angle)+this.getY()*Math.cos(angle)+center.getY();
        Point q = new Point2D(xInverseTransfer, yInverseTransfer);
        return q;
    }

    public Point2D getProjection(Segment2D segment){
        if(segment.getK()>1.0e+7)
            return new Point2D(segment.getReferencePoint().getX(),values[1]);
        return new Point2D((segment.getK()*(values[1]-segment.getB())+values[0])/(segment.getK()*segment.getK()+1),((segment.getK()*(values[1]-segment.getB())+values[0])/(segment.getK()*segment.getK()+1))*segment.getK()+segment.getB());
    }

}
