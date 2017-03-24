package org.socialforce.model.impl;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.socialforce.geom.Force;
import org.socialforce.geom.impl.Force2D;
import org.socialforce.model.Agent;
import org.socialforce.model.ForceRegulation;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.Model;

/**
 * 人对门的力，为一恒定力矩。
 * 注：门对人的力在bodyForce里面。
 * Created by Whatever on 2017/3/1.
 */
public class DoorForce extends TypeMatchRegulation<Agent,Door>{

    public DoorForce(Class<Agent> agentClass, Class<Door> doorClass, Model model) {
        super(agentClass, doorClass, model);
    }

    /**
     * 获取源实体和目标实体之间的作用力。
     *
     * @param interactiveEntity
     * @param interactiveEntity2
     * @return force
     */
    @Override
    public Force getForce(Agent interactiveEntity, Door interactiveEntity2) {
        return new Force2D(1,1);
    }
}
