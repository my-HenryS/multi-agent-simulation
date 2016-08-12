package org.socialforce.entity;

/**
 * represent all interactive entities in scene.
 *
 * @author Ledenel
 *         Created by Ledenel on 2016/7/28.
 * @see Scene
 * @see Pool
 * @see Agent
 */
public interface InteractiveEntity {
    /**
     * affect the target entity with this.
     * for example, walls can affect an agent (push them).
     *
     * @param affectedEntity the entity to be affected.
     * @see Agent
     * @see SocialForceModel
     */
    void affect(InteractiveEntity affectedEntity);

    /**
     * get the Shape of this entity.
     * line,square,circle etc.
     */
    Shape getShape();

    /**
     * get the total mass of the entity.
     * usually the mass is all on the shape's reference point.
     * @return the mass.
     */
    double getMass();

    /**
     * get the social force model the entity is using.
     * @return the model.
     */
    SocialForceModel getModel();

    /**
     * set the social force model.
     * @param model the model.
     */
    void setModel(SocialForceModel model);

    /**
     * place this entity on a specific point.
     * the shape will {@code moveTo} that point.
     * @param point the target point.
     */
    void placeOn(Point point);

}

