package org.socialforce.entity.impl;

import org.socialforce.entity.Agent;
import org.socialforce.entity.Force;
import org.socialforce.entity.SocialForceModel;

/**
 * Created by Ledenel on 2016/8/19.
 */
public class PsychologicalForceRegulation extends TypeMatchRegulation<Agent, Agent> {

    public static final double A = 0.3;
    public static final double B = 0.18;

    public PsychologicalForceRegulation(Class<Agent> agentClass, Class<Agent> agentClass2, SocialForceModel model) {
        super(agentClass, agentClass2, model);
    }

    @Override
    public Force getForce(Agent source, Agent target) {
        Force force = model.zeroForce();
        force.add(target.getShape().getReferencePoint());
        force.sub(source.getShape().getReferencePoint());
        double scale = A * Math.exp(source.getShape().distanceTo(target.getShape()) / B);
        force.scale(scale / force.length());
        return force;
    }
}
