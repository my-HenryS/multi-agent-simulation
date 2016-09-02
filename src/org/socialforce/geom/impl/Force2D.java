package org.socialforce.geom.impl;

import org.socialforce.geom.Force;
import org.socialforce.geom.Velocity;

/**
 * Created by Ledenel on 2016/8/16.
 */
public class Force2D extends Vector2D implements Force {
    public Force2D() {
    }

    public Force2D(double x, double y) {
        super(x, y);
    }

    /**
     * 累积的力和获取速度的增量 .
     *
     * @param mass 实体的质量.
     * @param time 累积的时间.
     * @return 速度的增量.
     */
    @Override
    public Velocity deltaVelocity(double mass, double time) {
        double scale = time / mass;
        return new Velocity2D(values[0] * scale, values[1] * scale);
    }

    /**
     * 创建并返回此向量的副本.
     * “复制”的确切含义可能取决于向量的类.
     * 对于任意向量x，它的一般含义表达式是: <br>
     * x.clone() != x <br>
     * 将是真.
     *
     * @return 向量的副本.
     */
    @Override
    public Force clone() {
        Force2D force = new Force2D();
        force.values[0] = values[0];
        force.values[1] = values[1];
        return force;
    }
}
