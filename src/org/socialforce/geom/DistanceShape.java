package org.socialforce.geom;

public interface DistanceShape extends Shape {
    /**
     * 获取该形状与其他形状的距离
     * @return 距离
     */
    double distanceTo(Shape other);
}
