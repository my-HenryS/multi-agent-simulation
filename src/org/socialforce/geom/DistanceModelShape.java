package org.socialforce.geom;

/**
 * 定义了一个与物体形状相关的距离接口DistanceShape,其继承于父类Shape接口
 */
public interface DistanceModelShape extends ModelShape {
    /**
     * 获取该形状与其他形状的距离.
     * 相交则为负
     * @param other 另一个形状
     * @return 距离.
     */
    double distanceTo(ModelShape other);

    /**
     * 获取该形状与其他形状的距离矢量.
     * 方向指向目标形状
     * @param other 另一个形状
     * @return 距离矢量.
     */
    Vector directionTo(ModelShape other);

    /**
     * 判断该形状是否与另一个形状严格相交。
     * @param other 另一个形状。
     * @return 相交时返回true；否则返回false。
     */
    boolean intersects(ModelShape other);

    /**
     * 创建并返回该形状的副本.
     * @return 该形状的副本.
     */
    DistanceModelShape clone();
}
