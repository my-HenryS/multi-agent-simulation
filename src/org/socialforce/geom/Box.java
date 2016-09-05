package org.socialforce.geom;

/**
 * 表示一个box.
 * box是一种特殊的形状，它可以是其他形状的边界. <br>
 * 它通常是边界坐标平行的长方形或长方体.<br>
 * 一个起点和一个终点就可以定义一个box，定义如下: <br>
 * -----------.(end point)<br>
 * |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|<br>
 * |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|<br>
 * |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|<br>
 * .-----------<br>
 * (start point)
 *
 * @author Ledenel
 * @see Shape
 * Created by Ledenel on 2016/8/1 .
 */
public interface Box extends Shape,ClippableShape,ClipperShape,Expandable {
    /**
     * 获取box的起点.
     *
     * @return 起点.
     */
    Point getStartPoint();

    /**
     * 获取box的终点.
     *
     * @return 终点.
     */
    Point getEndPoint();

    /**
     * 获取box规模大小.
     *
     * @return 代表规模大小的向量.
     */
    Vector getSize();

    /**
     * 返回该box和其他box相交的部分.
     *
     * @param other 该box是相交的.
     * @return 相交的部分.
     */
    Box intersect(Box other);

}
