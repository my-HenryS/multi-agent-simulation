package org.socialforce.geom;

/**
 * 定义了一个与物体形状相关的距离接口DistanceShape,其继承于父类Shape接口
 */
public interface DistanceShape extends Shape {
    /**
     * TODO谁目标形状的距离？
     * 获取到目标形状的距离
     * @return 到目标形状的距离
     */
    double distanceTo(Shape other);

    /**
     * 判断该形状是否与另一个形状严格相交。
     * @param other 另一个形状。
     * @return 相交时返回true；否则返回false。
     */
    boolean intersects(Shape other);
}
