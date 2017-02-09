package org.socialforce.geom.impl;

import org.socialforce.geom.*;
import org.socialforce.drawer.Drawer;

/**这是一个二维的线段
 *
 * 有两个点定义.
 * Created by Whatever on 2016/8/10.
 */
public class Segment2D implements ModelShape,PrimitiveShape {
    //protected Point2D a, b;
    //protected double k1, b1, startX, endX;
    protected double x1, x2, y1, y2;
    protected Drawer drawer;

    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    @Override
    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
    }

    public Segment2D() {
    }

    public Segment2D(Point2D a, Point2D b) {
        if (a.equals(b)) {
            throw new IllegalArgumentException("a and b can not be the same point");
        }
        //this.a = a;
        //this.b = b;
        x1 = a.getX();
        x2 = b.getX();
        y1 = a.getY();
        y2 = b.getY();
       /* if (x1 != x2) {
            k1 = (y1 - y2) / (x1 - x2);
            b1 = y1 - k1 * x1;
            startX = Math.min(x1, x2);
            endX = Math.max(x1, x2);
        }*/
    }

    public Segment2D(double k1, double b1, double startX, double endX) {
        if (startX == endX) {
            throw new IllegalArgumentException("a and b can not be the same point");
        }
        /*this.k1 = k1;
        this.b1 = b1;
        this.startX = startX;
        this.endX = endX;*/
        x1 = startX;
        x2 = endX;
        y1 = k1 * startX + b1;
        y2 = k1 * endX + b1;
       /* a = new Point2D(startX, k1 * startX + b1);
        b = new Point2D(endX, k1 * endX + b1);*/
    }

    @Override
    public int dimension() {
        return 2;
    }

    @Override
    public boolean contains(Point point) {
        double x = point.getX();
        double y = point.getY();
        double dx1 = x - x1;
        double dx2 = x - x2;
        double dy1 = y - y1;
        double dy2 = y - y2;
        double dx12 = x1 - x2;
        double dy12 = y1 - y2;
        dx1 *= dx1;
        dx2 *= dx2;
        dy1 *= dy1;
        dy2 *= dy2;
        dx12 *= dx12;
        dy12 *= dy12;
        double delta = dx12 + dy12 - dx1 - dy1 - dx2 - dy2;
        return delta >= 0 && Math.abs(4 * (dx1 + dy1) * (dx2 + dy2) - delta * delta) < 1e-15;
    }

    @Override
    public double getDistanceToPoint(Point point) {
        double x = point.getX(), y = point.getY();
        //equation: (x-x1)(y2-y1) - (x2-x1)(y-y1) = 0
        double dy12 = y2 - y1;
        double dx12 = x2 - x1;
        double dx1 = x - x1;
        double dy1 = y - y1;
        double dot = dx1*dx12+dy1*dy12;
        double len_sq = dx12*dx12+dy12*dy12;
        double scale = dot / len_sq;
        double tx,ty;
        if(scale < 0) {
            tx = x1;
            ty = y1;
        } else if(scale > 1) {
            tx = x2;
            ty = y2;
        } else {
            tx = x1 + scale * dx12;
            ty = y1+scale*dy12;
        }
        tx -= x;
        ty -= y;
        return new Vector2D(-tx,-ty).length();
    }

    public Vector getDirectionToPoint(Point point){
        double x = point.getX(), y = point.getY();
        //equation: (x-x1)(y2-y1) - (x2-x1)(y-y1) = 0
        double dy12 = y2 - y1;
        double dx12 = x2 - x1;
        double dx1 = x - x1;
        double dy1 = y - y1;
        double dot = dx1*dx12+dy1*dy12;
        double len_sq = dx12*dx12+dy12*dy12;
        double scale = dot / len_sq;
        double tx,ty;
        if(scale < 0) {
            tx = x1;
            ty = y1;
        } else if(scale > 1) {
            tx = x2;
            ty = y2;
        } else {
            tx = x1 + scale * dx12;
            ty = y1+scale*dy12;
        }
        tx -= x;
        ty -= y;
        return new Vector2D(-tx,-ty).getRefVector();
    }

    //  2016/8/19 refactored with x1,y1,x2,y2.;
    @Override
    public Point getReferencePoint() {
        return new Point2D((x1 + x2) / 2, (y1 + y2) / 2);
    }

    @Override
    public Box getBounds() {
        return new Box2D(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    public double getAngel(){
        return Math.atan2((y2-y1),(x2-x1));
    }
    @Override
    public boolean hits(Box hitbox) {
        boolean flag = false;
        Box2D covBox;
        double xmin,xmax,ymin,ymax;
        if (getBounds().hits(hitbox)){
            covBox = (Box2D) getBounds().intersect(hitbox);
            xmin = covBox.getXmin();
            xmax = covBox.getXmax();
            ymin = covBox.getYmin();
            ymax = covBox.getYmax();
            if (xmin != xmax){
                if (ymin != ymax){
                    if (intersect(new Segment2D(new Point2D(xmin,ymin),new Point2D(xmin,ymax)))){return true;}
                    if (intersect(new Segment2D(new Point2D(xmin,ymax),new Point2D(xmax,ymax)))){return true;}
                    if (intersect(new Segment2D(new Point2D(xmax,ymax),new Point2D(xmax,ymin)))){return true;}
                    if (intersect(new Segment2D(new Point2D(xmin,ymin),new Point2D(xmax,ymin)))){return true;}
                }
                else return intersect(new Segment2D(new Point2D(xmin,ymin),new Point2D(xmax,ymin)));
            }
            else if (ymin != ymax){
                return intersect(new Segment2D(new Point2D(xmin,ymin),new Point2D(xmax,ymax)));
            }
            else return contains(new Point2D(xmin,ymin));
        }
        return false;
    }

    @Override
    public void moveTo(Point location) {
        double movedX, movedY;
        movedX = getReferencePoint().getX() - location.getX();
        movedY = getReferencePoint().getY() - location.getY();
        x1 -= movedX;
        y1 -= movedY;
        x2 -= movedX;
        y2 -= movedY;
        /*a = new Point2D(x1 - movedX, y1 - movedY);
        b = new Point2D(x2 - movedX, y2 - movedY);*/
/*        if (x1 != x2) {
            k1 = (y1 - y2) / (x1 - x2);
            b1 = y1 - k1 * x1;
            startX = Math.min(x1, x2);
            endX = Math.max(x1, x2);
        }*/
    }

    /**
     * * 创建并返回这条线段的副本.
     * “复制”的精确含义可能取决于这条线的类.
     * @return 线的副本
     */
    @Override
    public Segment2D clone() {
        Segment2D cloned = new Segment2D();
        cloned.x1 = x1;
        cloned.x2 = x2;
        cloned.y1 = y1;
        cloned.y2 = y2;
        //Segment2D lineClone = new Segment2D(a, b);
        return cloned;
    }

    public double getLenth(){
        return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Segment2D) {
            Segment2D tg = (Segment2D) obj;
            if (Math.abs(x1 - tg.x1) < 1e-15 && Math.abs(y1 - tg.y1) < 1e-15) {
                return Math.abs(x2 - tg.x2) < 1e-15 && Math.abs(y2 - tg.y2) < 1e-15;
            } else {
                return Math.abs(x1 - tg.x2) < 1e-15 && Math.abs(y1 - tg.y2) < 1e-15 && Math.abs(x2 - tg.x1) < 1e-15 && Math.abs(y2 - tg.y1) < 1e-15;
            }
/*if (getReferencePoint().equals(testLine.getReferencePoint()) &&
                    a.distanceToPoint(testLine.getReferencePoint()) == a.distanceToPoint(getReferencePoint())
                    && testLine.getDistanceToPoint(a) == 0) {
                return true;
            } else {
                return false;
            }*/
        }
        return false;
    }
/*
    public double distanceToSegment(Point2D point) {
        double distance, temp;
        distance = getDistanceToPoint(point);
        temp = a.distanceToPoint(point);
        if (b.distanceToPoint(point) < a.distanceToPoint(point)) {
            temp = b.distanceToPoint(point);
        }
        if (b.distanceToPoint(point) * b.distanceToPoint(point) - a.distanceToPoint(point) * a.distanceToPoint(point) + a.distanceToPoint(b) * a.distanceToPoint(b) < 0 ||
                -b.distanceToPoint(point) * b.distanceToPoint(point) + a.distanceToPoint(point) * a.distanceToPoint(point) + a.distanceToPoint(b) * a.distanceToPoint(b) < 0) {
            distance = temp;
        }
        return distance;
    }

    public void setParallelX(double Y, double startX, double endX) {
        this.k1 = 0;
        this.b1 = Y;
        this.startX = startX;
        this.endX = endX;
        this.a = new Point2D(startX, Y);
        this.b = new Point2D(endX, Y);
    }

    public void setParallelY(double X, double startY, double endY) {
        this.a = new Point2D(X, startY);
        this.b = new Point2D(X, endY);
        //this.k1 = 10000;
    }
*/
    /*这个方法从来没有被调用过，目前来说不会引发任何的问题，
    主要是怕在set的时候如果k是无穷，只设置ab，没设置k1b1，导致之后直接调用k1b1时出现问题。
    但是目前的方法里暂时没有这种情况出现。
    protected void quiteConvert(Segment2D line){
            if (k1 < 9999) {
            //do nothing
            } else {
                throw new IllegalArgumentException("the slope of line is null or too big, please ues point A and B");
            }
            
        }*/

    public Point2D[] getExtrimePoint(){
        return new Point2D[]{new Point2D(x1,y1),new Point2D(x2,y2)};
    }


    public boolean intersect(Segment2D line){
        double px3 = line.getExtrimePoint()[0].getX();
        double px4 = line.getExtrimePoint()[1].getX();
        double py3 = line.getExtrimePoint()[0].getY();
        double py4 = line.getExtrimePoint()[1].getY();
        boolean flag = false;
        double d = (x2-x1)*(py4-py3) - (y2-y1)*(px4-px3);
        if(d!=0)
        {
            double r = ((y1-py3)*(px4-px3)-(x1-px3)*(py4-py3))/d;
            double s = ((y1-py3)*(x2-x1)-(x1-px3)*(y2-y1))/d;
            if((r>=0) && (r <= 1) && (s >=0) && (s<=1))
            {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 以一个点为圆心旋转整个线段
     * @param center
     * @param angle
     */
    public void spin(Point2D center,double angle){
        Vector2D v1,v2;
        v1 = new Vector2D(x1-center.getX(),y1 - center.getY());
        v2 = new Vector2D(x2-center.getX(),y2 - center.getY());
        v1.spin(angle);
        v2.spin(angle);
        x1 = center.getX()+v1.values[0];
        x2 = center.getX()+v2.values[0];
        y1 = center.getX()+v1.values[1];
        y2 = center.getX()+v2.values[1];
    }





    @Override
    public boolean intersect(PrimitiveShape shape) {
        if (shape instanceof Segment2D){
            return ((Segment2D) shape).intersect(this);
        }
        if (shape instanceof Arc2D){

        }
        return false;
    }

    @Override
    public Point[] intersectPoint(PrimitiveShape shape) {
        return null;
    }

    @Override
    public double distanceTo(PrimitiveShape shape) {
        return 0;
    }

    @Override
    public Vector directionTo(PrimitiveShape shape) {
        return null;
    }

    @Override
    public void rotate(Point point, double angel) {

    }

    @Override
    public void relativeExpand(double scale) {

    }

    @Override
    public void absoluteExpand(double value) {

    }

    public Rectangle2D flatten(double width){
        return new Rectangle2D((Point2D) getReferencePoint(),getLenth(),width,getAngel());
    }
}
