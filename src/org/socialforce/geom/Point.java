package org.socialforce.geom;

/**
 * 代表坐标系中的一个点.
 * @author Ledenel
 * @see Vector
 * Created by Ledenel on 2016/7/28.
 */
public interface Point extends Vector {
    
    /**
     * 获取该点的X坐标
     * @return X坐标
     */ 
    double getX();
    
    /**
     * 获取该点的Y坐标.
     * @return Y坐标
     */ 
    double getY();

    /**
     * 获取该点到另一个点的距离矢量.
     * 可以是多维度的距离.
     * 方向指向目标点
     * @param other 需要计算距离的另一个点
     * @return 距离.
     */
    Vector distanceTo(Point other);

    /**
     * 创建并返回这个点的副本.
     * @return 这个点的副本.
     */
    Point clone();

    Point moveTo(double x, double y);

    Point moveBy(double x, double y);

    Point scaleBy(double s);
}
