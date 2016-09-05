package org.socialforce.model;

import org.socialforce.geom.Force;
import org.socialforce.geom.Point;
import org.socialforce.geom.Velocity;

/**
 * 表示可以移动的实体。
 *
 * @see Agent
 * Created by Ledenel on 2016/7/30.
 */
public interface Moveable {
    /**
     * 获取移动实体的速度。.
     *
     * @return currVelocity。 当前速度
     */
    Velocity getVelocity();

    /**
     * 将实体以一定大小的力推向目标点。
     *
     * @param force 推时力的大小
     */
    void push(Force force);

    /**
     * 用大小为force的力推位于特定位置上的点。
     * 该方法还可以使实体旋转。
     *
     * @param force      推力大小
     * @param startPoint 力作用的位置。
     */
    void push(Force force, Point startPoint);
}
