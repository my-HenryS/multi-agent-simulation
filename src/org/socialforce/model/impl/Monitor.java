package org.socialforce.model.impl;

import org.socialforce.container.impl.LinkListAgentPool;
import org.socialforce.geom.PhysicalEntity;
import org.socialforce.geom.impl.Vector2D;
import org.socialforce.model.Agent;
import org.socialforce.model.Influential;
import org.socialforce.scene.Scene;

/**
 * Created by sunjh1999 on 2017/1/21.
 */
public class Monitor extends Entity implements Influential {
    double rho, averV;
    LinkListAgentPool agents = new LinkListAgentPool();
    public Monitor(PhysicalEntity physicalEntity) {
        super(physicalEntity);
    }

    @Override
    public PhysicalEntity getView() {
        return this.getPhysicalEntity();
    }

    public void affect(Agent target) {
    }

    @Override
    public void affectAll(Iterable<Agent> affectableAgents) {
        double velocities = 0;
        int agentNum = 0;
        for(Agent agent:affectableAgents){
            if(physicalEntity.contains(agent.getPhysicalEntity().getReferencePoint())) {
                velocities += agent.getVelocity().length();
                agentNum++;
            }
        }
        averV = agentNum != 0 ? velocities / agentNum : 0;
        rho = agentNum / physicalEntity.getArea();
    }

    @Override
    public double getMass() {
        return 0;
    }

    @Override
    public Monitor clone() {
        return new Monitor(physicalEntity.clone());
    }

    public double sayVelocity(){
       return averV;
    }

    public double sayRho(){
        return rho;
    }

    @Override
    public boolean onAdded(Scene scene) {
        return true;
    }

    @Override
    public void onStep(Scene scene) {

    }
}
