package org.socialforce.geom.impl;

import org.socialforce.geom.Point;
import org.socialforce.geom.PrimitiveShape;
import org.socialforce.geom.Vector;

/**
 * Created by Ledenel on 2016/8/8.
 */
public class Point2D extends Vector2D implements Point, PrimitiveShape {
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
    public double distanceToPoint(Point other) {
        double dx = this.getX() - other.getX();
        double dy = this.getY() - other.getY();
        return new Vector2D(-dx,-dy).length();
    }

    public Vector directionToPoint(Point other){
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
    public Point clone() {
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






    @Override
    public boolean intersect(PrimitiveShape shape) {
        return distanceTo(shape) == 0;
    }

    @Override
    public Point[] intersectPoint(PrimitiveShape shape) {
        if (intersect(shape)){
            return new Point[]{this};
        }
        else return null;
    }

    @Override
    public double distanceTo(PrimitiveShape shape) {
        if (shape instanceof Segment2D){
            return ((Segment2D) shape).getDistanceToPoint(this);
        }
        if (shape instanceof Point2D){
            return this.distanceToPoint((Point) shape);
        }
        if (shape instanceof Arc2D){
            double temp = Double.NEGATIVE_INFINITY;
            Circle2D circle2D = ((Arc2D) shape).getCircle();
            Point2D[] point2DS = ((Arc2D) shape).getEndpoints();
            Segment2D segment2D = new Segment2D(this, (Point2D) circle2D.getReferencePoint());
            if (temp > circle2D.getDistanceToPoint(this) && segment2D.intersect(shape)){
                temp = circle2D.getDistanceToPoint(this);
            }
            else if (temp > point2DS[0].distanceToPoint(this)){
                temp = point2DS[0].distanceToPoint(this);
            }
            else if (temp > point2DS[1].distanceToPoint(this)){
                temp = point2DS[1].distanceToPoint(this);
            }
            return temp;
        }
        else throw new IllegalArgumentException("待补充的图元！");
    }

    @Override
    public Vector directionTo(PrimitiveShape shape) {
        Vector2D direction = new Vector2D();
        if (shape instanceof Segment2D){
            direction = (Vector2D) ((Segment2D) shape).getDirectionToPoint(this);
            direction.scale(-1);
            return direction;
        }
        if (shape instanceof Point2D){
            return directionToPoint((Point2D) shape);
        }
        if (shape instanceof Arc2D){
            double temp = Double.NEGATIVE_INFINITY;
            Circle2D circle2D = ((Arc2D) shape).getCircle();
            Point2D[] point2DS = ((Arc2D) shape).getEndpoints();
            Segment2D segment2D = new Segment2D(this, (Point2D) circle2D.getReferencePoint());
            if (temp > circle2D.getDistanceToPoint(this) && segment2D.intersect(shape)){
                temp = circle2D.getDistanceToPoint(this);
                direction = (Vector2D) circle2D.getDirectionToPoint(this);
                direction.scale(-1);
            }
            else if (temp > point2DS[0].distanceToPoint(this)){
                temp = point2DS[0].distanceToPoint(this);
                direction = (Vector2D) directionToPoint(point2DS[0]);
            }
            else if (temp > point2DS[1].distanceToPoint(this)){
                temp = point2DS[1].distanceToPoint(this);
                direction = (Vector2D) directionToPoint(point2DS[1]);
            }
            return direction;
        }
        else throw new IllegalArgumentException("待补充的图元！");
    }

    @Override
    public void rotate(Point point, double angel) {
        Vector2D vector2D = (Vector2D) this.directionToPoint(point);
        vector2D.scale(this.distanceToPoint(point));
        vector2D.spin(angel);
        this.moveTo(point.getX()+vector2D.getX(),point.getY()+vector2D.getY());
    }

    @Override
    public void relativeExpand(double scale) {
        //do nothing
    }

    @Override
    public void absoluteExpand(double value) {
        //do nothing
    }

    @Override
    public Point getReferencePoint() {
        return this;
    }
}
