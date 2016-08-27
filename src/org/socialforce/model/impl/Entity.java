package org.socialforce.model.impl;

import org.socialforce.model.InteractiveEntity;
import org.socialforce.geom.Point;
import org.socialforce.geom.Shape;
import org.socialforce.model.SocialForceModel;

/**
 * Created by Ledenel on 2016/8/14.
 */
public abstract class Entity implements InteractiveEntity {
    protected SocialForceModel model;
    protected Shape shape;

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     *  set the name of the entity
     * @param name
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    protected String name;

    /**
     *
     * @param shape
     */
    public Entity(Shape shape) {
        this.shape = shape;
    }

    /**
     *
     * @param model
     * @param shape
     */
    public Entity(SocialForceModel model, Shape shape) {
        this.model = model;
        this.shape = shape;
    }

    /**
     * set the Shape of the entity
     * @param shape
     */
    public void setShape(Shape shape) {
        this.shape = shape;
    }

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
