package org.socialforce.geom.impl;

import org.socialforce.geom.Vector;
import org.socialforce.geom.Velocity;

/**
 * Created by Ledenel on 2016/8/16.
 */
public class
Velocity2D extends Vector2D implements Velocity, Vector {
    public Velocity2D(double x, double y) {
        super(x, y);
    }

    public Velocity2D() {
    }

    /**
     * 在一定的时间累积的总距离.
     * @param time 累积的时间.
     * @return 累积距离的向量.
     */

    @Override
    public Vector deltaDistance(double time) {
        return new Vector2D(values[0] * time, values[1] * time);
    }

    /**
     * 创建并返回这个向量的副本.
     * “复制”的精确含义可能取决于这个向量的类.
     * 对于任意向量x，它的一般含义表达式是:  <br>
     * x.clone() != x <br>
     * 将为真.
     *
     * @return 向量的副本.
     */
    @Override
    public Velocity clone() {
        Velocity2D cloned = new Velocity2D();
        cloned.values[0] = values[0];
        cloned.values[1] = values[1];
        return cloned;
    }

    /**
     * 返回
     * @return
     */
    @Override
    public String toString(){
        return "速度为：（"+ String.format("%.3f",values[0]) +","+ String.format("%.3f",values[1]) +")";
    }
}
