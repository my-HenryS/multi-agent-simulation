package org.socialforce.model.impl;

import org.socialforce.geom.PhysicalEntity;
import org.socialforce.model.*;
import org.socialforce.scene.Scene;

/**
 * Created by Ledenel on 2016/8/14.
 */
public class Wall extends Entity implements Blockable, Influential {
    @Override
    public PhysicalEntity getView() {
        return (this.getPhysicalEntity().clone()).expandBy(3);
    }

    /**
     * 当前this所影响的实体
     * 例如，墙会影响agent(反作用，反推)
     * @param target 被影响的实体
     * @see Agent
     * @see Model
     */

    public void affect(Agent target) {
        target.push(model.interactAffection(this,target));
    }

    @Override
    public void affectAll(Iterable<Agent> affectableAgents) {
        for(Agent agent:affectableAgents){
            affect(agent);
        }
    }


    public Wall(PhysicalEntity physicalEntity) {
        super(physicalEntity);
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

    @Override
    public Wall clone() {
        return new Wall(physicalEntity.clone());
    }

    @Override
    public PhysicalEntity blockSize() {
        return this.getPhysicalEntity().clone();
    }

    @Override
    public boolean onAdded(Scene scene) {
        return true;
    }

    @Override
    public void onStep(Scene scene) {

    }
}
