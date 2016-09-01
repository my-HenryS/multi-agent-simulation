package org.socialforce.model.impl;

import org.socialforce.app.*;
import org.socialforce.geom.Shape;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.SocialForceModel;

/**
 * 定义Gate类，其继承于父类Entity， 并实现了接口InteractiveEntity 的方法
 *  * Created by Ledenel on 2016/8/14.
 */
public class Gate extends Entity implements InteractiveEntity {
    public Gate(Shape shape) {
        super(shape);
    }

    /**
     * 当前this所影响的实体
     * 例如，墙会影响agent(反作用，反推)
     * @param affectedEntity 被影响的实体
     * @see Agent
     * @see SocialForceModel
     */
    @Override
    public void affect(InteractiveEntity affectedEntity) {
        if(affectedEntity instanceof Agent) {
            Agent agent = (Agent)affectedEntity;
            agent.push(model.calcualte(this,agent));
            if(shape.contains(agent.getShape().getReferencePoint())) {
                //agent exited.
                if(listener != null) {
                    listener.onAgentEscape(null,agent); //FIXME: add valid scene.
                }
            }
        }
    }

    /**
     * 获取应用的监听器。
     * @return listener 监听器
     */
    public ApplicationListener getListener() {
        return listener;
    }

    /**
     * 设置应用的监听器。
     * @param listener 监听器
     */
    public void setListener(ApplicationListener listener) {
        this.listener = listener;
    }

    ApplicationListener listener;

    /**
     * 获取实体的质量。
     * 通常质量位于形状的参考点上（或者是位于质心上）
     * @return POSITIVE_INFINITY.
     */
    @Override
    public double getMass() {
        return Double.POSITIVE_INFINITY;
    }
}
