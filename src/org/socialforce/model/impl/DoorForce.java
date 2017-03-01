package org.socialforce.model.impl;

import org.socialforce.geom.Force;
import org.socialforce.geom.impl.Force2D;
import org.socialforce.model.Agent;
import org.socialforce.model.ForceRegulation;
import org.socialforce.model.InteractiveEntity;

/**
 * 人对门的力，为一恒定力矩。
 * 门对人的力在bodyforce里面。
 * Created by Whatever on 2017/3/1.
 */
public class DoorForce implements ForceRegulation{
    /**
     * 判断源实体和目标实体之间是否有作用力。
     *
     * @param source
     * @param target
     * @return true 当源实体和目标实体之间是有作用力时返回真。
     */
    @Override
    public boolean hasForce(InteractiveEntity source, InteractiveEntity target) {
        if (source instanceof Agent && target instanceof Door){
            if (((Agent) source).getShape().distanceTo(target.getShape())<0.1){
            return true && source.getScene().getCurrentSteps() != 1;}
            return false;
        }
        else
            return false;
    }

    /**
     * 获取源实体和目标实体之间的作用力。
     *
     * @param interactiveEntity
     * @param interactiveEntity2
     * @return force
     */
    @Override
    public Force getForce(InteractiveEntity interactiveEntity, InteractiveEntity interactiveEntity2) {
        return new Force2D(1,1);
    }
}
