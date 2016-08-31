package org.socialforce.model.impl;

import org.socialforce.geom.Shape;
import org.socialforce.model.Agent;
import org.socialforce.model.Blockable;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.SocialForceModel;

/**
 * Created by Ledenel on 2016/8/14.
 */
public class Wall extends Entity implements Blockable {
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
        if (affectedEntity instanceof Agent) {
            Agent agent = (Agent)affectedEntity;
            agent.push(model.calcualte(this,affectedEntity));
        }
    }

    public Wall(Shape shape) {
        super(shape);
    }

    public Shape getShape(){
        return shape;
    }

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
