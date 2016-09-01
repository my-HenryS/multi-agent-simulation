package org.socialforce.geom;

import org.socialforce.model.Agent;
import org.socialforce.model.Moveable;

/**
 * 代表速度向量.
 * @author Ledenel
 * @see Agent
 * @see Moveable
 * Created by Ledenel on 2016/7/30.
 */
public interface Velocity extends Vector {
    /**
     * 在特定的时间内累积的总距离.
     * @param time 累积的时间.
     * @return 距离增量的向量.
     */
    Vector deltaDistance(double time);

    /** 拷贝这个速度，创建并返回这个拷贝的速度.
     * 它是个向量.
     * @return 这个拷贝的速度.
     */
    Velocity clone();
}
