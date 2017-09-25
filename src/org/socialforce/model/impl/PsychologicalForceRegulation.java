package org.socialforce.model.impl;

import org.socialforce.geom.Force;
import org.socialforce.model.*;

/**
 * 定义了PsychologicalForceRegulation类，其继承于父类TypeMatchRegulation
 * Created by Ledenel on 2016/8/19.
 */

public class PsychologicalForceRegulation extends TypeMatchRegulation<Blockable, Agent> {
    public static final double A = 2000;
    public static final double B = 0.08;

    /**
     *TODO regulate 是翻译成调节还是控制，还是计算的意思？
     *  计算两个agent之间的心理作用力。
     * @param agentClass
     * @param agentClass2
     * @param model
     */
    public PsychologicalForceRegulation(Class<Blockable> agentClass, Class<Agent> agentClass2, Model model) {
        super(agentClass, agentClass2, model);
    }


    /**
     *获取源实体和目标实体之间的作用力。
     * @param source
     * @param target
     * @return force
     */
    @Override
    public Force getForce(Blockable source, Agent target) {
        Force force = model.zeroForce();
        force.add((target.getPhysicalEntity()).directionTo(source.getPhysicalEntity()));
        double scale = A * Math.exp(- (target.getPhysicalEntity().distanceTo(source.getPhysicalEntity()) + 0.1)/ B);
        force.scale(scale / force.length());
        return force;
    }

    @Override
    public Class forceType() {
        return model.zeroForce().getClass();
    }
}
