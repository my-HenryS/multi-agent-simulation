package org.socialforce.model.impl;

import org.socialforce.geom.Shape;
import org.socialforce.model.Agent;
import org.socialforce.model.Blockable;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.SocialForceModel;

/**
 * Created by Ledenel on 2016/8/14.
 */
public class Wall extends Entity implements Blockable {
    /**
     * 当前this所影响的实体
     * 例如，墙会影响agent(反作用，反推)
     * @param affectedEntity 被影响的实体
     * @see Agent
     * @see SocialForceModel
     */
    @Override
    public void affect(InteractiveEntity affectedEntity) {
        if (affectedEntity instanceof Agent) {
            Agent agent = (Agent)affectedEntity;
            agent.push(model.calcualte(this,affectedEntity));
        }
    }

    public Wall(Shape shape) {
        super(shape);
    }
    public Shape getShape(){
        return shape;
    }
    /**
     * 获取实体的质量。
     * 通常质量位于形状的参考点上（或者是位于质心上）
     * @return the mass.
     */
    @Override
    public double getMass() {
        return Double.POSITIVE_INFINITY;
    }

}
