package org.socialforce.entity.impl;

import org.socialforce.entity.Agent;
import org.socialforce.entity.Force;
import org.socialforce.entity.SocialForceModel;

/**
 * Created by Ledenel on 2016/8/19.
 */
public class PsychologicalForceRegulation extends TypeMatchRegulation<Agent, Agent> {

    public static final double A = 1.0;
    public static final double B = 1.0;

    public PsychologicalForceRegulation(Class<Agent> agentClass, Class<Agent> agentClass2, SocialForceModel model) {
        super(agentClass, agentClass2, model);
    }

    @Override
    public Force getForce(Agent source, Agent target) {
        return model.zeroForce();
    }
}
