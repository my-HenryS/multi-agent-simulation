package org.socialforce.geom.impl;

import org.socialforce.drawer.Drawer;
import org.socialforce.geom.Box;
import org.socialforce.geom.Point;
import org.socialforce.geom.Shape;
import org.socialforce.geom.Vector;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Ledenel on 2016/8/8.
 */
public class Box2D implements Box {
    /**
     * 指示是否有其他对象“等于”这一个.
     * <p>
     * The {@code equals} method implements an equivalence relation
     * on non-null object references:
     * <ul>
     * <li>It is <i>reflexive</i>: for any non-null reference value
     * {@code x}, {@code x.equals(x)} should return
     * {@code true}.
     * <li>It is <i>symmetric</i>: for any non-null reference values
     * {@code x} and {@code y}, {@code x.equals(y)}
     * should return {@code true} if and only if
     * {@code y.equals(x)} returns {@code true}.
     * <li>It is <i>transitive</i>: for any non-null reference values
     * {@code x}, {@code y}, and {@code z}, if
     * {@code x.equals(y)} returns {@code true} and
     * {@code y.equals(z)} returns {@code true}, then
     * {@code x.equals(z)} should return {@code true}.
     * <li>It is <i>consistent</i>: for any non-null reference values
     * {@code x} and {@code y}, multiple invocations of
     * {@code x.equals(y)} consistently return {@code true}
     * or consistently return {@code false}, provided no
     * information used in {@code equals} comparisons on the
     * objects is modified.
     * <li>For any non-null reference value {@code x},
     * {@code x.equals(null)} should return {@code false}.
     * </ul>
     * <p>
     * The {@code equals} method for class {@code Object} implements
     * the most discriminating possible equivalence relation on objects;
     * that is, for any non-null reference values {@code x} and
     * {@code y}, this method returns {@code true} if and only
     * if {@code x} and {@code y} refer to the same object
     * ({@code x == y} has the value {@code true}).
     * <p>
     * Note that it is generally necessary to override the {@code hashCode}
     * method whenever this method is overridden, so as to maintain the
     * general contract for the {@code hashCode} method, which states
     * that equal objects must have equal hash codes.
     *
     * @param obj the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     * argument; {@code false} otherwise.
     * @see #hashCode()
     * @see HashMap
     */


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Box2D) {
            Box2D box = (Box2D) obj;
            return box.xmin == xmin &&
                    box.xmax == xmax &&
                    box.ymin == ymin &&
                    box.ymax == ymax;
        }
        return false;
    }

    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    @Override
    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
    }

    protected Drawer drawer;

    protected double xmin, ymin, xmax, ymax;

    public Box2D() {

    }

    public Box2D(double left, double bottom, double width, double height) {
        double right = left + width;
        double top = bottom + height;
        double temp;
        if (right < left) {
            temp = right;
            right = left;
            left = temp;
        }
        if (top < bottom) {
            temp = top;
            top = bottom;
            bottom = temp;
        }
        xmin = left;
        xmax = right;
        ymin = bottom;
        ymax = top;
    }

    public Box2D(Point start, Point end) {
        this(start.getX(),
                start.getY(),
                end.getX() - start.getX(),
                end.getY() - start.getY());
    }

    /**
     * 检查点是否属于 <code>Shape</code>.
     *
     * @param point 将被检查的点.
     * @return 如果点是该形状的一部分，就为真，否则为假.
     */
    @Override
    public boolean contains(Point point) {
        return xmin <= point.getX()
                && point.getX() <= xmax
                && ymin <= point.getY()
                && point.getY() <= ymax;
    }

    /**
     * 获取点到形状的距离.
     * 如果点在形状上，距离就是0.
     * Double.NaN 表示空形状.
     *
     * @param point 将被检查的点.
     * @return 距离. 如果点不能到形状上话，返回 Double.NaN .
     */
    @Override
    public double getDistance(Point point) {
        if (contains(point)) {
            return 0;
        }
        double dsx = point.getX() - xmin;
        double dsy = point.getY() - ymin;
        double dex = point.getX() - xmax;
        double dey = point.getY() - ymax;
        if (dsx > 0) {
            if (dsy > 0) {
                if (dex > 0) {
                    if (dey > 0) {
                        return point.distanceTo(new Point2D(xmax, ymax));
                    } else { // dey <= 0
                        return dex;
                    }
                } else { // dex <= 0
                    return dey;
                }
            } else { // dsy <= 0
                if (dex > 0) {
                    return point.distanceTo(new Point2D(xmax, ymin));
                } else { // dex <= 0
                    return -dsy;
                }
            }
        } else { // dsx <= 0
            if (dsy > 0) {
                if (dey > 0) {
                    return point.distanceTo(new Point2D(xmin, ymax));
                } else { // dey <= 0
                    return -dsx;
                }
            } else { // dsy <= 0
                return point.distanceTo(new Point2D(xmin, ymin));
            }
        }
        //return Double.NaN;
    }

    /**
     * 获取这个形状的参考点.
     * 参考点通常是这个形状的中心点.
     * 对于球面/球体，参考点就是中心点.
     *
     * @return 参考点.如果该形状是空的话，就返回空.
     */
    @Override
    public Point getReferencePoint() {
        return new Point2D((xmin + xmax) / 2, (ymin + ymax) / 2);
    }

    /**
     * 获取该形状的边界.
     * 如果该形状不能放进一个box的话，返回空.
     * 如果该形状是空的话，返回空的形状.
     *
     * @return 一个代表该形状边界的box.
     */
    @Override
    public Box getBounds() {
        return (Box) this.clone();
    }

    protected Box2D quiteConvert(Box box) {
        if (box instanceof Box2D) {
            return (Box2D) box;
        } else {
            throw new IllegalArgumentException("Box2D could only calculated with Box2D.");
        }
    }

    /**
     * 检查该形状是否与一个hitbox的box有交集.
     *
     * @param hitbox 将被检查的box.
     * @return 如果相交，为真，否则为假.
     */
    @Override
    public boolean hits(Box hitbox) {
        Box2D in = quiteConvert(hitbox);
        return ymin <= in.ymin && in.ymin <= ymax ||
                ymin <= in.ymax && in.ymax <= ymax ||
                xmin <= in.xmin && in.xmin <= xmax ||
                xmin <= in.xmax && in.xmax <= xmax;
    }

    /**
     * 移动这个形状到一个指定的位置.
     * 对于一个非空的形状，参考点等于位置点.
     * 对于空的形状，什么也不做.
     *
     * @param location 指定的位置
     */
    @Override
    public void moveTo(Point location) {
        double xsum = location.getX() * 2;
        double ysum = location.getY() * 2;
        double xsub = xmin - xmax;
        double ysub = ymin - ymax;
        xmin = (xsum + xsub) / 2;
        ymin = (ysum + ysub) / 2;
        xmax = (xsum - xsub) / 2;
        ymax = (ysum - ysub) / 2;
        double temp;
        if(xmin > xmax) {
            temp = xmin;
            xmin = xmax;
            xmax = temp;
        }
        if(ymin > ymax) {
            temp = ymin;
            ymin = ymax;
            ymax = temp;
        }
    }

    /**
     * 创建并返回这个形状的副本.
     *
     * @return 这个形状的副本.
     */
    @Override
    public Shape clone() {
        Box2D cloned = new Box2D();
        cloned.xmin = xmin;
        cloned.xmax = xmax;
        cloned.ymin = ymin;
        cloned.ymax = ymax;
        return cloned;
    }

    /**
     * 获取box的起点.
     *
     * @return 起点.
     */
    @Override
    public Point getStartPoint() {
        return new Point2D(xmin, ymin);
    }

    /**
     * 获取box的终点.
     *
     * @return 终点.
     */
    @Override
    public Point getEndPoint() {
        return new Point2D(xmax, ymax);
    }

    /**
     * 获取box的规模大小.
     *
     * @return 代表规模大小的向量.
     */
    @Override
    public Vector getSize() {
        return new Vector2D(xmax - xmin, ymax - ymin);
    }

    /**
     * 返回该box和另外box的交集.
     *
     * @param other 有交集的box.
     * @return 交集.
     */
    @Override
    public Box intersect(Box other) {
        Box2D in = quiteConvert(other);
        if (hits(in)) {
            double[] xarr = new double[]{xmin, xmax, in.xmin, in.xmax};
            double[] yarr = new double[]{ymin, ymax, in.ymin, in.ymax};
            Arrays.sort(xarr);
            Arrays.sort(yarr);
            Box2D box = new Box2D();
            box.xmin = xarr[1];
            box.xmax = xarr[2];
            box.ymin = yarr[1];
            box.ymax = yarr[2];
            return box;
        }
        return null;
    }

    public double getXmin() {
        return xmin;
    }

    public double getYmin() {
        return ymin;
    }

    public double getXmax() {
        return xmax;
    }

    public double getYmax() {
        return ymax;
    }

    /**
     * 获取 DimensionEntity 的维度.
     *
     * @return 维度.
     */
    @Override
    public int dimension() {
        return 2;
    }
}
