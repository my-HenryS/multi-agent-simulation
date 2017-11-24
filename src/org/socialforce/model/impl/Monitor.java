package org.socialforce.model.impl;

import org.socialforce.container.impl.LinkListAgentPool;
import org.socialforce.geom.PhysicalEntity;
import org.socialforce.model.Agent;
import org.socialforce.model.Influential;
import org.socialforce.scene.Scene;

/**
 * Created by sunjh1999 on 2017/1/21.
 */
public class Monitor extends Entity implements Influential {
    double volume = 0, velocity = 0,  timePerStep = 0;
    int vNum = 0, EC;
    int firstT = -1, lastT;
    LinkListAgentPool agents = new LinkListAgentPool();
    public Monitor(PhysicalEntity physicalEntity) {
        super(physicalEntity);
    }

    public void setTimePerStep(double timePerStep){
        this.timePerStep = timePerStep;
    }

    @Override
    public PhysicalEntity getView() {
        return this.getPhysicalEntity();
    }
    public void affect(Agent target) {
        velocity += target.getVelocity().length();
        vNum += 1;
        if(!agents.contains(target)){   //EC计数不复用Agent
            agents.addLast(target);
            EC += 1;
            if(firstT == -1) firstT = scene.getCurrentSteps();
            lastT = scene.getCurrentSteps();
        }
        volume += 1;
    }

    @Override
    public void affectAll(Iterable<Agent> affectableAgents) {
        for(Agent agent:affectableAgents){
            affect(agent);
        }
    }

    @Override
    public double getMass() {
        return 0;
    }

    @Override
    public Monitor clone() {
        return new Monitor(physicalEntity.clone());
    }

    public double sayVolume(){
       return volume/scene.getCurrentSteps();
    }

    public double sayVelocity(){
        return vNum > 0 ? velocity/vNum : 0;
    }

    public double sayEC(){
        return EC/(lastT - firstT);
    }

    @Override
    public boolean onAdded(Scene scene) {
        return false;
    }

    @Override
    public void onStep(Scene scene) {

    }
}
