package org.socialforce.model;

import org.socialforce.geom.DistanceShape;
import org.socialforce.geom.Shape;

/**
 * 可影响Agent的实体
 * @see Agent
 * Created by sunjh1999 on 2017/3/6.
 */
public interface Influential extends InteractiveEntity {
    /**
     * 获取一个Affectable的可影响范围。
     * 该Affectable只和位于该视域范围内的Affected进行交互
     * @return 一个表示该视域范围的形状
     * @see Shape
     */
    Shape getView();

    /**
     * 定义一个实体如何影响Agent
     * 若target能影响实体本身，则相应影响也在此处定义
     * @see Agent
     * @param target
     */
    void affect(Agent target);
}
