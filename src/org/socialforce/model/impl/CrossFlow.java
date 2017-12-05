package org.socialforce.model.impl;

import org.socialforce.geom.PhysicalEntity;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.model.Agent;
import org.socialforce.model.Influential;


/**
 * Created by Administrator on 2017/12/4 0004.
 */
public class CrossFlow extends Exit implements Influential {

    protected Box2D zone;

    public CrossFlow(Box2D box2D) {
        super(box2D);
        zone = box2D;
    }

    @Override
    public CrossFlow clone() {
        return new CrossFlow((Box2D) zone.clone());
    }

    @Override
    public PhysicalEntity getView() {
        return this.getPhysicalEntity();
    }

    @Override
    public void affect(Agent target) {
        target.push(model.interactAffection(this,target));
    }

    @Override
    public void affectAll(Iterable<Agent> affectableAgents) {
        for(Agent agent:affectableAgents){
            affect(agent);
        }
    }
}
