package org.socialforce.model.impl;

import org.socialforce.app.*;
import org.socialforce.geom.Shape;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.SocialForceModel;

/**
 * Created by Ledenel on 2016/8/14.
 */
public class SafetyRegion extends Entity implements InteractiveEntity {
    public SafetyRegion(Shape shape) {
        super(shape);
    }
    public Shape getShape(){
        return shape;
    }
    /**
     * affect the target entity with this.
     * for example, walls can affect an agent (push them).
     *
     * @param affectedEntity the entity to be affected.
     * @see Agent
     * @see SocialForceModel
     */
    @Override
    public void affect(InteractiveEntity affectedEntity) {
        if(affectedEntity instanceof Agent) {
            Agent agent = (Agent)affectedEntity;
            agent.push(model.calcualte(this,agent));
            if(shape.contains(agent.getShape().getReferencePoint())) {
                //agent exited.
                if(scene != null) {
                    scene.onAgentEscape(agent); // add valid scene.
                }
            }
        }
    }

    /*
     * get the listener of the application
     * @return

    public ApplicationListener getListener() {
        return listener;
    }

    /*
     * set the listener of the application
     * @param listener

    public void setListener(ApplicationListener listener) {
        this.listener = listener;
    }

    ApplicationListener listener;
*/
    /**
     * get the total mass of the entity.
     * usually the mass is all on the shape's reference point.
     *
     * @return the mass.
     */
    @Override
    public double getMass() {
        return Double.POSITIVE_INFINITY;
    }
}
