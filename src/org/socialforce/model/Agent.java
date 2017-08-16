package org.socialforce.model;

import org.socialforce.geom.DistancePhysicalEntity;
import org.socialforce.geom.Velocity;
import org.socialforce.strategy.Path;

/**
 * 定义了Multi-Agent System中的Agent接口，继承Affectable, Moveable, Blockable
 * 与只同时实现了Affectable和Affected的实体的区别在于Agent有自己的Path, 可以应用于寻路算法，并且有逃离场景的可能
 * @author Ledenel sunjh(edit)
 * Created by Ledenel on 2016/7/28.
 */
public interface Agent extends Influential, Moveable, Blockable,Rotateable {

    /**
     * 获取一个Agent的形状
     * 如圆等。
     *
     * @return 实体的形状.
     */
    DistancePhysicalEntity getPhysicalEntity();
    /**
     * 获取agent的路径
     *
     * @return 路径对象
     */
    Path getPath();

    /**
     *为agent设置路径
     *
     * @param path 要设置的路径
     */
    void setPath(Path path);

    /**
     * 标明已经逃离的agent
     */
    void escape();

    /**
     * 检查这个Agent是否已经逃离。
     * @return 是否已经逃离。
     */
    boolean isEscaped();

    /**
     * 因为Scene不会过滤Agent本身，所以Agent应该定义其如何影响自己
     */
    void selfAffect();

    /**
     * 获取加速度
     */

    Velocity getAcceleration();
}
