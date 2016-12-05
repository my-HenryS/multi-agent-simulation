package org.socialforce.model.impl;

import org.socialforce.geom.Force;
import org.socialforce.model.*;

/**
 * 定义了PsychologicalForceRegulation类，其继承于父类TypeMatchRegulation
 * Created by Ledenel on 2016/8/19.
 */

public class PsychologicalForceRegulation extends TypeMatchRegulation<InteractiveEntity, Agent> {

    public static final double A = 1000;
    public static final double B = 0.18;

    /**
     *TODO regulate 是翻译成调节还是控制，还是计算的意思？
     *  计算两个agent之间的心理作用力。
     * @param agentClass
     * @param agentClass2
     * @param model
     * return force
     */
    public PsychologicalForceRegulation(Class<InteractiveEntity> agentClass, Class<Agent> agentClass2, SocialForceModel model) {
        super(agentClass, agentClass2, model);
    }

    /**
     * 判断两个agent之间是否有心理作用力。
     * @param source
     * @param target
     * @return tree  如果两个agent之间是有心理作用力则返回真。
     */
    @Override
    public boolean hasForce(InteractiveEntity source, InteractiveEntity target) {
        return super.hasForce(source, target) && source instanceof Blockable &&
        ((Agent) target).getView().intersects(source.getShape());
    }

    /**
     *获取源实体和目标实体之间的作用力。
     * @param source
     * @param target
     * @return force
     */
    @Override
    public Force getForce(InteractiveEntity source, Agent target) {
        Force force = model.zeroForce();
        force.add((target.getShape()).directionTo(source.getShape()));
        double scale = A * Math.exp(- target.getShape().distanceTo(source.getShape()) / B);
        force.scale(scale / force.length());
        return force;
    }
}
