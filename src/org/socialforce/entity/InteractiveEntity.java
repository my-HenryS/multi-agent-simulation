package org.socialforce.entity;

/**
 * Created by Ledenel on 2016/7/28.
 */
public interface InteractiveEntity {
     /**
     * the prosses of stuff interacting with other stuff
     * such as agent, wall, fire etc.
     * @param affectedEntity could be any class of InteractiveEntity
     * if InteractiveEntity is Agent
     * @see Shape Force Agent
     */
    void affect(InteractiveEntity affectedEntity);
    /**
     * get the class of Shape
     * line,square,circle etc.
     */
    Shape getShape();
    double getMass();
    SocialForceModel getModel();
    void setModel(SocialForceModel model);
    void placeOn(Point point);

}

