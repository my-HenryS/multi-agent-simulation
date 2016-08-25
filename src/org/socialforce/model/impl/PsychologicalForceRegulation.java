package org.socialforce.model.impl;

import org.socialforce.geom.Force;
import org.socialforce.model.*;

/**
 * Created by Ledenel on 2016/8/19.
 */

public class PsychologicalForceRegulation extends TypeMatchRegulation<InteractiveEntity, Agent> {

    public static final double A = 0.3;
    public static final double B = 0.18;

    /**
     * regualate(or caclculate) the psychological force between two agent
     * @param agentClass
     * @param agentClass2
     * @param model
     * @return force
     */
    public PsychologicalForceRegulation(Class<InteractiveEntity> agentClass, Class<Agent> agentClass2, SocialForceModel model) {
        super(agentClass, agentClass2, model);
    }

    /**
     * judge if there has psychological force between two agent(????应该得区别于ForceRegulation 中的 hasForce)
     * @param source
     * @param target
     * @return tree if  there has psychological force between two agent.
     */
    @Override
    public boolean hasForce(InteractiveEntity source, InteractiveEntity target) {
        return super.hasForce(source, target) && source instanceof Blockable;
    }

    /**
     *  get the psychological force between  source and target
     * @param source
     * @param target
     * @return force
     */
    @Override
    public Force getForce(InteractiveEntity source, Agent target) {
        Force force = model.zeroForce();
        force.add(target.getShape().getReferencePoint());
        force.sub(source.getShape().getReferencePoint());
        double scale = A * Math.exp(target.getShape().distanceTo(source.getShape()) / B);
        force.scale(scale / force.length());
        return force;
    }
}
