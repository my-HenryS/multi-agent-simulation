package org.socialforce.entity.impl;

import org.socialforce.entity.*;

/**
 * Created by Ledenel on 2016/8/14.
 */
public class Wall implements InteractiveEntity {
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

    SocialForceModel model;

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    Shape shape;

    /**
     * get the Shape of this entity.
     * line, square, circle etc.
     *
     * @return the shape.
     */
    @Override
    public Shape getShape() {
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

    /**
     * get the social force model the entity is using.
     *
     * @return the model.
     */
    @Override
    public SocialForceModel getModel() {
        return model;
    }

    /**
     * set the social force model.
     *
     * @param model the model.
     */
    @Override
    public void setModel(SocialForceModel model) {
        this.model = model;
    }

    /**
     * place this entity on a specific point.
     * the shape will {@code moveTo} that point.
     *
     * @param point the target point.
     */
    @Override
    public void placeOn(Point point) {
        shape.moveTo(point);
    }
}
