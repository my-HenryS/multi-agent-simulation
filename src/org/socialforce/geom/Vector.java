package org.socialforce.geom;

import java.io.Serializable;

/**
 * 表示一个向量，如 <strong>a</strong> = (a<sub>1</sub>,a<sub>2</sub>,a<sub>3</sub>,...).
 * <p>
 * 向量可以加减其他向量，来改变大小. <br>
 * (使用 <code>double</code> 来表示向量组件.)
 * 如果必要的话，向量的默认单位是米.
 *
 * @author Ledenel
 *         <p>
 *         Created by Ledenel on 2016/7/25.
 */
public interface Vector extends Cloneable, Serializable, DimensionEntity {
    /**
     * 获取向量的尺寸.
     *
     * @return 尺寸.
     */
    int dimension();

    /**
     * 一个向量加上另外一个向量.
     * 执行模式如 this = this + other.
     *
     * @param other 被加向量.
     */
    void add(Vector other);

    /**
     * 用一个向量减去另外一个向量.
     * 执行模式如 this = this - other.
     *
     * @param other 被减向量.
     */
    void sub(Vector other);

    /**
     * 一个向量乘一个数.
     * 执行模式如 this = this * rate.
     *
     * @param rate 被乘数.
     */
    void scale(double rate);

    /**
     * 检查一个对象与这个向量是否相等 <code>Vector</code> and <strong>strictly</strong> .
     *
     * @param object 被检查的对象.
     * @return 如果该对象与这个向量相等，就返回真，否则就返回假.
     */
    boolean equals(Object object);

    /**
     * 检查这个向量是否与另外一个向量相等 <strong>strictly</strong> .
     *
     * @param other 将被检查的向量.
     * @return 如果维度相同并且所有组件都一样，就返回真，否则返回假.
     */
    boolean equals(Vector other);

    /**
     * 检查该向量在ε范围内是否大约等于其他向量.
     * 当且仅当两个向量间的 L-infinite distance 距离小于等于ε时，这两个向量大致相等.
     * L-infinite 距离等于 MAX[abs(x1-x2), abs(y1-y2)].
     *
     * @param other   其他向量.
     * @param epsilon ε.
     * @return 如果该向量大约与其他向量相等，就返回真,
     * 否则返回假.
     */
    boolean epsilonEquals(Vector other, double epsilon);

    /**
     * 获取向量的长度.
     * |<strong>v</strong>| 在数学上.
     *
     * @return 长度.
     */
    double length();

    /**
     * 计算该向量与其他向量的点积.
     * |<strong>a</strong>||<strong>b</strong>|Cos&lt;<strong>a</strong>,<strong>b</strong>&gt; 在数学上.
     *
     * @param other 将要计算点积运算的另外一个向量.
     * @return 点积结果.
     */
    double dot(Vector other);

    /**
     * 复制该向量值并转化为数组 arrayToCopy.
     *
     * @param arrayToCopy 复制的数组.
     */
    void get(double[] arrayToCopy);

    /**
     * 将此向量的值给另外一个向量.
     *
     * @param other 将此向量的值给另外一个向量.
     */
    void set(Vector other);

    /**
     * 将该向量的值设置成一个数组的值.
     *
     * @param values 将被设置的值.
     */
    void set(double[] values);

    /**
     * 在一个特定的方向上设定这个向量.
     * @param direction 设定的方向.
     */
    void project(Vector direction);

    /**
     * 清除该向量在一定方向上的投影.
     * 清除之后，这个向量是垂直于该方向的.
     * @param direction 清除的方向.
     */
    void clearProject(Vector direction);
    /**
     * 创建并返回这个向量的副本.
     * “复制”的精确含义可能取决于这个向量的类.
     * 对于任意向量x，它的一般含义表达式是: <br>
     * x.clone() != x <br>
     * 将是真.
     *
     * @return 这个向量的副本.
     */
    Vector clone();
}
