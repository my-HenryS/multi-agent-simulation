package org.socialforce.geom.impl;

import org.socialforce.geom.Vector;

import java.util.HashMap;

/**
 * 一个简单的二维向量an simple 2-D Vector.
 *
 * @author Ledenel
 * @see Vector
 * Created by Ledenel on 2016/8/7.
 */
public class Vector2D implements Vector {
    protected double values[];

    public Vector2D() {
        values = new double[2];
    }

    /**
     * 表明是否有其他对象“等于”这一个.
     * <p>
     * {@code equals} 在对非空对象引用上，该方法实现了一个等价关系:
     * <ul>
     * <li> 它是 <i> 它的对立是 </i>: 对于任何非空参考值
     * {@code x}, {@code x.equals(x)} 应该返回
     * {@code true}.
     * <li>它是 <i> 它的对称是</i>: 对于任何非空参考值
     * {@code x} 和 {@code y}, {@code x.equals(y)}
     * 应该返回 {@code true} 当且仅当
     * {@code y.equals(x)} 返回 {@code true}.
     * <li> 它是 <i> 它的传递是 </i>: 对于任何非空参考值
     * {@code x}, {@code y}, 和 {@code z}, 如果
     * {@code x.equals(y)} 返回 {@code true} 和
     * {@code y.equals(z)} 返回 {@code true}, 然后
     * {@code x.equals(z)} 应该返回 {@code true}.
     * <li>它是 <i> 它的连续是 </i>: 对于任何非空参考值
     * {@code x} 和 {@code y}, 多个连续的调用
     * {@code x.equals(y)} 返回 {@code true}
     * 或者连续返回 {@code false}, 在对对象的改进上，没有在提供信息用于 {@code equals} 的比较
     * <li>对于任何非空参考值 {@code x},
     * {@code x.equals(null)} 应该返回 {@code false}.
     * </ul>
     * <p>
     * {@code Object}类的 {@code equals} 方法实现了对象的差别可能性最大的等价关系;
     * 也就是说, 对于任何非空参考值 {@code x} 和
     * {@code y}, 这种方法返回 {@code true} 当且仅当
     *  {@code x} 和 {@code y} 参照相同的对象
     * ({@code x == y} 有值 {@code true}).
     * <p>
     * 注意，通常当此方法被重写了，就有必要重写{@code hashCode}方法，目的是保持{@code hashCode}法的一般的约定，
     * 即平等的对象必须具有相同散列码
     * @param obj 要进行比较的参考对象.
     * @return  如果这个对象与该 obj 的参数相同就返回 {@code true} ，否则返回 {@code false} .
     * @see #hashCode()
     * @see HashMap
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Vector && this.equals((Vector) obj);
    }

    public Vector2D(double x,double y) {
        this();
        values[0] = x;
        values[1] = y;
    }

    /**
     * 由point1指向point2的向量
     */
    public Vector2D(Point2D point1,Point2D point2){
        this();
        values[0] = point2.getX()-point1.getX();
        values[1] = point2.getY()-point1.getY();
    }



    /**
     * 获取向量的维度.
     *
     * @return 维度.
     */
    @Override
    public int dimension() {
        return values.length;
    }

    /**
     * 该向量加上另外一个向量add another vector on this vector.
     * 它的执行模式如 this = this + other.
     *
     * @param other 被加数.
     */
    @Override
    public void add(Vector other) {
        double[] otherv = quiteConvert(other).values;
        for (int i = 0; i < values.length; i++) {
            values[i] += otherv[i];
        }
    }

    protected Vector2D quiteConvert(Vector vector) {
        if (vector instanceof Vector2D) {
            return (Vector2D) vector;
        } else {
            throw new IllegalArgumentException("Vector2D could only calculated with Vector2D object.");
        }
    }

    /**
     * 该向量减去另外一个向量.
     * 它的执行模式如 this = this - other.
     *
     * @param other 被减数.
     */
    @Override
    public Vector2D sub(Vector other) {
        double[] otherv = quiteConvert(other).values;
        for (int i = 0; i < values.length; i++) {
            values[i] -= otherv[i];
        }
        return this;
    }

    /**
     * 该向量乘以一个数.
     * 它的执行模式如 this = this * rate.
     *
     * @param rate 被乘数.
     */
    @Override
    public void scale(double rate) {
        for (int i = 0; i < values.length; i++) {
            values[i] *= rate;
        }
    }

    /**
     *  <strong>strictly</strong> 检查该向量是否与另外一个向量相等.
     *
     * @param other 将被检查的向量.
     * @return 如果维度是一样并且所有参数都是一样的，就返回真，否则返回假.
     */
    @Override
    public boolean equals(Vector other) {
        /*if (this.dimension() == other.dimension()) {
            double[] c = new double[this.dimension()];
            other.get(c);
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i] != c[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;*/
        return epsilonEquals(other, 1e-15);
    }

    /**
     * 检查该向量在ε范围内是否大约等于其他向量.
     * 当且仅当两个向量间的 L-infinite distance 距离小于等于ε时，这两个向量大致相等.
     * L-infinite 距离等于 MAX[abs(x1-x2), abs(y1-y2)].
     *
     * @param other   另外一个向量.
     * @param epsilon ε.
     * @return 如果该向量大约与其他向量相等，就返回真,
     * 否则返回假.
     */
    @Override
    public boolean epsilonEquals(Vector other, double epsilon) {
        if(this.dimension() == other.dimension()) {
            double [] c = new double[dimension()];
            other.get(c);
            for (int i = 0; i < values.length; i++) {
                if(Math.abs(values[i]-c[i]) > epsilon) {
                    return  false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 获取向量的长度.
     * |<strong>v</strong>| 在数学上.
     *
     * @return 长度.
     */
    @Override
    public double length() {
        return Math.sqrt(length_sq());
    }

    protected double length_sq() {
        double length=0;
        for (double value : values) {
            length += value * value;
        }
        return length;
    }

    /**
     * 计算该向量与其他向量的点积.
     * |<strong>a</strong>||<strong>b</strong>|Cos&lt;<strong>a</strong>,<strong>b</strong>&gt; 在数学上.
     *
     * @param other 需要进行点积的另外一个向量.
     * @return 点积结果.
     */
    @Override
    public double dot(Vector other) {
        double [] otherv = quiteConvert(other).values;
        double sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum += values[i] * otherv[i];
        }
        return sum;
    }

    /**
     * 复制该向量值并转化为数组 arrayToCopy.
     *
     * @param arrayToCopy 复制的数组.
     */
    @Override
    public void get(double[] arrayToCopy) {
        System.arraycopy(values,0,arrayToCopy,0,Math.min(arrayToCopy.length,this.values.length));
    }

    /**
     * 将此向量的值给另外一个向量.
     *
     * @param other 将此向量的值给另外一个向量.
     */
    @Override
    public void set(Vector other) {
        double [] otherv = new double[2];
        other.get(otherv);
        this.set(otherv);
    }

    /**
     * 将该向量的值设置成一个数组的值.
     *
     * @param values 将被设置的值.
     */
    @Override
    public void set(double[] values) {
        System.arraycopy(values,0,this.values,0,Math.min(values.length,this.values.length));
    }

    public void setX(double x) {
        this.values[0] = x;
    }

    public void setY(double y){
        this.values[1] = y;
    }

    /**
     * 在一个特定的方向上设定这个向量.
     *
     * @param direction 设定的方向.
     */
    @Override
    public void project(Vector direction) {
        double scale = getProjectScale(direction);
        this.set(direction);
        this.scale(scale);
    }

    protected double getProjectScale(Vector direction) {
        double len_sq = quiteConvert(direction).length_sq();
        if(len_sq == 0) {
            return 0;
        }
        return this.dot(direction) / len_sq;
    }

    /**
     * 清除该向量在一定方向上的投影.
     * 清除之后，这个向量是垂直于该方向的.
     * @param direction 清除的方向.
     */
    @Override
    public void clearProject(Vector direction) {
        Vector2D projection = quiteConvert(this.clone());
        projection.project(direction);
        this.sub(projection);
    }

    /**
     * 创建并返回这个向量的副本.
     * “复制”的精确含义可能取决于这个向量的类.
     * 对于任意向量x，它的一般含义表达式是: <br>
     * x.clone() != x <br>
     * 将是真.
     *
     * @return 这个向量的副本.
     */
    @Override
    public Vector2D clone() {
        Vector2D vec = new Vector2D();
        System.arraycopy(values,0,vec.values,0,values.length);
        return vec;
    }


    /**
     * 获取参考的向量
     * 与原向量同向但是长度为1.
     * 零向量没有参考向量
     * @return 参考向量，即单位向量
     */
    public Vector2D getRefVector(){
        Vector2D ref;
        if (values[0] == 0 && values[1] == 0){
            return new Vector2D(0,0);
        }
        else
        ref = new Vector2D(values[0],values[1]);
        ref.scale(1/ref.length());
        return ref;
    }

    /**
     * 逆时针旋转某个角度
     * @param angle 旋转的角度，为弧度制
     */
    public void rotate(double angle){
        double r,t;
        r = length();
        t = 0;
        if (values[0] == 0 && values[1] == 0){
            ;//do nothing
        }
        else
        t = Math.atan2(values[1],values[0]);
        t = t+angle;
        values[0] = r*Math.cos(t);
        values[1] = r*Math.sin(t);
    }

    public String toString(){
        return "坐标为： ("+values[0]+"," +values[1]+")";
    }
    public double getX(){return values[0];}
    public double getY(){return values[1];}

    /**
     * 获取从基向量逆时针旋转的到环绕向量的旋转角(取值范围:-π_π)
     * @param vr 环绕向量
     * @param vb 基向量
     * @return 旋转角
     */
    public static double getRotateAngle(Vector2D vr, Vector2D vb){
        Vector2D vrR = vr.getRefVector(), vbR = vb.getRefVector();
        double angle = Math.acos(getDot(vrR, vbR));
        double cross = vbR.getX() * vrR.getY() - vrR.getX() * vbR.getY();
        if(cross < 0){
            angle = - angle;
        }
        return angle;
    }

    /**
     * 获取两个向量的点积
     * @param v1 向量1
     * @param v2 向量2
     * @return 点积
     */
    public static double getDot(Vector2D v1, Vector2D v2){
        return v1.getX() * v2.getX() + v1.getY() * v2.getY();
    }
}
