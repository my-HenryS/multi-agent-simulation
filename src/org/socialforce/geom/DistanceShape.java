package org.socialforce.geom;

public interface DistanceShape extends Shape {
    /**
     * 获取该形状与其他形状的距离.
     * @return 距离.
     */
    double distanceTo(Shape other);

    /**
     * 判断该形状是否与另一个形状严格相交。
     * @param other 另一个形状。
     * @return 相交时返回true；否则返回false。
     */
    boolean intersects(Shape other);
}
