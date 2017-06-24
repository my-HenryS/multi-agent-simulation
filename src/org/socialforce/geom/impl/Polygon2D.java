package org.socialforce.geom.impl;

import org.socialforce.drawer.Drawer;
import org.socialforce.geom.*;

import java.util.LinkedList;

/**
 * 凸多边形
 * 以其顶点的数组描述
 * Created by Whatever on 2017/1/29.
 */
public class Polygon2D implements Shape{
    public Polygon2D(){}
    public Polygon2D(Point2D[] nods){
        this.nods = nods;
        this.nods = sort(nods);
    }
    protected Point2D[] nods;
    protected Segment2D[] edges;

    /**
     * 从正上方以顺时针顺序排列端点。
     */
    public Point2D[] sort(Point2D[] nods){
        Point2D[] sorted = new Point2D[nods.length];
        edges = new Segment2D[nods.length];
        Point2D fuck = null;
        LinkedList<Point2D> Nods = new LinkedList<>();
        int order,org;
        org = nods.length;
        for (int i = 0; i<nods.length;i++ ){
            Nods.add(nods[i]);
        }
        double flag = Double.NEGATIVE_INFINITY, direcflag;
        Vector2D now = new Vector2D(0,1),direc,temp1 = new Vector2D(),temp2 = new Vector2D();
        Point2D point2D = (Point2D) getReferencePoint();
        while(true) {
            order = org - Nods.size();
            for (Point2D nod : Nods) {
                direc = (Vector2D) getReferencePoint().directionTo(nod);
                if (direc.dot(now)> flag){
                    direcflag = direc.dot(now);
                    temp1 = direc;
                    direc.rotate(0.001);
                    if (direc.dot(now) > direcflag) {
                        flag = direc.dot(now);
                        fuck = nod;
                        temp2 = temp1;
                    }
                    else temp1 = temp2;
                    direc.rotate(-0.001);
                }
            }
            flag = Double.NEGATIVE_INFINITY;
            now = (Vector2D) getReferencePoint().directionTo(fuck);
            Nods.remove(fuck);
            sorted[order] = fuck;
            if (Nods.size() == 0){break;}
        }
        for (int i =0; i< sorted.length-1;i++){
            edges[i] = new Segment2D(sorted[i],sorted[i+1]);
        }
        edges[sorted.length-1] = new Segment2D(sorted[sorted.length-1],sorted[0]);
        return sorted;
    }

    /**
     * set the drawer for the drawable.
     *
     * @param drawer the drawer.
     */
    @Override
    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
    }
    protected Drawer drawer;
    /**
     * get the current drawer the object is using.
     *
     * @return the drawer.
     */
    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    /**
     * 获取该维度实体的尺寸.
     *
     * @return 尺寸.
     */
    @Override
    public int dimension() {
        return 2;
    }

    /**
     * 检查一个点是否属于 <code>Shape</code>.
     *
     * @param point 将被检查的点.
     * @return 如果该点是该形状上的一部分，就返回真，否则返回假.
     */
    @Override
    public boolean contains(Point point) {
        if (point.equals(getReferencePoint())){return true;}
        //我就强转了，烦死了……
        Point2D point1 = (Point2D) point,point2 = (Point2D) getReferencePoint();
        int flag = 0;
        Segment2D segment2D =  new Segment2D(point1,point2);
        for (Segment2D edge : edges) {
            if (edge.intersect(segment2D)){
                flag++;
            }
        }
        if (flag%2 == 0){
        return true;
        }
        else return false;
    }

    /**
     * 获取一个点到这条直线的距离矢量.
     * 如果距离为负，就说明这个点在这个形状上.
     * 空的形状为 Double.NaN .
     *
     * @param point 将被检查的点.
     * @return 该距离 .
     */
    @Override
    public double getDistance(Point point) {
        double temp = 0;
        for (Segment2D edge : edges){
            if (temp < edge.getDistance(point)){
                temp = edge.getDistance(point);
            }
        }
        return temp;
    }

    /**
     * 获取该点到另一个点的方向矢量.
     * 这个向量的模长为1.
     *
     * @param point 将被检查的点.
     * @return 该距离矢量.
     */
    @Override
    public Vector getDirection(Point point) {
        double temp = 0;
        Segment2D nearest = new Segment2D();
        for (Segment2D edge : edges){
            if (temp < edge.getDistance(point)){
                temp = edge.getDistance(point);
                nearest = edge;
            }
        }
        return nearest.getDirection(point);
    }

    /**
     * 获取该形状的参考点.
     * 定义为重心
     * @return 参考点. 如果这个形状是控的话，就返回空.
     */
    @Override
    public Point getReferencePoint() {
        double x = 0 ,y = 0;
        for (Point2D nod : nods){
            x += nod.getX();
            y += nod.getY();
        }
        return new Point2D(x/nods.length,y/nods.length);
    }

    /**
     * 获取这个形状的边界.
     * 如果这个形状不能放到一个box里的话，就返回空.
     * 如果这个形状是空的，就返回一个空的形状.
     *
     * @return 代表这个形状的box.
     */
    @Override
    public Box getBounds() {
        double xmin = Double.POSITIVE_INFINITY,xmax = Double.NEGATIVE_INFINITY,ymin = Double.POSITIVE_INFINITY,ymax = Double.NEGATIVE_INFINITY;
        for (Point2D nod : nods) {
            if (nod.getX() < xmin) {
                xmin = nod.getX();
            }
            if (nod.getY() < ymin) {
                nod.getY();
            }
            if (nod.getX() > xmax) {
                xmax = nod.getX();
            }
            if (nod.getY() > ymax) {
                ymax = nod.getY();
            }
        }
        return new Box2D(new Point2D(xmin,ymin),new Point2D(xmax,ymax));
    }

    /**
     * 检查这个形状是否与一个hitbox的box相交.
     *
     * @param hitbox 将要被检查的box.
     * @return 如果相交，返回真，否则返回假.
     */
    @Override
    public boolean hits(Box hitbox) {
        if(hitbox.contains(nods[0])){return true;}
        else if (contains(hitbox.getReferencePoint())){return true;}//不包含或被包含
        else for (Segment2D edge : edges){
            if (edge.hits(hitbox)){
                return true;
                }
            }
        return false;
    }

    /**
     * 移动这个形状到一个指定的位置.
     * 对于非空形状，参考点是等于位置点.
     * 对于空形状，什么也不做.
     *
     * @param location 指定的位置
     */
    @Override
    public void moveTo(Point location) {
        double movedX, movedY,x,y;
        movedX = getReferencePoint().getX() - location.getX();
        movedY = getReferencePoint().getY() - location.getY();
        for (Point2D nod : nods) {
            x = nod.getX()-movedX;
            y = nod.getY()-movedY;
            nod.moveTo(x,y);
        }
    }

    /**
     * 创建并返回该形状的副本.
     *
     * @return 该形状的副本.
     */
    @Override
    public Shape clone() {
        return new Polygon2D(nods);
    }

    @Override
    public Shape expandBy(double extent) {
        Point2D center = (Point2D) this.getReferencePoint().clone();
        Vector2D vector = new Vector2D();
        double[] temp = new double[2];
        for (Point2D nod : nods){
            vector = (Vector2D) center.directionTo(nod);
            vector.scale(extent);
            vector.get(temp);
            nod.moveBy(temp[0],temp[1]);
        }
        return this;
    }
}
