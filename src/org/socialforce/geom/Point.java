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
     * 获取该点到另一个点的距离.
     * @param other 需要计算距离的另一个点
     * @return 距离.
     */
    double distanceTo(Point other);

    /**
     * 获取该点到另一个点的距离矢量.
     * 可以是多维度的距离.
     * 方向指向目标点
     * @param other 需要计算距离的另一个点
     * @return 距离.
     */
    Vector directionTo(Point other);

    /**
     * 创建并返回这个点的副本.
     * @return 这个点的副本.
     */
    Point clone();

    /**
     * 将该点移动到目标位置
     * @param x x轴坐标
     * @param y y轴坐标
     * @return 移动后的点.
     */
    Point moveTo(double x, double y);

    /**
     * 将该点根据偏移量移动到目标位置
     * @param x x轴坐标偏差
     * @param y y轴坐标偏差
     * @return 移动后的点.
     */
    Point moveBy(double x, double y);

    /**
     * 将该点根据乘数移动到目标位置
     * @param s 乘数
     * @return 移动后的点.
     */
    Point scaleBy(double s);
}
