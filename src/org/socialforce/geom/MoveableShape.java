package org.socialforce.geom;

/**DistanceShape（agent需要满足）的子类，MoveableShape（能够施加力矩的agent需要满足）
 * Created by Administrator on 2017/6/23 0023.
 */
public interface MoveableShape extends DistanceShape {
    /**
     * 逆时针旋转某个角度
     * @param angle 旋转的角度，为弧度制
     */
    void spin(double angle);
}
